package queue;

public class LinkedQueue extends AbstractQueue {
    private Node tail, head;

    protected void enqueueImpl(Object element) {
        if (size() == 1) {
            head = tail = new Node(element, null);
        } else {
            tail.next = new Node(element, null);
            tail = tail.next;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void dequeueImpl() {
        head = head.next;
        if (size() == 1) {
            tail = null;
        }
    }

    protected void clearImpl() {
        head = tail = null;
    }
}
