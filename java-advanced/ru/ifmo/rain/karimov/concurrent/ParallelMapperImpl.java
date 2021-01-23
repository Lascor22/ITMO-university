package ru.ifmo.rain.karimov.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

/**
 * Implementation class for {@link ParallelMapper} interface
 */
public class ParallelMapperImpl implements ParallelMapper {
    private final Queue<Runnable> tasks;
    private final List<Thread> workers;

    /**
     * Thread-count constructor.
     * Creates a ParallelMapperImpl instance operating with maximum of {@code threads}
     * threads of type {@link Thread}.
     *
     * @param threads maximum count of operable threads
     */
    public ParallelMapperImpl(int threads) {
        tasks = new ArrayDeque<>();
        workers = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            workers.add(new Thread(new Worker()));
        }
        workers.forEach(Thread::start);
    }

    /**
     * Maps function {@code f} over specified {@code args}.
     * Mapping for each element performs in parallel.
     *
     * @param f    mapping function
     * @param args arguments for mapping function
     * @param <T>  type of arguments
     * @param <R>  type of resulting values
     * @return {@link List} of mapping results
     * @throws InterruptedException if some of the threads were interrupted during execution
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        ResultCollector<R> collector = new ResultCollector<>(args.size());
        for (int i = 0; i < args.size(); i++) {
            final int ind = i;
            addTask(() -> collector.setData(ind, f.apply(args.get(ind))));
        }
        return collector.getResult();
    }

    private void addTask(Runnable task) throws InterruptedException {

        synchronized (tasks) {
            int MAX_SIZE = 1000;
            while (tasks.size() == MAX_SIZE) {
                tasks.wait();
            }
            tasks.add(task);
            tasks.notifyAll();
        }
    }

    /**
     * Stops all threads. All unfinished mappings leave in undefined state.
     */
    @Override
    public void close() {
        workers.forEach(Thread::interrupt);
        workers.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        });
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Runnable task;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        task = tasks.poll();
                        tasks.notifyAll();
                    }
                    task.run();
                }
            } catch (InterruptedException ignored) {
            } finally {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class ResultCollector<R> {
        private final List<R> result;
        private int count;

        ResultCollector(final int size) {
            result = new ArrayList<>(Collections.nCopies(size, null));
            count = 0;
        }

        void setData(final int pos, R data) {
            result.set(pos, data);
            synchronized (this) {
                count++;
                if (count == result.size()) {
                    notify();
                }
            }
        }

        synchronized List<R> getResult() throws InterruptedException {
            while (count < result.size()) {
                wait();
            }
            return result;
        }
    }

}
