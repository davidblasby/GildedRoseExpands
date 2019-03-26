package com.miw.gildedroseexpands.integrationtests;

import com.miw.gildedroseexpands.SurgeProperties;
import com.miw.gildedroseexpands.TestCaseNowProvider;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import com.miw.gildedroseexpands.entity.item.controller.ItemController;
import com.miw.gildedroseexpands.entity.item.support.ItemDB;
import com.miw.gildedroseexpands.entity.item.support.ItemDBRepository;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * base case for integration tests
 *
 * Sets up a database with;
 * a) four ItemDB
 * b) three InventoryItemDB
 *
 * also sets up testRestTemplate, nowProvider, and some other simple support
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTestBase {

        @Autowired
        protected ItemController itemController;

        @Autowired
        protected ItemDBRepository itemRepository;

        @Autowired
        protected ItemViewRepository itemViewRepository;

        @Autowired
        InventoryItemRepository inventoryItemRepository;

        ItemDB item1 = new ItemDB("widget1", "desc widge 1", 100);

        ItemDB item2 = new ItemDB("widget2", "desc widge 2", 200);

        ItemDB item3 = new ItemDB("widget3", "desc widge 3", 300);

        ItemDB item4 = new ItemDB("widget4", "desc widge 4", 400);

        InventoryItemDB inventory1, inventory2, inventory3;

        @Before
        public void setUp() throws Exception {
                itemRepository.deleteAll();
                itemViewRepository.deleteAll();
                inventoryItemRepository.deleteAll();

                itemRepository.save(item1);
                itemRepository.save(item2);
                itemRepository.save(item3);
                itemRepository.save(item4);

                inventory1 = new InventoryItemDB(item1.getName(), 7L);
                inventory2 = new InventoryItemDB(item2.getName(), 7L);
                inventory3 = new InventoryItemDB(item3.getName(), 7L);

                inventoryItemRepository.save(inventory1);
                inventoryItemRepository.save(inventory2);
                inventoryItemRepository.save(inventory3);
        }

        @After
        public void tearDown() throws Exception {
        }

        @Autowired
        protected TestRestTemplate testRestTemplate;

        @Autowired
        protected TestCaseNowProvider nowProvider;

        @Autowired
        protected SurgeProperties surgeProperties;

        @Autowired
        Environment environment;

        @Test
        public void test() {
                // nothing - for framework
        }

}
