package art.luaj.hyperisle.ext;

import art.luaj.hyperisle.BuildConfig;

public class Config {
    public static final String DEFAULT_STORAGE = "config_preferences";
    public static String AppPackage = BuildConfig.APPLICATION_ID;
    public static String SystemUiPackage = "com.android.systemui";
    public static String StrongToast = Tools.concat(SystemUiPackage, ".toast.MIUIStrongToast");
    public static String StatusBarGuideModel = Tools.concat(SystemUiPackage, ".toast.bean.StrongToastModel");
    public static String NotificationListenerPackage = Tools.concat(SystemUiPackage, ".statusbar.notification.MiuiNotificationListener");
    public static String DaggerReferenceGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerReferenceGlobalRootComponent");
    public static String DaggerGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerGlobalRootComponent");
    public static String APP_DEFAULT_STORAGE = "app_default_config";
    public static String MOD_DEFAULT_STORAGE = "mod_default_config";
}
