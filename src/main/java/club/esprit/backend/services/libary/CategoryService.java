package club.esprit.backend.services.libary;

import club.esprit.backend.entities.libary.Category;
import club.esprit.backend.repository.libary.CategotyRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService{
    private CategotyRepo categotyRepo;
    @Override
    public Category addCategory(Category category) {
        return categotyRepo.save(category);
    }

    @Override
    public List<Category> getAll() {
        return categotyRepo.findAll();
    }
}
