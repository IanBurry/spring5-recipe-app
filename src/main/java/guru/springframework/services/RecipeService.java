package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeService {
    // this is all gonna be very basic now
    // for ex. there are no real sanity checks

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // this only needs to be Iterable. Use abstractions.
    public Iterable<Recipe> getRecipes() {
        log.debug("Gettin' me some hot, iterable recipe action...");
        Iterable<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public Recipe save(Recipe object) {
        return recipeRepository.save(object);
    }

    public void delete(Recipe object){}

    public void deleteById(Long id){}

}
