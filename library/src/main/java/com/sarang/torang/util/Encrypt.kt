package com.sarang.torang.util

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Encrypt {

    // 256-bit key (32 bytes)
    val secretKey = "0123456789abcdef0123456789abcdef"

    // 128-bit IV (16 bytes)
    val iv1 = "0123456789abcdef"

    fun encrypt(text: String, key: String = secretKey, iv: String = iv1): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(iv.toByteArray())

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encryptedBytes = cipher.doFinal(text.toByteArray())

        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(text: String, key: String = secretKey, iv: String = iv1): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(iv.toByteArray())

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text))

        return String(decryptedBytes)
    }

}