package msqueue;

import java.util.concurrent.atomic.AtomicReference;

public class MSQueue implements Queue {
    private final AtomicReference<Node> head;
    private final AtomicReference<Node> tail;

    public MSQueue() {
        final Node dummy = new Node(0);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    @Override
    public void enqueue(int x) {
        final Node newTail = new Node(x);
        while (true) {
            final Node oldTail = tail.get();
            if (oldTail.next.compareAndSet(null, newTail)) {
                tail.compareAndSet(oldTail, newTail);
                return;
            } else {
                tail.compareAndSet(oldTail, oldTail.next.get());
            }
        }
    }

    @Override
    public int dequeue() {
        while (true) {
            final Node currHead = head.get();
            final Node newHead = currHead.next.get();
            if (newHead == null) {
                return Integer.MIN_VALUE;
            }
            if (currHead == tail.get()) {
                tail.compareAndSet(tail.get(), newHead);
            }
            if (head.compareAndSet(currHead, newHead)) {
                return newHead.x;
            }
        }
    }

    @Override
    public int peek() {
        while (true) {
            final Node currHead = head.get();
            final Node newHead = currHead.next.get();
            if (newHead == null) {
                return Integer.MIN_VALUE;
            }
            if (currHead == tail.get()) {
                tail.compareAndSet(tail.get(), newHead);
            }
            if (head.get() == currHead) {
                return newHead.x;
            }
        }
    }

    private static class Node {
        final int x;
        final AtomicReference<Node> next;

        Node(int x) {
            this.x = x;
            this.next = new AtomicReference<>(null);
        }
    }
}