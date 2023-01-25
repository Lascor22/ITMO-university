class Storage {
    private val arr = ArrayList<Entry>(0)

    fun addToStorage(hash: Int, shard: Shard) {
        arr.add(Entry(hash, shard))
        arr.sortBy { entry -> entry.hash }
    }

    fun isEmpty(): Boolean {
        return arr.isEmpty()
    }

    fun findAllHashes(shard: Shard): List<Int> {
        return arr.filter { entry -> entry.shard == shard }
            .map { entry -> entry.hash }
    }

    fun remove(shard: Shard) {
        arr.filter { entry -> entry.shard == shard }
            .forEach { entry -> arr.remove(entry) }

    }

    fun searchShard(hash: Int): Shard {
        return upperBound(hash).shard
    }

    fun searchAddBounds(hash: Int): Pair<Shard, HashRange> {
        val right = upperBound(hash)
        val left = lowerBound(hash)
        return Pair(right.shard, HashRange(left.hash + 1, hash))
    }

    fun searchRemoveBounds(hash: Int, shard: Shard): Pair<Shard, HashRange> {
        var right = upperBound(hash + 1)
        while (right.shard == shard) {
            right = upperBound(right.hash + 1)
        }
        var left = lowerBound(hash)
        while (left.shard == shard) {
            left = lowerBound(left.hash)
        }
        return Pair(right.shard, HashRange(left.hash + 1, hash))
    }

    private fun upperBound(hash: Int): Entry {
        val right = generalBinary(hash).second
        if (right == arr.size) {
            return upperBound(Int.MIN_VALUE)
        }
        return arr[right]
    }

    private fun lowerBound(hash: Int): Entry {
        val left = generalBinary(hash).first
        if (left == -1) {
            return lowerBound(Int.MAX_VALUE)
        }
        return arr[left]
    }

    private fun generalBinary(hash: Int): Pair<Int, Int> {
        var left = -1
        var right = arr.size
        while (left < right - 1) {
            val mid = (right + left) / 2
            if (arr[mid].hash < hash) {
                left = mid
            } else {
                right = mid
            }
        }
        return Pair(left, right)
    }
}

data class Entry(val hash: Int, val shard: Shard)

