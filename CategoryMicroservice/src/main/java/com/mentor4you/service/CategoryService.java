package com.mentor4you.service;

import com.mentor4you.config.ActiveMQProperties;
import com.mentor4you.domain.Category;
import com.mentor4you.event.UpdateCategoryEvent;
import com.mentor4you.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final JmsTemplate jmsTemplate;

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) { return categoryRepository.findById(id).get(); }

    public void updateCategoryName(Integer categoryId, String newName) {
        categoryRepository.findById(categoryId).ifPresent(category -> {
            category.setName(newName);
            categoryRepository.save(category);
            UpdateCategoryEvent updateCategoryEvent = UpdateCategoryEvent.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
            jmsTemplate.convertAndSend(ActiveMQProperties.CATEGORY_EVENTS_QUEUE, updateCategoryEvent);
        });
    }
}
