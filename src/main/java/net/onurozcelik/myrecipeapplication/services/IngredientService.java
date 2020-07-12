package net.onurozcelik.myrecipeapplication.services;

import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
