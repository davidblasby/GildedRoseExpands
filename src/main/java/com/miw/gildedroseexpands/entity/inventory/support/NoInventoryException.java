package com.miw.gildedroseexpands.entity.inventory.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to represent trying to buy an item that isn't available
 * (ie. either 0 left in inventory or we have no inventory info for that item)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoInventoryException extends RuntimeException {

        public String getItemName() {
                return itemName;
        }

        final String itemName;

        public NoInventoryException(String itemName) {
                this.itemName = itemName;
        }

}