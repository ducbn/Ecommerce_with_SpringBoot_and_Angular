package com.ducbn.shopapp.services;

import com.ducbn.shopapp.dtos.CategoryDTO;
import com.ducbn.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);

    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long id);
}
