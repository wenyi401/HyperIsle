package art.luaj.hyperisle.utils;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class AnimateUtil {

    public static void animateViewSize(final View view, final float startScale, final float endScale, long duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(startScale, endScale);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = (int) (view.getWidth() * animatedValue);
                layoutParams.height = (int) (view.getHeight() * animatedValue);
                view.requestLayout();
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

}
