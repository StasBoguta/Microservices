package com.mentor4you.config;

import com.mentor4you.service.AuthenticationService;
import io.micrometer.core.instrument.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ActiveUsersMetrics {
    @Lazy
    @Autowired
    protected AuthenticationService authenticationService;

    public Supplier<Number> fetchUserCount() {
        return ()-> authenticationService.getActiveMentors() + authenticationService.getActiveMentees();
    }
    public Supplier<Number> fetchMentorCount() {
        return ()-> authenticationService.getActiveMentors();
    }
    public Supplier<Number> fetchMenteeCount() {
        return ()-> authenticationService.getActiveMentees();
    }

    public ActiveUsersMetrics(MeterRegistry registry) {
        String name = "number.of.activeUsers";
        Gauge.builder(name, fetchUserCount())
                .tags("Role", "All")
                .register(registry);
        Gauge.builder(name, fetchMentorCount())
                .tags("Role", "Mentor")
                .register(registry);
        Gauge.builder(name, fetchMenteeCount())
                .tags("Role", "Mentee")
                .register(registry);
    }
}

