package art.luaj.hyperisle.ui.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceFragmentCompat
import art.luaj.hyperisle.R
import art.luaj.hyperisle.databinding.FragmentSettingsBinding
import art.luaj.hyperisle.ext.Tools
import art.luaj.hyperisle.ui.base.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding, ViewModel>(FragmentSettingsBinding::inflate, null) {
    override fun initFragment(binding: FragmentSettingsBinding, viewModel: ViewModel?) {
        childFragmentManager.beginTransaction().add(R.id.setting_container, PreferenceFragment()).commitNow()
    }

    class PreferenceFragment : PreferenceFragmentCompat() {

        public fun getKey(Text: CharSequence): CharSequence? {
            return Tools.concat("system_preference_", Text);
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.prefs_settings);
            /*
            val hideIcon = findPreference(getKey("hide_icon"));
            if (hideIcon != null) {
                hideIcon.setOnPreferenceClickListener {
                    // 处理点击事件
                    true
                }
            }
             */
        }

    }
}