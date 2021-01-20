import java.util.concurrent.atomic.*;

public class Solution implements Lock<Solution.Node> {
    private final Environment env;
    private final AtomicReference<Node> tail = new AtomicReference<>();

    public Solution(Environment env) {
        this.env = env;
    }

    @Override
    public Node lock() {
        Node my = new Node();
        my.lock.set(true);
        Node prev = tail.getAndSet(my);
        if (prev != null) {
            prev.next.set(my);
            while (my.lock.get()) {
                env.park();
            }
        }
        return my;
    }

    @Override
    public void unlock(Node node) {
        if (node.next.get() == null && tail.compareAndSet(node, null)) {
            return;
        } else {
            while (node.next.get() == null) {
                //wait
            }
        }
        node.next.get().lock.set(false);
        env.unpark(node.next.get().thread);
    }

    static class Node {
        final Thread thread = Thread.currentThread();
        final AtomicReference<Node> next = new AtomicReference<>();
        final AtomicReference<Boolean> lock = new AtomicReference<>(false);
    }
}
