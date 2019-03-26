package com.miw.gildedroseexpands.entity.item.support;

import com.miw.gildedroseexpands.entity.inventory.InventoryService;
import com.miw.gildedroseexpands.entity.item.ItemEntityBuilder;
import com.miw.gildedroseexpands.entity.item.calculations.SurgePriceCalculator;
import com.miw.gildedroseexpands.entity.surgetracking.SurgePriceDeterminer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * This creates a spring-configured ItemEntityBuilder.
 * Could likely replace this with a bean factory.
 */
@Service
public class ItemEntityBuilderFactory {

        @Autowired
        public SurgePriceCalculator surgePriceCalculator;

        @Autowired
        public SurgePriceDeterminer surgePriceDeterminer;

        @Autowired
        public ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        public ItemViewedEventFactory itemViewedEventFactory;

        @Autowired
        public InventoryService inventoryService;

        /**
         * creates an ItemEntityBuilder with the above services configured
         */
        public ItemEntityBuilder create() {
                return new ItemEntityBuilder(surgePriceCalculator, surgePriceDeterminer,
                        applicationEventPublisher, itemViewedEventFactory, inventoryService);
        }
}
