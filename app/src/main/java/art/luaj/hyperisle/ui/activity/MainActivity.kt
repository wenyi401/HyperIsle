package art.luaj.hyperisle.ui.activity

import android.os.Bundle
import art.luaj.hyperisle.databinding.ActivityMainBinding
import art.luaj.hyperisle.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}