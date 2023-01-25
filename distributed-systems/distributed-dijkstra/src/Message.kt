package dijkstra.messages

sealed class Message

object AccParentMessage : Message()

object RejectParentMessage : Message()

object DieMessage : Message()

data class DistanceMessage(val data: Long) : Message()