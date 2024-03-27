package art.luaj.hyperisle.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import art.luaj.hyperisle.R
import art.luaj.hyperisle.ui.utils.isSystemInDarkMode
import com.google.android.material.internal.EdgeToEdgeUtils

open class BaseActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant", "RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        EdgeToEdgeUtils.applyEdgeToEdge(window, true)
        initBar()
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