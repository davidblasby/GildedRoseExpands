package com.miw.gildedroseexpands;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * allows application.properties to be used to setup how surge tracking works.
 */
@ConfigurationProperties("surge")
@Validated
public class SurgeProperties {

        public int getMaxViewsToActiveSurge() {
                return maxViewsToActiveSurge;
        }

        public void setMaxViewsToActiveSurge(int maxViewsToActiveSurge) {
                this.maxViewsToActiveSurge = maxViewsToActiveSurge;
        }

        public int getSurgeTimeframeSeconds() {
                return surgeTimeframeSeconds;
        }

        public void setSurgeTimeframeSeconds(int surgeTimeframeSeconds) {
                this.surgeTimeframeSeconds = surgeTimeframeSeconds;
        }

        public double getSurgePriceFactor() {
                return surgePriceFactor;
        }

        public void setSurgePriceFactor(double surgePriceFactor) {
                this.surgePriceFactor = surgePriceFactor;
        }

        @Min(0)
        private int maxViewsToActiveSurge;

        @Min(0)
        private int surgeTimeframeSeconds;

        @Min(1)
        private double surgePriceFactor;

}
