# Elin2026 source integration record

## Source files retrieved

- NODIN 9.17 full Android source artifact: [GitHub Actions run 39063593092](https://github.com/Esmaeil897-897/App-Nodin/actions/runs/39063593092), artifact ID `13024770011`.
- NODIN 9.17 Android build artifact: same run, artifact ID `13023694087`.
- NODIN 9.24 overlay archive: `Nodin-Android-Chromebook-9.24-Source.zip`, SHA-256 `b87f9bc302919929a94368b7c9f1987f01866099e04f18e04edf0e7b74ea0485`.

## Integration

The full source patch overlays the 9.24 `android-update/assets/` tree on the 9.17 native Android project and preserves 9.17-only assets. It ports the available 9.24 translator Java source, Vosk/JNA dependencies, translator services, and unified Telegram market selector/filter code. It also adds the Elin2026 package identity, profile repository, AES-GCM Android Keystore vault, and quiz session engine.

The app installs separately:
- Release: `com.elin2026.nodin`
- Debug: `com.elin2026.nodin.debug`

## Preview APK verification

The preview APK is based on the 9.17 Android build artifact with the merged 9.24 assets. Its binary manifest was changed to the Elin2026 debug package and version, OS backup was disabled, and the APK was signed with the new Elin2026 certificate.

Verified:
- APK signature schemes v2 and v3
- ZIP integrity
- Manifest package, versionCode, versionName and app label
- Android OS backup disabled
- DEX checksums/signatures after visible app-name string updates
- 16 KiB alignment of stored native libraries

Not yet verified:
- Full Gradle build of the merged source project
- Emulator or physical-device installation and runtime behavior
- Native translator in the preview APK (the source port is in the full source patch)
- Hardware microphone, screen-capture and floating-overlay behavior

The full source integration archive is delivered separately in the conversation because it contains a large binary asset tree and is not committed as a single Git object in this branch.
