package com.miw.gildedroseexpands.entity.surgetracking.support;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * JPA entity to store ItemViewDB info in the database
 * itemName - name of the item viewed
 * timeViewd - when viewed (UTC)
 */
@Entity
@Table(name = "ITEM_VIEW")
public class ItemViewDB implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String itemName;

        //UTC
        private Instant timeViewed;

        public String getItemName() {
                return itemName;
        }

        public void setItemName(String itemName) {
                this.itemName = itemName;
        }

        public Instant getTimeViewed() {
                return timeViewed;
        }

        public void setTimeViewed(Instant timeViewed) {
                this.timeViewed = timeViewed;
        }

        public Long getId() {
                return id;
        }

        public ItemViewDB() {
        }

        public ItemViewDB(String itemName, Instant timeViewed) {
                this.itemName = itemName;
                this.timeViewed = timeViewed;
        }

        @Override
        public String toString() {
                return "ItemViewDB{" + "id=" + id + ", itemName='" + itemName + '\''
                        + ", itemViewed='" + timeViewed + '\'' + '}';
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                ItemViewDB other = (ItemViewDB) obj;
                boolean sameName =  other.getItemName().equals(this.getItemName());
                boolean sameTime =  other.getTimeViewed().equals(this.getTimeViewed());
                return sameName && sameTime;
        }

        @Override
        public int hashCode() {
                return getItemName().hashCode();
        }
}