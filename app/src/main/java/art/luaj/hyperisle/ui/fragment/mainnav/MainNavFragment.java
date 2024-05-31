package art.luaj.hyperisle.ui.fragment.mainnav;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.databinding.FragmentMainNavBinding;
import art.luaj.hyperisle.ext.BaseFragment;

public class MainNavFragment extends BaseFragment<FragmentMainNavBinding> {
    @Override
    protected FragmentMainNavBinding inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMainNavBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void initFragment(FragmentMainNavBinding binding) {
        NavHostFragment navFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.mainNavView);
        NavController navController = navFragment.getNavController();
        BottomNavigationView navView = binding.mainNavBottomNav;
        navView.setLabelVisibilityMode(BottomNavigationView.LABEL_VISIBILITY_SELECTED);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
