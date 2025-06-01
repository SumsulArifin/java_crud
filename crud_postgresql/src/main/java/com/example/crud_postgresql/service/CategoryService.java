package com.example.crud_postgresql.service;
import com.example.crud_postgresql.exception.CategoryNotFoundException;
import com.example.crud_postgresql.exception.DuplicateCategoryNameException;
import com.example.crud_postgresql.model.Category;
import com.example.crud_postgresql.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For transaction management
import java.util.List;
import java.util.Optional;

@Service // Indicates this is a Spring Service component
public class CategoryService {

    private final CategoryRepository categoryRepository;
    // Potentially other services if category operations involve other entities
    // private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository /*, ProductService productService */) {
        this.categoryRepository = categoryRepository;
        // this.productService = productService;
    }

    @Transactional(readOnly = true) // Read-only transactions are optimized
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public Category createCategory(Category category) {
        // Business logic: Ensure category name is unique
//        if (categoryRepository.findByName(category.getName()).isPresent()) {
//            throw new DuplicateCategoryException("Category with name '" + category.getName() + "' already exists.");
//        }
//        // Business logic: If parent is provided, ensure it exists
//        if (category.getParent() != null && category.getParent().getId() != null) {
//            categoryRepository.findById(category.getParent().getId())
//                    .orElseThrow(() -> new CategoryNotFoundException("Parent category not found with ID: " + category.getParent().getId()));
//        }

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        // Business logic: Update name only if it's different and not a duplicate
        if (!existingCategory.getName().equals(updatedCategory.getName())) {
            if (categoryRepository.findByName(updatedCategory.getName()).isPresent()) {
                throw new DuplicateCategoryNameException("Category with name '" + updatedCategory.getName() + "' already exists.");
            }
            existingCategory.setName(updatedCategory.getName());
        }

        existingCategory.setDescription(updatedCategory.getDescription());

//        // Business logic: Handle parent category updates, preventing self-referencing or circular dependencies
//        if (updatedCategory.getParent() != null && updatedCategory.getParent().getId() != null) {
//            if (updatedCategory.getParent().getId().equals(id)) {
//                throw new IllegalArgumentException("A category cannot be its own parent.");
//            }
//            Category newParent = categoryRepository.findById(updatedCategory.getParent().getId())
//                    .orElseThrow(() -> new CategoryNotFoundException("New parent category not found with ID: " + updatedCategory.getParent().getId()));
//            existingCategory.setParent(newParent);
//        } else {
//            existingCategory.setParent(null); // Detach from parent if null is passed
//        }

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        // Business logic: What happens to products associated with this category?
        // Option 1: Disassociate products (set their category_id to null)
        // productService.removeCategoryFromProducts(id);
        // Option 2: Delete products associated with this category (use with extreme caution!)
        // productService.deleteProductsByCategoryId(id);
        // Option 3: Prevent deletion if products are associated
        // if (productService.countProductsByCategoryId(id) > 0) {
        //     throw new IllegalStateException("Cannot delete category as it has associated products.");
        // }

        // Business logic: What happens to child categories?
        // Option 1: Re-parent children to the deleted category's parent
        // categoryRepository.reparentChildren(id, categoryToDelete.getParent() != null ? categoryToDelete.getParent().getId() : null);
        // Option 2: Disallow deletion if children exist
        // if (!categoryToDelete.getChildren().isEmpty()) { // Assuming children are loaded or fetched
        //     throw new IllegalStateException("Cannot delete category as it has subcategories.");
        // }

        categoryRepository.delete(categoryToDelete);
    }

    // --- Potential new methods to support business logic ---

    @Transactional(readOnly = true)
    public List<Category> getRootCategories() {
        // Example: Find categories with no parent
        return categoryRepository.findByParentIsNull();
    }

    @Transactional(readOnly = true)
    public List<Category> getSubcategories(Long parentId) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(() -> new CategoryNotFoundException("Parent category not found with ID: " + parentId));
        return categoryRepository.findByParent(parent);
    }

    // You might also add methods here if the ProductService needs to call them
    // For example, to check if a category is "active" or "visible" before linking products.
}

//
//@Service
//public class CategoryService {
//    @Autowired
//    private final CategoryRepository categoryRepository;
//
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
////
////    @Transactional
////    public Category createCategory(Category category) {
////        // Advanced validation: check if product name already exists (case-insensitive)
////        if (categoryRepository.findByNameIgnoreCase(category.getName()).isPresent()) {
////            throw new ValidationException("Product with name '" + category.getName() + "' already exists.");
////        }
////        return categoryRepository.save(category);
////    }
//    public Page<Category> getAllProducts(Pageable pageable) {
//        return categoryRepository.findAll(pageable);
//    }
//}
//
