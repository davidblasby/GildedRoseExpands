package com.miw.gildedroseexpands.integrationtests;

import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * simple integration tests for the main EntityItem (/items and /items/{name})
 */
public class SimpleItemIntegrationTests extends IntegrationTestBase {

        // Test that /items/{name} returns the correct entity and not surged priced
        @Test
        public void test_getOneItem() {
                ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", ItemViewModel.class, item1.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                ItemViewModel i1 = responseEntity.getBody();
                assertTrue(i1 != null);
                assertTrue(i1.getName().equals(item1.getName()));
                assertTrue(i1.getDescription().equals(item1.getDescription()));
                assertTrue(i1.getPrice() == item1.getPrice());
        }

        //Test that /items/{name} gives a 404 if you give an incorrect name
        @Test
        public void test_getOneItem_doesntExist() {
                ResponseEntity<String> responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", String.class, "DOESNOTEXISST");

                assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
        }

        // Test that /items/ returns the correct entities and not surged priced
        @Test
        public void test_geAllItems() {
                ResponseEntity<List<ItemViewModel>> responseEntity = this.testRestTemplate
                        .exchange("/items", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ItemViewModel>>() {
                                }, item1.getName());

                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                List<ItemViewModel> items = responseEntity.getBody();
                assertTrue(items.size() == 4);
                items.sort((o1, o2) -> o1.getName()
                        .compareTo(o2.getName())); //make sure in right order
                assertTrue(items.get(0).getName().equals(item1.getName()));
                assertTrue(items.get(1).getName().equals(item2.getName()));
                assertTrue(items.get(2).getName().equals(item3.getName()));
                assertTrue(items.get(3).getName().equals(item4.getName()));

                assertTrue(items.get(0).getPrice() == item1.getPrice());
                assertTrue(items.get(1).getPrice() == item2.getPrice());
                assertTrue(items.get(2).getPrice() == item3.getPrice());
                assertTrue(items.get(3).getPrice() == item4.getPrice());
        }

        // Test that /items/{name} correctly does surge pricing
        //
        // make a set of request (all at the same time value) --> should all be non-surged
        // make one more request (over max) --> should be surged
        // move time forward to just before interval end --> should be surged
        // move time forward a bit more (after interval end) --> should be non-surged
        @Test
        public void test_getOneItem_surge() {

                //get an item, SHOULD NOT BE SURGE PRICED
                for (int t = 0; t < surgeProperties.getMaxViewsToActiveSurge(); t++) {
                        ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                                .getForEntity("/items/{name}", ItemViewModel.class,
                                        item2.getName());
                        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                        ItemViewModel i2 = responseEntity.getBody();
                        assertTrue(i2.getPrice() == item2.getPrice());//not surge priced
                }

                // THIS SHOULD BE SURGE PRICED
                ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", ItemViewModel.class, item2.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                ItemViewModel i2 = responseEntity.getBody();
                assertTrue(i2.getPrice() > item2.getPrice());// SURGE PRICED

                // move forward in time to just before the max interval, should still be surge priced
                nowProvider.moveToFuture(surgeProperties.getSurgeTimeframeSeconds() - 5);

                // THIS SHOULD BE SURGE PRICED
                responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", ItemViewModel.class, item2.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                i2 = responseEntity.getBody();
                assertTrue(i2.getPrice() > item2.getPrice());// SURGE PRICED

                // move forward in time to just AFTER the max interval, should NOT be surge priced
                nowProvider.moveToFuture(10);

                responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", ItemViewModel.class, item2.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                i2 = responseEntity.getBody();
                assertTrue(i2.getPrice() == item2.getPrice());//not surge priced
        }

        // this is the same as test_getOneItem_surge, but the activation of the surge
        // is done with a get all instead of a get one.
        // this will ensure that get all is doing as it should
        @Test
        public void test_getAllItem_surge() {

                //get an item, SHOULD NOT BE SURGE PRICED
                for (int t = 0; t < surgeProperties.getMaxViewsToActiveSurge(); t++) {
                        ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                                .getForEntity("/items/{name}", ItemViewModel.class,
                                        item2.getName());
                        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                        ItemViewModel i2 = responseEntity.getBody();
                        assertTrue(i2.getPrice() == item2.getPrice());//not surge priced
                }

                // THIS SHOULD BE SURGE PRICED - use get all
                ResponseEntity<List<ItemViewModel>> responseEntity = this.testRestTemplate
                        .exchange("/items", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ItemViewModel>>() {
                                }, item2.getName());

                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                List<ItemViewModel> items = responseEntity.getBody();
                assertTrue(items.size() == 4);
                items.sort((o1, o2) -> o1.getName()
                        .compareTo(o2.getName())); //make sure in right order

                ItemViewModel i2 = items.get(1);
                assertTrue(i2.getPrice() > item2.getPrice());// SURGE PRICED

                ItemViewModel i1 = items.get(0);
                assertTrue(i1.getPrice() == item1.getPrice());//not surge priced

                // move forward in time to just before the max interval, should still be surge priced
                nowProvider.moveToFuture(surgeProperties.getSurgeTimeframeSeconds() - 5);

                // THIS SHOULD BE SURGE PRICED
                responseEntity = this.testRestTemplate.exchange("/items", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ItemViewModel>>() {
                        }, item2.getName());

                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                items = responseEntity.getBody();
                assertTrue(items.size() == 4);
                items.sort((o1, o2) -> o1.getName()
                        .compareTo(o2.getName())); //make sure in right order
                i2 = items.get(1);
                assertTrue(i2.getPrice() > item2.getPrice());// SURGE PRICED

                i1 = items.get(0);
                assertTrue(i1.getPrice() == item1.getPrice());//not surge priced

                // move forward in time to just AFTER the max interval, should NOT be surge priced
                nowProvider.moveToFuture(10);

                responseEntity = this.testRestTemplate.exchange("/items", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ItemViewModel>>() {
                        }, item1.getName());

                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                items = responseEntity.getBody();
                assertTrue(items.size() == 4);
                items.sort((o1, o2) -> o1.getName()
                        .compareTo(o2.getName())); //make sure in right order
                i2 = items.get(1);
                assertTrue(i2.getPrice() == item2.getPrice());// not surge priced

                i1 = items.get(0);
                assertTrue(i1.getPrice() == item1.getPrice());//not surge priced
        }

}
