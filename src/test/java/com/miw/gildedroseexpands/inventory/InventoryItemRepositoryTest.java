package com.miw.gildedroseexpands.inventory;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * tests the InventoryItemRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class InventoryItemRepositoryTest {

        @Autowired
        private InventoryItemRepository inventoryItemRepository;

        /**
         * simple test - make sure that decrementInventory() works
         */
        @Test
        public void test_decrementInventory_decrements() {
                InventoryItemDB inventoryItemDB = new InventoryItemDB("itemname", 22L);
                inventoryItemDB = inventoryItemRepository.save(inventoryItemDB);

                int n = inventoryItemRepository.decrementInventory("itemname");
                assertEquals(1, n); //one row updated
                inventoryItemDB = inventoryItemRepository.findById(inventoryItemDB.getId())
                        .get(); //re-load

                assertEquals(new Long(21L), inventoryItemDB.getNumberRemaining());
        }

        /**
         * test that 0 is returned if you try to decrementInventory of a non-existing item
         */
        @Test
        public void test_decrementInventory_returns_0_if_no_item() {
                int n = inventoryItemRepository.decrementInventory("DOES NOT EXIST");
                assertEquals(0, n); //none updated

        }

        /**
         * returns an exception when you try to bring inventory below 0
         */
        @Test(expected = DataIntegrityViolationException.class)
        public void test_decrementInventory_throws_error_if_no_inventory() {
                InventoryItemDB inventoryItemDB = new InventoryItemDB("itemname", 0L);
                inventoryItemDB = inventoryItemRepository.save(inventoryItemDB);

                int n = inventoryItemRepository.decrementInventory("itemname"); //no allowed
        }

}
