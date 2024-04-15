package tn.esprit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.Service.ICategory;
import tn.esprit.backend.entities.Category;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategory categoryService;

    @Autowired
    public CategoryController(ICategory categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.of(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Category> updateCategoryName(@PathVariable Long id, @RequestBody String newName) {
        return ResponseEntity.ok(categoryService.updateCategoryName(id, newName));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<Category> updateCategoryDescription(@PathVariable Long id, @RequestBody String newDescription) {
        return ResponseEntity.ok(categoryService.updateCategoryDescription(id, newDescription));
    }
}