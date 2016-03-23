package org.ahomewithin.ahomewithin.util;

import org.ahomewithin.ahomewithin.adapters.ItemsStreamAdapter;
import org.ahomewithin.ahomewithin.models.Item;

/**
 * Created by chezlui on 22/03/16.
 */
public interface ItemHomeClickListener {
    void onEventClicked(ItemsStreamAdapter.ItemViewHolder holder, Item item);
}

