package com.miw.gildedroseexpands.integrationtests;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemViewModel;
import com.miw.gildedroseexpands.entity.item.controller.PurchaseRequest;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 *  integration tests for buying items and the admin pages for looking at inventory
 */
public class InventoryIntegrationTests extends IntegrationTestBase {

        //attempt to buy an item that doesn't exist -> 404
        @Test
        public void test_cannot_buy_non_existing_item() {
                PurchaseRequest purchace = new PurchaseRequest("DOES NOT EXIST");

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .postForEntity("/buy", request, String.class);

                assertTrue(result.getStatusCode() == HttpStatus.NOT_FOUND);
        }

        // attempt to buy an item (item4) that doesn't have any inventory info -> 404
        @Test
        public void test_cannot_buy_without_inventoryinfo() {
                PurchaseRequest purchace = new PurchaseRequest(item4.getName());

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .postForEntity("/buy", request, String.class);

                assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
        }

        // attempt to buy an item (item1) --> should work
        @Test
        public void test_canbuy() {
                PurchaseRequest purchace = new PurchaseRequest(item1.getName());

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<Boolean> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .postForEntity("/buy", request, Boolean.class);

                assertTrue(result.getStatusCode() == HttpStatus.OK);
                assertTrue(result.getBody().booleanValue());
        }

        // attempt to buy 7 of item1 --> should work
        // 8th attempt should throw a 404 (no inventory)
        @Test
        public void test_canbuy_max() {
                PurchaseRequest purchace = new PurchaseRequest(item1.getName());

                //will all work
                for (int t = 0; t < 7; t++) {
                        HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                        ResponseEntity<Boolean> result = this.testRestTemplate
                                .withBasicAuth("guest", "password")
                                .postForEntity("/buy", request, Boolean.class);

                        assertTrue(result.getStatusCode() == HttpStatus.OK);
                        assertTrue(result.getBody().booleanValue());
                }

                //will not work
                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .postForEntity("/buy", request, String.class);

                assertTrue(result.getStatusCode() == HttpStatus.BAD_REQUEST);
        }

        // get all the inventory + verify it is correct
        @Test
        public void test_getAllInventory() {
                ResponseEntity<List<InventoryItemViewModel>> responseEntity = this.testRestTemplate
                        .withBasicAuth("admin", "admin")
                        .exchange("/inventory", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<InventoryItemViewModel>>() {
                                });

                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                List<InventoryItemViewModel> inventory = responseEntity.getBody();
                inventory.sort((o1, o2) -> o1.getItemName()
                        .compareTo(o2.getItemName())); //make sure in right order

                assertEquals(inventory1.getItemName(), inventory.get(0).getItemName());
                assertEquals(inventory2.getItemName(), inventory.get(1).getItemName());
                assertEquals(inventory3.getItemName(), inventory.get(2).getItemName());
        }
}
