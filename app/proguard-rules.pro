-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
}

-obfuscationdictionary dictionary-rules.txt
-classobfuscationdictionary dictionary-rules.txt
-packageobfuscationdictionary dictionary-rules.txt
-optimizationpasses 5

-keep class art.luaj.hyperisle.HookInit
-keep class art.luaj.hyperisle.plugin.*