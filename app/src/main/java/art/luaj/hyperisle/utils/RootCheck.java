package art.luaj.hyperisle.utils;

import java.io.IOException;

public class RootCheck {

    /**
     * 获取root状态
     * @return code = 0为root权限
     */
    public static int hasRootPrivilege() {
            Process process = null;
            int exitCode = -1;
            try {
                process = Runtime.getRuntime().exec("su -c true");
                return process.waitFor();
            } catch (IOException | InterruptedException e) {
                return exitCode;
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
    }
}
