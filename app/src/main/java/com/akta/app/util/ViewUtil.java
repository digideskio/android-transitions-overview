package com.akta.app.util;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class ViewUtil {

    private ViewUtil() {
        super();
    }

    public static void onPreDraw(final View view, final ViewTreeObserver.OnPreDrawListener listener) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (view.getViewTreeObserver().isAlive()) {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return listener.onPreDraw();
            }
        });
    }

}
