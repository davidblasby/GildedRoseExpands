package com.miw.gildedroseexpands.entity.surgetracking;

import org.springframework.stereotype.Service;

/**
 * interface for components that can determine if an item needs to be surge priced
 */
@Service
public interface SurgePriceDeterminer {

        /**
         * true if that item needs surge pricing
         * @param itemName name of item
         */
        boolean needsSurgePrice(String itemName);
}
