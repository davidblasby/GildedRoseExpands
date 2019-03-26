package com.miw.gildedroseexpands.entity.surgetracking.support;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

/**
 * JPA repository to manage ItemViewDB
 *
 *  findByItemName - find all the views associated to an item
 *  countByItemNameAndTimeViewedGreaterThanEqual - how many views were made to an item SINCE timeViewed
 *  removeByItemNameAndTimeViewedLessThan - purge old views for an item
 */
public interface ItemViewRepository extends CrudRepository<ItemViewDB, Long> {
        /**
         * find all the views associated with an item
         * @param itemName name of item
         */
        List<ItemViewDB> findByItemName(String itemName);

        /**
         * how many views were made to an item SINCE timeViewed
         * @param itemName name of item
         * @param timeViewed time (UTC) to search forward by
         * @return
         */
        Long countByItemNameAndTimeViewedGreaterThanEqual(String itemName, Instant timeViewed);

        /**
         * delete older views for an item
         * @param itemName name of item
         * @param timeViewed time to delete views earlier than
         * @return
         */
        Long removeByItemNameAndTimeViewedLessThan(String itemName, Instant timeViewed);
}

