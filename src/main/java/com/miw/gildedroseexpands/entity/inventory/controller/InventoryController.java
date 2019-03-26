package com.miw.gildedroseexpands.entity.inventory.controller;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemDB;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * ADMIN ONLY controller to display inventory (good for debugging)
 */
@Controller
public class InventoryController {

        @Autowired
        private InventoryItemRepository inventoryItemRepository;

        public InventoryController() {
        }

        /**
         * gets all the inventory items and returns to user.
         * @return
         */
        @Secured({ "ROLE_ADMIN" })
        @GetMapping("/inventory")
        @ResponseBody
        public Iterable<InventoryItemViewModel> getAllInventory() {
                Iterable<InventoryItemDB> inventories = inventoryItemRepository.findAll();
                List<InventoryItemViewModel> result = new ArrayList<InventoryItemViewModel>();
                for (InventoryItemDB inventory : inventories) {
                        result.add(new InventoryItemViewModel(inventory));
                }
                return result;
        }

}
