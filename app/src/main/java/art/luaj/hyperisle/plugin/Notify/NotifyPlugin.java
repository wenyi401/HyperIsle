package art.luaj.hyperisle.plugin.Notify;

import android.content.Context;
import android.view.View;

import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.plugin.InitPlugin;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotifyPlugin extends BasePlugin {

    private Context mContext;
    private XC_LoadPackage.LoadPackageParam mPackageParam;
    private View mView;

    @Override
    public String getName() {
        return "NotifyPlugin";
    }

    @Override
    public View onBind() {
        return null;
    }

    @Override
    public void onCreate(InitPlugin initPlugin) {
        /*
        this.plugin = initPlugin;
        this.pluginController = this.plugin.getPluginController();
        this.mContext = this.pluginController.getContext();
        this.modContext = this.pluginController.getModContext();
        this.mPackageParam = this.pluginController.getLoadParam();
        XposedHelpers.findAndHookMethod(
            findClass(Config.NotificationListenerPackage),
            "onNotificationPosted",
            StatusBarNotification.class,
            NotificationListenerService.RankingMap.class,
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                }
            });

         */
    }
}
