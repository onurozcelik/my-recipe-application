package net.onurozcelik.myrecipeapplication.converters;

import lombok.Synchronized;
import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.domain.Ingredient;
import net.onurozcelik.myrecipeapplication.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        if(ingredientCommand.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setId(ingredientCommand.getId());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setUnitOfMeasure(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));
        return ingredient;
    }
}
