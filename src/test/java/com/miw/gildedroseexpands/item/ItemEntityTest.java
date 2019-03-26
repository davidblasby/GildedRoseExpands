package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.entity.inventory.InventoryService;
import com.miw.gildedroseexpands.entity.item.ItemEntity;
import com.miw.gildedroseexpands.entity.item.ItemEntityBuilder;
import com.miw.gildedroseexpands.entity.item.ItemPurchasedEvent;
import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import com.miw.gildedroseexpands.entity.item.calculations.SurgePriceCalculator;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import com.miw.gildedroseexpands.entity.item.support.ItemViewedEventFactory;
import com.miw.gildedroseexpands.entity.surgetracking.SurgePriceDeterminer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

/**
 * test ItemEntity
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemEntityTest {

        @Mock
        ApplicationEventPublisher applicationEventPublisher;

        @Mock
        SurgePriceDeterminer surgePriceDeterminer;

        @Mock
        SurgePriceCalculator surgePriceCalculator;

        @Mock
        InventoryService inventoryService;

        @Autowired
        ItemViewedEventFactory itemViewedEventFactory;

        ItemEntityBuilder builder;

        ItemDB itemDB = new ItemDB("item1", "desc1", 100);

        // setup objects and services and mocks
        @Before
        public void setup() {
                builder = new ItemEntityBuilder(surgePriceCalculator, surgePriceDeterminer,
                        applicationEventPublisher, itemViewedEventFactory, inventoryService);

                doReturn(111).when(surgePriceCalculator).calculateSurgePrice(100);

        }

        /**
         * verify that calling #toViewModel() fires a ItemViewedEvent event
         */
        @Test
        public void test_view_firesevent() {

                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();
                ItemViewModel vm = item.toViewModel();

                Mockito.verify(surgePriceCalculator, times(0)).calculateSurgePrice(100);
                Mockito.verify(applicationEventPublisher, times(1))
                        .publishEvent(any(ItemViewedEvent.class));
        }

        /**
         * verify that building an ItemEntity calls surgePriceDeterminer.
         * if not surge priced, then surgePriceCalculator is not called.
         */
        @Test
        public void test_view_calls_surgePriceDeterminer() {

                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();
                ItemViewModel vm = item.toViewModel();

                Mockito.verify(surgePriceCalculator, times(0)).calculateSurgePrice(100);
                Mockito.verify(surgePriceDeterminer, times(1)).needsSurgePrice("item1");
        }

        /**
         * verify that building an ItemEntity calls surgePriceDeterminer.
         * if IS surge priced, then surgePriceCalculator IS called.
         */
        @Test
        public void test_view_calls_surgePriceDeterminer_and_calculates() {

                doReturn(true).when(surgePriceDeterminer).needsSurgePrice("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();
                ItemViewModel vm = item.toViewModel();

                Mockito.verify(surgePriceCalculator, times(1)).calculateSurgePrice(100);
                Mockito.verify(surgePriceDeterminer, times(1)).needsSurgePrice("item1");
        }

        // verify that buying calls the inventory service with correct params
        @Test
        public void test_buy_calls_inventory() {
                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("item1");
                doNothing().when(inventoryService).decrementInventory("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();

                item.purchase("guest", "item1");
                Mockito.verify(inventoryService, times(1)).decrementInventory("item1");
        }

        // verify that buy publishes the ItemPurchasedEvent
        @Test
        public void test_buy_publishes_event() {
                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();
                item.purchase("guest", "item1");

                Mockito.verify(applicationEventPublisher, times(1))
                        .publishEvent(any(ItemPurchasedEvent.class));
        }

        // verify that exception thrown if try to buy an item with 0 inventory or no inventory
        @Test(expected = RuntimeException.class)
        public void test_buy_fails_if_no_inventory() {
                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("item1");
                doThrow(new RuntimeException()).when(inventoryService).decrementInventory("item1");

                builder.setItem(itemDB);
                ItemEntity item = builder.build();

                item.purchase("guest", "item1");
                Mockito.verify(inventoryService, times(1)).decrementInventory("item1");
        }

}
