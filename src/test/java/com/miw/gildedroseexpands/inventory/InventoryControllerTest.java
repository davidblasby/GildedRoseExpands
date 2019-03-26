package com.miw.gildedroseexpands.inventory;

import com.miw.gildedroseexpands.entity.inventory.controller.InventoryController;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemViewModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * simple test that the InventoryController is working.
 * This isn't a core API, so this is just a basic test.
 */
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

        @Mock
        private InventoryItemRepository inventoryItemRepository;

        @InjectMocks
        private InventoryController inventoryController;

        InventoryItemDB inventory1 = new InventoryItemDB("item1", 1L);

        InventoryItemDB inventory2 = new InventoryItemDB("item2", 2L);

        /**
         * makes sure the controller calls the repo and returns a result with the correct # of items
         */
        @Test
        public void test_basicFunctionality() throws Exception {
                List<InventoryItemDB> inventory = new ArrayList<>();
                inventory.add(inventory1);
                inventory.add(inventory2);
                doReturn(inventory).when(inventoryItemRepository).findAll();

                Iterable<InventoryItemViewModel> result = inventoryController.getAllInventory();
                long n = StreamSupport.stream(result.spliterator(), false).count();
                assertEquals(2, n);

                verify(inventoryItemRepository, times(1)).findAll();
        }
}
