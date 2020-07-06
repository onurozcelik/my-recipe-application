package net.onurozcelik.myrecipeapplication.converters;

import lombok.Synchronized;
import net.onurozcelik.myrecipeapplication.commands.RecipeCommand;
import net.onurozcelik.myrecipeapplication.domain.Category;
import net.onurozcelik.myrecipeapplication.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setCookTime(recipe.getCookTime());
        command.setPrepTime(recipe.getPrepTime());
        command.setDescription(recipe.getDescription());
        command.setDifficulty(recipe.getDifficulty());
        command.setDirections(recipe.getDirections());
        command.setServings(recipe.getServings());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setNotesCommand(notesConverter.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
