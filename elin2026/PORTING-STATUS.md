# Elin2026 migration checklist

Reference: NODIN Android/Chromebook 9.24 overlay archive (25 October 2026).

| Area | 9.24 reference | Elin2026 status |
|---|---:|---|
| Android host / WebView | Existing host retained via APK overlay | Kotlin host rebuilt; local-origin-only WebView; CI build configured |
| School lessons | 143 | Not yet imported/validated |
| School questions | 596 | Not yet imported/validated |
| Driving theory questions | 1,308 | Quiz engine implemented; bank not yet imported/validated |
| Banking / HMRC tools | Present in source assets | Not yet imported/validated |
| Translator | Floating/continuous translator sources included | Not yet ported |
| Telegram learning reports | Source contains native patches and JS | Not yet ported |
| Offline profiles / backup | IndexedDB-based profiles documented | Native local multi-profile repository implemented; family backup/export not yet ported |
| Release signing | Original key unavailable in source bundle | New Elin2026 keystore generated; stored outside Git; CI signing is opt-in via Actions secrets |
| Hardware validation | Original 9.24 reports hardware test false | Not yet tested on physical devices |

No item should be marked complete until its implementation and regression tests are present in this branch.

## Current implementation\n\n- Persian home dashboard with six module entry points, local search, and module status screens.\n- UI-independent driving theory quiz engine with input validation, one-answer-per-question flow, scoring, and unit tests.\n\n## First clean-room implementation

- `MainActivity.kt`: new Kotlin Activity and WebViewAssetLoader host. File/content access and cleartext traffic are disabled; only appassets origin is accepted.
- `SecureTokenVault.kt`: AES-GCM secrets encrypted with a non-exportable Android Keystore key; ciphertext only is persisted.
- `ProfileRepository.kt`: local child profiles, active-profile switching, rename and delete; malformed profile storage is not silently overwritten.
- `LocalDataSecurityTest.kt`: Android instrumentation tests for vault encryption and profile isolation.
- `BuildIdentityTest.kt`: asserts the new application identity is distinct from legacy NODIN.
- GitHub Actions builds the debug APK, runs JVM tests and emulator instrumentation tests; release signing is enabled only when protected secrets are supplied.

The new keystore certificate fingerprint is recorded in `SIGNING.md`. The private keystore is not in this repository.
