package com.miw.gildedroseexpands.surgetracking;

import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * tests the ItemViewRepository.
 * These methods are all provided by Spring/JPA, so just quick tests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemViewRepositoryTest {

        @Autowired
        private ItemViewRepository itemViewRepository;

        @Test
        public void test_findByItemName_nodata() {
                List<ItemViewDB> result = itemViewRepository.findByItemName("DOES NOT EXIST");
                assertEquals(0, result.size());
        }

        /**
         * quick test to make sure that that #findByItemName() is filtering correctly
         */
        @Test
        public void test_findByItemName_filtersCorrectly() {
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                ItemViewDB inventoryItemDB = new ItemViewDB("itemname", instant);
                ItemViewDB inventoryItemDB2 = new ItemViewDB("itemname", instant);
                ItemViewDB inventoryItemDB3 = new ItemViewDB("OTHER_ITEM_NAME", instant);
                inventoryItemDB = itemViewRepository.save(inventoryItemDB);
                inventoryItemDB2 = itemViewRepository.save(inventoryItemDB2);
                inventoryItemDB3 = itemViewRepository.save(inventoryItemDB3);

                List<ItemViewDB> result = itemViewRepository.findByItemName("itemname");
                assertEquals(2, result.size());

                result = itemViewRepository.findByItemName("OTHER_ITEM_NAME");
                assertEquals(1, result.size());
        }

        /**
         * quick test to ensure that countByItemNameAndTimeViewedGreaterThanEqual() is filtering correctly
         */
        @Test
        public void test_countByItemNameAndTimeViewedGreaterThanEqual_filtersCorrectly() {
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                Instant instant2 = Instant.parse("2019-03-25T11:00:00.00Z");
                Instant instant3 = Instant.parse("2019-03-25T12:00:00.00Z");

                ItemViewDB inventoryItemDB = new ItemViewDB("itemname", instant);
                ItemViewDB inventoryItemDB2 = new ItemViewDB("itemname", instant2);
                ItemViewDB inventoryItemDB3 = new ItemViewDB("OTHER_ITEM_NAME", instant3);

                itemViewRepository.save(inventoryItemDB);
                itemViewRepository.save(inventoryItemDB2);
                itemViewRepository.save(inventoryItemDB3);

                long result = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant);
                assertEquals(2, result);

                result = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant2);
                assertEquals(1, result);

                result = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant3);
                assertEquals(0, result);
        }

        /**
         * quick test to make sure that removeByItemNameAndTimeViewedLessThan is removing correctly.
         * We test by setting up some test data, then slowly moving forward in time, deleting old
         * entries.  We check that the expected # of deletes are correct and that the query returns
         * the correct number of entries.
         * <p>
         * finally, we verify that the other time hasn't been modified.
         */
        @Test
        public void test_removeByItemNameAndTimeViewedLessThan_correctlyRemoves() {
                Instant instant0 = Instant.parse("2019-03-25T09:00:00.00Z"); //in past

                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                Instant instant_plus_alittle = Instant
                        .parse("2019-03-25T10:00:01.00Z"); //1s in future

                Instant instant2 = Instant.parse("2019-03-25T11:00:00.00Z");
                Instant instant2_plus_alittle = Instant
                        .parse("2019-03-25T11:00:01.00Z");//1s in future

                Instant instant3 = Instant.parse("2019-03-25T12:00:00.00Z");

                ItemViewDB inventoryItemDB = new ItemViewDB("itemname", instant);
                ItemViewDB inventoryItemDB2 = new ItemViewDB("itemname", instant2);
                ItemViewDB inventoryItemDB3 = new ItemViewDB("OTHER_ITEM_NAME", instant3);

                itemViewRepository.save(inventoryItemDB);
                itemViewRepository.save(inventoryItemDB2);
                itemViewRepository.save(inventoryItemDB3);

                long nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant0);
                assertEquals(2, nfound);

                long nremoved = itemViewRepository
                        .removeByItemNameAndTimeViewedLessThan("itemname", instant0);
                assertEquals(0, nremoved);

                nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant0);
                assertEquals(2, nfound); //unchanged

                nremoved = itemViewRepository
                        .removeByItemNameAndTimeViewedLessThan("itemname", instant_plus_alittle);
                assertEquals(1, nremoved);

                nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant);
                assertEquals(1, nfound); //one removed

                nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant2);
                assertEquals(1, nfound); //no more

                nremoved = itemViewRepository
                        .removeByItemNameAndTimeViewedLessThan("itemname", instant2_plus_alittle);
                assertEquals(1, nremoved);

                nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instant2);
                assertEquals(0, nfound); //one removed

                //unchanged
                nfound = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual("OTHER_ITEM_NAME", instant);
                assertEquals(1, nfound); //no more
        }
}
