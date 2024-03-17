package art.luaj.hyperisle.ext;

public class Config {
    public static String SystemUiPackage = "com.android.systemui";
    public static String StrongToast = Tools.concat(SystemUiPackage, ".toast.MIUIStrongToast");
    public static String StatusBarGuideModel = Tools.concat(SystemUiPackage, ".toast.bean.StrongToastModel");
    public static String NotificationListenerPackage = Tools.concat(SystemUiPackage, ".statusbar.notification.MiuiNotificationListener");

    public static String DaggerReferenceGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerReferenceGlobalRootComponent");
    public static String DaggerGlobalRootComponent = Tools.concat(SystemUiPackage, ".dagger.DaggerGlobalRootComponent");
    public static final String DEFAULT_STORAGE = "config_preferences";
}
