package com.elin2026.nodin

import org.junit.Assert.assertEquals
import org.junit.Test

class BuildIdentityTest {
    @Test
    fun newPackageIdentityIsIsolatedFromLegacyNodin() {
        assertEquals("com.elin2026.nodin", BuildConfig.APPLICATION_ID)
    }

    @Test
    fun initialVersionIsAnAlphaBuild() {
        assertEquals("0.1.0-alpha01", BuildConfig.VERSION_NAME)
    }
}
