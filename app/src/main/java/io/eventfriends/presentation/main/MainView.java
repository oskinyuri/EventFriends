package io.eventfriends.presentation.main;

import android.content.Intent;

public interface MainView {

    void showProgress();

    void hideProgress();

    void showToast(String text);

    void startSplashScreen();
}
