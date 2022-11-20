package com.mentor4you.config;

import com.mentor4you.repository.UserRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Supplier;

@Component
public class InternetConnectionMetrics {
    @Lazy
    @Autowired
    protected UserRepository userRepository;
    public Supplier<Number> fetchInternetConnection() {
        return ()->  checkInternet() ? 1 : 0;
    }

    private boolean checkInternet() {
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public InternetConnectionMetrics(MeterRegistry registry) {
        String name = "internet.connection";
        Gauge.builder(name, fetchInternetConnection())
                .tags("Connection", "Enable")
                .register(registry);
    }
}

