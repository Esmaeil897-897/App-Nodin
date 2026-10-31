# Elin2026 migration checklist

Reference: NODIN Android/Chromebook 9.24 overlay archive (25 October 2026).

| Area | 9.24 reference | Elin2026 status |
|---|---:|---|
| Android host / WebView | Existing host retained via APK overlay | Rebuilt cleanly in Kotlin; scaffold only |
| School lessons | 143 | Not yet imported/validated |
| School questions | 596 | Not yet imported/validated |
| Driving theory questions | 1,308 | Not yet imported/validated |
| Banking / HMRC tools | Present in source assets | Not yet imported/validated |
| Translator | Floating/continuous translator sources included | Not yet ported |
| Telegram learning reports | Source contains native patches and JS | Not yet ported |
| Offline profiles / backup | IndexedDB-based profiles documented | Not yet ported |
| Release signing | Original key unavailable in source bundle | New Elin2026 key to be created and stored outside Git |
| Hardware validation | Original 9.24 reports hardware test false | Not yet tested on physical devices |

No item should be marked complete until its implementation and regression tests are present in this branch.
