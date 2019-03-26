package com.miw.gildedroseexpands.utility;

import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * interface for classes that can provide the current time.
 */
@Service
public interface TimeProvider {

        Instant now();
}
