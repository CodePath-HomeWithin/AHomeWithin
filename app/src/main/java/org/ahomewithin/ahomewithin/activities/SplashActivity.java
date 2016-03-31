package org.ahomewithin.ahomewithin.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.ahomewithin.ahomewithin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 23/03/16.
 */
public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 20000;

    @Bind(R.id.ivLargeHouse) ImageView ivLargeHouse;
    @Bind(R.id.ivSmallHouse) ImageView ivSmallHouse;
    @Bind(R.id.ivAHome) ImageView ivAHome;
    @Bind(R.id.ivWithin) ImageView ivWithin;
    @Bind(R.id.rlSplashScreen) RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // TODO retirar
        goToMainActivity();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });



        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                goToMainActivity();
            }
        }, SPLASH_TIME_OUT);

        // Animations

//        ivSmallHouse.setTranslationX(-500f);
//        ivSmallHouse.setTranslationY(500f);
//        ivSmallHouse.invalidate();
        ivSmallHouse.setAlpha(0f);
        ivSmallHouse.setScaleX(0f); ivSmallHouse.setScaleY(0f);
        ivLargeHouse.setAlpha(0f);
        ivAHome.setAlpha(0f);
        ivWithin.setAlpha(0f);



        new Handler().postDelayed(new Runnable() {
            //
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                animateScreen();
            }
        }, 200);
    }

    private void goToMainActivity() {
        ivSmallHouse.animate().cancel();
        ivLargeHouse.animate().cancel();
        ivAHome.animate().cancel();
        ivWithin.animate().cancel();

        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);

        // close this activity
        finish();
    }

    private void animateScreen() {
        ObjectAnimator oaFadeLarge = ObjectAnimator.ofFloat(ivLargeHouse, "alpha", 0f, 1f);
        oaFadeLarge.setInterpolator(new AccelerateInterpolator());
        oaFadeLarge.setDuration(1000).setStartDelay(200);
        oaFadeLarge.start();

        final int location[] = new int[2];
        ivSmallHouse.getLocationOnScreen(location);
//         move outside screen
//        ivSmallHouse.setTranslationX(-500f);
//        ivSmallHouse.setTranslationY(500f);
        Log.d("Location", "location x: " + location[0] + " , y: " + location[1]);


        ObjectAnimator oaFadeSmall = ObjectAnimator.ofFloat(ivSmallHouse, "alpha", 0f, 1f);
        oaFadeSmall.setInterpolator(new DecelerateInterpolator(1.5f));
        oaFadeSmall.setDuration(800).setStartDelay(1000);
        oaFadeSmall.start();

        ObjectAnimator oaEnterSmallX = ObjectAnimator.ofFloat(ivSmallHouse, "scaleX", 0f, 0.25f);
        ObjectAnimator oaEnterSmallY = ObjectAnimator.ofFloat(ivSmallHouse, "scaleY", 0f, 0.25f);
        oaEnterSmallX.setInterpolator(new OvershootInterpolator(8));
        oaEnterSmallY.setInterpolator(new OvershootInterpolator(8));
        oaEnterSmallX.setDuration(400).setStartDelay(1100);
        oaEnterSmallY.setDuration(400).setStartDelay(1100);
        oaEnterSmallX.start(); oaEnterSmallY.start();

        ObjectAnimator oaRotateSmall = ObjectAnimator.ofFloat(ivSmallHouse, "rotation", 45f, 4365f);
        oaRotateSmall.setInterpolator(new HesitateInterpolator());
        oaRotateSmall.setDuration(8000).setStartDelay(1600);
        oaRotateSmall.start();

        ObjectAnimator oaRotateSmall1 = ObjectAnimator.ofFloat(ivSmallHouse, "rotation", 45f, 360f);
        oaRotateSmall1.setInterpolator(new DecelerateInterpolator());
        oaRotateSmall1.setDuration(2000).setStartDelay(9600);
        oaRotateSmall1.start();

        ObjectAnimator oaBounceLarge = ObjectAnimator.ofFloat(ivLargeHouse, "rotation", 270f, 315f);
        oaBounceLarge.setInterpolator(new BounceInterpolator());
        oaBounceLarge.setDuration(2000).setStartDelay(2600);
        oaBounceLarge.start();

        ObjectAnimator oaBounceLarge1 = ObjectAnimator.ofFloat(ivLargeHouse, "rotation", 315f, 360f);
        oaBounceLarge1.setInterpolator(new BounceInterpolator());
        oaBounceLarge1.setDuration(2000).setStartDelay(8000);
        oaBounceLarge1.start();

        ObjectAnimator oaScaleSmall3 = ObjectAnimator.ofFloat(ivLargeHouse, "scaleX", 1f, 0.25f).setDuration(4000);
        oaScaleSmall3.setStartDelay(11600); oaScaleSmall3.start();
//
        ObjectAnimator oaScaleSmall4 = ObjectAnimator.ofFloat(ivLargeHouse, "scaleY", 1f, 0.25f).setDuration(4000);
        oaScaleSmall4.setStartDelay(11600); oaScaleSmall4.start();

        ObjectAnimator oaScaleSmall = ObjectAnimator.ofFloat(ivSmallHouse, "scaleX", 0.25f, 0.05f).setDuration(4000);
        oaScaleSmall.setStartDelay(11600); oaScaleSmall.start();
//
        ObjectAnimator oaScaleSmall1 = ObjectAnimator.ofFloat(ivSmallHouse, "scaleY", 0.25f, 0.05f).setDuration(4000);
        oaScaleSmall1.setStartDelay(11600); oaScaleSmall1.start();


//        ObjectAnimator oaMoveSmallX = ObjectAnimator.ofFloat(ivSmallHouse, "translationX", -600, 0);
//        ObjectAnimator oaMoveSmallY = ObjectAnimator.ofFloat(ivSmallHouse, "translationY", 600, 0);

        ivAHome.animate().alpha(1).setStartDelay(12000).setDuration(5000).setInterpolator(new AccelerateInterpolator(2));
        ivWithin.animate().alpha(1).setStartDelay(12000).setDuration(5000).setInterpolator(new AccelerateInterpolator(2));

    }

    public class HesitateInterpolator implements Interpolator {
        public HesitateInterpolator() {}
        public float getInterpolation(float t) {
            float x=2.0f*t-1.0f;
            return 0.5f*(x*x*x + 1.0f);
        }
    }
}
