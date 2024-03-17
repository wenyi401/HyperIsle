package art.luaj.hyperisle.plugin;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

import art.luaj.hyperisle.HookInit;
import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Tools;
import art.luaj.hyperisle.ext.XResources;
import art.luaj.hyperisle.ext.XSharedPre;
import art.luaj.hyperisle.plugin.Media.MediaPlugin;
import art.luaj.hyperisle.plugin.Notify.NotifyPlugin;
import art.luaj.hyperisle.plugin.StrongToast.StrongToastPlugin;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitPlugin {
    private XC_LoadPackage.LoadPackageParam loadPackageParam;
    private Context modContext;
    private BasePlugin modPlugin;
    private WindowManager.LayoutParams mWindow;
    private XSharedPre xSharedPre;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private WindowManager mWindowManager;
    private View mDarkContent; // view
    private int minWidth; // view min width
    private int minHeight; // view min height
    private int mView_x;
    private int mView_y;
    private boolean mSiteMode; // view site
    public InitPlugin(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
        this.modContext = HookInit.modContext;
        this.mWindow =  HookInit.mWindow;
    }

    public static InitPlugin with(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        return new InitPlugin(loadPackageParam);
    }

    public static ArrayList<BasePlugin> getPlugins() {
        ArrayList<BasePlugin> plugins = new ArrayList<BasePlugin>();
        plugins.add(new MediaPlugin()); // add
        plugins.add(new NotifyPlugin()); // add
        plugins.add(new StrongToastPlugin()); //add
        return plugins;
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
        minHeight = dp(40);
        mSiteMode = true;
        mView_x = 0;
        mView_y = 0;
    }

    private void initWindowManager() {
        this.mWindowManager = (WindowManager) modContext.getSystemService(Context.WINDOW_SERVICE);
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
    }

    private WindowManager.LayoutParams initWindowParam() {
        WindowManager.LayoutParams mParams = Tools.getWindowParam(minWidth,  minHeight);
        if (mSiteMode) {
            mParams.gravity = Gravity.TOP | Gravity.CENTER;
        } else {
            mParams.gravity = Gravity.TOP | Gravity.LEFT;
        }
        mParams.x = mView_x;
        mParams.y = mView_y;
        return mParams;
    }

    private void onBind() {
        View view = modPlugin.onBind();
        View bind = this.mDarkContent.findViewById(R.id.vertical_bind);
        if (bind != null) {
            // 刷新时删除上一个
            ViewGroup parent = (ViewGroup) bind.getParent();
            parent.removeView(parent);
            parent.addView(view);
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
            mWindowManager.addView(mDarkContent, mWindow);
            modPlugin.onCreate(this.modContext, this.loadPackageParam);
        }
        return this;
    }

    public InitPlugin setXSharedPre(XSharedPreferences sharedPreferences) {
        //this.xSharedPre  = new XSharedPre(sharedPreferences);
        return this;
    }

    public XSharedPre getXSharedPre() {
        return this.xSharedPre;
    }

    public int dp(int number) {
        return Tools.dp(modContext, number);
    }
}
