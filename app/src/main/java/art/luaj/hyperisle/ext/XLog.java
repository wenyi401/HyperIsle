package art.luaj.hyperisle.ext;

import de.robv.android.xposed.XposedBridge;

public class XLog {
    private static String LOG = "[ HyperIsle ]: ";

    /**
     * 打印log内容
     *
     * @param text 文本
     */
    public static void print(String text) {
        XposedBridge.log("---");
        XposedBridge.log(Tools.concat(LOG, text));
    }
}
