# Elin2026 — independent Android rebuild

This is the separate experimental project area. **NODIN LEGACY** and its existing branches remain untouched.

## Source baseline

- Native Android baseline: complete NODIN 9.17 source from GitHub Actions.
- Web/assets overlay: NODIN Android/Chromebook 9.24 source archive.
- New release application ID: `com.elin2026.nodin`.
- New debug application ID: `com.elin2026.nodin.debug`.
- Internal Android namespace remains `com.nodin.english` for compatibility with the 9.17 Java/Kotlin source.
- Current integration version: `0.2.0-alpha01`, versionCode 2.

## Get the source

The full source integration is provided as a separate patch archive in this conversation. It contains the 9.24 asset overlay, ported native additions, Elin2026 build configuration, security classes, tests and an apply script.

To retrieve the 9.17 source baseline, download the `Nodin-9.17-source` artifact from [GitHub Actions run 39063593092](https://github.com/Esmaeil897-897/App-Nodin/actions/runs/39063593092). Then follow `README-fa.md` inside the patch archive and run `apply_to_nodin917.py`.

## Build requirements

- Android SDK Platform 35
- Java 17
- Python 3 and internet access for the pinned Vosk model downloads

Build the debug APK with:

```bash
./gradlew :app:assembleDebug
```

The full source project downloads the Vosk English and Persian model archives and checks their SHA-256 before packaging. Set the four `ELIN_*` signing environment variables to sign debug/release builds with the new Elin2026 keystore.

## Preview APK

A separate preview APK is supplied in this conversation. It is based on the 9.17 Android build artifact with 9.24 web assets and is signed with the new Elin2026 certificate. It is not a full Gradle build; its native translator is not active yet. Build the merged source project to include that port.

## Important compatibility note

The 9.24 archive is an overlay intended for an exact 9.23 APK and the original private key. Those base-specific DEX/SMALI patches are not reused. Elin2026 is a separate package and signing identity; it does not overwrite or impersonate NODIN LEGACY.
