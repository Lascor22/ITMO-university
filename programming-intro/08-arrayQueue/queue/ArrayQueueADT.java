package queue;

public class ArrayQueueADT {
    // Inv: (n >= 0) & (a[i] != null  ∀i ∈ [1, n)
    private static final int QUEUE_SIZE = 16;
    private /*static*/ Object[] elements = new Object[QUEUE_SIZE];
    private /*static*/ int size = 0;
    private /*static*/ int head = 0;
    private /*static*/ int tail = 0;

    // Pre:
    private static int getCapacity(ArrayQueueADT queue) {
        return queue.elements.length;
    }
    // Post: res = |elements|

    // Pre: capacity >= 0
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (getCapacity(queue) <= capacity || getCapacity(queue) > capacity * 4) {
            queue.elements = toArray(queue);
            queue.head = 0;
            queue.tail = getCapacity(queue);
            Object[] newElements = new Object[capacity * 2 + 1];
            System.arraycopy(queue.elements, 0, newElements, 0, getCapacity(queue));
            queue.elements = newElements;
        }
    }
    // Post: capacity < |elements| <= capacity * 4

    // Pre: (|elements != 0) && (0 <= x < |elements|)
    private static int dec(ArrayQueueADT queue, int x) {
        if (x == 0) {
            return getCapacity(queue) - 1;
        } else {
            return x - 1;
        }
    }
    // Post (result = x - 1 && x > 0) || (result = |element| && x == 0)

    // Pre: (|elements != 0) && (0 <= x < |elements|)
    private static int inc(ArrayQueueADT queue, int x) {
        if (x + 1 == getCapacity(queue)) {
            return 0;
        } else {
            return x + 1;
        }
    }
    // result == (x + 1) % |elements|

    // Pre:
    private static Object[] toArray(ArrayQueueADT queue) {
        Object newElements[];
        if (queue.head <= queue.tail) {
            newElements = new Object[queue.tail - queue.head];
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.tail - queue.head);
        } else {
            newElements = new Object[getCapacity(queue) + queue.tail - queue.head];
            System.arraycopy(queue.elements, queue.head, newElements, 0, getCapacity(queue) - queue.head);
            System.arraycopy(queue.elements, 0, newElements, getCapacity(queue) - queue.head, queue.tail);
        }
        return newElements;
    }
    // Post: result == a[];

    // Pre: n > 0
    private static void popBack(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.tail = dec(queue, queue.tail);
        queue.elements[queue.tail] = null;
        queue.size--;
    }
    // Post: (n' == n - 1) && (a'[i] == a[i] ∀i ∈ [0, n - 1)

    // Pre: n > 0
    private static void popFront(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.elements[queue.head] = null;
        queue.head = inc(queue, queue.head);
        queue.size--;
    }
    // Post: (n' == n - 1) && (a'[i - 1] == a[i] ∀i ∈ [1, n)

    // Pre: n > 0
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }
    // Post: result == a[0]

    // Pre: elem != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        queue.size++;
        ensureCapacity(queue, queue.size);
        queue.elements[queue.tail] = element;
        queue.tail = inc(queue, queue.tail);
    }
    // (n' == n + 1) && (a'[i] == a[i] for i = 0..n - 1) && (a'[n] == element)

    // Pre: n > 0
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object temp = element(queue);
        popFront(queue);
        return temp;
    }
    // Post: (n' == n - 1) && (a'[i - 1] = a[i] ∀i ∈ [1, n)) && (result == a[0])

    // Pre:
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    // Post: result == n;

    // Pre:
    public static void clear(ArrayQueueADT queue) {
        ensureCapacity(queue, QUEUE_SIZE);
        queue.size = 0;
        queue.head = 0;
        queue.tail = 0;
    }
    // Post: n == 0;

    // Pre:
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    // Post: result == (n == 0)

    // Pre: element != null
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;
        queue.size++;
        ensureCapacity(queue, queue.size);
        queue.head = dec(queue, queue.head);
        queue.elements[queue.head] = element;
    }
    // Post: (n' == n + 1) && (a'[i + 1] = a[i] ∀i ∈ [1, n - 1)) && (a'[0] == element)

    // Pre: n > 0
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[dec(queue, queue.tail)];
    }
    // Post: res == a[n - 1]

    // Pre: n > 0
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object temp = peek(queue);
        popBack(queue);
        return temp;
    }
    // Post: (n' == n - 1) && (a'[i] = a[i] ∀i ∈ [1, n - 1)) && (result == a[n -1])
}