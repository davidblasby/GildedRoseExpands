package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.TestCaseNowProvider;
import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import com.miw.gildedroseexpands.entity.item.support.ItemViewedEventFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * tests ItemViewedEventFactory
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemViewedEventFactoryTest {

        @Autowired
        TestCaseNowProvider timeProvider;

        @Autowired
        ItemViewedEventFactory itemViewedEventFactory;

        // simple test to make sure its working (esp time provider)
        @Test
        public void test_factory() {
                ItemViewedEvent event = itemViewedEventFactory.create(this, "itemname");

                assertEquals("itemname", event.getItemName());
                assertEquals(timeProvider.now(), event.getTimeOccurred());

        }

}
