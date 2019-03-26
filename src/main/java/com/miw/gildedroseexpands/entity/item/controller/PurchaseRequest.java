package com.miw.gildedroseexpands.entity.item.controller;

/**
 * simple object used in "/buy" requests.  Just contains the itemName wanting to buy.
 */
public class PurchaseRequest {

        private String itemName;

        public PurchaseRequest() {
        }

        public PurchaseRequest(String itemName) {
                this.itemName = itemName;
        }

        public String getItemName() {
                return itemName;
        }

        public void setItemName(String itemName) {
                this.itemName = itemName;
        }

}
