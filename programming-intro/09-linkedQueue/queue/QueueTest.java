package queue;

public class QueueTest {
    private static void fill(Queue queue) {
        for (int i = 0; i < 3; i++) {
            queue.enqueue(i);
        }
    }

    private static void dump(Queue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " +
                    queue.element() + " " + queue.dequeue());
        }
    }

    private static void toArray(Queue queue) {
        Object[] array = queue.toArray();
        for (Object temp : array) {
            System.out.print(temp + " ");
        }
        System.out.println();
    }

    private static void test(Queue queue) {
        fill(queue);
        toArray(queue);
        System.out.println("-----");

        dump(queue);
        System.out.println();

        dump(queue);
        System.out.println("-----");
    }

    public static void main(String[] args) {
        test(new ArrayQueue());
        test(new LinkedQueue());
    }
}
