class ConsistentHashImpl<K> : ConsistentHash<K> {
    private val storage = Storage()

    override fun getShardByKey(key: K): Shard {
        return storage.searchShard(key.hashCode())
    }

    override fun addShard(newShard: Shard, vnodeHashes: Set<Int>): Map<Shard, Set<HashRange>> {
        val map = HashMap<Shard, Set<HashRange>>()
        if (!storage.isEmpty()) {
            vnodeHashes.forEach { hash ->
                val (existShard, range) = storage.searchAddBounds(hash)
                val newRanges = map.computeIfPresent(existShard) { _, set -> set.plus(range) }
                if (newRanges == null) map[existShard] = setOf(range)
            }
        }
        vnodeHashes.forEach { hash ->
            storage.addToStorage(hash, newShard)
        }
        deduplicateRanges(map)
        return map
    }

    override fun removeShard(shard: Shard): Map<Shard, Set<HashRange>> {
        val hashes = storage.findAllHashes(shard)
        val map = HashMap<Shard, Set<HashRange>>()
        hashes.forEach { hash ->
            val (existShard, range) = storage.searchRemoveBounds(hash, shard)
            val newRanges = map.computeIfPresent(existShard) { _, set -> set.plus(range) }
            if (newRanges == null) map[existShard] = setOf(range)
        }
        storage.remove(shard)
        deduplicateRanges(map)
        return map
    }

    private fun deduplicateRanges(map: HashMap<Shard, Set<HashRange>>) {
        map.forEach { (shard, set) ->
            if (set.isNotEmpty()) {
                val inf = (Int.MAX_VALUE.toLong() - Int.MIN_VALUE.toLong())
                val scanArray = ArrayList<Triple<Boolean, Boolean, Long>>()
                set.forEach { (left, right) ->
                    scanArray.add(Triple(first = true, second = false, third = left.toLong()))
                    scanArray.add(
                        Triple(
                            false,
                            (right < left),
                            if (right < left) right.toLong() + inf else right.toLong()
                        )
                    )
                }
                scanArray.sortWith(compareBy { p -> p.third })
                var count = 0
                var lastOpened: Long? = scanArray.find { (isOpen, _, _) -> isOpen }!!.third
                val answer = HashSet<HashRange>()
                scanArray.forEach { (isOpen, isDeg, x) ->
                    if (isOpen) {
                        if (lastOpened == null) lastOpened = if (isDeg) x - inf else x
                        count++
                    } else {
                        count--
                        if (count == 0) {
                            answer.add(HashRange(lastOpened!!.toInt(), (if (isDeg) x - inf else x).toInt()))
                            lastOpened = null
                        }
                    }
                }
                map[shard] = answer
            }
        }
    }
}
