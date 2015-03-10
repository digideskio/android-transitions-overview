package com.akta.app.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.akta.R;
import com.akta.app.fragment.MainFragment;

/**
 * @author Zac Siegel (github: zsiegel)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new MainFragment())
                    .commit();
        }
    }
}
