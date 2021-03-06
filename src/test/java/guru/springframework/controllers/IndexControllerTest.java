package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
        // assert that the method returns the correct string ("index")
        assertEquals("index", indexController.getIndexPage(model));

        // verify interaction with mocks
        verify(recipeService, times(1)).getRecipes();

        // this avoids the recipe code. Just have to use a matcher with anySet() instead of raw string
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}