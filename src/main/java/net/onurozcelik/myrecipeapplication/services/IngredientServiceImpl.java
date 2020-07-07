package net.onurozcelik.myrecipeapplication.services;

import lombok.extern.slf4j.Slf4j;
import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.converters.IngredientToIngredientCommand;
import net.onurozcelik.myrecipeapplication.domain.Ingredient;
import net.onurozcelik.myrecipeapplication.domain.Recipe;
import net.onurozcelik.myrecipeapplication.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand converter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
           log.error("Recipe not found");
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
        if (!ingredientOptional.isPresent()) {
            log.error("Ingredient not found");
        }
        return converter.convert(ingredientOptional.get());
    }
}
