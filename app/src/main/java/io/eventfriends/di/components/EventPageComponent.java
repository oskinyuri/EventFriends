package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.fragments.EventPageModule;
import io.eventfriends.di.scopes.EventPageScope;
import io.eventfriends.presentation.pageEvent.EventPageFragment;

@Component (dependencies = AppComponent.class, modules = EventPageModule.class)
@EventPageScope
public interface EventPageComponent {
    void injectEventPage(EventPageFragment fragment);
}
