package queue;

public interface Queue {
    // Pre:
    int size();
    // Post: result == n;

    // Pre:
    boolean isEmpty();
    // Post: result == (n == 0)

    // Pre:
    void clear();
    // Post: n == 0;

    // Pre: elem != null
    void enqueue(Object element);
    // (n' == n + 1) && (a'[i] == a[i] for ∀i ∈ [0, n)) && (a'[n] == element)

    // Pre: n > 0
    Object element();
    // Post: result == a[0]

    // Pre: n > 0
    Object dequeue();
    // Post: (n' == n - 1) && (a'[i - 1] = a[i] ∀i ∈ [1, n)) && (result == a[0])

    // Pre:
    Object[] toArray();
    // Post: result == a[];
}
