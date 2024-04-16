package art.luaj.hyperisle.ui.base

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.core.view.WindowCompat
import art.luaj.hyperisle.R
import art.luaj.hyperisle.ui.utils.isSystemInDarkMode
import com.google.android.material.internal.EdgeToEdgeUtils
import rikka.material.app.MaterialActivity

open class BaseActivity : MaterialActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        EdgeToEdgeUtils.applyEdgeToEdge(window, true)
        //initBar()
    }

    override fun onApplyThemeResource(theme: Resources.Theme, resid: Int, first: Boolean) {
        theme.applyStyle(rikka.material.preference.R.style.ThemeOverlay_Rikka_Material3_Preference, true)
    }

    override fun onApplyTranslucentSystemBars() {
        super.onApplyTranslucentSystemBars()
        window.statusBarColor =  Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
    }

    private fun initBar() {
        supportActionBar?.hide()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = isSystemInDarkMode()
            isAppearanceLightNavigationBars = isSystemInDarkMode()
        }
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
        val colorPrimary = typedValue.data
        window.statusBarColor = colorPrimary
    }
}