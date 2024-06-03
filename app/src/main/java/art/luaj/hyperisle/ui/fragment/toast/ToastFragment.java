package art.luaj.hyperisle.ui.fragment.toast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import art.luaj.hyperisle.databinding.FragmentToastBinding;
import art.luaj.hyperisle.ext.BaseFragment;

public class ToastFragment extends BaseFragment<FragmentToastBinding>  {
    @Override
    protected FragmentToastBinding inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentToastBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void initFragment(FragmentToastBinding binding) {

    }
}
