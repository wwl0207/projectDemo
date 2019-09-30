package com.bs.android.activity;

import com.bs.android.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.WelcomeConfiguration;

/**
 * created by WWL on 2019/6/9 0009:16
 */
public class WelcomeGuideActivity extends com.stephentuso.welcome.WelcomeActivity {


    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")

                .page(new BasicPage(R.drawable.laucher_back,
                        "Welcome",
                        "An Android library for onboarding, instructional screens, and more")
                        .background(R.color.orange_background)
                )

                .page(new BasicPage(R.drawable.laucher_back,
                        "Simple to use",
                        "Add a welcome screen to your app with only a few lines of code.")
                        .background(R.color.red_background)
                )


                .page(new BasicPage(R.drawable.laucher_back,
                        "Customizable",
                        "All elements of the welcome screen can be customized easily.")
                        .background(R.color.blue_background)
                )

                .canSkip(false)
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)

                .build();
    }


}
