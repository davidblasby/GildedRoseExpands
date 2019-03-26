package com.miw.gildedroseexpands.entity.item.support;

/**
 * User's view of ItemEntity (very similar to ItemDB)
 */
public class ItemViewModel {

        private String name;

        private String description;

        private int price;

        public String getName() {
                return name;
        }

        public String getDescription() {
                return description;
        }

        public int getPrice() {
                return price;
        }

        public ItemViewModel() {
        }

        public ItemViewModel(String name, String description, int price) {
                this.name = name;
                this.description = description;
                this.price = price;
        }

        @Override
        public String toString() {
                return "ItemViewModel name='" + name + '\'' + ", description='" + description + '\''
                        + ", price='" + price + '\'' + '}';
        }
}
