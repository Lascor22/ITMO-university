package dijkstra

import dijkstra.messages.*
import dijkstra.system.environment.Environment

class ProcessImpl(private val env: Environment) : Process {
    private var distance: Long? = null
    private var parentID: Int? = null

    private var messageBalance = 0
    private var childCount = 0

    private var initial = false

    override fun onMessage(srcId: Int, message: Message) {
        when (message) {
            is AccParentMessage -> {
                messageBalance--
                childCount++
            }
            is RejectParentMessage -> {
                messageBalance--
                maybeDie()
            }
            is DieMessage -> {
                childCount--
                maybeDie()
            }
            is DistanceMessage -> {
                if (distance == null || distance!! > message.data) {
                    distance = message.data
                    parentID?.let { env.send(it, DieMessage) }
                    parentID = srcId
                    env.send(srcId, AccParentMessage)
                    sendNeighbours()
                } else {
                    env.send(srcId, RejectParentMessage)
                }
            }
        }
    }

    override fun getDistance(): Long? {
        return distance
    }

    override fun startComputation() {
        initial = true
        distance = 0
        sendNeighbours()
    }

    private fun sendNeighbours() {
        env.neighbours.forEach {
            messageBalance++
            env.send(it.key, DistanceMessage(distance!! + it.value))
        }
        maybeDie()
    }

    private fun maybeDie() {
        if (childCount == 0 && messageBalance == 0) {
            if (initial) {
                env.finishExecution()
            } else {
                env.send(parentID!!, DieMessage)
                parentID = null
            }
        }
    }
}