package art.luaj.hyperisle.ext;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.color.DynamicColors;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
