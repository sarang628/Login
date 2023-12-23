package com.sarang.torang

import com.sarang.torang.util.Encrypt
import com.sarang.torang.util.Encrypt.decrypt
import com.sarang.torang.util.Encrypt.encrypt
import org.junit.Test

class EncryptTest {

    @Test
    fun encryptTest() {
        // 비밀번호 생성
        val plainText = "My password is very secure"

        // 암호화
        val cipherText = encrypt(plainText)

        println(cipherText)

        // 복호화
        val decryptedText = decrypt(cipherText)

        // 출력
        println(decryptedText)
    }

}