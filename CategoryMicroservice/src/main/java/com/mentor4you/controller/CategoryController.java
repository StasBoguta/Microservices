package com.mentor4you.controller;

import com.mentor4you.domain.Category;
import com.mentor4you.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
