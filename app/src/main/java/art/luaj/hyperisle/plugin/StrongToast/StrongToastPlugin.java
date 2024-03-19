package art.luaj.hyperisle.plugin.StrongToast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.Config;
import art.luaj.hyperisle.plugin.InitPlugin;
import art.luaj.hyperisle.plugin.PluginController;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StrongToastPlugin extends BasePlugin {
    private InitPlugin plugin;
    private PluginController pluginController;
    private Context mContext;
    private Context modContext;
    private XC_LoadPackage.LoadPackageParam mPackageParam;
    private View mView;
    private ImageView mImageView;
    private TextView mTextView;
    private LinearLayout mBinded;

    @Override
    public String getName() {
        return "StrongToastPlugin";
    }

    @Override
    public View onBind() {
        LayoutInflater layoutInflater = (LayoutInflater) this.modContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = layoutInflater.inflate(R.layout.layout_strongtoast, null);
        init();
        return this.mView;
    }

    /*
        @Override
        public String getDescription() {
            return "澎湃系统strong提示";
        }

        @Override
        public View onBind() {
            return new LinearLayout(mContext);
        }


     */
    @Override
    public void onCreate(InitPlugin initPlugin) {
        this.plugin = initPlugin;
        this.pluginController = this.plugin.getPluginController();
        this.mContext = this.pluginController.getContext();
        this.modContext = this.pluginController.getModContext();
        this.mPackageParam = this.pluginController.getLoadParam();
        reOnPreDrawListener();
    }

    private void init() {
        this.mBinded = mView.findViewById(R.id.vertical_bind);
        this.mImageView = mView.findViewById(R.id.vertical_image);
        this.mTextView = mView.findViewById(R.id.vertical_text);
        XC_MethodHook methodHook = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                String mCurrentToastCategory = (String) XposedHelpers.getObjectField(param.thisObject, "mCurrentToastCategory");
                if (mCurrentToastCategory.equals("text_bitmap")) {
                    TextView mLeftTextView = (TextView) XposedHelpers.getObjectField(param.thisObject, "mLeftTextView");
                    ImageView mRightImageView = (ImageView) XposedHelpers.getObjectField(param.thisObject, "mRightImageView");
                    mImageView.setImageDrawable(mRightImageView.getDrawable());
                    mImageView.setMaxHeight(plugin.dp(30));
                    mImageView.setMaxWidth(plugin.dp(30));
                    mTextView.setText(mLeftTextView.getText());
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, plugin.dp(150));
                    valueAnimator.setDuration(300);
                    valueAnimator.addUpdateListener(valueAnimator1 -> {
                        ViewGroup.LayoutParams p = mBinded.getLayoutParams();
                        p.width = (int) valueAnimator1.getAnimatedValue();
                        mBinded.setLayoutParams(p);
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mTextView.setVisibility(View.VISIBLE);
                            mBinded.requestLayout();
                        }
                    });
                    valueAnimator.start();
                }

            }
        };

        XposedHelpers.findAndHookMethod(
            this.pluginController.findClass(Config.StrongToast),
            "setValue",
            methodHook
        );
    }

    private void reOnPreDrawListener() {
        XposedHelpers.findAndHookMethod(
            this.pluginController.findClass(Config.StrongToast),
            "onAttachedToWindow",
            new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                    final Object thisObject = param.thisObject;
                    final FrameLayout toast = (FrameLayout) thisObject;
                    toast.getViewTreeObserver()
                        .addOnPreDrawListener(
                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {
                                    // XposedHelpers.setBooleanField(thisObject, "mCheckStartAnimation", true);
                                    // toast.getViewTreeObserver().removeOnPreDrawListener(this);
                                    return false;
                                }
                            });
                }
            });
    }
}
