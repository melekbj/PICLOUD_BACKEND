package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> getAll();
}
