package io.eventfriends.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.MainComponent;

public class MainActivity extends AppCompatActivity implements MainView {

    private int mRequestCodeSignIn;

    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;

    @Inject
    public MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mToolbar);

        MainComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getMainComponent();
        component.injectMain(this);

        mNavController = Navigation.findNavController(this, R.id.main_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_graph).build();

        NavigationUI.setupWithNavController(mToolbar, mNavController, mAppBarConfiguration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onStop() {
        mPresenter.onDetach();
        super.onStop();
    }
}
