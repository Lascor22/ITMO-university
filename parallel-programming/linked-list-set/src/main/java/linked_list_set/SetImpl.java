package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {
    private final Node head = new Node(Integer.MIN_VALUE, new Node(Integer.MAX_VALUE, null));

    private Window findWindow(int key) {
        while (true) {
            Node curr = head;
            Node next = remNext(curr);
            while (next.x < key) {
                Node newNext = next.next.getValue();
                if (newNext instanceof Removed) {
                    if (!curr.next.compareAndSet(next, castNext(newNext))) {
                        curr = head;
                        next = remNext(curr);
                    } else {
                        next = castNext(newNext);
                    }
                } else {
                    curr = next;
                    next = remNext(curr);
                }
            }
            Node newNext = next.next.getValue();
            if (newNext instanceof Removed) {
                curr.next.compareAndSet(next, castNext(newNext));
            } else {
                return new Window(curr, next);
            }
        }
    }

    private Node remNext(Node node) {
        Node next = node.next.getValue();
        if (next instanceof Removed) {
            next = castNext(next);
        }
        return next;
    }

    private Node castNext(Node node) {
        return ((Removed) node).next;
    }

    @Override
    public boolean add(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x == x) {
                return false;
            }
            Node newNext = new Node(x, w.next);
            if (w.curr.next.compareAndSet(w.next, newNext)) return true;
        }
    }

    @Override
    public boolean remove(int x) {
        while (true) {
            Window w = findWindow(x);
            if (w.next.x != x) {
                return false;
            }
            Node newNext = remNext(w.next);
            Node newNextRemoved = new Removed(newNext);
            if (w.next.next.compareAndSet(newNext, newNextRemoved)) {
                w.curr.next.compareAndSet(w.next, newNext);
                return true;
            }
        }
    }

    @Override
    public boolean contains(int x) {
        Window w = findWindow(x);
        return w.next.x == x;
    }

    private static class Node {
        final AtomicRef<Node> next;
        int x;

        Node() {
            next = new AtomicRef<>(null);
        }

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }
    }

    private static class Window {
        final Node curr, next;

        public Window(Node curr, Node next) {
            this.curr = curr;
            this.next = next;
        }
    }

    private static class Removed extends Node {e
        final Node next;

        Removed(Node next) {
            this.next = next;
        }
    }
}
