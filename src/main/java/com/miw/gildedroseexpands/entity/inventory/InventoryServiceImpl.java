package com.miw.gildedroseexpands.entity.inventory;

import com.miw.gildedroseexpands.entity.inventory.support.InventoryItemRepository;
import com.miw.gildedroseexpands.entity.inventory.support.NoInventoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *  Manages inventory via the InventoryItemRepository
 */
@Service
public class InventoryServiceImpl implements InventoryService {

        @Autowired
        InventoryItemRepository inventoryItemRepository;

        /**
         * use the repo to see if can allocate an inventory item (i.e. number_available is reduced by one)
         * If nUpdated ==0 --> no row in database (don't know about this item)
         * DataIntegrityViolationException -> inventory is at 0
         *
         * This is transactionally atomic and will lock the row (if multiple people buying the same
         * item at the same time).
         *
         * @param itemName name of the item
         */
        public void decrementInventory(String itemName) {
                try {
                        int nUpdated = inventoryItemRepository.decrementInventory(itemName);
                        if (nUpdated != 1) {
                                throw new NoInventoryException(itemName);
                        }
                } catch (DataIntegrityViolationException dive) {
                        throw new NoInventoryException(itemName);
                }
        }

        public InventoryServiceImpl() {
        }
}
