package art.luaj.hyperisle.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import art.luaj.hyperisle.R
import com.google.android.material.internal.EdgeToEdgeUtils


open class BaseActivity : AppCompatActivity() {
    private var context: Context? = null;
    private var window: Window? = null;
    private var insets: OnApplyWindowInsetsListener? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        init();
    }

    @SuppressLint("RestrictedApi", "WrongConstant")
    fun init() {
        this.context = this
        this.window = getWindow()
        this.insets = OnApplyWindowInsetsListener label@{ v, insets ->
            if (v.getResources()
                    .getConfiguration().orientation !== Configuration.ORIENTATION_LANDSCAPE
            ) {
                return@label insets
            }
            if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
                v.setPadding(
                    insets.getInsets(WindowInsets.Type.systemBars()).left,
                    0,
                    insets.getInsets(WindowInsets.Type.systemBars()).right,
                    insets.getInsets(WindowInsets.Type.systemBars()).bottom
                )
            }
            insets
        }
        EdgeToEdgeUtils.applyEdgeToEdge(this.window!!, true);
        ViewCompat.setOnApplyWindowInsetsListener(this.window!!.getDecorView(), this.insets);
    }
}