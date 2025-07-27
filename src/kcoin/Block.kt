package kcoin

import java.time.Instant
import java.security.MessageDigest

data class Block(
    val previousHash: String,
    val data: String,
    val timestamp: Long = Instant.now().toEpochMilli(),
    val nonce: Long = 0,
    val hash: String = ""
) {
    fun calculateHash(): String {
        return "$previousHash$data$timestamp$nonce".hash()
    }

    companion object {
        fun create(previousHash: String, data: String): Block {
            val block = Block(previousHash, data)
            return block.copy(hash = block.calculateHash())
        }
    }
}


fun String.hash(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}
