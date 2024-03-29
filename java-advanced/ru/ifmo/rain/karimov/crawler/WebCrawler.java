package ru.ifmo.rain.karimov.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements Crawler {
    private final Downloader downloader;
    private final ExecutorService downloaderPool;
    private final ExecutorService extractorPool;
    private final ConcurrentMap<String, HostDownloader> hostMapper;
    private final int perHost;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloaderPool = Executors.newFixedThreadPool(downloaders);
        this.extractorPool = Executors.newFixedThreadPool(extractors);
        this.hostMapper = new ConcurrentHashMap<>();
        this.perHost = perHost;
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0 || Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.err.println("Expected not null arguments and at least one argument");
        } else {
            try {
                try (Crawler crawler = new WebCrawler(new CachingDownloader(), getArg(args, 2),
                        getArg(args, 3), getArg(args, 4))) {

                    crawler.download(args[0], getArg(args, 1));
                }
            } catch (NumberFormatException e) {
                System.err.println("Expected numbers in arguments");
            } catch (IOException e) {
                System.err.println("Unable to initialize downloader: " + e.getMessage());
            }
        }
    }

    private static int getArg(String[] args, int index) {
        return index >= args.length ? 1 : Integer.parseInt(args[index]);
    }

    @Override
    public Result download(String url, int depth) {
        return new Worker(url, depth).getResult();
    }

    private class HostDownloader {
        private final Queue<Runnable> waitingTasks;
        private int currentRunning;

        HostDownloader() {
            waitingTasks = new ArrayDeque<>();
            currentRunning = 0;
        }

        synchronized private void checkAndRun(boolean finished) {
            if (finished) {
                currentRunning--;
            }
            if (currentRunning < perHost) {
                runNext();
            }
        }

        synchronized private void runNext() {
            Runnable task = waitingTasks.poll();
            if (task != null) {
                currentRunning++;
                downloaderPool.submit(() -> {
                    try {
                        task.run();
                    } finally {
                        checkAndRun(true);
                    }
                });
            }
        }

        synchronized void addTask(Runnable task) {
            waitingTasks.add(task);
            checkAndRun(false);
        }

    }

    private class Worker {
        private final Set<String> result;
        private final ConcurrentMap<String, IOException> exceptions;
        private final ConcurrentLinkedQueue<String> awaits;
        private final Set<String> extracted;
        private final Phaser lock;

        Worker(String url, int depth) {
            result = ConcurrentHashMap.newKeySet();
            exceptions = new ConcurrentHashMap<>();
            awaits = new ConcurrentLinkedQueue<>();
            extracted = ConcurrentHashMap.newKeySet();
            lock = new Phaser(1);
            awaits.add(url);
            run(depth);
        }

        private void run(int depth) {
            lock.register();
            for (int d = 0; d < depth; d++) {
                final int finalDepth = depth - d;
                final Phaser level = new Phaser(1);
                List<String> processing = new ArrayList<>(awaits);
                awaits.clear();
                processing.stream().filter(extracted::add)
                        .forEach(link -> download(link, finalDepth, level));
                level.arriveAndAwaitAdvance();
            }
            lock.arrive();
        }

        private void download(String link, int depth, Phaser level) {
            String host;
            try {
                host = URLUtils.getHost(link);
            } catch (MalformedURLException e) {
                exceptions.put(link, e);
                return;
            }
            HostDownloader hostDownloader = hostMapper.computeIfAbsent(host, t -> new HostDownloader());
            level.register();
            hostDownloader.addTask(() -> {
                try {
                    Document document = downloader.download(link);
                    result.add(link);
                    if (depth > 1) {
                        extract(document, level);
                    }
                } catch (IOException e) {
                    exceptions.put(link, e);
                } finally {
                    level.arrive();
                }
            });
        }

        private void extract(Document document, Phaser level) {
            level.register();
            extractorPool.submit(() -> {
                try {
                    List<String> extractLinks = document.extractLinks();
                    awaits.addAll(extractLinks);
                } catch (IOException ignored) {
                } finally {
                    level.arrive();
                }
            });
        }

        Result getResult() {
            lock.arriveAndAwaitAdvance();
            return new Result(new ArrayList<>(result), exceptions);
        }

    }

    @Override
    public void close() {
        extractorPool.shutdown();
        downloaderPool.shutdown();
        try {
            extractorPool.awaitTermination(0, TimeUnit.MILLISECONDS);
            downloaderPool.awaitTermination(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.err.println("Couldn't terminate executor pools: " + e.getMessage());
        }
    }
}
