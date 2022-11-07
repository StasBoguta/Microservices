package com.mentor4you.service;

import com.mentor4you.domain.User;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<User> getUserById(Integer id);
}
