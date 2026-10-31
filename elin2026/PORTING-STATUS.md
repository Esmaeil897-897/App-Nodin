# Elin2026 migration status

The full source integration package was prepared separately from this lightweight Git branch. It uses the complete NODIN 9.17 Android source as the native baseline and overlays transferable NODIN 9.24 assets and source additions.

| Area | 9.24 reference | Current integration status |
|---|---:|---|
| Android native baseline | 9.17 full source | Obtained from GitHub Actions artifact and used in the Elin2026 source integration package |
| Web assets | 9.24 overlay | 686 overlay files merged into the 9.17 asset tree |
| School lessons | 143 | Imported from 9.24; JSON list count verified as 143 |
| School questions | 596 | 9.24 school data assets included; full semantic/regression audit still pending |
| Driving theory questions | 1,308 | Imported; 1,308 unique records and valid answer indices verified |
| Banking / HMRC | Present in 9.24 assets | Assets included; runtime and privacy review pending |
| Translator | Floating/continuous translator | 9.24 Java sources, Vosk/JNA dependencies, manifest components and model-fetch script ported in source package; not included in the preview APK |
| Telegram learning reports | Native patches and JS | 9.24 UnifiedTelegram Java logic ported into source package; device testing pending |
| Local profiles / secrets | Elin2026 | Local profile repository and AES-GCM Android Keystore vault included |
| Release signing | New identity | Preview APK signed with the new Elin2026 key; legacy key not reused |
| Hardware validation | Android / Chromebook | Not yet tested on physical devices |

## Deliverables

- **Preview APK:** package `com.elin2026.nodin.debug`, versionCode 2, versionName `0.2.0-alpha01-debug`. It is based on the existing 9.17 Android build artifact with the merged 9.24 web assets, manifest identity changed, OS backup disabled, and a new Elin2026 signature. It is not a fresh Gradle compilation. The translator tab shows an availability notice in this preview because its native source port is in the full source package, not in this repackaged APK.
- **Source integration patch:** contains the 9.24 asset overlay, ported native source additions, Elin2026 build configuration, local security code, tests and a script that applies them to the 9.17 source artifact.
- **GitHub branch:** this repository branch retains the small scaffold and progress documentation. The full binary asset set is delivered separately because the source integration is too large for a normal Git commit.

## Source provenance

- 9.17 source artifact: GitHub Actions run [39063593092](https://github.com/Esmaeil897-897/App-Nodin/actions/runs/39063593092), artifact `Nodin-9.17-source` (ID `13024770011`).
- 9.17 Android artifact: same run, artifact `Nodin-9.17-Android` (ID `13023694087`).
- 9.24 overlay archive SHA-256: `b87f9bc302919929a94368b7c9f1987f01866099e04f18e04edf0e7b74ea0485`.

## Remaining release gates

- Build the full source project with Android SDK 35 and Java 17.
- Run JVM and Android instrumentation tests.
- Download and verify the pinned Vosk English and Persian models.
- Test banking, Telegram, microphone capture, floating overlay and backup/export flows.
- Install and test on a physical Android phone and Chromebook.

The historical 9.24 APK cannot be reproduced from its source archive alone: its opaque DEX/SMALI overlay expects an exact 9.23 base APK and the original private signing key. Elin2026 is built as a separate application identity and does not modify NODIN LEGACY.
