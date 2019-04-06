package io.eventfriends.presentation.splashActivity;

import androidx.appcompat.app.AppCompatActivity;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.di.components.SplashComponent;
import io.eventfriends.presentation.main.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.IdpResponse;

import javax.inject.Inject;

public class SplashScreen extends AppCompatActivity implements SplashView {

    private int mRequestCodeSignIn;

    @Inject
    public SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getSplashComponent();
        component.injectSplash(this);

        mPresenter.onAttach(this);
    }

    @Override
    public void startSignInActivity(Intent intent, int requestCode) {
        mRequestCodeSignIn = requestCode;
        startActivityForResult(intent, mRequestCodeSignIn);
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
    }

    //Maybe once it would be useful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mRequestCodeSignIn) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {
                //TODO создать db пользователей и сохранять новых туды
                if (response != null) {
                    boolean newUser = response.isNewUser();
                }
                // Successfully signed in
                // ...
            } else {
                finishAffinity();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    protected void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
