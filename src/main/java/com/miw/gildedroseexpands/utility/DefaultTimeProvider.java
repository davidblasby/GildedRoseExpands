package com.miw.gildedroseexpands.utility;

import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * very simple class that can provide the current time
 *
 * This is mostly here for test cases to re-implement.
 */
@Service
public class DefaultTimeProvider implements TimeProvider {

        @Override
        public Instant now() {
                return Instant.now();
        }
}
