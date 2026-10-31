# Source reference for Elin2026

- Archive: `Nodin-Android-Chromebook-9.24-Source.zip`
- SHA-256 of supplied archive: `b87f9bc302919929a94368b7c9f1987f01866099e04f18e04edf0e7b74ea0485`
- Source baseline: Android / Chromebook 9.24
- Declared version code: 54
- Declared package: `com.nodin.english.stable`
- Declared ABI targets: `arm64-v8a`, `x86_64`
- Declared APK certificate SHA-256: `a3dcb890c724cb85ff4a22317dfaf160cfe484c0f91f6834f253a737122631e9`
- Declared 9.24 APK SHA-256 (mobile): `2ee73fbb911435a7a12d1fc7f87bf81d1fa84c75027932f642941e682750cfb2`
- Declared 9.24 APK SHA-256 (Chromebook): `dab976b571b94268f2e6182f1e0c551259a58633b786e051730f0c7bc8754ead`
- Required original base APK: `Nodin-9.23-Floating-Translator.apk`
- Required base APK SHA-256: `3e56005813755ea9fad3eb9aea3d647db7e2e2b6dc7af81f73f32b1573814db2`

## Important source limitation

The supplied 9.24 archive is an asset/native-overlay package, not a complete Android Studio project. Its build script patches a specific prior APK, carries DEX/SMALI patch payloads, and expects the matching base APK and original private stable signing key. The matching base APK was not present among the available project files during this setup.

Elin2026 therefore uses the 9.24 archive as a feature and content reference only. It will be rebuilt as a separate Kotlin Android application with application ID `com.elin2026.nodin`, rather than modifying the old app or pretending that the overlay is a clean source tree.

The source metadata reports 146 automated tests and explicitly says hardware testing was not performed. The currently visible `mobile29.test.js` suite was independently run in this workspace: 4 tests passed. This does not independently verify all 146 reported tests.
