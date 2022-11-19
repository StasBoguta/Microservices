package com.mentor4you.config;

import com.mentor4you.controller.AuthController;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ActiveUsersMetrics {
    @Lazy
    @Autowired
    protected AuthController authController;

    public Supplier<Number> fetchUserCount() {
        return ()-> authController.getActiveUsers();
    }

    public ActiveUsersMetrics(MeterRegistry registry) {
        Gauge.builder("number.of.activeUsers",fetchUserCount()).register(registry);
    }
}

