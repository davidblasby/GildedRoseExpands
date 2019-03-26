package com.miw.gildedroseexpands.entity.item;

import com.miw.gildedroseexpands.entity.inventory.InventoryService;
import com.miw.gildedroseexpands.entity.item.calculations.SurgePriceCalculator;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemViewedEventFactory;
import com.miw.gildedroseexpands.entity.surgetracking.SurgePriceDeterminer;
import org.springframework.context.ApplicationEventPublisher;

/**
 * builds ItemEntity from an ItemDB
 *  a) sets up itemName, description, and basePrice (from ItemDB)
 *  b) configures all the services
 *  c) calls ItemEntity#init()
 */
public class ItemEntityBuilder {

        SurgePriceCalculator surgePriceCalculator;

        public SurgePriceDeterminer surgePriceDeterminer;

        ApplicationEventPublisher applicationEventPublisher;

        ItemViewedEventFactory itemViewedEventFactory;

        InventoryService inventoryService;

        ItemDB item;

        public ItemEntityBuilder(SurgePriceCalculator surgePriceCalculator,
                SurgePriceDeterminer surgePriceDeterminer,
                ApplicationEventPublisher applicationEventPublisher,
                ItemViewedEventFactory itemViewedEventFactory, InventoryService inventoryService) {
                this.surgePriceCalculator = surgePriceCalculator;
                this.surgePriceDeterminer = surgePriceDeterminer;
                this.applicationEventPublisher = applicationEventPublisher;
                this.itemViewedEventFactory = itemViewedEventFactory;
                this.inventoryService = inventoryService;
        }

        /**
         * builds the ItemEntity
         */
        public ItemEntity build() {
                if (item ==null){
                        throw new RuntimeException("#setItem() was not set");
                }
                ItemEntity result = new ItemEntity(item.getName(), item.getDescription(),
                        item.getPrice());
                result.setSurgePriceCalculator(surgePriceCalculator);
                result.setSurgePriceDeterminer(surgePriceDeterminer);
                result.setApplicationEventPublisher(applicationEventPublisher);
                result.setItemViewedEventFactory(itemViewedEventFactory);
                result.setInventoryService(inventoryService);

                result.init();
                return result;
        }

        public void setItem(ItemDB item) {
                this.item = item;
        }

}
