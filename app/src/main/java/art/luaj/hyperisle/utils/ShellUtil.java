package art.luaj.hyperisle.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ShellUtil {

    /**
     * 执行命令
     *
     * @param command 命令
     * @param isSu    设置是否su执行
     */
    public static void exec(String command, Boolean isSu) {
        try {
            if (isSu) {
                Process p = null;
                p = Runtime.getRuntime().exec("su");
                OutputStream outputStream = p.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(command);
                dataOutputStream.flush();
                dataOutputStream.close();
            } else {
                Runtime.getRuntime().exec(command);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 卸载应用
     * @param packageName 包名
     * @return 状态
     */
    public static boolean uninstallApk(String packageName) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "pm uninstall " + packageName});
            if (p == null) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
