package art.luaj.hyperisle.ext;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

public abstract class BaseFragment extends Fragment {

    public void navigateUp() {
        getNavController().navigateUp();
    }

    public NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }

    public boolean safeNavigate(@IdRes int resId) {
        try {
            getNavController().navigate(resId);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public boolean safeNavigate(NavDirections direction) {
        try {
            getNavController().navigate(direction);
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public void setupToolbar(Toolbar toolbar, View tipsView, int title) {
        setupToolbar(toolbar, tipsView, getString(title), -1);
    }

    public void setupToolbar(Toolbar toolbar, View tipsView, int title, int menu) {
        setupToolbar(toolbar, tipsView, getString(title), menu, null);
    }

    public void setupToolbar(Toolbar toolbar, View tipsView, String title, int menu) {
        setupToolbar(toolbar, tipsView, title, menu, null);
    }

    public void setupToolbar(Toolbar toolbar, View tipsView, String title, int menu, View.OnClickListener navigationOnClickListener) {
        toolbar.setNavigationOnClickListener(navigationOnClickListener == null ? (v -> navigateUp()) : navigationOnClickListener);
        //toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitle(title);
        toolbar.setTooltipText(title);
        if (tipsView != null) tipsView.setTooltipText(title);
        if (menu != -1) {
            toolbar.inflateMenu(menu);
            if (this instanceof MenuProvider self) {
                toolbar.setOnMenuItemClickListener(self::onMenuItemSelected);
                self.onPrepareMenu(toolbar.getMenu());
            }
        }
    }
}
