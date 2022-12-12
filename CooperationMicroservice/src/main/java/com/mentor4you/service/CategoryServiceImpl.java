package com.mentor4you.service;

import com.mentor4you.domain.dto.AddCategoryDTO;
import com.mentor4you.domain.mapper.CategoryMapper;
import com.mentor4you.domain.model.Category;
import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<Category> getAllCategories() {
    final List<Category> categories = new ArrayList<>();
    categoryRepository.findAll().forEach(categories::add);
    return categories;
  }

  @Override
  public Category getCategoryById(Integer categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category with provided id doesn't exist"));
  }

  @Override
  public Category addCategory(AddCategoryDTO addCategoryDTO) {
    return categoryRepository.save(categoryMapper.toCategory(addCategoryDTO));
  }
}
