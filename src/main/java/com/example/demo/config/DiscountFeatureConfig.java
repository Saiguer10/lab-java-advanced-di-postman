package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.EarlyBirdDiscountService;

/**
 * Configuration class that conditionally creates the EarlyBirdDiscountService bean
 * based on the 'feature.earlybird.enabled' property value.
 */
@Configuration
public class DiscountFeatureConfig {

    /**
     * Creates an EarlyBirdDiscountService bean only when feature.earlybird.enabled is true.
     *
     * @return the EarlyBirdDiscountService instance
     */
    @Bean
    @ConditionalOnProperty(name = "feature.earlybird.enabled", havingValue = "true")
    public EarlyBirdDiscountService earlyBirdDiscountService() {
        return new EarlyBirdDiscountService();
    }
}
