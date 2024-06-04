package site.shaerware.store.utils

import site.shaerware.store.config.Config
import java.math.BigInteger
import java.security.MessageDigest

object Password {
    fun hash(password: String): String {
        val pass = Config.passwordSalt + password
        val digest = MessageDigest.getInstance(Config.passwordAlgorithm).digest(pass.toByteArray())
        val number = BigInteger(1, digest)
        return number.toString(16)
    }
}
