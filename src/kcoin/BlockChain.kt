package kcoin

class BlockChain {
    private val blocks: MutableList<Block> = mutableListOf()

    fun add(block: Block): Block {
        blocks.add(block)
        return block
    }

    fun getBlocks(): List<Block> = blocks.toList()
}
