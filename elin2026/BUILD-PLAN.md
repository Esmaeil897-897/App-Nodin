# Elin2026 build plan

## Milestone 1 — independent app foundation
- [x] Separate `Elin2026` branch and Android application identity
- [x] Security-first local WebView host
- [x] Local profile repository and encrypted token vault
- [x] Persian module dashboard with search and navigation
- [x] CI workflow definition

## Milestone 2 — learning and theory
- [x] UI-independent driving theory quiz session engine
- [ ] Import and validate the 1,308-question reference bank
- [ ] Connect question bank to the quiz screen
- [ ] Add answer review, explanations and progress persistence
- [ ] Port the 143 school lessons and 596 school questions

## Milestone 3 — utilities
- [ ] Port translator and offline language assets
- [ ] Port banking/HMRC tools after dependency and privacy review
- [ ] Review Telegram/report integrations and permissions
- [ ] Add export/import and backup with explicit user control

## Release gates
- Debug build and JVM tests pass in CI
- Android emulator instrumentation tests pass
- Question-bank counts, schema, media references and answer indices validated
- Manual testing on Android phone and Chromebook
- Release signing uses only the new Elin2026 key; never the legacy NODIN key

The 9.24 source archive is a patch/asset bundle, not a complete Android Studio project. It requires a separately supplied 9.23 base APK and the original signing key to reproduce the historical APK. Elin2026 is therefore being rebuilt as an independent application; it will not overwrite or impersonate the legacy package.
