package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class DataLoader {

    private final RecipeService recipeService;

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeService recipeService, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @EventListener
    private void handleContextRefresh(ContextRefreshedEvent event) {
        log.debug("Loading dev data...");
        System.out.println("Loading dev data...");

        // get categories and uoms
        Category mexican = categoryRepository.findByDescription("Mexican").get();
        Category fastFood = categoryRepository.findByDescription("Fast Food").get();
        UnitOfMeasure each = unitOfMeasureRepository.findByDescription("Each").get();
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
        UnitOfMeasure dash = unitOfMeasureRepository.findByDescription("Dash").get();


        // create a new Recipe (wow!)
        Recipe guac = new Recipe();
        guac.setName("Perfect Guacamole");
        guac.setCookTime(10);
        guac.setPrepTime(7);
        guac.setServings(4);
        guac.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guac.setSource("Simply Recipes");
        guac.setDescription("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade. ");
        guac.setDifficulty(Difficulty.EASY);
        guac.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n");



        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("These are Guacamole recipe notes");
        guac.setNotes(guacNotes);
        guac.getCategories().add(mexican);
        guac.getCategories().add(fastFood);

        // now ingredients. Just one for now
        guac.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each)) ;
        guac.addIngredient(new Ingredient("salt", new BigDecimal(0.25), teaspoon));
        guac.addIngredient(new Ingredient("lime juice", new BigDecimal(1), tablespoon));
        guac.addIngredient(new Ingredient("onion", new BigDecimal(2), tablespoon));

        //    2 ripe avocados
//    1/4 teaspoon of salt, more to taste
//    1 tablespoon fresh lime juice or lemon juice
//    2 tablespoons to 1/4 cup of minced red onion or thinly sliced green onion
//    1-2 serrano chiles, stems and seeds removed, minced
//    2 tablespoons cilantro (leaves and tender stems), finely chopped
//    A dash of freshly grated black pepper
//    1/2 ripe tomato, seeds and pulp removed, chopped
//    Red radishes or jicama, to garnish
//    Tortilla chips, to serve


        recipeService.save(guac);

        // Now the taco recipe. Make this as barebones as possible: Name, description, category(ies)
        Recipe tacos = new Recipe();
        tacos.setName("Spicy Grilled Chicken Tacos");
        tacos.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        tacos.setPrepTime(5);
        tacos.setCookTime(15);
        tacos.setDifficulty(Difficulty.MODERATE);
        Set<Category> tacoCats = new HashSet<>();
        tacoCats.add(mexican);
        tacos.setCategories(tacoCats);

        recipeService.save(tacos);

        // and that's all for now
    }


}


