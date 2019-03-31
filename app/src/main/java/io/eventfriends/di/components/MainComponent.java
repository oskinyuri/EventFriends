package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.activities.MainModule;
import io.eventfriends.di.scopes.MainScope;
import io.eventfriends.presentation.main.MainActivity;

@Component(dependencies = AppComponent.class, modules = MainModule.class)
@MainScope
public interface MainComponent {
    void injectMain(MainActivity mainActivity);
}
