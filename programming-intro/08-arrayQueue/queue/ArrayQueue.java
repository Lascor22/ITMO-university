package queue;

public class ArrayQueue {
    // Inv: (n >= 0) & (a[i] != null  ∀i ∈ [1, n)
    private final int QUEUE_SIZE = 16;
    private Object[] elements = new Object[QUEUE_SIZE];
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    // Pre:
    private int getCapacity() {
        return elements.length;
    }
    // Post: res = |elements|

    // Pre: capacity >= 0
    private void ensureCapacity(int capacity) {
        if (getCapacity() <= capacity || getCapacity() > capacity * 4) {
            elements = toArray();
            head = 0;
            tail = getCapacity();
            Object[] newElements = new Object[capacity * 2 + 1];
            System.arraycopy(elements, 0, newElements, 0, getCapacity());
            elements = newElements;
        }
    }
    // Post: capacity < |elements| <= capacity * 4

    // Pre: (|elements != 0) && (0 <= x < |elements|)
    private int dec(int x) {
        if (x == 0) {
            return getCapacity() - 1;
        } else {
            return x - 1;
        }
    }
    // Post (result = x - 1 && x > 0) || (result = |element| && x == 0)

    // Pre: (|elements != 0) && (0 <= x < |elements|)
    private int inc(int x) {
        if (x + 1 == getCapacity()) {
            return 0;
        } else {
            return x + 1;
        }
    }
    // result == (x + 1) % |elements|

    // Pre:
    private Object[] toArray() {
        Object newElements[];
        if (head <= tail) {
            newElements = new Object[tail - head];
            System.arraycopy(elements, head, newElements, 0, tail - head);
        } else {
            newElements = new Object[getCapacity() + tail - head];
            System.arraycopy(elements, head, newElements, 0, getCapacity() - head);
            System.arraycopy(elements, 0, newElements, getCapacity() - head, tail);
        }
        return newElements;
    }
    // Post: result == a[];

    // Pre: n > 0
    private void popBack() {
        assert size > 0;
        tail = dec(tail);
        elements[tail] = null;
        size--;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] ∀i ∈ [0, n - 1)

    // Pre: n > 0
    private void popFront() {
        assert size > 0;
        elements[head] = null;
        head = inc(head);
        size--;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] ∀i ∈ [1, n)

    // Pre: n > 0
    public Object element() {
        assert size > 0;
        return elements[head];
    }
    // Post: result == a[0]

    // Pre: elem != null
    public void enqueue(Object element) {
        assert element != null;
        size++;
        ensureCapacity(size);
        elements[tail] = element;
        tail = inc(tail);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == element)

    // Pre: n > 0
    public Object dequeue() {
        assert size > 0;
        Object temp = element();
        popFront();
        return temp;
    }
    // Post: (n' == n - 1) && (a'[i - 1] = a[i] ∀i ∈ [1, n)) && (result == a[0])

    // Pre:
    public int size() {
        return size;
    }
    // Post: result == n;

    // Pre:
    public void clear() {
        ensureCapacity(QUEUE_SIZE);
        size = 0;
        head = 0;
        tail = 0;
    }
    // Post: n == 0;

    // Pre:
    public boolean isEmpty() {
        return size == 0;
    }
    // Post: result == (n == 0)

    // Pre: element != null
    public void push(Object element) {
        assert element != null;
        size++;
        ensureCapacity(size);
        head = dec(head);
        elements[head] = element;
    }
    // Post: (n' == n + 1) && (a'[i + 1] = a[i] ∀i ∈ [1, n - 1)) && (a'[0] == element)

    // Pre: n > 0
    public Object peek() {
        assert size > 0;
        return elements[dec(tail)];
    }
    // Post: res == a[n - 1]

    // Pre: n > 0
    public Object remove() {
        assert size > 0;
        Object temp = peek();
        popBack();
        return temp;
    }
    // Post: (n' == n - 1) && (a'[i] = a[i] ∀i ∈ [1, n - 1)) && (result == a[n -1])
}