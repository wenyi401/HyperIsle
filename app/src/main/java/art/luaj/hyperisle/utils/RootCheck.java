package art.luaj.hyperisle.utils;

public class RootCheck {
    /*
    private static final String[] rootRelatedDirs = new String[]{
        "/su", "/su/bin/su", "/sbin/su",
        "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su",
        "/system/xbin/su",
        "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su",
        "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr",
        "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd",
        "/system/bin/conbb", "/system/xbin/conbb"};
*/
    public static boolean hasRootPrivilege() {
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
