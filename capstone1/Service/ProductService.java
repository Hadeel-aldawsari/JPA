package com.example.capstone1.Service;

import com.example.capstone1.Model.Product;
import com.example.capstone1.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    // Get all products
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    //check the category exists
    public boolean add(Product product) {
        if (categoryService.isCatogetyExist(product.getCategory_id())) {
            productRepository.save(product);
            return true;
        }
        return false;
    }


    public boolean update(Integer id, Product updatedProduct) {
        Product existingProduct = productRepository.getById(id);
        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setCategory_id(updatedProduct.getCategory_id());
            existingProduct.setPrice(updatedProduct.getPrice());

            productRepository.save(existingProduct);
            return true;
        }
        return false;
    }


    public boolean delete(Integer id) {
        Product existingProduct = productRepository.getById(id);
        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return true;
        }
        return false;
    }

    // Get a product by ID
    public Product getProductByID(Integer id) {
        return productRepository.getById(id);
    }
}
