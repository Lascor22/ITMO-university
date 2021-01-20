package stack;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicRef;

import java.util.Random;

public class StackImpl implements Stack {
    private static final Random random = new Random(0);
    private static final int ELIMINATION_SIZE = 32;
    private static final int WINDOW_SIZE = 4;

    private final AtomicRef<Node> head = new AtomicRef<>(null);
    private final AtomicArray<Integer> eliminationArray = new AtomicArray<>(ELIMINATION_SIZE);

    @Override
    public void push(int x) {
        if (!pushWithElimination(x)) {
            while (true) {
                Node curHead = head.getValue();
                Node newHead = new Node(x, curHead);
                if (head.compareAndSet(curHead, newHead)) {
                    return;
                }
            }
        }
    }

    private boolean pushWithElimination(int x) {
        int init = random.nextInt(ELIMINATION_SIZE - WINDOW_SIZE);
        for (int i = init; i < init + WINDOW_SIZE; i++) {
            Integer newValue = x;
            if (eliminationArray.get(i).compareAndSet(null, newValue)) {
                for (int j = 0; j < 30; j++) {
                    Integer value = eliminationArray.get(i).getValue();
                    if (value == null || !value.equals(newValue)) {
                        return true;
                    }
                }
                return !eliminationArray.get(i).compareAndSet(newValue, null);
            }
        }
        return false;
    }

    @Override
    public int pop() {
        int init = random.nextInt(ELIMINATION_SIZE - WINDOW_SIZE);
        for (int i = init; i < init + WINDOW_SIZE; i++) {
            Integer value = eliminationArray.get(i).getValue();
            if (value != null && eliminationArray.get(i).compareAndSet(value, null)) {
                return value;
            }
        }
        while (true) {
            Node currHead = head.getValue();
            if (currHead == null) {
                return Integer.MIN_VALUE;
            }
            if (head.compareAndSet(currHead, currHead.next.getValue())) {
                return currHead.x;
            }
        }
    }

    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }

    }
}
