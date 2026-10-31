package com.elin2026.nodin

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elin2026.nodin.data.ProfileRepository
import com.elin2026.nodin.data.SecureTokenVault
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataSecurityTest {
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun vaultEncryptsAndRoundTripsSecretsWithoutPlaintextPreferenceValue() {
        val vault = SecureTokenVault(context)
        val secret = "elin-test-secret-987"
        vault.put("telegram.bot", secret)
        assertEquals(secret, vault.get("telegram.bot"))
        val stored = context.getSharedPreferences("elin_secure_vault_v1", Context.MODE_PRIVATE)
            .getString("telegram.bot", "")
        assertFalse(stored!!.contains(secret))
        vault.remove("telegram.bot")
        assertNull(vault.get("telegram.bot"))
    }

    @Test
    fun profilesRemainIndependentAndSwitchable() {
        context.getSharedPreferences("elin_profiles_v1", Context.MODE_PRIVATE).edit().clear().commit()
        val repository = ProfileRepository(context)
        val first = repository.create("Child One", "fox")
        val second = repository.create("Child Two", "owl")
        assertEquals(first.id, repository.active()!!.id)
        repository.switchTo(second.id)
        assertEquals("Child Two", repository.active()!!.name)
        assertEquals(2, repository.all().size)
        repository.delete(first.id)
        assertEquals(second.id, repository.active()!!.id)
        repository.delete(second.id)
        assertNull(repository.active())
    }
}
