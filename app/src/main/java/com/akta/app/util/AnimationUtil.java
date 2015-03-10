package com.akta.app.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.internal.VersionUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class AnimationUtil {

    private AnimationUtil() {
        super();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Interpolator interpolator() {
        Interpolator interpolator = null;
        if (VersionUtils.isAtLeastL()) {
            //Fast out slow in
            interpolator = new PathInterpolator(0.4f, 0f, 0.2f, 1f);
        } else {
            interpolator = new DecelerateInterpolator();
        }
        return interpolator;
    }
}
