package com.miw.gildedroseexpands.entity.item.support;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The JPA Entity to represent an Item in the database.
 * Has - name, description, and baseprice (int -- cents)
 */
@Entity
@Table(name = "ITEM")
public class ItemDB implements Serializable, Cloneable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public Long getId() {
                return id;
        }

        @Column(unique = true)
        private String name;

        private String description;

        private int price;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public int getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public ItemDB() {
        }

        public ItemDB(String name, String description, int price) {
                this.name = name;
                this.description = description;
                this.price = price;
        }

        @Override
        public String toString() {
                return "Item{" + "id=" + id + ", name='" + name + '\'' + ", description='"
                        + description + '\'' + ", price='" + price + '\'' + '}';
        }
}
