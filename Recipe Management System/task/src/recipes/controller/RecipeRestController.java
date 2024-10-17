package recipes.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipes.entity.Recipe;
import recipes.service.RecipeService;
import recipes.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeRestController {

    final RecipeService recipeService;

    final UserService userService;

    public RecipeRestController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.findAll();
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Long>> addRecipe(@RequestBody Recipe recipe,
                                                       @AuthenticationPrincipal
                                                       UserDetails details) {
        recipe.setAuthor(details.getUsername());
        Recipe savedRecipe = recipeService.save(recipe);
        Map<String, Long> response = new HashMap<>();
        response.put("id", savedRecipe.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipeFound = recipeService.findById(id);
        return recipeFound
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetails details) {
        if (!recipeService.existById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Recipe> foundRecipe = recipeService.findById(id);

        if (foundRecipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Recipe recipe = foundRecipe.get();
        boolean isOwner = recipeService.isOwner(recipe, details.getUsername());
        if (isOwner) {
            recipeService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id,
                                             @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        if (!recipeService.existById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Recipe> foundRecipe = recipeService.findById(id);

        if (foundRecipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Recipe recipeToUpdate = foundRecipe.get();
        boolean isOwner = recipeService.isOwner(recipeToUpdate, details.getUsername());
        if (isOwner) {
            recipeToUpdate.setName(recipe.getName());
            recipeToUpdate.setDescription(recipe.getDescription());
            recipeToUpdate.setCategory(recipe.getCategory());
            recipeToUpdate.setIngredients(recipe.getIngredients());
            recipeToUpdate.setDirections(recipe.getDirections());
            recipeToUpdate.setDate(LocalDateTime.now());
            recipeService.save(recipeToUpdate);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/search/")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam(required = false) Optional<String> category,
            @RequestParam(required = false) Optional<String> name) {
        if (category.isPresent() == name.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Recipe> recipes;
        if (category.isPresent()) {
            recipes = recipeService.findRecipesByCategory(category.get());
        } else {
            recipes = recipeService.findRecipesByName(name.get());
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
                       ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
