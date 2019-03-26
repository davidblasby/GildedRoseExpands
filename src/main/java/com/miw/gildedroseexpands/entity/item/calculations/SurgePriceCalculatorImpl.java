package com.miw.gildedroseexpands.entity.item.calculations;

import com.miw.gildedroseexpands.SurgeProperties;
import org.springframework.stereotype.Service;

/**
 * Simple SurgePriceCalculator
 * Uses the application.properties (via SurgeProperties) to inject
 * the surgePriceFactor.
 *
 * Formula is baseprice * surgePriceFactor = price
 * We round to the nearest int.
 */
@Service
public class SurgePriceCalculatorImpl implements SurgePriceCalculator {

        private double surgePriceFactor;

        public SurgePriceCalculatorImpl(SurgeProperties properties) {
                this.surgePriceFactor = properties.getSurgePriceFactor();
        }

        @Override
        public int calculateSurgePrice(int basePrice) {
                return (int) Math.round(basePrice * surgePriceFactor);
        }
}
