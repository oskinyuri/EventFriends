package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.fragments.CreateEventModule;
import io.eventfriends.di.scopes.CreateEventScope;
import io.eventfriends.presentation.createEvent.CreateEventFragment;

@Component (dependencies = AppComponent.class, modules = CreateEventModule.class)
@CreateEventScope
public interface CreateEventComponent {
    void injectCreateEvent (CreateEventFragment fragment);
}
