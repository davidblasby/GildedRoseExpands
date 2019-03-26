package com.miw.gildedroseexpands.item;

import com.miw.gildedroseexpands.SurgeProperties;
import com.miw.gildedroseexpands.entity.item.calculations.SurgePriceCalculatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * tests SurgePriceCalculatorImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SurgePriceCalculatorTest {

        @Autowired
        SurgeProperties properties;

        @Autowired
        SurgePriceCalculatorImpl surgePriceCalculator;

        /**
         * simple test to make sure the calculations are correct
         */
        @Test
        public void testSurgePrice() {
                int result = surgePriceCalculator.calculateSurgePrice(100);
                assertEquals((int) Math.round(100 * properties.getSurgePriceFactor()), result);

                result = surgePriceCalculator.calculateSurgePrice(111);
                assertEquals((int) Math.round(111 * properties.getSurgePriceFactor()), result);
        }
}
