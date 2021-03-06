package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;


@Controller
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipe/{recipeId}", "/recipe/show/{recipeId}"})
    public String showRecipe(@PathVariable long recipeId, Model model) {
        Recipe recipe = recipeService.findById(recipeId);

        // this is probably not the best place to handle this.
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cain't find it. Sorry");
        }

        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);

        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String createNewRecipe(@ModelAttribute("recipe") Recipe recipe) {
        Recipe savedRecipe = recipeService.save(recipe);

        return "redirect:/recipe/show/" + savedRecipe.getId();
    }

}
