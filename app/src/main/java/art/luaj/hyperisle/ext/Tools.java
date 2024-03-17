package art.luaj.hyperisle.ext;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ui.dialog.BlurDialogBuilder;
import de.robv.android.xposed.XposedBridge;

public class Tools {
    public static void showAlert(Context context, String text, boolean cancelable) {
        AlertDialog dialog = new BlurDialogBuilder(context).setTitle(R.string.dialog_alert_tip).setMessage(text).setCancelable(cancelable).create();
        dialog.show();
    }
    
    public static String concat(CharSequence... charSequenceArr) {
        try {
            return TextUtils.concat(charSequenceArr).toString();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            for (CharSequence charSequence : charSequenceArr) {
                sb.append(charSequence);
            }
            return sb.toString();
        }
    }

    public static String toJson(Object obj) {
        JSONObject jsonObject = new JSONObject(getFieldMap(obj));
        return jsonObject.toString();
    }

    private static Map<String, Object> getFieldMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    if (value.getClass().getName().startsWith("java")) {
                        map.put(field.getName(), value);
                    } else {
                        map.put(field.getName(), getFieldMap(value));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static void exec(String command, Boolean isSu) {
        try {
            if (isSu) {
                Process p = Runtime.getRuntime().exec("su");
                OutputStream outputStream = p.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(command);
                dataOutputStream.flush();
                dataOutputStream.close();
                return;
            } else {
                Runtime.getRuntime().exec(command);
            }
        } catch (Throwable e) {
        }
    }

    public static boolean joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(
                "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D"
                        + key));
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    public static WindowManager.LayoutParams getWindowParam(int width, int height) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, height, 2017, 0x01800738, -3);
        try {
            Method setTrustedOverlay = layoutParams.getClass().getMethod("setTrustedOverlay");
            setTrustedOverlay.setAccessible(true);
            setTrustedOverlay.invoke(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return layoutParams;
    }

    public static int dp(Context context ,int number) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, number, context.getResources().getDisplayMetrics());
    }
}
