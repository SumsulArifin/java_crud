package com.example.crud_postgresql.controller;
//
// Assuming you have a Product entity and ProductService/Repository// New: Product Service
import com.example.crud_postgresql.model.Category;
import com.example.crud_postgresql.model.Product;
import com.example.crud_postgresql.service.CategoryService;
import com.example.crud_postgresql.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService; // Inject ProductService

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // --- Existing Category Management Endpoints (from previous example) ---

    /**
     * GET /api/categories
     * Retrieve all categories.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /api/categories/{id}
     * Retrieve a category by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/categories
     * Create a new category.
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * PUT /api/categories/{id}
     * Update an existing category.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/categories/{id}
     * Delete a category by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // --- NEW: Product-related Endpoints for Category Controller ---

    /**
     * GET /api/categories/{categoryId}/products
     * Retrieve all products belonging to a specific category.
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        // First, ensure the category exists
        if (!categoryService.getCategoryById(categoryId).isPresent()) {
            return ResponseEntity.notFound().build(); // 404 if category not found
        }

        // Delegate to ProductService to find products by category
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/categories/{categoryId}/products/similar/{productId}
     * Retrieve similar products for a given product within the context of a category.
     * "Similar" here might mean:
     * 1. Other products in the *same category*.
     * 2. Products in *related categories* (e.g., child categories, parent category).
     * 3. Products matching certain tags or attributes (requires more complex ProductService logic).
     */
//    @GetMapping("/{categoryId}/products/similar/{productId}")
//    public ResponseEntity<List<Product>> getSimilarProductsInCategory(
//            @PathVariable Long categoryId,
//            @PathVariable Long productId) {
//
//        // Ensure both category and product exist
//        if (!categoryService.getCategoryById(categoryId).isPresent() ||
//                !productService.getProductById(productId).isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Delegate to ProductService for the logic of finding similar products.
//        // The ProductService might take categoryId as a hint for similarity.
//        List<Product> similarProducts = productService.findSimilarProducts(productId, categoryId);
//
//        if (similarProducts.isEmpty()) {
//            return ResponseEntity.noContent().build(); // 204 if no similar products
//        }
//
//        return ResponseEntity.ok(similarProducts);
//    }

    // You might also have endpoints like:
    // GET /api/categories/{categoryId}/product-count
    // GET /api/categories/{categoryId}/top-products
}








































//import com.example.crud_postgresql.model.Category;
//import com.example.crud_postgresql.model.Product;
//import com.example.crud_postgresql.service.CategoryService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/category")
//public class CategoryController {
//
//    @Autowired
//    private CategoryService categoryService;
////    @PostMapping
////    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
////        Category createdCategory = categoryService.createCategory(category);
////        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
////    }
//
//    @GetMapping
//    public ResponseEntity<Page<Category>> getAllCategory(Pageable pageable) {
//        Page<Category> categories = categoryService.getAllProducts(pageable);
//        return ResponseEntity.ok(categories);
//    }
//
//}
