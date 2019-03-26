package com.miw.gildedroseexpands.entity.inventory.support;

/**
 * simple view model for InventoryItem - pretty much exactly the same a InventoryItemDB
 *
 * This is used to show an InventoryItemDB to a user.
 */
public class InventoryItemViewModel {

        private String itemName;

        private Long numberRemaining;

        public InventoryItemViewModel(InventoryItemDB inventory) {
                this(inventory.getItemName(), inventory.getNumberRemaining());
        }

        public String getItemName() {
                return itemName;
        }

        public Long getNumberRemaining() {
                return numberRemaining;
        }

        public InventoryItemViewModel(String itemName, Long numberRemaining) {
                this.itemName = itemName;
                this.numberRemaining = numberRemaining;
        }

        public InventoryItemViewModel() {
        }

}
