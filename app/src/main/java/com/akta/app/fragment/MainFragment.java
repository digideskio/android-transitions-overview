package com.akta.app.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.internal.VersionUtils;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.akta.R;
import com.akta.app.util.AnimationUtil;
import com.akta.app.util.SimpleTransitionListener;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class MainFragment extends Fragment {

    @InjectView(R.id.list_view)
    ListView listView;

    public MainFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.list_item,
                        android.R.id.text1,
                        new String[]{"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"}));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transitionToDetails(view, position);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void transitionToDetails(View view, int position) {

        final DetailsFragment detailsFragment = DetailsFragment.newInstance(position);
        View imageView = view.findViewById(R.id.image_view);

        if (VersionUtils.isAtLeastL()) {
            //Setup how any non shared elements in the current fragment should animate out
            setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));

            //Setup how any shared elements should animate into the next fragment
            detailsFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_move));

            //Setup how the content elements should animate
            final TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(
                    new Slide(Gravity.TOP)
                            .addTarget(R.id.header_container)
                                    //Why do i have to exclude this?
                            .excludeTarget(R.id.content_container, true));

            transitionSet.addTransition(
                    new Slide(Gravity.BOTTOM)
                            .addTarget(R.id.content_container)
                                    //Why do i have to exclude this?
                            .excludeTarget(R.id.header_container, true));

            transitionSet.setInterpolator(AnimationUtil.interpolator());
            transitionSet.addListener(new SimpleTransitionListener() {

                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    detailsFragment.revealContent();
                    transition.removeListener(this);
                }
            });

            detailsFragment.setEnterTransition(transitionSet);

            imageView.setTransitionName(DetailsFragment.headerTransitionName(position));
        }

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (VersionUtils.isAtLeastL()) {
            transaction.addSharedElement(imageView, imageView.getTransitionName());
        } else {
            transaction.setCustomAnimations(
                    android.R.animator.fade_in,
                    android.R.animator.fade_out,
                    android.R.animator.fade_in,
                    android.R.animator.fade_out);
        }

        transaction
                .hide(MainFragment.this)
                .add(R.id.content, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
