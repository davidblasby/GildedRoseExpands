package com.miw.gildedroseexpands.entity.item;

import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 * event class for ItemViewed
 *
 * itemName - name of the item viewed
 * timeOccurred -- when the view happened (UTC)
 */
public class ItemViewedEvent extends ApplicationEvent {

        private String itemName;

        private Instant timeOccurred;

        public ItemViewedEvent(Object source, String itemName, Instant now) {
                super(source);
                this.itemName = itemName;
                this.timeOccurred = now;
        }

        public String getItemName() {
                return itemName;
        }

        public Instant getTimeOccurred() {
                return timeOccurred;
        }
}