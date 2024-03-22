package art.luaj.hyperisle.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import art.luaj.hyperisle.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
    }
}