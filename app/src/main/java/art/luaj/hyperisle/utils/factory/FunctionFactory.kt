package art.luaj.hyperisle.utils.factory

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import art.luaj.hyperisle.BuildConfig

/**
 * 隐藏或显示启动器图标
 *
 * - 你可能需要 LSPosed 的最新版本以开启高版本系统中隐藏 APP 桌面图标功能
 * @param isShow 是否显示
 */
fun Context.hideOrShowLauncherIcon(isShow: Boolean) {
    packageManager.setComponentEnabledSetting(
        ComponentName(packageName, "${BuildConfig.APPLICATION_ID}.Home"),
        if (isShow) PackageManager.COMPONENT_ENABLED_STATE_DISABLED else PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )
}

/**
 * 获取启动器图标状态
 * @return [Boolean] 是否显示
 */
val Context.isLauncherIconShowing
    get() = packageManager?.getComponentEnabledSetting(
        ComponentName(packageName, "${BuildConfig.APPLICATION_ID}.Home")
    ) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
