package com.mentor4you.service;

import com.mentor4you.domain.dto.AddCategoryDTO;
import com.mentor4you.domain.model.Category;
import java.util.List;

public interface CategoryService {

  List<Category> getAllCategories();

  Category getCategoryById(Integer categoryId);

  Category addCategory(AddCategoryDTO addCategoryDTO);
}
