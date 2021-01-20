import kotlin.coroutines.resume
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine
import java.util.concurrent.atomic.AtomicReference

const val RETRY = "RETRY"

@Suppress("UNCHECKED_CAST")
class SynchronousQueueMS<E> : SynchronousQueue<E> {
    private val head: AtomicReference<Node>
    private val tail: AtomicReference<Node>
//    private var debug = false

    init {
        val initialNode = Node()
        head = AtomicReference(initialNode)
        tail = AtomicReference(initialNode)
    }

    override suspend fun send(element: E) {
        while (true) {
            val tail = this.tail.get()
            if (tail == head.get() || tail is Sender<*>) {
                val res = suspendCoroutine<Any> sc@{ cont ->
//                    println(element)
                    val newTail = Sender(element, cont)
                    val oldTail = this.tail.get()
                    if ((oldTail == head.get() || oldTail is Sender<*>) &&
                            oldTail.next.compareAndSet(null, newTail)) {
                        this.tail.compareAndSet(oldTail, newTail)
                    } else {
                        cont.resume(RETRY)
                        return@sc
                    }
                }
                if (res != RETRY) {
                    return
                }
                continue
            }
            val head = this.head.get()
            if (head == this.tail.get() || head.next.get() == null) {
                continue
            } else {
                val headNext = head.next.get()
                if (headNext is Receiver<*> && this.head.compareAndSet(head, headNext)) {
                    (headNext.f as Continuation<E>).resume(element)
//                    if (debug) {
//                        println(element)
//                        debug = false
//                    }
                    return
                }
            }
        }
    }

    override suspend fun receive(): E {
        while (true) {
            val tail = this.tail.get()
            if (tail == this.head.get() || tail is Receiver<*>) {
                val res = suspendCoroutine<E?> sc@{ cont ->
                    val newTail = Receiver(cont)
                    val oldTail = this.tail.get()
                    if ((oldTail == head.get() || oldTail is Receiver<*>) &&
                            oldTail.next.compareAndSet(null, newTail)) {
                        this.tail.compareAndSet(oldTail, newTail)
                    } else {
                        cont.resume(null)
                        return@sc
                    }
                }
                if (res != null) {
                    return res
                }
                continue
            }
            val head = this.head.get()
            if (head == this.tail.get() || head.next.get() == null) {
                continue
            }
            val headNext = head.next.get()
            if (head != this.tail.get() && headNext is Sender<*> &&
                    this.head.compareAndSet(head, headNext)) {
                headNext.f.resume(Unit)
//                debug = true
                return (headNext.value as E)
            }
        }
    }

    private open class Node {
        val next = AtomicReference<Node>(null)
    }

    private class Receiver<E>(
            val f: Continuation<E>
    ) : Node()

    private class Sender<E>(
            val value: E,
            val f: Continuation<Unit>
    ) : Node()
}
