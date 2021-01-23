package ru.ifmo.rain.karimov.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {
    private final ReverseViewArrayList<E> elements;
    private final Comparator<? super E> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    public ArraySet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }


    public ArraySet(Collection<? extends E> collection) {
        this(collection, null);
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        NavigableSet<E> temp = new TreeSet<>(comparator);
        temp.addAll(collection);
        elements = new ReverseViewArrayList<>(temp);
        this.comparator = comparator;
    }

    @Override
    public boolean contains(Object o) {
        return Collections.binarySearch(elements, (E) o, comparator) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public E lower(E element) {
        return getOrNull(indexOf(element, false, true));
    }

    @Override
    public E floor(E element) {
        return getOrNull(indexOf(element, true, true));
    }

    @Override
    public E higher(E element) {
        return getOrNull(indexOf(element, false, false));
    }

    @Override
    public E ceiling(E element) {
        return getOrNull(indexOf(element, true, false));
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArraySet<E>(new ReverseViewArrayList<>(elements, true), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }
        return subSetImplementation(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        if (isEmpty()) {
            return this;
        }
        return subSetImplementation(first(), true, toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        if (isEmpty()) {
            return this;
        }
        return subSetImplementation(fromElement, inclusive, last(), true);
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return elements.get(0);
        }
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return elements.get(size() - 1);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    // lower: -1 => e less than all elements
    // upper: size => e greater than all elements
    private int indexOf(E element, boolean inclusive, boolean lower) {
        int index = Collections.binarySearch(elements, element, comparator);
        if (index < 0) {
            return lower ? (-index - 1 - 1) : (-index - 1);
        } else {
            return inclusive ? index : (lower ? (index - 1) : (index + 1));
        }
    }

    private E getOrNull(int index) {
        return (0 <= index && index < size()) ? elements.get(index) : null;
    }

    private NavigableSet<E> subSetImplementation(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        int fromIndex = indexOf(fromElement, fromInclusive, false);
        int toIndex = indexOf(toElement, toInclusive, true);

        if (fromIndex > toIndex) {
            return new ArraySet<>(comparator);
        }
        return new ArraySet<E>(elements.subList(fromIndex, toIndex + 1), comparator);
    }

    private int compare(E e1, E e2) {
        return (comparator == null) ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    private class ReverseViewArrayList<T> extends AbstractList<T> implements RandomAccess {
        List<T> list;
        boolean isReversed;

        ReverseViewArrayList(Collection<T> collection) {
            this.list = List.copyOf(collection);
            this.isReversed = false;
        }

        ReverseViewArrayList(List<T> list) {
            this.list = Collections.unmodifiableList(list);
            this.isReversed = false;
        }

        ReverseViewArrayList(ReverseViewArrayList<T> reverseArray, boolean isReversed) {
            this.list = reverseArray.list;
            this.isReversed = reverseArray.isReversed ^ isReversed;
        }

        private int flippedIndex(int index) {
            return size() - 1 - index;
        }

        @Override
        public ReverseViewArrayList<T> subList(int fromIndex, int toIndex) {
            if (isReversed) {
                return new ReverseViewArrayList<>(list.subList(flippedIndex(toIndex - 1), flippedIndex(fromIndex) + 1));
            } else {
                return new ReverseViewArrayList<>(list.subList(fromIndex, toIndex));
            }
        }

        public void reverse() {
            isReversed ^= true;
        }

        @Override
        public T get(int index) {
            return list.get(isReversed ? flippedIndex(index) : index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }

    private ArraySet(ReverseViewArrayList<E> reversibleArray, Comparator<? super E> comparator) {
        this.elements = reversibleArray;
        this.comparator = comparator;
    }

}
