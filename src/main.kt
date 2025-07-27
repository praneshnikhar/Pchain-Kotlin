import kcoin.Block
import kcoin.BlockChain
import kcoin.hash

val blockchain = mutableListOf<Block>()

fun main() {
    val genesisBlock = Block.create(previousHash = "0", data = "I'm the first")
    val secondBlock = Block.create(genesisBlock.hash, "I'm the second")
    val thirdBlock = Block.create(secondBlock.hash, "I'm the third")

    add(genesisBlock)
    add(secondBlock)
    add(thirdBlock)

    println(genesisBlock)
    println(secondBlock)
    println(thirdBlock)

    println("Is blockchain valid? ${isValid()}")
}

fun add(block: Block): Block {
    val minedBlock = if (isMined(block)) block else mine(block)
    blockchain.add(minedBlock)
    return minedBlock
}

private val difficulty = 5
private val validPrefix = "0".repeat(difficulty)

private fun isMined(block: Block): Boolean {
    return block.hash.startsWith(validPrefix)
}

private fun mine(block: Block): Block {
    println("Mining: $block")
    var minedBlock = block.copy()
    while (!isMined(minedBlock)) {
        minedBlock = minedBlock.copy(nonce = minedBlock.nonce + 1)
        minedBlock = minedBlock.copy(hash = minedBlock.calculateHash())
    }
    println("Mined : $minedBlock")
    return minedBlock
}

fun isValid(): Boolean {
    if (blockchain.isEmpty()) return true
    if (blockchain.size == 1) return blockchain[0].hash == blockchain[0].calculateHash()

    for (i in 1 until blockchain.size) {
        val previous = blockchain[i - 1]
        val current = blockchain[i]

        if (current.hash != current.calculateHash()) return false
        if (current.previousHash != previous.hash) return false
        if (!isMined(current)) return false
    }
    return true
}
