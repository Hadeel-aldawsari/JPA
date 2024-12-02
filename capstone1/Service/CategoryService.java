package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Repository.CategoryRepository;
import com.example.capstone1.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    // Get all products
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }


    public Boolean add(Category category) {
        if (categoryService.isCatogetyExist(category.getId())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }


    public Boolean update(Integer id, Category updatedCategory) {
        Category existingCategory = categoryRepository.getById(id);
        if (existingCategory != null) {
            existingCategory.setName(updatedCategory.getName());
            categoryRepository.save(existingCategory);
            return true;
        }
        return false;
    }

    // Delete a product by ID
    public Boolean delete(Integer id) {
        Category existingCategory = categoryRepository.getById(id);
        if (existingCategory != null) {
            categoryRepository.delete(existingCategory);
            return true;
        }
        return false;
    }

    public Boolean isCatogetyExist(Integer catogeryid){
        for (Category c:categoryRepository.findAll()){
            if(c.getId().equals(catogeryid)){
                return true;
            }
        }
        return false;
    }


    public Category getCategoryByID(Integer id) {
        return categoryRepository.getById(id);
    }
}
