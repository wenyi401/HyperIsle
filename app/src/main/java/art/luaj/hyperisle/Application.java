package art.luaj.hyperisle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import art.luaj.hyperisle.ext.BaseApplication;
import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.utils.EncUtil;

public class Application extends BaseApplication {
    private static Application instance;
    private static SharedPreferences pref;

    public static Application getInstance() {
        return instance;
    }

    public static SharedPreferences getPreferences() {
        return instance.pref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        pref = getSharedPreferences(Config.APP_DEFAULT_STORAGE, MODE_PRIVATE);
    }

    public static SharedPreferences getModPreferences() {
        return getInstance().getSharedPreferences(Config.MOD_DEFAULT_STORAGE, Context.MODE_WORLD_READABLE);
    }

    public static SharedPreferences getAppPreferences() {
        return instance.pref;
    }
}
