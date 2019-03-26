package com.miw.gildedroseexpands.entity.item.calculations;

import org.springframework.stereotype.Service;

/**
 * Given a base price of an item, calculate its surged price
 */
@Service
public interface SurgePriceCalculator {
        int calculateSurgePrice(int basePrice);
}
