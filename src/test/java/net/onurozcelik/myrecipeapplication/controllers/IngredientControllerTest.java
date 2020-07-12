package net.onurozcelik.myrecipeapplication.controllers;

import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.commands.RecipeCommand;
import net.onurozcelik.myrecipeapplication.commands.UnitOfMeasureCommand;
import net.onurozcelik.myrecipeapplication.services.IngredientService;
import net.onurozcelik.myrecipeapplication.services.RecipeService;
import net.onurozcelik.myrecipeapplication.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showIngredients() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(3L);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient"))
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
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void updateIngredient() throws Exception {
        UnitOfMeasureCommand uom1Command = new UnitOfMeasureCommand();
        uom1Command.setId(1L);
        UnitOfMeasureCommand uom2Command = new UnitOfMeasureCommand();
        uom2Command.setId(2L);
        Set<UnitOfMeasureCommand> uomCommands = new HashSet<>();
        uomCommands.add(uom1Command);
        uomCommands.add(uom2Command);
        when(unitOfMeasureService.findAllUoMs()).thenReturn(uomCommands);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient", "uoms"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        // when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "desc 1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void newIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uoms"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }
}