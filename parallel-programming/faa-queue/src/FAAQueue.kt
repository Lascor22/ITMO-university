import kotlinx.atomicfu.*

class FAAQueue<T> {
    private val head: AtomicRef<Segment> // Head pointer, similarly to the Michael-Scott queue (but the first node is _not_ sentinel)
    private val tail: AtomicRef<Segment> // Tail pointer, similarly to the Michael-Scott queue

    init {
        val firstNode = Segment()
        head = atomic(firstNode)
        tail = atomic(firstNode)
    }
    /**
     * Adds the specified element [x] to the queue.
     */
    fun enqueue(x: T) {
        while (true) {
            val tailVal = tail.value
            val tailNextVal = tailVal.next.value
            if (tailNextVal != null) {
                tail.compareAndSet(tailVal, tailNextVal)
                continue
            }
            val enqIdx = tailVal.enqIdx.getAndIncrement()
            if (SEGMENT_SIZE <= enqIdx) {
                if (tail.value.next.compareAndSet(null, Segment(x))) return
            } else {
                if (tailVal.elements[enqIdx].compareAndSet(null, x)) return
            }
        }
    }

    /**
     * Retrieves the first element from the queue
     * and returns it; returns `null` if the queue
     * is empty.
     */

    fun dequeue(): T? {
        while (true) {
            val headVal = head.value
            val deqIdx = headVal.deqIdx.getAndIncrement()
            if (deqIdx >= SEGMENT_SIZE) {
                val headNext = headVal.next.value ?: return null
                head.compareAndSet(headVal, headNext)
                continue
            }
            val res = headVal.elements[deqIdx].getAndSet(DONE)
            if (res != null) return res as T?
        }
    }

    /**
     * Returns `true` if this queue is empty;
     * `false` otherwise.
     */
    val isEmpty: Boolean
        get() {
            while (true) {
                if (head.value.isEmpty) {
                    if (head.value.next.value == null) return true
                    head.value = head.value.next.value!!
                    continue
                } else {
                    return false
                }
            }
        }
}

private class Segment {
    val next: AtomicRef<Segment?> = atomic(null)
    val enqIdx = atomic(0) // index for the next enqueue operation
    val deqIdx = atomic(0) // index for the next dequeue operation
    val elements = atomicArrayOfNulls<Any>(SEGMENT_SIZE)

    constructor() // for the first segment creation

    constructor(x: Any?) { // each next new segment should be constructed with an element
        enqIdx.incrementAndGet()
        elements[0].getAndSet(x)
    }

    val isEmpty: Boolean get() = deqIdx.value >= enqIdx.value || deqIdx.value >= SEGMENT_SIZE
}

private val DONE = Any() // Marker for the "DONE" slot state; to avoid memory leaks
const val SEGMENT_SIZE = 2 // DO NOT CHANGE, IMPORTANT FOR TESTS
