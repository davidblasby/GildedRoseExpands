package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemDBRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * test the ItemDBRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemDBRepositoryTest {

        @Autowired
        private ItemDBRepository itemDBRepository;

        @Test
        public void test_findByName_nodata() {
                ItemDB result = itemDBRepository.findByName("DOES NOT EXIST");
                assertNull(result);
        }

        /**
         * make sure filtering for findByName working
         */
        @Test
        public void test_findByName_filtersCorrectly() {
                ItemDB item1 = new ItemDB("itemname1", "desc1", 100);
                ItemDB item2 = new ItemDB("itemname2", "desc2", 200);
                ItemDB item3 = new ItemDB("itemname3", "desc3", 300);

                item1 = itemDBRepository.save(item1);
                item2 = itemDBRepository.save(item2);
                item3 = itemDBRepository.save(item3);

                ItemDB result = itemDBRepository.findByName("itemname2");
                assertEquals("itemname2", result.getName());
        }
}
