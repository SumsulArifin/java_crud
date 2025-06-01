package com.example.crud_postgresql.repository;

import com.example.crud_postgresql.model.Category;
import com.example.crud_postgresql.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom query method for finding products by name (case-insensitive)
    Optional<Product> findByNameIgnoreCase(String name);

    // Find active products by category and paginate/sort
    Page<Product> findByDeletedFalseAndCategory(Category category, Pageable pageable);

    // Search active products by name or description containing a keyword
    Page<Product> findByDeletedFalseAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descriptionKeyword, Pageable pageable);

    // Find all active products, with pagination and sorting
    Page<Product> findByDeletedFalse(Pageable pageable);

    // Find all products (including soft-deleted ones)
    List<Product> findAll();
}