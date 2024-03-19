package art.luaj.hyperisle.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.Tools;
import art.luaj.hyperisle.ext.XSharedPre;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitPlugin {
    public int minWidth; // view min width
    public int minHeight; // view min height
    IntentFilter filter = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
    private PluginController mPluginController;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private Context mContext;
    private Context modContext;
    private WindowManager.LayoutParams mWindow;
    private XSharedPre xSharedPre;
    private ArrayList<String> mQuened = new ArrayList<>();
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private WindowManager mWindowManager;
    private View mDarkContent; // view
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
                int orientation = context.getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mDarkContent.setVisibility(View.INVISIBLE);
                } else {
                    mDarkContent.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    private int mView_x;
    private int mView_y;
    private boolean mSiteMode; // view site

    public InitPlugin(PluginController pluginController) {
        this.mPluginController = pluginController;
        this.loadPackageParam = this.mPluginController.getLoadParam();
        this.mContext = this.mPluginController.getContext();
        this.modContext = this.mPluginController.getModContext();
    }

    public PluginController getPluginController() {
        return this.mPluginController;
    }

    public XSharedPre getXSharedPre() {
        return this.xSharedPre;
    }

    public InitPlugin setXSharedPre(XSharedPreferences sharedPreferences) {
        //this.xSharedPre  = new XSharedPre(sharedPreferences);
        return this;
    }

    public int dp(int number) {
        return Tools.dp(modContext, number);
    }

    private void initArg() {
        /*
        XSharedPre sp = getXSharedPre();
        if (minWidth == 0) {
            minWidth = dp(sp.optInt("overlay_w", 100));
        }
        if (minHeight == 0) {
            minHeight = dp(sp.optInt("overlay_h", 40));
        }

        mSiteMode = sp.optBoolean("overlay_site", true);

        if (mView_x == 0) {
            mView_x = (int) (sp.optFloat("overlay_x", 0) * 0.01f * displayMetrics.widthPixels);
        }
        if (mView_y == 0) {
             mView_y = (int) (sp.optFloat("overlay_y", 0.67f) * 0.01f * displayMetrics.heightPixels);
        }
        */
        minWidth = dp(100);
        minHeight = dp(35);
        mSiteMode = true;
        mView_x = 0;
        mView_y = dp(3);
    }

    private void initWindowManager() {
        this.mWindowManager = (WindowManager) modContext.getSystemService(Context.WINDOW_SERVICE);
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
    }

    private WindowManager.LayoutParams initWindowParam() {
        WindowManager.LayoutParams mParams = Tools.getWindowParam(minWidth, minHeight);
        if (mSiteMode) {
            mParams.gravity = Gravity.TOP | Gravity.CENTER;
        } else {
            mParams.gravity = Gravity.TOP | Gravity.LEFT;
        }
        int flags = mParams.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
        mParams.flags = flags;
        mParams.x = mView_x;
        mParams.y = mView_y;
        return mParams;
    }

    private void onBind() {
        View bind = this.mDarkContent.findViewById(R.id.vertical_bind);
        if (bind != null) {
            // 刷新时删除上一个
            ViewGroup parent = (ViewGroup) bind.getParent();
            parent.removeView(parent);
            //parent.addView(view);
            this.mWindowManager.updateViewLayout(this.mDarkContent, this.mWindow);
        }
    }

    public InitPlugin init() {
        if (mDarkContent == null && mWindowManager == null) {
            initArg();
            initWindowManager();
            mWindow = initWindowParam();
            LayoutInflater layoutInflater = (LayoutInflater) modContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDarkContent = layoutInflater.inflate(R.layout.overlay_main, null);
            LinearLayout layout = this.mDarkContent.findViewById(R.id.vertical_main);
            mContext.registerReceiver(broadcastReceiver, filter);
            XposedBridge.log("a:" + mContext + " b:" + modContext + " c:" + loadPackageParam);
            //  排队
            // Optional<BasePlugin> optionalBasePlugin  = getPlugins().stream().filter(x -> x.getName().equals(mQuened.get(0))).findFirst();
            StrongToastPlugin strongToastPlugin = new StrongToastPlugin();
            strongToastPlugin.onCreate(this);

            layout.addView(strongToastPlugin.onBind());
            mWindowManager.addView(layout, mWindow);
        }
        return this;
    }
}
