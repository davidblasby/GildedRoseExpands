package com.miw.gildedroseexpands.entity.surgetracking;

import com.miw.gildedroseexpands.SurgeProperties;
import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import com.miw.gildedroseexpands.utility.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * service for dealing with SurgeTracking info and determination
 * Does two things;
 * a) accepts ItemViewedEvent and record them to the DB
 *     ** old views will be purged from database
 * b) needsSurgePrice
 *     ** determines if an item needs to be surge priced
 *
 * Configured with SurgeProperties (application.properties)
 */
@Component
@Qualifier("remoteSurgePriceDeterminer")
public class SurgeTrackingService
        implements ApplicationListener<ItemViewedEvent>, SurgePriceDeterminer {

        @Autowired
        TimeProvider timeProvider;

        @Autowired
        ItemViewRepository itemViewRepository;

        final int maxViewsToActiveSurge;

        final Duration surgeTimeframe;

        /**
         * accept a ItemViewedEvent from ItemEntity
         * @param event metadata about view
         */
        @Override
        public void onApplicationEvent(ItemViewedEvent event) {
                recordViewEvent(event);
                purgeUnneededEntries(event.getItemName());
        }

        /**
         * determines if the item needs to be surge priced
         * @param itemName name of item
         */
        @Override
        public boolean needsSurgePrice(String itemName) {
                Instant fromWhen = timeProvider.now().minus(surgeTimeframe);
                Long count = itemViewRepository
                        .countByItemNameAndTimeViewedGreaterThanEqual(itemName, fromWhen);
                return count >= maxViewsToActiveSurge;
        }

        public SurgeTrackingService(SurgeProperties properties, TimeProvider timeProvider,
                ItemViewRepository itemViewRepository) {
                this.maxViewsToActiveSurge = properties.getMaxViewsToActiveSurge();
                this.surgeTimeframe = Duration.ofSeconds(properties.getSurgeTimeframeSeconds());
                this.timeProvider = timeProvider;
                this.itemViewRepository = itemViewRepository;
        }

        //--------

        /**
         * have the repo delete old view entries for this item.
         * Calculates the earlier period to delete before via timeProvider (for now) and
         * SurgeProperties for how long ago.
         * @param itemName which item
         */
        private void purgeUnneededEntries(String itemName) {
                Instant fromWhen = timeProvider.now().minus(surgeTimeframe);
                itemViewRepository.removeByItemNameAndTimeViewedLessThan(itemName, fromWhen);
        }

        /**
         * save a view event to the database
         * @param event
         */
        private void recordViewEvent(ItemViewedEvent event) {
                ItemViewDB itemView = new ItemViewDB(event.getItemName(), event.getTimeOccurred());
                itemViewRepository.save(itemView);
        }

}
