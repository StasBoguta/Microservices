package com.mentor4you.config;

import com.mentor4you.repository.PostRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class AvailablePostsMetrics {
    @Lazy
    @Autowired
    protected PostRepository postRepository;

    public Supplier<Number> fetchPostCount() {
        return () -> postRepository.findAll().size();
    }

    public AvailablePostsMetrics(MeterRegistry registry) {
        String name = "number.of.availablePosts";
        Gauge.builder(name, fetchPostCount())
                .tags("Posts", "All")
                .register(registry);
    }
}

