package com.mentor4you.domain.mapper;

import com.mentor4you.domain.dto.AddCategoryDTO;
import com.mentor4you.domain.dto.CategoryDTO;
import com.mentor4you.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  CategoryDTO toCategoryDTO(Category category);

  @Mapping(target = "name", source = "name")
  Category toCategory(AddCategoryDTO addCategoryDTO);
}
