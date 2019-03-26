package com.miw.gildedroseexpands.entity.item;

import com.miw.gildedroseexpands.entity.inventory.InventoryService;
import com.miw.gildedroseexpands.entity.item.calculations.SurgePriceCalculator;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import com.miw.gildedroseexpands.entity.item.support.ItemViewedEventFactory;
import com.miw.gildedroseexpands.entity.surgetracking.SurgePriceDeterminer;
import org.springframework.context.ApplicationEventPublisher;

/**
 * MAIN ENTITY
 *
 * This represent the Item from a domain model perspective.
 * Has two main operation;
 *   a) asViewModel() -- send out an ItemViewedEvent
 *        Will use the surgePriceDeterminer and surgePriceCalculator to do surge pricing
 *   b) purchase -- send out an ItemPurchasedEvent
 *        Will use inventoryService to make sure there's inventory (and allocate it)
 *
 * It has no direct spring injection (indirect through ItemEntityBuilderFactory and ItemEntityBuilder).
 */
public class ItemEntity {

        private String name;

        private String description;

        private int basePrice;

        private int price;

        //considered bad practice to use spring in an Entity
        SurgePriceCalculator surgePriceCalculator; // calculates the surge price from base price

        SurgePriceDeterminer surgePriceDeterminer; // determines if the price should be surged

        ItemViewedEventFactory itemViewedEventFactory; // factory to make itemViewedEvent

        public ApplicationEventPublisher applicationEventPublisher; //publish events

        public InventoryService inventoryService; // allocate inventory

        public ItemEntity(String name, String description, int basePrice) {
                this.name = name;
                this.description = description;
                this.basePrice = basePrice;
                this.basePrice = basePrice;
        }

        /**
         * called after construction and setup - will setup surge pricing
         */
        public void init() {
                if (surgePriceDeterminer.needsSurgePrice(name)) {
                        price = surgePriceCalculator.calculateSurgePrice(basePrice);
                } else {
                        price = basePrice;
                }
        }

        /**
         * convert this to something that can be returned to the user
         * @return something that can be returned to the user
         */
        public ItemViewModel toViewModel() {
                ItemViewModel result = new ItemViewModel(this.name, this.description, this.price);
                sendViewedEvent();
                return result;
        }

        /**
         * send event that an item has been viewed
         */
        public void sendViewedEvent() {
                ItemViewedEvent event = itemViewedEventFactory.create(this, name);
                applicationEventPublisher.publishEvent(event);
        }

        /**
         * purchase an item
         * @param userName who purchasing
         * @param itemName name of item being purchased
         */
        public void purchase(String userName, String itemName) {
                inventoryService.decrementInventory(itemName);
                applicationEventPublisher
                        .publishEvent(new ItemPurchasedEvent(this, itemName, userName));
        }

        //getters and setters

        public void setSurgePriceCalculator(SurgePriceCalculator surgePriceCalculator) {
                this.surgePriceCalculator = surgePriceCalculator;
        }

        public void setSurgePriceDeterminer(SurgePriceDeterminer surgePriceDeterminer) {
                this.surgePriceDeterminer = surgePriceDeterminer;
        }

        public void setInventoryService(InventoryService inventoryService) {
                this.inventoryService = inventoryService;
        }

        public void setItemViewedEventFactory(ItemViewedEventFactory itemViewedEventFactory) {
                this.itemViewedEventFactory = itemViewedEventFactory;
        }

        public void setApplicationEventPublisher(
                ApplicationEventPublisher applicationEventPublisher) {
                this.applicationEventPublisher = applicationEventPublisher;
        }
}
