package com.miw.gildedroseexpands.entity.item;

import org.springframework.context.ApplicationEvent;

/**
 * simple event to represent an item has been purchased
 *  itemName - which item purchased
 *  purchaserName - who purchased it
 */
public class ItemPurchasedEvent extends ApplicationEvent {

        private String itemName;

        private String purchaserName;

        public ItemPurchasedEvent(Object source, String itemName, String purchaserName) {
                super(source);
                this.itemName = itemName;
                this.purchaserName = purchaserName;
        }

        public String getItemName() {
                return itemName;
        }

        public String getPurchaserName() {
                return purchaserName;
        }

}
