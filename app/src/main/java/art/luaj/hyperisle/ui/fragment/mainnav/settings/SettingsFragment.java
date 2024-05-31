package art.luaj.hyperisle.ui.fragment.mainnav.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.preference.PreferenceFragmentCompat;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.databinding.FragmentSettingsBinding;
import art.luaj.hyperisle.ext.BaseFragment;

public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> {
    @Override
    protected FragmentSettingsBinding inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSettingsBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void initFragment(FragmentSettingsBinding binding) {
    }

    public static class PreferenceFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.prefs_settings, rootKey);
        }
    }
}
