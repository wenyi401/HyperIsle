package art.luaj.hyperisle;

import art.luaj.hyperisle.ext.Config;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookInit implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        String packageName = loadPackageParam.packageName;
        if (packageName.equals(Config.SystemUiPackage)) {
            //ArtIsle isle = new ArtIsle(loadPackageParam);
        }
    }

}
