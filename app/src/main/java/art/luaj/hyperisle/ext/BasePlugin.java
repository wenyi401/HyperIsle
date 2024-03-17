package art.luaj.hyperisle.ext;

import android.content.Context;
import android.view.View;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class BasePlugin {
    public abstract String getName();
    public abstract String getDescription();
    public abstract View onBind(); // 绑定view
    public abstract void onCreate(Context context, XC_LoadPackage.LoadPackageParam packageParam); // 窗口创建
    public abstract void onClick(); // 点击事件
    public void onTouch() {} // 触摸事件
    public void onLongClick() {} // 长按事件
    public void onLeftClick() {} // 左击（x）
    public void onRightClick() {}  //  右击（x）

}
