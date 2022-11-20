package com.mentor4you.config;

import com.mentor4you.model.Role;
import com.mentor4you.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class NewUsersMetrics {
    @Lazy
    @Autowired
    protected UserRepository userRepository;

    public Supplier<Number> fetchUserCount() {
        return ()-> userRepository.findAll().size();
    }
    public Supplier<Number> fetchMentorCount() {
        return ()-> userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(Role.MENTOR))
                .count();
    }
    public Supplier<Number> fetchMenteeCount() {
        return ()-> userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(Role.MENTEE))
                .count();
    }

    public NewUsersMetrics(MeterRegistry registry) {
        String name = "number.of.newUsers";
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

