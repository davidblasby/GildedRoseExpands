package com.miw.gildedroseexpands.integrationtests;

import com.miw.gildedroseexpands.entity.item.controller.PurchaseRequest;
import com.miw.gildedroseexpands.entity.item.support.ItemViewModel;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * basic interation tests to ensure that security is properly configured
 */
public class SecurityIntegrationTests extends IntegrationTestBase {

        //NOTE: this is tested else where (indirectly), but here for completeness
        // test that non-authenticated users can access /items and /items/{name}
        @Test
        public void test_nonauthenticated_access_items() {

                //single
                ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                        .getForEntity("/items/{name}", ItemViewModel.class, item1.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                //multiple
                ResponseEntity<List<ItemViewModel>> responseEntity2 = this.testRestTemplate
                        .exchange("/items", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ItemViewModel>>() {
                                });

                assertTrue(responseEntity2.getStatusCode() == HttpStatus.OK);
        }

        // test that guest (non-admin) users can access /items and /items/{name}
        @Test
        public void test_guestauthenticated_access_items() {

                //single
                ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .getForEntity("/items/{name}", ItemViewModel.class, item1.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                //multiple
                ResponseEntity<List<ItemViewModel>> responseEntity2 = this.testRestTemplate
                        .withBasicAuth("guest", "password").exchange("/items", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ItemViewModel>>() {
                                });

                assertTrue(responseEntity2.getStatusCode() == HttpStatus.OK);
        }

        // test that  admin users can access /items and /items/{name}
        @Test
        public void test_admin_authenticated_access_items() {

                //single
                ResponseEntity<ItemViewModel> responseEntity = this.testRestTemplate
                        .withBasicAuth("admin", "admin")
                        .getForEntity("/items/{name}", ItemViewModel.class, item1.getName());
                assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);

                //multiple
                ResponseEntity<List<ItemViewModel>> responseEntity2 = this.testRestTemplate
                        .withBasicAuth("admin", "admin").exchange("/items", HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<ItemViewModel>>() {
                                });

                assertTrue(responseEntity2.getStatusCode() == HttpStatus.OK);
        }

        // test that authenticated can access /buy
        @Test
        public void test_nonauthenticated_cannot_access_buy() {
                PurchaseRequest purchace = new PurchaseRequest(item1.getName());

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<String> result = this.testRestTemplate
                        .postForEntity("/buy", request, String.class);

                assertTrue(result.getStatusCode() == HttpStatus.UNAUTHORIZED);
        }

        // test that authenticated can access /buy
        @Test
        public void test_authenticated_can_access_buy() {
                PurchaseRequest purchace = new PurchaseRequest(item1.getName());

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<Boolean> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .postForEntity("/buy", request, Boolean.class);

                assertTrue(result.getStatusCode() == HttpStatus.OK);
        }

        // test that admin can access /buy
        @Test
        public void test_admin_can_access_buy() {
                PurchaseRequest purchace = new PurchaseRequest(item1.getName());

                HttpEntity<PurchaseRequest> request = new HttpEntity<>(purchace);

                ResponseEntity<Boolean> result = this.testRestTemplate
                        .withBasicAuth("admin", "admin")
                        .postForEntity("/buy", request, Boolean.class);

                assertTrue(result.getStatusCode() == HttpStatus.OK);
        }

        // test non-authenticated user cannot access admin areas
        @Test
        public void test_nonauthenticated_cannot_access_admin() {
                ResponseEntity<String> result = this.testRestTemplate
                        .getForEntity("/admin/itemviews", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.UNAUTHORIZED);

                result = this.testRestTemplate
                        .getForEntity("/admin/itemviews/{name}", String.class, "any");

                assertTrue(result.getStatusCode() == HttpStatus.UNAUTHORIZED);
        }

        // test authenticated user cannot access admin areas
        @Test
        public void test_authenticated_cannot_access_admin() {
                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .getForEntity("/admin/itemviews", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.FORBIDDEN);

                result = this.testRestTemplate.withBasicAuth("guest", "password")
                        .getForEntity("/admin/itemviews/{itemid}", String.class, 1L);

                assertTrue(result.getStatusCode() == HttpStatus.FORBIDDEN);
        }

        // test admin user can access admin areas
        @Test
        public void test_admin_can_access_admin() {
                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("admin", "admin")
                        .getForEntity("/admin/itemviews", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.OK);

                result = this.testRestTemplate.withBasicAuth("admin", "admin")
                        .getForEntity("/admin/itemviews/{itemid}", String.class, 1L);

                assertTrue(result.getStatusCode() == HttpStatus.OK);
        }

        // test non-authenticated user cannot access /inventory
        @Test
        public void test_nonauthenticated_cannot_access_inventory() {
                ResponseEntity<String> result = this.testRestTemplate
                        .getForEntity("/inventory", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.UNAUTHORIZED);
        }

        // test authenticated user cannot access /inventory
        @Test
        public void test_authenticated_cannot_access_inventory() {
                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("guest", "password")
                        .getForEntity("/inventory", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.FORBIDDEN);
        }

        // test admin user can access /inventory
        @Test
        public void test_admin_can_access_inventory() {
                ResponseEntity<String> result = this.testRestTemplate
                        .withBasicAuth("admin", "admin").getForEntity("/inventory", String.class);

                assertTrue(result.getStatusCode() == HttpStatus.OK);
        }

}
