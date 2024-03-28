package art.luaj.hyperisle.plugin.Battery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import art.luaj.hyperisle.R;
import art.luaj.hyperisle.ext.BasePlugin;
import art.luaj.hyperisle.ext.XLog;
import art.luaj.hyperisle.plugin.InitPlugin;
import art.luaj.hyperisle.plugin.PluginController;
import art.luaj.hyperisle.ui.view.BatteryImageView;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryPlugin extends BasePlugin {
    private InitPlugin plugin;
    private PluginController pluginController;
    private Context mContext;
    private Context modContext;
    private XC_LoadPackage.LoadPackageParam mPackageParam;
    private View mView;
    private BatteryImageView mBatteryImageView;
    private TextView mTextView;
    private LinearLayout mBinded;
    private float batteryPercent;
    @Override
    public String getName() {
        return "BatteryPlugin";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public View onBind() {
        LayoutInflater layoutInflater = (LayoutInflater) this.modContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mView = layoutInflater.inflate(R.layout.layout_battery, null);
        return this.mView;
    }

    @Override
    public void onUnbind() {
        mBinded = null;
    }

    @Override
    public void onCreate(InitPlugin initPlugin) {
        this.plugin = initPlugin;
        this.pluginController = this.plugin.getPluginController();
        this.mContext = this.pluginController.getContext();
        this.modContext = this.pluginController.getModContext();
        this.mPackageParam = this.pluginController.getLoadParam();
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void init() {
        this.mBinded = mView.findViewById(R.id.vertical_bind);
        this.mBatteryImageView = mBinded.findViewById(R.id.vertical_battery);
        this.mTextView = mBinded.findViewById(R.id.vertical_text);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("miui.intent.action.ACTION_SOC_DECIMAL")) {
                XLog.print("电池");
                plugin.queueInsert(BatteryPlugin.this);
                int level = intent.getIntExtra("miui.intent.extra.soc_decimal", 0);
                int scale = intent.getIntExtra("miui.intent.extra.soc_decimal_rate", 0);
                batteryPercent = level * 100 / (float) scale;
                updateView();
            } else {
                plugin.queueRemove(BatteryPlugin.this);
                if (mTextView != null && mBatteryImageView != null) {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, plugin.dp(0));
                    valueAnimator.setDuration(300);
                    valueAnimator.addUpdateListener(valueAnimator1 -> {
                        ViewGroup.LayoutParams p = mBatteryImageView.getLayoutParams();
                        p.width = (int) valueAnimator1.getAnimatedValue();
                        p.height = (int) valueAnimator1.getAnimatedValue();
                        mBatteryImageView.setLayoutParams(p);
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationEnd(animation);
                            mTextView.setVisibility(View.INVISIBLE);
                        }
                    });
                    valueAnimator.start();
                }
            }
        }
    };

    private void updateView() {
        if (mView != null) {
            mTextView.setText((int) batteryPercent + "%");
            mBatteryImageView.updateBatteryPercent(batteryPercent);
            if (batteryPercent > 80) {
                mBatteryImageView.setStrokeColor(Color.GREEN);
                mTextView.setTextColor(Color.GREEN);
            } else if (batteryPercent < 80 && batteryPercent > 20) {
                mBatteryImageView.setStrokeColor(Color.YELLOW);
                mTextView.setTextColor(Color.YELLOW);
            } else {
                mBatteryImageView.setStrokeColor(Color.RED);
                mTextView.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void onClick() {

    }
}
