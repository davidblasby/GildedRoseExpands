package com.miw.gildedroseexpands.entity.inventory.support;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * repository for InventoryItemRepository
 * Includes a decrementInventory() custom update to do transaction isolation, and row locking
 * (its OK to have 2 people attempting to decrementInventory at the same time).
 */
public interface InventoryItemRepository extends CrudRepository<InventoryItemDB, Long> {

        // note - we are clearing caches by setting clearAutomatically
        @Modifying(clearAutomatically = true)
        @Query("UPDATE InventoryItemDB SET NUMBER_REMAINING = NUMBER_REMAINING -1 WHERE ITEM_NAME = :itemName")
        int decrementInventory(@Param("itemName") String itemName);
}
