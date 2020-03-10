package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.h2.index.Index;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        // mock Service and Model
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipesData);

        // assert that the method returns the correct string ("index")
        assertEquals("index", indexController.getIndexPage(model));

        // verify interaction with mocks
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute("recipes", recipesData);
    }
}