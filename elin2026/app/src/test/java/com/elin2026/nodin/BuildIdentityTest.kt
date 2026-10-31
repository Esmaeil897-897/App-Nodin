package com.elin2026.nodin

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BuildIdentityTest {
    @Test
    fun packageIdentityIsIsolatedFromLegacyNodin() {
        assertTrue(BuildConfig.APPLICATION_ID.startsWith("com.elin2026.nodin"))
        assertFalse(BuildConfig.APPLICATION_ID.startsWith("com.nodin.english.stable"))
    }

    @Test
    fun versionIsAnAlphaBuild() {
        assertTrue(BuildConfig.VERSION_NAME.startsWith("0.1.0-alpha01"))
    }
}
