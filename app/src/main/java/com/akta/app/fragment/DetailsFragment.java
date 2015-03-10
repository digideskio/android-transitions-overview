package com.akta.app.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.internal.VersionUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.akta.R;
import com.akta.app.util.AnimationUtil;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class DetailsFragment extends Fragment {

    private static final String ARGS_ID = "id";

    @InjectView(R.id.image_view)
    ImageView imageView;

    @InjectView(R.id.title)
    TextView titleField;

    @InjectView(R.id.text_content)
    TextView contentField;

    private int id;

    public static String headerTransitionName(int id) {
        return id + "_header_transition";
    }

    public static DetailsFragment newInstance(int id) {

        DetailsFragment fragment = new DetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ARGS_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    public DetailsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ARGS_ID);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.inject(this, view);
        titleField.setText("Item " + id);

        if (VersionUtils.isAtLeastL()) {
            imageView.setTransitionName(headerTransitionName(id));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!VersionUtils.isAtLeastL()) {
            revealContent();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void revealContent() {
        if (VersionUtils.isAtLeastL()) {
            int cx = 0;
            int cy = (titleField.getTop() + titleField.getBottom()) / 2;
            int finalRadius = Math.max(titleField.getWidth(), titleField.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(titleField, cx, cy, 0, finalRadius);
            animator.setDuration(getResources().getInteger(R.integer.anim_duration));
            animator.setInterpolator(AnimationUtil.interpolator());
            titleField.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            titleField.setVisibility(View.VISIBLE);
            titleField.setAlpha(0);
            titleField.animate()
                    .alpha(1)
                    .setDuration(getResources().getInteger(R.integer.anim_duration))
                    .setInterpolator(AnimationUtil.interpolator())
                    .start();
        }

        contentField.animate()
                .alpha(1)
                .setDuration(getResources().getInteger(R.integer.anim_duration))
                .setInterpolator(AnimationUtil.interpolator())
                .start();
    }
}
