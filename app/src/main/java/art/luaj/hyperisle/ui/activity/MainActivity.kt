package art.luaj.hyperisle.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import art.luaj.hyperisle.databinding.ActivityMainBinding
import art.luaj.hyperisle.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sp: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
//        sp = getSharedPreferences(Config.APP_DEFAULT_STORAGE, MODE_PRIVATE)
//        if (sp!!.getBoolean("app_init", true)) {
//            startActivity(Intent(this, StartActivity::class.java))
//        }
    }
}