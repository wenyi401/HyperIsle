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
import java.util.Objects;
import java.util.Optional;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Tools;
import art.luaj.hyperisle.ext.XLog;
import art.luaj.hyperisle.ext.XSharedPre;
import art.luaj.hyperisle.plugin.Battery.BatteryPlugin;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitPlugin {
    public int minWidth; // view min width
    public int minHeight; // view min height
    public WindowManager.LayoutParams mWindow; // window
    public WindowManager mWindowManager;
    public View mDarkContent; // view main
    public boolean mSiteMode; // view site
    private IntentFilter filter = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
    private BasePlugin bindPlugin;
    private PluginController pluginController;
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private Context context;
    private Context modContext;
    private XSharedPre xsharedPre;
    private ArrayList<String> mQuened = new ArrayList<>();
    private ArrayList<BasePlugin> mPlugin = PluginData.getPlugins();
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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

    public InitPlugin(PluginController pluginController) {
        this.pluginController = pluginController;
        this.loadPackageParam = this.pluginController.getLoadParam();
        this.context = this.pluginController.getContext();
        this.modContext = this.pluginController.getModContext();
    }

    public PluginController getPluginController() {
        return this.pluginController;
    }

    public XSharedPre getXSharedPre() {
        return this.xsharedPre;
    }

    public InitPlugin setXSharedPre(XSharedPreferences sharedPreferences) {
        //this.xSharedPre  = new XSharedPre(sharedPreferences);
        return this;
    }

    public int dp(int number) {
        return Tools.dp(modContext, number);
    }

    public void queueInsert(BasePlugin plugin) {
        if (!mQuened.contains(plugin.getName())) {
            if (bindPlugin != null && mPlugin.indexOf(plugin) <  mPlugin.indexOf(bindPlugin)) {
                mQuened.add(0,  plugin.getName());
            } else {
                mQuened.add(plugin.getName());
            }
        }
        bindPlugin();
    }

    public void queueRemove(BasePlugin plugin) {
        if (!mQuened.contains(plugin.getName())) {
            return;
        } else {
            mQuened.remove(plugin.getName());
        }
        if (bindPlugin != plugin && bindPlugin.getName().equals(plugin.getName())) {
            bindPlugin = null;
        }
        bindPlugin();
    }

    private void bindPlugin() {
        if (mQuened.size() <= 0) {
            if (bindPlugin != null) {
                bindPlugin.onUnbind();
                return;
            }
        }

        if (bindPlugin != null && mQuened.get(0).equals(bindPlugin.getName())) { return; }
        if (bindPlugin != null) {  bindPlugin.onUnbind(); }
        Optional<BasePlugin> optionalBasePlugin = mPlugin.stream().filter(x -> x.getName().equals(mQuened.get(0))).findFirst();
        if (optionalBasePlugin.isPresent()) {  return; }
        bindPlugin = optionalBasePlugin.get();
        bindPlugin.onCreate(this);
        View view = bindPlugin.onBind();
        View bind = view.findViewById(R.id.vertical_bind);
        XLog.print("布局=" + bind.toString());
        if (bind != null) {
            // 刷新时删除上一个
            ViewGroup parent = (ViewGroup) bind.getParent();
            parent.removeView(parent);
            parent.addView(view);
            this.mWindowManager.updateViewLayout(bind, mWindow);
        }
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

    public InitPlugin init() {
        if (mDarkContent == null && mWindowManager == null) {
            initArg();
            initWindowManager();
            mWindow = initWindowParam();
            LayoutInflater layoutInflater = (LayoutInflater) modContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mDarkContent = layoutInflater.inflate(R.layout.overlay_main, null);
            LinearLayout layout = this.mDarkContent.findViewById(R.id.vertical_main);
            context.registerReceiver(broadcastReceiver, filter);
            mWindowManager.addView(layout, mWindow);
            /*
            BasePlugin basePlugin = new BatteryPlugin();
            basePlugin.onCreate(this);

             */
        }
        return this;
    }
}
