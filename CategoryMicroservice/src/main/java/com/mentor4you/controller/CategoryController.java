package com.mentor4you.controller;

import com.mentor4you.domain.Category;
import com.mentor4you.domain.UpdateCategoryNameDTO;
import com.mentor4you.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable(name = "categoryId") Integer categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategoryName(@PathVariable(name = "categoryId") Integer categoryId,
                                                @RequestBody UpdateCategoryNameDTO updateCategoryNameDTO) {
        categoryService.updateCategoryName(categoryId, updateCategoryNameDTO.getName());
        return ResponseEntity.ok().build();
    }
}
