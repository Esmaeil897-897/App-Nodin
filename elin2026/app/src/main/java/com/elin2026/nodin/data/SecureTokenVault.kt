package com.elin2026.nodin.data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Device-bound AES-GCM vault. The Android Keystore key is non-exportable;
 * only authenticated ciphertext is persisted in app-private preferences.
 */
class SecureTokenVault(context: Context) {
    private val preferences = context.getSharedPreferences("elin_secure_vault_v1", Context.MODE_PRIVATE)

    @Synchronized
    fun put(name: String, secret: String) {
        requireValidName(name)
        require(secret.isNotBlank()) { "Secret must not be blank" }
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        cipher.updateAAD(name.toByteArray(StandardCharsets.UTF_8))
        val encrypted = cipher.doFinal(secret.toByteArray(StandardCharsets.UTF_8))
        val packed = cipher.iv + encrypted
        check(preferences.edit().putString(name, Base64.encodeToString(packed, Base64.NO_WRAP)).commit()) {
            "Could not persist encrypted secret"
        }
    }

    @Synchronized
    fun get(name: String): String? {
        requireValidName(name)
        val packed = preferences.getString(name, null) ?: return null
        val bytes = Base64.decode(packed, Base64.NO_WRAP)
        require(bytes.size > IV_BYTES) { "Stored secret is invalid" }
        val iv = bytes.copyOfRange(0, IV_BYTES)
        val encrypted = bytes.copyOfRange(IV_BYTES, bytes.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(TAG_BITS, iv))
        cipher.updateAAD(name.toByteArray(StandardCharsets.UTF_8))
        return String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
    }

    @Synchronized
    fun remove(name: String) {
        requireValidName(name)
        check(preferences.edit().remove(name).commit()) { "Could not remove encrypted secret" }
    }

    private fun getOrCreateKey(): SecretKey {
        val store = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (store.getKey(KEY_ALIAS, null) as? SecretKey)?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(true)
                .setUserAuthenticationRequired(false)
                .build()
        )
        return generator.generateKey()
    }

    private fun requireValidName(name: String) {
        require(name.matches(Regex("[a-zA-Z0-9_.-]{1,80}"))) { "Invalid vault entry name" }
    }

    private companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val KEY_ALIAS = "com.elin2026.nodin.vault.aesgcm.v1"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val IV_BYTES = 12
        const val TAG_BITS = 128
    }
}
