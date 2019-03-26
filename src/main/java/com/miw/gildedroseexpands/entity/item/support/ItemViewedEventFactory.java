package com.miw.gildedroseexpands.entity.item.support;

import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import com.miw.gildedroseexpands.utility.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * very simple factory that creates ItemViewedEvent
 * These will be marked with the current time (via TimeProvider)
 */
@Service
public class ItemViewedEventFactory {

        @Autowired
        private TimeProvider timeProvider;

        /**
         * creates a ItemViewedEvent with the ItemViewedEvent#timeOccured set to "now()", based
         * on the injected timeProvider.
         * @param source where this event came from (typically ItemEntity)
         * @param itemName name of the item viewed
         * @return
         */
        public ItemViewedEvent create(Object source, String itemName) {
                ItemViewedEvent event = new ItemViewedEvent(source, itemName, timeProvider.now());
                return event;
        }
}
