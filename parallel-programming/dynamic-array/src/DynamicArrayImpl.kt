import kotlinx.atomicfu.*

class DynamicArrayImpl<E> : DynamicArray<E> {
    private val core = atomic(Core<E>(INITIAL_CAPACITY))
    private val _size = atomic(0)

    override fun get(index: Int): E {
        require(index < size)
        while (true) {
            if (core.value.size > index) {
                return core.value.array[index].value ?: continue
            }
        }
    }

    override fun put(index: Int, element: E) {
        require(index < size)
        while (true) {
            if (core.value.size > index &&
                core.value.array[index].getAndSet(null) != null
            ) {
                core.value.array[index].value = element
                return
            }
        }
    }

    override fun pushBack(element: E) {
        val newSize = _size.getAndIncrement()
        while (true) {
            val coreValue = this.core.value
            if (ensureCapacity(newSize, coreValue)) continue
            if (coreValue.array[newSize].compareAndSet(null, element)) return
        }
    }

    private fun ensureCapacity(newSize: Int, oldCore: Core<E>): Boolean {
        if (newSize < oldCore.size) return false
        val core = Core<E>(oldCore.size * 2)
        if (oldCore.next.compareAndSet(null, core)) {
            for (i in 0 until oldCore.size) {
                while (true) {
                    val element = oldCore.array[i].getAndSet(null)
                    if (element != null) {
                        core.array[i].value = element
                        break
                    }
                }
            }
            this.core.value = core
        }
        return true
    }

    override val size: Int
        get() {
            return _size.value
        }
}

class Core<E>(val size: Int) {
    val next: AtomicRef<Core<E>?> = atomic(null)
    val array = atomicArrayOfNulls<E>(size)
}

private const val INITIAL_CAPACITY = 1 // DO NOT CHANGE ME
