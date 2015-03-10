package com.akta.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.akta.R;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class TransitionActivity extends Activity {

    @InjectView(R.id.left_box)
    View left;

    @InjectView(R.id.right_box)
    View right;

    @InjectView(R.id.container)
    ViewGroup container;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_activity);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.container)
    public void click() {
        Transition transition = new Fade().addTarget(left).setDuration(1500);
        TransitionManager.beginDelayedTransition(container, transition);
        if (left.getVisibility() == View.GONE) {
            left.setVisibility(View.VISIBLE);
            right.setVisibility(View.VISIBLE);
        } else {
            left.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
        }
    }
}
