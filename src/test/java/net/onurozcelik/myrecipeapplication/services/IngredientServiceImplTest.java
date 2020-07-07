package net.onurozcelik.myrecipeapplication.services;

import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.converters.IngredientToIngredientCommand;
import net.onurozcelik.myrecipeapplication.domain.Ingredient;
import net.onurozcelik.myrecipeapplication.domain.Recipe;
import net.onurozcelik.myrecipeapplication.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        when(ingredientToIngredientCommand.convert(any())).thenReturn(ingredientCommand);
        IngredientCommand ingredientCommandReturned = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
        assertEquals(ingredientCommand.getId(), ingredientCommandReturned.getId());
    }
}