package com.cygnet.framework.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.cygnet.framework.R;

/**
 * A Base activity that handles common functionality in the app.
 * Inherited activity can use the methods defined in this class.
 * <p/>
 * This activity also track that the application going to background or coming back to foreground
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;
    public static boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        applicationWillEnterForeground();
        super.onStart();

    }

    /**
     * Name : BaseActivity applicationWillEnterForeground
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose : This method will execute when activity will enter in foreground state
     */
    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
        }
}

    @Override
    public void onStop() {
        super.onStop();
        applicationGoesInBackground();
    }

    /**
     * Name : BaseActivity applicationGoesInBackground
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose : This method will execute when activity will enter in background state
     */
    public void applicationGoesInBackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }

}