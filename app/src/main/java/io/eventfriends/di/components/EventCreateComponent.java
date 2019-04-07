package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.fragments.EventCreateModule;
import io.eventfriends.di.scopes.EventCreateScope;
import io.eventfriends.presentation.createEvent.EventCreateFragment;

@Component (dependencies = AppComponent.class, modules = EventCreateModule.class)
@EventCreateScope
public interface EventCreateComponent {
    void injectCreateEvent (EventCreateFragment fragment);
}
