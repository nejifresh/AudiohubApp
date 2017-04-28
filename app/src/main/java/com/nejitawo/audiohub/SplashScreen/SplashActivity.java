package com.nejitawo.audiohub.SplashScreen;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.nejitawo.audiohub.Activity.HomeActivity;
import com.nejitawo.audiohub.Activity.LoginActivity;
import com.parse.ParseUser;
import com.nejitawo.audiohub.R;

/**
 * Created by Neji on 11/17/2014.
 */
public class SplashActivity extends Activity implements SplashContract {
    ImageView logo;
    TextView alto;

    private ImageView mLogo;
    private TextView welcomeText;
    ParseUser currentUser;

    SplashPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(this);

        logo = (ImageView) findViewById(R.id.profile_image);
        alto = (TextView) findViewById(R.id.textstart);

        mLogo = (ImageView) findViewById(R.id.logo);
        welcomeText = (TextView) findViewById(R.id.welcome_text);
         //setAnimation();
        //proceed to next
        //Get the current active user from Parse
        currentUser = ParseUser.getCurrentUser();

        presenter.animationStarted();
        presenter.nextActivityStarted(currentUser);



    }












        /****** Create Thread that will sleep for 5 seconds *************/









    /*private void setAnimation() {

            animation2();
            animation3();

    }*/



   /* private void animation2() {
        mLogo.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    private void animation3() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }*/


    @Override
    public void animationEntry() {
        mLogo.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();

    }

    @Override
    public void proceedNext(final ParseUser theCurrentUser) {
        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {

                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);


                    if (theCurrentUser == null) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                        startActivity(intent);
                        finish();
                    } else {


                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);

                        startActivity(intent);
                        finish();


                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        };

        // start thread
        background.start();
    }
}
