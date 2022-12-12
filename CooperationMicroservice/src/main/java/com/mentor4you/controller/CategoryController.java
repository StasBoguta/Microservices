package com.mentor4you.controller;

import com.mentor4you.domain.dto.AddCategoryDTO;
import com.mentor4you.domain.dto.CategoryDTO;
import com.mentor4you.domain.mapper.CategoryMapper;
import com.mentor4you.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping
  public List<CategoryDTO> getAllCategories() {
    return categoryService.getAllCategories().stream().map(categoryMapper::toCategoryDTO).toList();
  }

  @GetMapping("/{categoryId}")
  public CategoryDTO getAllCategories(@PathVariable Integer categoryId) {
    return categoryMapper.toCategoryDTO(categoryService.getCategoryById(categoryId));
  }

  @PostMapping
  public ResponseEntity<?> addCategory(@RequestBody AddCategoryDTO addCategoryDTO) {
    categoryService.addCategory(addCategoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
