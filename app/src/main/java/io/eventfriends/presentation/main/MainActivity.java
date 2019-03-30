package io.eventfriends.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;

import javax.inject.Inject;

import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.MainComponent;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView mUserName;
    private int mRequestCodeSignIn;

    @Inject
    public MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserName = findViewById(R.id.main_user_name);

        MainComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getMainComponent();
        component.injectMain(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
    }

    @Override
    public void startSignInActivity(Intent intent, int requestCode) {
        mRequestCodeSignIn = requestCode;
        startActivityForResult(intent, mRequestCodeSignIn);
    }

    //Maybe once it would be useful
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mRequestCodeSignIn) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                // ...
            } else {
                finish();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                mPresenter.signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        //TODO
    }

    @Override
    public void hideProgress() {
        //TODO
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserName(String text) {
        mUserName.setText(text);
    }

    @Override
    protected void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }
}
