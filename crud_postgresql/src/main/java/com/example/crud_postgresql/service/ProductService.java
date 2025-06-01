package com.example.crud_postgresql.service;
import com.example.crud_postgresql.exception.ResourceNotFoundException;
import com.example.crud_postgresql.exception.ValidationException;
import com.example.crud_postgresql.model.Category;
import com.example.crud_postgresql.model.Product;
import com.example.crud_postgresql.repository.CategoryRepository;
import com.example.crud_postgresql.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // Assuming you have a CategoryRepository

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Product createProduct(Product product, Long categoryId) {
        // Advanced validation: check if product name already exists (case-insensitive)
        if (productRepository.findByNameIgnoreCase(product.getName()).isPresent()) {
            throw new ValidationException("Product with name '" + product.getName() + "' already exists.");
        }

        // Associate with category
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
            product.setCategory(category);
        }

        product.setDeleted(false); // Ensure it's not soft-deleted on creation
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findByDeletedFalse(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .filter(product -> !product.isDeleted()) // Only return active products
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct, Long categoryId) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        // Update fields only if provided in updatedProduct
        if (StringUtils.hasText(updatedProduct.getName()) && !updatedProduct.getName().equalsIgnoreCase(existingProduct.getName())) {
            // Check for name uniqueness only if name is actually changing
            if (productRepository.findByNameIgnoreCase(updatedProduct.getName()).isPresent()) {
                throw new ValidationException("Product with name '" + updatedProduct.getName() + "' already exists.");
            }
            existingProduct.setName(updatedProduct.getName());
        }
        Optional.ofNullable(updatedProduct.getDescription()).ifPresent(existingProduct::setDescription);
        Optional.ofNullable(updatedProduct.getPrice()).ifPresent(existingProduct::setPrice);
        Optional.ofNullable(updatedProduct.getStock()).ifPresent(existingProduct::setStock);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
            existingProduct.setCategory(category);
        }

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void softDeleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        product.setDeleted(true); // Mark as deleted
        productRepository.save(product);
    }

    @Transactional
    public void hardDeleteProduct(Long id) {
        // This is a permanent delete, usually reserved for administrators or specific cases
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            return getAllProducts(pageable);
        }
        return productRepository.findByDeletedFalseAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Transactional
    public List<Product> createMultipleProducts(List<Product> products) {
        // This is a batch operation example
        // You might want to add more robust validation for each product in the list
        products.forEach(product -> {
            if (productRepository.findByNameIgnoreCase(product.getName()).isPresent()) {
                throw new ValidationException("Product with name '" + product.getName() + "' already exists in batch.");
            }
            product.setDeleted(false);
        });
        return productRepository.saveAll(products); // Uses batch insert/update for efficiency
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return null;
    }
}