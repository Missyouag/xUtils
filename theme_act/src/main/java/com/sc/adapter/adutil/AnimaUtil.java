package com.sc.adapter.adutil;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Administrator on 2015/12/10.
 */
public class AnimaUtil {

    private static final long ANIMATION_DURATION = 1000;

    public interface AnimaCall {
        public void Call();
    }

    public static void deleteCell(final View v, final AnimaCall animaCall) {
        Animation.AnimationListener al = null;
        if (null != animaCall) al = new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                v.setVisibility(View.VISIBLE);
                animaCall.Call();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        };
        collapse(v, al);
    }

    public static void collapse(final View v, Animation.AnimationListener al) {
        Animation anim = new Animation() {
            int initialHeight = v.getMeasuredHeight();

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.getLayoutParams().height = initialHeight;
                    v.requestLayout();
                    v.clearAnimation();
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        if (null != al) {
            anim.setAnimationListener(al);
        }
        anim.setDuration(ANIMATION_DURATION);
        v.startAnimation(anim);
    }
}
