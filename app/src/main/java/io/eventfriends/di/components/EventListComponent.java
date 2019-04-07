package io.eventfriends.di.components;

import dagger.Component;
import io.eventfriends.di.modules.fragments.EventListModule;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.presentation.eventList.EventsListFragment;

@Component (dependencies = AppComponent.class, modules = EventListModule.class)
@EventListScope
public interface EventListComponent {
    void injectEventList(EventsListFragment fragment);
}
