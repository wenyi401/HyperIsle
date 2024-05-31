package art.luaj.hyperisle.ui.fragment.mainnav.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import art.luaj.hyperisle.databinding.FragmentHomeBinding;
import art.luaj.hyperisle.ext.BaseFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    protected FragmentHomeBinding inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentHomeBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void initFragment(FragmentHomeBinding binding) {

    }
}
