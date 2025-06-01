package com.example.crud_postgresql.controller;
import com.example.crud_postgresql.model.Product;
import com.example.crud_postgresql.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,
                                                 @RequestParam(required = false) Long categoryId) {
        Product createdProduct = productService.createProduct(product, categoryId);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @Valid @RequestBody Product product,
                                                 @RequestParam(required = false) Long categoryId) {
        Product updatedProduct = productService.updateProduct(id, product, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteProduct(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Optional: for permanent deletion (use with caution and proper authorization)
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<Void> hardDeleteProduct(@PathVariable Long id) {
        productService.hardDeleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(@RequestParam String keyword, Pageable pageable) {
        Page<Product> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Product>> createMultipleProducts(@Valid @RequestBody List<Product> products) {
        List<Product> createdProducts = productService.createMultipleProducts(products);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }
}
