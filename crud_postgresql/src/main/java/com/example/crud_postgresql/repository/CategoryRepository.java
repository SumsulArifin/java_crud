package com.example.crud_postgresql.repository;
import com.example.crud_postgresql.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query method: Spring Data JPA automatically generates implementation
    Optional<Category> findByName(String name);
    List<Category> findByParent(Category parent); // Find children of a specific parent
    List<Category> findByParentIsNull(); // Find root categories (where parent is null)
}