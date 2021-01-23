package queue;

public class ArrayQueue extends AbstractQueue {
    private static final int QUEUE_SIZE = 16;
    private Object[] elements = new Object[QUEUE_SIZE];
    private int head = 0;
    private int tail = 0;

    private int getCapacity() {
        return elements.length;
    }

    private void ensureCapacity(int capacity) {
        if (getCapacity() <= capacity || getCapacity() > capacity * 4) {
            Object[] newElements = new Object[capacity * 2 + 1];
            int last;
            if (head <= tail) {
                last = tail - head;
                System.arraycopy(elements, head, newElements, 0, tail - head);
            } else {
                last = getCapacity() - head + tail;
                System.arraycopy(elements, head, newElements, 0, getCapacity() - head);
                System.arraycopy(elements, 0, newElements, getCapacity() - head, tail);
            }
            head = 0;
            tail = last;
            elements = newElements;
        }
    }


    private int inc(int x) {
        if (x + 1 == getCapacity()) {
            return 0;
        } else {
            return x + 1;
        }
    }

    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity(size());
        elements[tail] = element;
        tail = inc(tail);
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    protected void dequeueImpl() {
        assert size() > 0;
        elements[head] = null;
        head = inc(head);
    }

    @Override
    protected void clearImpl() {
        ensureCapacity(QUEUE_SIZE);
        head = 0;
        tail = 0;
    }

}
