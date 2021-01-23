package queue;

class Node {
    Object value;
    Node next;

    Node(Object value, Node next) {
        assert value != null;
        this.value = value;
        this.next = next;
    }
}
