package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        Iterable<Recipe> theRecipes = recipeService.getRecipes();
        model.addAttribute("recipes", theRecipes);

        return "index";
    }
}
