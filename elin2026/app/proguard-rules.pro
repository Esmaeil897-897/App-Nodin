# Keep JavaScript bridge names stable when explicit bridge methods are added.
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
