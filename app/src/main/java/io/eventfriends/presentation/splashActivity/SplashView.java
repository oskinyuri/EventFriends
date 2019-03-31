package io.eventfriends.presentation.splashActivity;

import android.content.Intent;

public interface SplashView {

    void startSignInActivity(Intent intent, int requestCode);

    void startMainActivity();
}
