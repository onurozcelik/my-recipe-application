package net.onurozcelik.myrecipeapplication.controllers;

import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.commands.RecipeCommand;
import net.onurozcelik.myrecipeapplication.services.IngredientService;
import net.onurozcelik.myrecipeapplication.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showIngredients() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(3L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void showIngredient() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(1L);
        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand1.setId(2L);
        IngredientCommand ingredientCommand3 = new IngredientCommand();
        ingredientCommand1.setId(3L);
        recipeCommand.getIngredients().add(ingredientCommand1);
        recipeCommand.getIngredients().add(ingredientCommand2);
        recipeCommand.getIngredients().add(ingredientCommand3);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand1);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }
}