package art.luaj.hyperisle.ui.fragment.start;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.databinding.FragmentStartBinding;
import art.luaj.hyperisle.ext.BaseFragment;
import art.luaj.hyperisle.ext.Tools;
import art.luaj.hyperisle.ui.view.dialog.BlurDialogBuilder;
import art.luaj.hyperisle.utils.RootCheck;


public class StartFragment extends BaseFragment<FragmentStartBinding> {
    private Chip appGrantRoot; // root
    private View rootView;
    private FragmentStartBinding binding;
    private BroadcastReceiver myReceiver;

    @Override
    protected FragmentStartBinding inflate(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStartBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void initFragment(FragmentStartBinding arg) {
        this.binding = arg;
        registerReceiver();
        this.appGrantRoot = binding.appGrantRoot;
        appGrantRoot.setOnClickListener(v -> {
            try {
                Tools.exec("su", false);
            } catch (Exception e) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        int checks = 0;
        // 设置状态
        if (checkRoot()) {
            appGrantRoot.setChecked(true);
            checks++;
        } else {
            appGrantRoot.setChecked(false);
        }
        if (checks >= 2) {
            NavController navController = Navigation.findNavController(rootView);
            navController.navigate(R.id.mainNavFragment);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.rootView = view;
        //NavController navController = Navigation.findNavController(view);
        //navController.navigate(R.id.mainNavFragment);
    }

    private boolean checkRoot() {
        return RootCheck.hasRootPrivilege();
    }

    /**
     * 判断网络
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        getContext().registerReceiver(myReceiver, filter);
    }

    /**
     * 关闭判断
     */
    private void unregisterReceiver() {
        getContext().unregisterReceiver(myReceiver);
    }

    class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            AlertDialog dialog = new BlurDialogBuilder(getActivity()).setMessage(R.string.app_link_error).create();
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                dialog.show();
            }
        }
    }
}
