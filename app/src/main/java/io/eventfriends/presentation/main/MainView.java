package io.eventfriends.presentation.main;

import android.content.Intent;

public interface MainView {

    public void startSignInActivity(Intent intent, int requestCode);

    void showProgress();

    void hideProgress();

    void showToast(String text);

    void setUserName(String text);

}
