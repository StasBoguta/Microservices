package com.mentor4you.service;

import com.mentor4you.domain.Category;

import java.util.concurrent.CompletableFuture;

public interface CategoryService {

    CompletableFuture<Category> getCategoryById(Integer id);
}
