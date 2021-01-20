/**
 * @author Karimov Azat
 */
class Solution : AtomicCounter {
    private val root = Node(0)
    private val curr = lazy { ThreadLocal.withInitial { 0 } }.value
    private val last = ThreadLocal.withInitial { root }

    override fun getAndAdd(x: Int): Int {
        val node = Node(x)
        var prev = 0
        while (last.get() != node) {
            prev = curr.get()
            last.set(last.get().next.decide(node))
            curr.set(curr.get() + last.get().x)
        }
        return prev
    }

    private class Node(val x: Int) {
        val next = Consensus<Node>()
    }
}
