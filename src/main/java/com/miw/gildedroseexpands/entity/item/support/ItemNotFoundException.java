package com.miw.gildedroseexpands.entity.item.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception for when an item is requests from the DB but not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

        public String getItemName() {
                return itemName;
        }

        final String itemName;

        public ItemNotFoundException(String itemName) {
                this.itemName = itemName;
        }

}
