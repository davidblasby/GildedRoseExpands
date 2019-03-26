package com.miw.gildedroseexpands.entity.item.support;

import org.springframework.data.repository.CrudRepository;

/**
 * JPA repository for ItemDB
 */
public interface ItemDBRepository extends CrudRepository<ItemDB, Long> {

        /**
         * find an ItemDB in the DB by Name
         * @param name - item name
         * @return item from DB
         */
        ItemDB findByName(String name);
}
