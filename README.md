# English with Nodin — Android

Capacitor Android build of the single-file English with Nodin app. The committed index.html.gz is unpacked to www/index.html before `cap add`/`cap sync`.

Open **Actions → Build Android APK (Capacitor)**, select the newest successful run, then download **English-with-Nodin-debug-apk**. Extract the artifact ZIP to get `app-debug.apk` and install it on Android. This is a debug-signed APK for testing, not a Play Store release.

The app has offline pages and online features (radio, news, translation, weather). The embedded web runtime is Android System WebView, as required by Capacitor. App data stays in the Android app storage. Export a JSON backup before uninstalling.
