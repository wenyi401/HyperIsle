package art.luaj.hyperisle.ui.utils

import android.content.Context
import android.content.res.Configuration

/**
 * 系统深色模式是否开启
 * @return [Boolean] 是否开启
 */
fun Context.isSystemInDarkMode(): Boolean {
    return (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}
