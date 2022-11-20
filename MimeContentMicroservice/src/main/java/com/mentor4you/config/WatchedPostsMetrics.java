package com.mentor4you.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class WatchedPostsMetrics {
    public Counter watchedPostsCounter;

    public WatchedPostsMetrics(MeterRegistry registry) {
        String name = "number.of.watchedPosts";
        watchedPostsCounter = Counter.builder(name)
                .tags("Posts", "All")
                .register(registry);
    }
}

