package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.entity.item.ItemEntity;
import com.miw.gildedroseexpands.entity.item.ItemEntityBuilder;
import com.miw.gildedroseexpands.entity.item.controller.ItemController;
import com.miw.gildedroseexpands.entity.item.controller.PurchaseRequest;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemDBRepository;
import com.miw.gildedroseexpands.entity.item.support.ItemEntityBuilderFactory;
import com.miw.gildedroseexpands.entity.item.support.ItemNotFoundException;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

/**
 * test for ItemController
 */
@RunWith(SpringRunner.class)
public class ItemControllerTest {

        @Mock
        private ItemDBRepository itemDBRepository;

        @Mock
        private ItemEntityBuilderFactory itemEntityBuilderFactory;

        @Mock
        private ItemEntityBuilder itemEntityBuilder;

        @Mock
        private ItemEntity itemEntity;

        @Mock
        private Principal principal;

        private ItemController itemController;

        // setup mocks and objects
        @Before
        public void setup() {
                ItemDB itemDB = new ItemDB("item1", "desc1", 100);
                ItemDB itemDB2 = new ItemDB("item2", "desc2", 200);
                List<ItemDB> items = new ArrayList<>();
                items.add(itemDB);
                items.add(itemDB2);

                doReturn("guest").when(principal).getName();

                doReturn(itemEntityBuilder).when(itemEntityBuilderFactory).create();
                doReturn(itemEntity).when(itemEntityBuilder).build();
                doReturn(null).when(itemEntity).toViewModel();
                doReturn(itemDB).when(itemDBRepository).findByName("item1");
                doReturn(items).when(itemDBRepository).findAll();
                doReturn(null).when(itemDBRepository).findByName("DOES NOT EXIST");

                itemController = new ItemController(itemDBRepository, itemEntityBuilderFactory);
        }

        /**
         * should throw an exception if it cannot find the item
         */
        @Test(expected = ItemNotFoundException.class)
        public void test_getItem_item_notfound() {
                ItemViewModel vm = itemController.getItem("DOES NOT EXIST");

        }

        /**
         * we verify that the entity is built and toViewModel() called
         */
        @Test
        public void test_getItem_normal() {
                ItemViewModel vm = itemController.getItem("item1");

                Mockito.verify(itemDBRepository, times(1)).findByName("item1");
                Mockito.verify(itemEntityBuilderFactory, times(1)).create();
                Mockito.verify(itemEntityBuilder, times(1)).build();
                Mockito.verify(itemEntity, times(1)).toViewModel();
        }

        /**
         * we verify that the entity is built and toViewModel() called
         */
        @Test
        public void test_getALLItems_normal() {
                List<ItemViewModel> vms = itemController.getItems();

                Mockito.verify(itemDBRepository, times(1)).findAll();
                Mockito.verify(itemEntityBuilderFactory, times(2)).create();
                Mockito.verify(itemEntityBuilder, times(2)).build();
                Mockito.verify(itemEntity, times(2)).toViewModel();
        }

        // try to buy an item that doesn't exist
        @Test(expected = ItemNotFoundException.class)
        public void test_buy_nonexist() {
                PurchaseRequest purchaseRequest = new PurchaseRequest("DOES NOT EXIST");
                boolean success = itemController.buyItem(purchaseRequest, principal);
        }

        //test normal case of buying an item
        @Test
        public void test_buy_normal() {
                PurchaseRequest purchaseRequest = new PurchaseRequest("item1");
                boolean success = itemController.buyItem(purchaseRequest, principal);

                assertTrue(success);

                Mockito.verify(itemDBRepository, times(1)).findByName("item1");
                Mockito.verify(itemEntityBuilderFactory, times(1)).create();
                Mockito.verify(itemEntityBuilder, times(1)).build();
                Mockito.verify(itemEntity, times(1)).purchase("guest", "item1");
        }

}
