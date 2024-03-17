package art.luaj.hyperisle.plugin.Media;

import android.content.Context;
import android.view.View;

import art.luaj.hyperisle.ext.BasePlugin;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MediaPlugin extends BasePlugin {
    @Override
    public String getName() {
        return "MediaPlugin";
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

    }

    @Override
    public void onClick() {

    }
}
