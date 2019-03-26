package com.miw.gildedroseexpands.inventory;

import com.miw.gildedroseexpands.entity.inventory.InventoryServiceImpl;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import com.miw.gildedroseexpands.entity.inventory.support.NoInventoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests InventoryServiceImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {

        @Mock
        InventoryItemRepository inventoryItemRepository;

        @InjectMocks
        InventoryServiceImpl inventoryService;

        // when you try to decrementInventory on an item with no info -> NoInventoryException
        @Test(expected = NoInventoryException.class)
        public void test_throws_when_no_inventory_information() {
                when(inventoryItemRepository.decrementInventory("NO INFO")).thenReturn(0);

                inventoryService.decrementInventory("NO INFO");
        }

        // when you try to decrementInventory on an item with 0 items remaining -> NoInventoryException
        @Test(expected = NoInventoryException.class)
        public void test_throws_when_0_inventory() {
                when(inventoryItemRepository.decrementInventory("itemname"))
                        .thenThrow(new DataIntegrityViolationException("bad"));

                inventoryService.decrementInventory("itemname");
        }

        // simple test case to make sure that decrementInventory is correctly talking to the repo
        @Test
        public void test_working_case() {
                when(inventoryItemRepository.decrementInventory("itemname")).thenReturn(1);

                inventoryService.decrementInventory("itemname");
                //verify that the repo method was called
                verify(inventoryItemRepository, times(1)).decrementInventory("itemname");
        }
}
