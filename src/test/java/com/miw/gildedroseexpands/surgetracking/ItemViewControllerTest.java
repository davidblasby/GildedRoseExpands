package com.miw.gildedroseexpands.surgetracking;

import com.miw.gildedroseexpands.entity.surgetracking.controller.ItemViewController;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * tests the ItemViewController
 */
@RunWith(SpringRunner.class)
public class ItemViewControllerTest {
        @Mock
        private ItemViewRepository itemViewRepository;

        @InjectMocks
        private ItemViewController itemViewController;

        ItemViewDB view1 = new ItemViewDB("item1", Instant.now());

        ItemViewDB view2 = new ItemViewDB("item1", Instant.now());

        ItemViewDB view3 = new ItemViewDB("item2", Instant.now());

        /**
         * makes sure the controller calls the repo and returns a result with the correct # of items
         */
        @Test
        public void test_getAllItemView() throws Exception {
                List<ItemViewDB> views = new ArrayList<>();
                views.add(view1);
                views.add(view2);
                views.add(view3);

                doReturn(views).when(itemViewRepository).findAll();

                Iterable<ItemViewDB> result = itemViewController.getAllItemView();
                long n = StreamSupport.stream(result.spliterator(), false).count();
                assertEquals(3, n);

                verify(itemViewRepository, times(1)).findAll();
        }

        /**
         * makes sure the controller calls the repo and returns a result with the correct # of items
         */
        @Test
        public void test_get_by_itemname_ItemView() throws Exception {
                List<ItemViewDB> views = new ArrayList<>();
                views.add(view1);
                views.add(view2);

                doReturn(views).when(itemViewRepository).findByItemName("item1");

                Iterable<ItemViewDB> result = itemViewController.getItemViews("item1");
                long n = StreamSupport.stream(result.spliterator(), false).count();
                assertEquals(2, n);

                verify(itemViewRepository, times(1)).findByItemName("item1");
        }

}
