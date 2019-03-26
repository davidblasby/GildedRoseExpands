package com.miw.gildedroseexpands;

import com.miw.gildedroseexpands.utility.TimeProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.time.Instant;

/**
 * this is test case implemetation of TimeProvider
 *
 * Mostly so we can test functionality without having to wait an hour
 *
 * You #setNow() and then each call to now() will return that time
 * You can then re-set the time with #setNow or #moveToFuture
 */
@Configuration
@Primary
public class TestCaseNowProvider implements TimeProvider {

        Instant now = Instant.now();

        /**
         * set the time that this will provide (each call to now() returns the same value)
         * @param d
         */
        public void setNow(Instant d) {
                now = d;
        }

        /**
         * move the current time to the future
         * @param seconds how far in the future
         * @return
         */
        public Instant moveToFuture(int seconds) {
                Duration duration = Duration.ofSeconds(seconds);
                now = now.plus(duration);
                return now;
        }

        /**
         * current time
         * (each call to now() returns the same value)
         */
        @Override
        public Instant now() {
                return now;
        }
}
