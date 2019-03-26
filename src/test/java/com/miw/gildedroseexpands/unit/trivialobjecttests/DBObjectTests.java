package com.miw.gildedroseexpands.unit.trivialobjecttests;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import org.junit.Test;

import java.time.Instant;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Very simple tests to make sure the DB objects are correctly implemented.
 * Mostly, this is just checking that the get/set methods are working (i.e. not hooked up incorrectly)
 */
public class DBObjectTests {

        //round trip for ItemDB
        @Test
        public void testItemDB() {
                ItemDB itemDB = new ItemDB("name", "desc", 100);
                assertEquals("name", itemDB.getName());
                assertEquals("desc", itemDB.getDescription());
                assertEquals(100, itemDB.getPrice());
                assertNull(itemDB.getId());
                assertNotNull(itemDB.toString());
                assertTrue(itemDB.toString().length() > 0);

                itemDB.setName("name2");
                itemDB.setDescription("desc2");
                itemDB.setPrice(222);

                assertEquals("name2", itemDB.getName());
                assertEquals("desc2", itemDB.getDescription());
                assertEquals(222, itemDB.getPrice());
        }

        // round trip for InventoryItemDB
        @Test
        public void testInventoryItemDB() {
                InventoryItemDB inventoryItemDB = new InventoryItemDB("itemname", 22L);

                assertEquals("itemname", inventoryItemDB.getItemName());
                assertEquals(new Long(22L), inventoryItemDB.getNumberRemaining());
                assertNull(inventoryItemDB.getId());
                assertNotNull(inventoryItemDB.toString());
                assertTrue(inventoryItemDB.toString().length() > 0);

                inventoryItemDB.setItemName("itemname2");
                inventoryItemDB.setNumberRemaining(11L);

                assertEquals("itemname2", inventoryItemDB.getItemName());
                assertEquals(new Long(11L), inventoryItemDB.getNumberRemaining());
        }

        //round trip for ItemViewDB
        @Test
        public void testItemViewDB() {
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                Instant instant2 = Instant.parse("2020-03-25T10:00:00.00Z");

                ItemViewDB itemViewDB = new ItemViewDB("itemname", instant);

                assertEquals("itemname", itemViewDB.getItemName());
                assertEquals(instant, itemViewDB.getTimeViewed());
                assertNull(itemViewDB.getId());
                assertNotNull(itemViewDB.toString());
                assertTrue(itemViewDB.toString().length() > 0);

                itemViewDB.setItemName("itemname2");
                itemViewDB.setTimeViewed(instant2);

                assertEquals("itemname2", itemViewDB.getItemName());
                assertEquals(instant2, itemViewDB.getTimeViewed());
        }

        /**
         * test that .equals and hashcode() are correct
         */
        @Test
        public void testItemViewDB_equals() {
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                Instant instant2 = Instant.parse("2019-03-25T12:00:00.00Z");


                ItemViewDB itemViewDB = new ItemViewDB("itemname", instant);
                ItemViewDB itemViewDB2 = new ItemViewDB("itemname", instant);
                ItemViewDB itemViewDB3 = new ItemViewDB("item2", instant);
                ItemViewDB itemViewDB4 = new ItemViewDB("itemname", instant2);


                assertEquals(itemViewDB, itemViewDB2);
                assertNotEquals(itemViewDB2, itemViewDB3);
                assertNotEquals(itemViewDB2, itemViewDB4);


                assertEquals(itemViewDB.hashCode(), itemViewDB2.hashCode());
                assertEquals(itemViewDB,itemViewDB); // identity
        }

}
