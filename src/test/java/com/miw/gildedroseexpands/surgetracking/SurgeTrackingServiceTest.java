package com.miw.gildedroseexpands.surgetracking;

import com.miw.gildedroseexpands.SurgeProperties;
import com.miw.gildedroseexpands.TestCaseNowProvider;
import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import com.miw.gildedroseexpands.entity.surgetracking.SurgeTrackingService;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewDB;
import com.miw.gildedroseexpands.entity.surgetracking.support.ItemViewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

/**
 * verifies the main interface of SurgeTrackingService - needsSurgePrice and accepting view event
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SurgeTrackingServiceTest {

        @Autowired
        public SurgeProperties surgeProperties;

        @Autowired
        public TestCaseNowProvider timeProvider;

        @Mock
        private ItemViewRepository itemViewRepository;

        SurgeTrackingService surgeTrackingService;

        //properly configure for spring boot injection and mock creation
        @Before
        public void setup() {
                surgeTrackingService = new SurgeTrackingService(surgeProperties, timeProvider,
                        itemViewRepository);
        }

        /**
         * tests that, when calling #needsSurgePrice(), it will call the repository "countByItemNameAndTimeViewedGreaterThanEqual"
         * with the correct itemname and time
         * Also, it correctly uses the result to determine if the price needs to be surged.
         */
        @Test
        public void test_needsSurgePrice_calls_with_right_time() {
                Duration timeframe = Duration.ofSeconds(surgeProperties.getSurgeTimeframeSeconds());
                int maxviews = surgeProperties.getMaxViewsToActiveSurge();
                Instant instantNow = Instant.parse("2019-03-25T10:00:00.00Z");
                timeProvider.setNow(instantNow);
                Instant instantEarlier = instantNow.minus(timeframe);

                doReturn(9L).when(itemViewRepository)
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instantEarlier);

                boolean result = surgeTrackingService.needsSurgePrice("itemname");
                assertEquals(false, result);
                Mockito.verify(itemViewRepository, times(1))
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instantEarlier);

                doReturn(10L).when(itemViewRepository)
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instantEarlier);

                result = surgeTrackingService.needsSurgePrice("itemname");
                assertEquals(true, result);
                Mockito.verify(itemViewRepository, times(2))
                        .countByItemNameAndTimeViewedGreaterThanEqual("itemname", instantEarlier);
        }

        /**
         * when accepting a view event, it adds it to the database
         */
        @Test
        public void test_acceptViewEvent_adds_view() {
                Instant instantNow = Instant.parse("2019-03-25T10:00:00.00Z");

                ItemViewedEvent event = new ItemViewedEvent(this, "itemname", instantNow);

                ItemViewDB result = new ItemViewDB("itemname", instantNow);

                doReturn(result).when(itemViewRepository).save(result);

                surgeTrackingService.onApplicationEvent(event);
                Mockito.verify(itemViewRepository, times(1)).save(result);

        }

        /**
         * when accepting a view event, it purges old events
         */
        @Test
        public void test_acceptViewEvent_purgesold() {
                Duration timeframe = Duration.ofSeconds(surgeProperties.getSurgeTimeframeSeconds());
                int maxviews = surgeProperties.getMaxViewsToActiveSurge();
                Instant instantNow = Instant.parse("2019-03-25T10:00:00.00Z");
                timeProvider.setNow(instantNow);
                Instant instantEarlier = instantNow.minus(timeframe);

                ItemViewedEvent event = new ItemViewedEvent(this, "itemname", instantNow);
                ItemViewDB result = new ItemViewDB("itemname", instantNow);

                doReturn(result).when(itemViewRepository).save(result);
                doReturn(0L).when(itemViewRepository)
                        .removeByItemNameAndTimeViewedLessThan("itemname", instantEarlier);

                surgeTrackingService.onApplicationEvent(event);
                Mockito.verify(itemViewRepository, times(1))
                        .removeByItemNameAndTimeViewedLessThan("itemname", instantEarlier);

        }

}
