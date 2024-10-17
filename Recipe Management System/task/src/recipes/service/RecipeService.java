package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public boolean existById(Long id) {
        return recipeRepository.existsById(id);
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findRecipesByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public boolean isOwner(Recipe recipe, String author) {
        return recipe.getAuthor().equals(author);
    }

}
