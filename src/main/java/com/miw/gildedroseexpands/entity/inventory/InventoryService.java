package com.miw.gildedroseexpands.entity.inventory;

import org.springframework.stereotype.Service;

/**
 *  Inventory Service interface - used to allocate inventory for buy request
 */
@Service
public interface InventoryService {

        /**
         * given an item name, see if there is available inventory
         * non-available inventory - throw NoInventoryException
         * item un-known - throw NoInventoryException
         * otherwise, allocate an inventory item and return
         */
        void decrementInventory(String itemName);
}
