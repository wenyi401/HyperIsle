package art.luaj.hyperisle.ui.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.databinding.ActivityMainBinding;
import art.luaj.hyperisle.ext.BaseActivity;
import art.luaj.hyperisle.ext.Tools;
import art.luaj.hyperisle.util.StatusBarUtil;

public class MainActivity extends BaseActivity {
    private Context context;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            init();
            this.context = this;
            this.binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            NavHostFragment navFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragment);
            NavController navController = navFragment.getNavController();
            BottomNavigationView navView = binding.navView;
            navView.setLabelVisibilityMode(BottomNavigationView.LABEL_VISIBILITY_SELECTED);

            NavigationUI.setupWithNavController(navView, navController);
            Tools.showAlert(this, "你好", true);
        } catch (Exception e) {
            Tools.showAlert(this, e.toString(), false);
        }
    }

    private void init() {
        StatusBarUtil.with(this).setTransparentStatusBar().setTransparentNavigationBar();
    }
}
