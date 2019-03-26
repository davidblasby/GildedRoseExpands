package com.miw.gildedroseexpands.unit.trivialobjecttests;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemViewModel;
import com.miw.gildedroseexpands.entity.item.controller.PurchaseRequest;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Very simple tests to make sure the ViewModel objects are setup correctly
 * (i.e. name and description aren't connected together)
 */
public class ViewModelTests {

        // tests the ItemViewModel
        @Test
        public void testItemDB() {
                ItemViewModel itemViewModel = new ItemViewModel("name", "desc", 100);
                assertEquals("name", itemViewModel.getName());
                assertEquals("desc", itemViewModel.getDescription());
                assertEquals(100, itemViewModel.getPrice());
                assertNotNull(itemViewModel.toString());
                assertTrue(itemViewModel.toString().length() > 0);
        }

        //tests the InventoryItemViewModel
        @Test
        public void testInventoryItemDB() {
                InventoryItemViewModel inventoryItemViewModel = new InventoryItemViewModel(
                        "itemname", 22L);

                assertEquals("itemname", inventoryItemViewModel.getItemName());
                assertEquals(new Long(22L), inventoryItemViewModel.getNumberRemaining());
                assertNotNull(inventoryItemViewModel.toString());
                assertTrue(inventoryItemViewModel.toString().length() > 0);
        }

        //tests the InventoryItemDB
        @Test
        public void testInventoryItemDB_convert() {
                InventoryItemDB inventoryItemDB = new InventoryItemDB("itemname", 22L);

                InventoryItemViewModel inventoryItemViewModel = new InventoryItemViewModel(
                        inventoryItemDB);

                assertEquals("itemname", inventoryItemViewModel.getItemName());
                assertEquals(new Long(22L), inventoryItemViewModel.getNumberRemaining());
                assertNotNull(inventoryItemViewModel.toString());
                assertTrue(inventoryItemViewModel.toString().length() > 0);
        }

        //tests the PurchaseRequest object
        @Test
        public void testPurchaseRequest() {
                PurchaseRequest purchaseRequest = new PurchaseRequest("itemname");

                assertEquals("itemname", purchaseRequest.getItemName());

                purchaseRequest.setItemName("itemname2");
                assertEquals("itemname2", purchaseRequest.getItemName());

        }
}
