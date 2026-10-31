# Elin2026 — NODIN clean rebuild

This branch is an isolated experimental rebuild. The previous NODIN project remains on its existing branches and is not modified.

## Baseline

- Reference package: `Nodin-Android-Chromebook-9.24-Source.zip`
- Reference version: 9.24 (versionCode 54)
- Original application ID: `com.nodin.english.stable`
- Elin2026 application ID: `com.elin2026.nodin` (intentionally different, for side-by-side installation)
- Source type: Android asset/native overlay. The 9.24 archive is not a complete native Android project and expects an exact 9.23 base APK plus the original private signing key. Elin2026 does not reuse that overlay or signing identity.

## Rebuild principles

1. Reimplement the Android host app cleanly in Kotlin.
2. Import and validate the 9.24 web assets and educational datasets using `scripts/import_924_assets.py`.
3. Rebuild native capabilities through documented, testable interfaces; do not copy opaque DEX or SMALI patches.
4. Keep family profiles and financial records local by default. Never upload them or include them in diagnostics.
5. Keep service credentials out of source control. External services stay disabled until configured.
6. Use a new application ID and a new signing certificate. The private keystore must remain outside this repository.

## Build

The project uses Gradle 8.9+ and Android Gradle Plugin 8.7.3.

```bash
gradle assembleDebug
gradle testDebugUnitTest
```

For the 9.24 UI/data baseline, extract the supplied source archive and import its `android-update/assets` directory:

```bash
python scripts/import_924_assets.py /path/to/Nodin-Android-Chromebook-9.24-Source.zip
```

Until those assets are imported, the app uses a small local placeholder page.

## Signing

Debug builds use the standard Android debug key. Release signing is intentionally not configured with a committed key. Configure the GitHub Actions secrets `ELIN_KEYSTORE_BASE64`, `ELIN_KEYSTORE_PASSWORD`, `ELIN_KEY_ALIAS`, and `ELIN_KEY_PASSWORD` only after creating and securely backing up the new Elin2026 keystore. Never upload the keystore or passwords into this repository.

## Scope

This is the starting scaffold, not a claim that all 9.24 features have already been ported. Each feature will be migrated with tests and a documented acceptance check before it is marked complete.
