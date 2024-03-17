package art.luaj.hyperisle.plugin.Notify;

import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import art.luaj.hyperisle.HookInit;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Config;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
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
    public String getDescription() {
        return null;
    }

    @Override
    public View onBind() {
        return null;
    }

    @Override
    public void onCreate(Context context, XC_LoadPackage.LoadPackageParam packageParam) {
        this.mContext = context;
        this.mPackageParam = packageParam;
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
    }

    @Override
    public void onClick() {

    }

    private Class<?> findClass(String classname) {
        return HookInit.getClass(this.mPackageParam, classname);
    }
}
