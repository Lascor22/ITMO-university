package mutex

/**
 * Distributed mutual exclusion implementation.
 * All functions are called from the single main thread.
 *
 * @author Karimov Azat
 */
class ProcessImpl(private val env: Environment) : Process {
    private val fReq = MutableList(env.nProcesses + 1) { false }
    private val fs = MutableList(env.nProcesses + 1) { ForkType.DIRTY }
    var l = false
    var lReq = false

    init {
        for (i in env.processId + 1 until env.nProcesses + 1) {
            fs[i] = ForkType.ABSENT
        }
    }

    override fun onMessage(srcId: Int, message: Message) {
        message.parse {
            when (readEnum<MessageType>()) {
                MessageType.FORK -> {
                    fs[srcId] = ForkType.CLEAN
                    if (lReq && fs.all { it != ForkType.ABSENT }) {
                        env.locked()
                        l = true
                    }
                }

                MessageType.REQUEST -> {
                    if (l || fs[srcId] != ForkType.DIRTY) {
                        fReq[srcId] = true
                    } else {
                        fs[srcId] = ForkType.ABSENT
                        fReq[srcId] = false

                        if (lReq) {
                            env.send(srcId) {
                                writeEnum(MessageType.REQUEST)
                            }
                        }
                        env.send(srcId) {
                            writeEnum(MessageType.FORK)
                        }
                    }
                }
            }
        }
    }

    override fun onLockRequest() {
        lReq = true
        if (!lookupLock()) {
            fs.forEachIndexed { index, f ->
                if (f == ForkType.ABSENT) env.send(index) {
                    writeEnum(MessageType.REQUEST)
                }
            }
        }
    }

    private fun lookupLock(): Boolean {
        if (lReq && fs.all { it != ForkType.ABSENT }) {
            env.locked()
            l = true
            return true
        }
        return false
    }

    override fun onUnlockRequest() {
        lReq = false
        fs.indices.forEach { fs[it] = ForkType.DIRTY }
        env.unlocked()
        l = false
        fReq.indices
                .asSequence()
                .filter { fReq[it] }
                .forEach {
                    fs[it] = ForkType.ABSENT
                    env.send(it) {
                        writeEnum(MessageType.FORK)
                    }
                    fReq[it] = false
                }
    }

    enum class ForkType { ABSENT, CLEAN, DIRTY }
    enum class MessageType { REQUEST, FORK }
}
