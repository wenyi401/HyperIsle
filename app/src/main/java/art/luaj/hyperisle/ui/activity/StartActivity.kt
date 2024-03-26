package art.luaj.hyperisle.ui.activity

import android.os.Bundle
import android.view.View
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.ActivityStartBinding
import art.luaj.hyperisle.ext.Tools
import art.luaj.hyperisle.ui.base.BaseActivity

class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        binding = ActivityStartBinding.inflate(layoutInflater)

        setContentView(binding.root)
        findViewById<View>(R.id.app_grant_root).setOnClickListener { Tools.exec("su", false) }
    }
}
