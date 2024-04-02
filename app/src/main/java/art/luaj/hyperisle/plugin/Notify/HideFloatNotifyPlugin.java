package art.luaj.hyperisle.plugin.Notify;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;

import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.plugin.InitPlugin;
import art.luaj.hyperisle.plugin.PluginController;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HideFloatNotifyPlugin extends BasePlugin {

    private InitPlugin plugin;
    private PluginController pluginController;
    private Context modContext;
    private Context mContext;
    private XC_LoadPackage.LoadPackageParam mPackageParam;
    private View mView;
    private Boolean mFcus;

    @Override
    public String getName() {
        return "HideFloatNotifyPlugin";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public View onBind() {
        return null;
    }

    @Override
    public void onUnbind() {

    }

    @Override
    public void onCreate(InitPlugin initPlugin) {

        this.plugin = initPlugin;
        this.pluginController = this.plugin.getPluginController();
        this.mContext = this.pluginController.getContext();
        this.modContext = this.pluginController.getModContext();
        this.mPackageParam = this.pluginController.getLoadParam();
        XC_MethodHook methodHook = new XC_MethodHook() {
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                boolean notibool =false;
                if(mFcus) {
                    //焦点通知不拦截
                    StatusBarNotification sbn = (StatusBarNotification) XposedHelpers.getObjectField(param.args[0], "mSbn");
                    notibool = (boolean)XposedHelpers.callStaticMethod("com.android.systemui.statusbar.notification.focus.FocusUtils".getClass(), "isFocusNotification", sbn.getNotification());
                }
                //通知弹窗拦截
                XposedHelpers.setBooleanField(param.thisObject,"mDisableFloatNotification",!notibool);
            }
        };
        XposedHelpers.findAndHookMethod(
            this.pluginController.findClass(Config.MiuiNotificationInterruptStateProviderImpl),
            "checkHeadsUp","com.android.systemui.statusbar.notification.collection.NotificationEntry".getClass(),Boolean.class,
            methodHook
        );

    }

    public void setFocusState(boolean focusState){
        this.mFcus=focusState;
    }
    @Override
    public void onClick() {

    }
}
