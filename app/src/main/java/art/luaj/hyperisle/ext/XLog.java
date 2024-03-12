package art.luaj.hyperisle.ext;

import de.robv.android.xposed.XposedBridge;

public class XLog {
    private static String LOG = "[ HyperIsle ]: ";
    public static void print(String text) {
        XposedBridge.log(Tools.concat(LOG, text));
    }

    public static void alert(String text) {

    }
}
