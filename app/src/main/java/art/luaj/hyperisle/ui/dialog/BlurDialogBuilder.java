package art.luaj.hyperisle.ui.dialog;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.function.Consumer;

public class BlurDialogBuilder extends MaterialAlertDialogBuilder {
    public BlurDialogBuilder(@NonNull Context context) {
        super(context);
    }

    public BlurDialogBuilder(@NonNull Context context, int overrideThemeResId) {
        super(context, overrideThemeResId);
    }

    @Override
    public AlertDialog create() {
        AlertDialog dialog = super.create();
        setupWindowBlurListener(dialog);
        return dialog;
    }

    private void setupWindowBlurListener(AlertDialog dialog) {
        Window window = dialog.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            Consumer<Boolean> windowBlurEnabledListener = enabled -> updateWindowForBlurs(window, enabled);
            window.getDecorView().addOnAttachStateChangeListener(
                    new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View view) {
                            window.getWindowManager().addCrossWindowBlurEnabledListener(
                                    windowBlurEnabledListener);
                        }

                        @Override
                        public void onViewDetachedFromWindow(View view) {
                            window.getWindowManager().removeCrossWindowBlurEnabledListener(
                                    windowBlurEnabledListener);
                        }
                    });
        }
    }

    private void updateWindowForBlurs(Window window, boolean blursEnabled) {
        float mDimAmountWithBlur = 0.1f;
        float mDimAmountNoBlur = 0.35f;
        window.setDimAmount(blursEnabled ? mDimAmountWithBlur : mDimAmountNoBlur);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            window.getAttributes().setBlurBehindRadius(20);
            window.setAttributes(window.getAttributes());
        }
    }
}
