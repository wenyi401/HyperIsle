package art.luaj.hyperisle.ext;

import android.view.View;

import art.luaj.hyperisle.plugin.InitPlugin;

public abstract class BasePlugin {
    public abstract String getName();

    public abstract String getDescription();

    public abstract View onBind(); // 绑定 view

    public abstract void onUnbind(); // 解绑

    public abstract void onCreate(InitPlugin initPlugin); // 窗口创建

    public abstract void onClick(); // 点击事件

    public void onTouch() {
    } // 触摸事件

    public void onLongClick() {
    } // 长按事件

    public void onLeftClick() {
    } // 左击（x）

    public void onRightClick() {
    }  //  右击（x）
}
