package org.ahomewithin.ahomewithin.util;

import org.ahomewithin.ahomewithin.adapters.EventsAdapter;
import org.ahomewithin.ahomewithin.models.Event;

/**
 * Created by chezlui on 22/03/16.
 */
public interface EventClickListener {
    void onEventClicked(EventsAdapter.ViewHolder holder, Event event);
}

