package art.luaj.hyperisle;

import android.content.SharedPreferences;

import art.luaj.hyperisle.ext.BaseApplication;
import art.luaj.hyperisle.ext.Config;

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
}
