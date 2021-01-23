package ru.ifmo.rain.karimov.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation class for {@link ListIP} interface
 */
public class IterativeParallelism implements ListIP {
    private final ParallelMapper parallelMapper;

    public IterativeParallelism(ParallelMapper parallelMapper) {
        this.parallelMapper = parallelMapper;
    }

    public IterativeParallelism() {
        parallelMapper = null;
    }

    /**
     * Joins values to string.
     *
     * @param threads number of concurrent threads
     * @param values  values to join
     * @return list of joined result of {@link #toString()} call on each value
     * @throws InterruptedException if executing thread was interrupted
     */
    @Override
    public String join(int threads, List<?> values) throws InterruptedException {
        return solveTask(threads, values,
                stream -> stream.map(Object::toString).collect(Collectors.joining()),
                stream -> stream.collect(Collectors.joining()));
    }

    /**
     * Filters values by predicate.
     *
     * @param threads   number of concurrent threads
     * @param values    values to filter
     * @param predicate filter predicate
     * @return list of values satisfying given predicated. Order of values is preserved
     * @throws InterruptedException if executing thread was interrupted
     */
    @Override
    public <T> List<T> filter(int threads, List<? extends T> values,
                              Predicate<? super T> predicate) throws InterruptedException {
        return solveTask(threads, values,
                stream -> stream.filter(predicate).collect(Collectors.toList()),
                stream -> stream.flatMap(List::stream).collect(Collectors.toList()));
    }

    /**
     * Maps values.
     *
     * @param threads number of concurrent threads
     * @param values  values to map
     * @param f       mapper function
     * @return list of values mapped by given function
     * @throws InterruptedException if executing thread was interrupted
     */
    @Override
    public <T, U> List<U> map(int threads, List<? extends T> values,
                              Function<? super T, ? extends U> f) throws InterruptedException {
        return solveTask(threads, values,
                stream -> stream.map(f).collect(Collectors.toList()),
                stream -> stream.flatMap(List::stream).collect(Collectors.toList()));
    }

    /**
     * Returns maximum value.
     *
     * @param threads    number or concurrent threads
     * @param values     values to get maximum of
     * @param comparator value comparator
     * @param <T>        value type
     * @return maximum of given values
     * @throws InterruptedException             if executing thread was interrupted
     * @throws java.util.NoSuchElementException if no values are given
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values,
                         Comparator<? super T> comparator) throws InterruptedException {
        return solveTask(threads, values,
                stream -> stream.max(comparator).orElse(null),
                stream -> stream.max(comparator).orElse(null));
    }

    /**
     * Returns maximum value.
     *
     * @param threads    number or concurrent threads
     * @param values     values to get maximum of
     * @param comparator value comparator
     * @param <T>        value type
     * @return maximum of given values
     * @throws InterruptedException             if executing thread was interrupted
     * @throws java.util.NoSuchElementException if no values are given
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values,
                         Comparator<? super T> comparator) throws InterruptedException {
        return solveTask(threads, values,
                stream -> stream.min(comparator).orElse(null),
                stream -> stream.min(comparator).orElse(null));
    }

    /**
     * Checks if all values satisfy given predicate.
     *
     * @param threads   number or concurrent threads
     * @param values    values to check predicate on
     * @param predicate values predicate
     * @param <T>       value type
     * @return {@code boolean} value indicating if all values satisfy given predicate
     * @throws InterruptedException if executing thread was interrupted
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values,
                           Predicate<? super T> predicate) throws InterruptedException {
        return solveTask(threads, values, stream -> stream.allMatch(predicate), stream -> stream.allMatch(Boolean::booleanValue));
    }

    /**
     * Checks if any value satisfies given predicate.
     *
     * @param threads   number or concurrent threads
     * @param values    values to check predicate on
     * @param predicate values predicate
     * @param <T>       value type
     * @return {@code boolean} value indicating if any value satisfies given predicate
     * @throws InterruptedException if executing thread was interrupted
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values,
                           Predicate<? super T> predicate) throws InterruptedException {
        return solveTask(threads, values, stream -> stream.anyMatch(predicate), stream -> stream.anyMatch(Boolean::booleanValue));
    }


    private <T, U> U solveTask(int threads, List<? extends T> values,
                               Function<? super Stream<? extends T>, ? extends U> f,
                               Function<? super Stream<? extends U>, ? extends U> collector) throws InterruptedException {
        List<Stream<? extends T>> splittedTasks = split(threads, values);
        List<InterruptedException> exceptions = new ArrayList<>();
        List<U> result;

        if (parallelMapper == null) {
            result = createAndStartThreads(f, splittedTasks, exceptions);
        } else {
            result = parallelMapper.map(f, splittedTasks);
        }

        if (!exceptions.isEmpty()) {
            throw exceptions.get(0);
        }
        return collector.apply(result.stream());
    }


    private <T, U> List<U> createAndStartThreads(Function<? super Stream<? extends T>, ? extends U> f,
                                                 List<Stream<? extends T>> splittedTasks,
                                                 List<InterruptedException> exceptions) {
        List<Thread> threadList = new ArrayList<>();
        List<U> answer = new ArrayList<>(Collections.nCopies(splittedTasks.size(), null));

        for (int i = 0; i < splittedTasks.size(); i++) {
            final int finalI = i;
            threadList.add(new Thread(() -> answer.set(finalI, f.apply(splittedTasks.get(finalI)))));
        }

        threadList.forEach(Thread::start);
        threadList.forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                exceptions.add(e);
            }
        }));
        return answer;
    }

    private <T> List<Stream<? extends T>> split(int threads, List<? extends T> values) {
        List<Stream<? extends T>> parts = new ArrayList<>();
        int step = values.size() / threads;
        int tail = values.size() % threads;

        int pos = 0;
        for (int i = 0; i < threads; i++) {
            int curr = step;
            if (tail > 0) {
                tail--;
                curr++;
            }
            if (curr > 0) {
                parts.add(values.subList(pos, pos + curr).stream());
            }
            pos += curr;
        }
        return parts;
    }
}
