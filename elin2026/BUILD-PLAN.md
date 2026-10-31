# Elin2026 build plan

## Milestone 1 — independent app foundation
- [x] Separate `Elin2026` branch and application identity
- [x] Initial Kotlin host, local profiles and encrypted token vault
- [x] Persian module dashboard and navigation
- [x] CI workflow definition

## Milestone 2 — 9.17 + 9.24 source integration
- [x] Retrieve the full 9.17 source artifact from GitHub Actions
- [x] Retrieve and audit the 9.24 Android/Chromebook overlay archive
- [x] Merge 9.24 assets into the 9.17 Android asset tree
- [x] Port 9.24 translator Java sources and Vosk/JNA build dependencies
- [x] Port 9.24 unified Telegram market selector/filter source
- [x] Add Elin2026 package identity, local security classes and build configuration
- [x] Create a signed installable preview APK from the 9.17 Android artifact plus 9.24 assets
- [ ] Compile the full merged Android Studio project with Gradle
- [ ] Run JVM and Android emulator tests on the full merged project

## Milestone 3 — feature validation
- [x] Import 1,308 driving theory questions and audit unique IDs/answer indices
- [x] Import 143 school lessons
- [ ] Audit all 596 school questions and connect every bank to regression tests
- [ ] Connect native translator and verify model downloads
- [ ] Test banking/HMRC OCR and document workflows
- [ ] Test Telegram configuration and report delivery with explicit user consent
- [ ] Test backup/export and profile isolation on device

## Release gates
- Full Gradle build succeeds
- JVM and emulator instrumentation tests pass
- Android phone and Chromebook manual tests pass
- New Elin2026 signing certificate is used; never the NODIN LEGACY key

The preview APK is a repackaged 9.17 build artifact with 9.24 assets, not the output of a full Gradle build. The full source integration patch includes the native translator port; build that project to activate it.
