package com.miw.gildedroseexpands.unit.trivialobjecttests;

import com.miw.gildedroseexpands.entity.item.ItemPurchasedEvent;
import com.miw.gildedroseexpands.entity.item.ItemViewedEvent;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

/**
 * Trival tests for our events - make sure setters/getters are hooked up correctly.
 */
public class EventTests {

        // tests construction of ItemViewedEvent
        @Test
        public void testItemViewedEvent() {
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                ItemViewedEvent event = new ItemViewedEvent(this, "itemname", instant);

                assertEquals(instant, event.getTimeOccurred());
                assertEquals("itemname", event.getItemName());
        }

        //tests construction of ItemPurchasedEvent
        @Test
        public void testItemPurchasedEvent() {
                ItemPurchasedEvent itemPurchasedEvent = new ItemPurchasedEvent(this, "itemname",
                        "user");

                assertEquals("user", itemPurchasedEvent.getPurchaserName());
                assertEquals("itemname", itemPurchasedEvent.getItemName());
        }
}
