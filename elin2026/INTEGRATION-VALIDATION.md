# Elin2026 integration validation

Date: 2026-10-31

## Source-patch application

The delivered `Elin2026-Source-Integration-Patch.zip` was extracted and applied to the downloaded `Nodin-9.17-source.zip` using its own `apply_to_nodin917.py` script.

- Result: successful
- Prepared project: 2,219 files
- Driving theory data: 1,308 questions detected
- School lesson data: 143 lessons detected
- Classic autosave regression test: passed (restore, reset, deletion, cache exclusion, clear, empty snapshot and initial migration)

## Build limitation in this environment

A full Android Gradle build was not run. This environment has no Android SDK Platform 35 and no Gradle installation; the available Java runtime is 21 rather than the project's required Java 17. Therefore no full-source APK or emulator pass is claimed.

## GitHub CI scope

The GitHub Actions run [41184714128](https://github.com/Esmaeil897-897/App-Nodin/actions/runs/41184714128) built the lightweight scaffold committed in the `Elin2026` branch, not the 9.17 + 9.24 merged source tree. Debug APK build and unit tests succeeded. Instrumentation did not complete: the emulator reported `adb: device offline`, then the instrumentation process crashed before any tests completed. The workflow was updated in commit `587ee72c22df448c109a5961368b6e5857a747a5` with explicit Google APIs target, fresh AVD creation, longer boot timeout, software GPU, disabled snapshots/animations, and clean data. Validation of that change is running at [41188269286](https://github.com/Esmaeil897-897/App-Nodin/actions/runs/41188269286).

## Next integration gate

The merged source project and its binary asset overlay must be made available to the GitHub Actions runner before CI can compile that project. The patch archive remains a separately delivered artifact; it has not been represented as committed source in this branch. Keep NODIN LEGACY branches and the original signing identity untouched.


## Direct full-source build attempt (2026-10-31)

The original 9.17 Actions source artifact was downloaded again and the integration patch was applied to a fresh output directory. The resulting project contained 2,219 files.

Additional checks passed:
- The patch application script completed successfully.
- The app Gradle configuration was corrected: the inherited 9.17 `app/build.gradle` Groovy file is removed during patch application so Gradle cannot silently prefer it over Elin2026's `app/build.gradle.kts`.
- Elin2026 application ID and version settings are present in the selected Kotlin DSL build file.
- Python syntax checks passed.
- `node tests/classic-autosave.test.js` passed.
- Asset data checks found 1,308 theory questions and 143 school lessons.

A real `:app:assembleDebug` was attempted with `-PskipTranslatorModels`, but Gradle could not start because the environment has no cached Gradle 8.11.1 distribution and outbound DNS/network access is unavailable (`curl: (6) Could not resolve host: services.gradle.org`). No Android SDK Platform 35 or Android Build Tools are installed in this environment either. Therefore **no new APK was produced from this full source project**. The previously delivered preview APK remains a repackaged 9.17 APK and must not be described as a fresh source build.

A corrected patch archive is supplied separately as `Elin2026-Source-Integration-Patch-Fixed.zip`, SHA-256 `592a29a1940b2d96c68c9e543b414aebb257238f74a30ffd870b8906ddcb61b4`.
