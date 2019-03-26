package com.miw.gildedroseexpands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * main application for spring bootå
 */
@SpringBootApplication
@EnableConfigurationProperties(SurgeProperties.class)
public class GildedRoseExpandsApplication {

        public static void main(String[] args) {
                SpringApplication.run(GildedRoseExpandsApplication.class, args);
        }

}
