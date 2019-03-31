package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.activities.SplashModule;
import io.eventfriends.di.scopes.MainScope;
import io.eventfriends.di.scopes.SplashScope;
import io.eventfriends.presentation.main.MainActivity;
import io.eventfriends.presentation.splashActivity.SplashScreen;

@Component (dependencies = AppComponent.class, modules = SplashModule.class)
@SplashScope
public interface SplashComponent {

    void injectSplash(SplashScreen splashScreen);
}
