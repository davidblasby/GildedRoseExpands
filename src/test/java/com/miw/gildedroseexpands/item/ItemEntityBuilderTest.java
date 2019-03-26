package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.entity.item.ItemEntity;
import com.miw.gildedroseexpands.entity.item.ItemEntityBuilder;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemEntityBuilderFactory;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import com.miw.gildedroseexpands.entity.surgetracking.SurgePriceDeterminer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

/**
 * test ItemEntityBuilder
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemEntityBuilderTest {

        @Autowired
        ItemEntityBuilderFactory builderFactory;

        @Mock
        SurgePriceDeterminer surgePriceDeterminer;

        /**
         * test that the builder is working correctly.
         * We test with no surging
         */
        @Test
        public void test_builder_simple() {
                ItemDB item1 = new ItemDB("itemname1", "desc1", 100);

                ItemEntityBuilder builder = builderFactory.create();
                builder.surgePriceDeterminer = surgePriceDeterminer; // overwrite with our mock

                doReturn(false).when(surgePriceDeterminer).needsSurgePrice("itemname1");

                builder.setItem(item1);
                ItemEntity item = builder.build();
                ItemViewModel vm = item.toViewModel();
                assertEquals("itemname1", vm.getName());
                assertEquals("desc1", vm.getDescription());
                assertEquals(100, vm.getPrice());
        }

        /**
         * test that the builder is working correctly.
         * We test WITH surging
         */
        @Test
        public void test_builder_surged() {
                ItemDB item1 = new ItemDB("itemname1", "desc1", 100);

                ItemEntityBuilder builder = builderFactory.create();
                builder.surgePriceDeterminer = surgePriceDeterminer;// overwrite with our mock

                doReturn(true).when(surgePriceDeterminer).needsSurgePrice("itemname1");

                builder.setItem(item1);
                ItemEntity item = builder.build();
                ItemViewModel vm = item.toViewModel();
                assertEquals("itemname1", vm.getName());
                assertEquals("desc1", vm.getDescription());
                assertTrue(vm.getPrice() > 100);

                Mockito.verify(surgePriceDeterminer, times(1)).needsSurgePrice("itemname1");
        }

        /**
         * test that get exception if itemDB is not set
         */
        @Test(expected = RuntimeException.class)
        public void test_exception_if_itemDB_not_set() {
                ItemEntityBuilder builder = builderFactory.create();
                builder.build();
        }
}
