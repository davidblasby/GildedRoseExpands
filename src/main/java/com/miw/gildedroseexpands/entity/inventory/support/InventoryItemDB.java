package com.miw.gildedroseexpands.entity.inventory.support;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * JPA entity for storing inventor information in the database
 * itemName -> name of the item
 * numberRemaining -> number of items in inventory (0 if none)
 */
@Entity
@Table(name = "INVENTORY_ITEM")
public class InventoryItemDB implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String itemName;

        private Long numberRemaining;

        public Long getId() {
                return id;
        }

        public String getItemName() {
                return itemName;
        }

        public void setItemName(String itemName) {
                this.itemName = itemName;
        }

        public Long getNumberRemaining() {
                return numberRemaining;
        }

        public void setNumberRemaining(Long count) {
                this.numberRemaining = count;
        }

        public InventoryItemDB() {
        }

        public InventoryItemDB(String itemName, Long numberRemaining) {
                this.itemName = itemName;
                this.numberRemaining = numberRemaining;
        }

}
