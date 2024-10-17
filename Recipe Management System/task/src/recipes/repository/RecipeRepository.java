package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    boolean existsById(Long id);

}
