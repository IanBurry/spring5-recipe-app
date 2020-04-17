package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @InjectMocks
    RecipeController recipeController;

    MockMvc theMockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        theMockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    public void testShowRecipe() throws Exception {
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        when(recipeService.findById(recipeId)).thenReturn(recipe);

        String requestString = "/recipe/" + recipeId;
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get(requestString))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

        verify(recipeService).findById(recipeId);
    }

    @Test
    public void testShowNewRecipeForm() throws Exception {
        String requestString = "/recipe/new";
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get(requestString))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipe() throws Exception {
        String postRequest = "/recipe";
        Recipe newRecipe = new Recipe();
        newRecipe.setId(2L);
        newRecipe.setName("Test Recipe");
        String viewName = "redirect:/recipe/show/" + newRecipe.getId();

        when(recipeService.save(any())).thenReturn(newRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(post(postRequest)
                .param("name", "Test Recipe")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name(viewName));
    }

    @Test
    public void testShowUpdateRecipeForm() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Update Recipe");
        String getRequest = "/recipe/update/" + recipe.getId();

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get(getRequest))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

}