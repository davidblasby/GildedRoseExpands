package com.miw.gildedroseexpands.unit.trivialobjecttests;

import com.miw.gildedroseexpands.TestCaseNowProvider;
import com.miw.gildedroseexpands.utility.DefaultTimeProvider;
import com.miw.gildedroseexpands.utility.InstantDateConverter;
import org.junit.Test;

import java.time.Instant;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Simple unit tests to test TimeProvider and InstantDateConverter implementations.
 * These are very simple classes, so just a few checks...
 */
public class TimeTests {

        /**
         * Test the DefaultTimeProvider - this test makes sure that timeProvider.now()
         * changes as time passes...
         */
        @Test
        public void testDefaultTimeProvider() throws InterruptedException {
                DefaultTimeProvider timeProvider = new DefaultTimeProvider();
                Instant instant = timeProvider.now();
                Thread.sleep(200);
                Instant instant2 = timeProvider.now();

                assertTrue(instant.isBefore(instant2)); //time passed..
        }

        /**
         * Test the TestCaseNowProvider.  This is only used in test cases.
         * This should (a) not change over time (b) #moveToFuture() should set the time
         * to a future time.
         */
        @Test
        public void testTestCaseNowProvider() throws InterruptedException {
                TestCaseNowProvider nowProvider = new TestCaseNowProvider();
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");
                nowProvider.setNow(instant);

                Thread.sleep(200);
                assertEquals(instant, nowProvider.now()); //hasn't moved

                nowProvider.moveToFuture(60);
                assertEquals(Instant.parse("2019-03-25T10:01:00.00Z"),
                        nowProvider.now()); //1 minute later

                Thread.sleep(200);
                assertEquals(Instant.parse("2019-03-25T10:01:00.00Z"),
                        nowProvider.now()); //hasn't moved
        }

        // tests that InstantDateConverter is working correctly
        @Test
        public void testInstantDateConverter() {
                InstantDateConverter converter = new InstantDateConverter();
                Instant instant = Instant.parse("2019-03-25T10:00:00.00Z");

                Instant roundTrip = converter
                        .convertToEntityAttribute(converter.convertToDatabaseColumn(instant));
                assertEquals(instant, roundTrip);
        }
}
