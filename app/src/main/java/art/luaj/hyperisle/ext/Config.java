package art.luaj.hyperisle.ext;

public class Config {
    public static String SystemUiPackage = "com.android.systemui";
    public static String DaggerReferenceGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerReferenceGlobalRootComponent");
    public static String DaggerGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerGlobalRootComponent");
    public static final String DEFAULT_STORAGE = "config_preferences";
}
