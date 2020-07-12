package net.onurozcelik.myrecipeapplication.services;

import lombok.extern.slf4j.Slf4j;
import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.converters.IngredientCommandToIngredient;
import net.onurozcelik.myrecipeapplication.converters.IngredientToIngredientCommand;
import net.onurozcelik.myrecipeapplication.domain.Ingredient;
import net.onurozcelik.myrecipeapplication.domain.Recipe;
import net.onurozcelik.myrecipeapplication.repositories.RecipeRepository;
import net.onurozcelik.myrecipeapplication.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand converter;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id:" + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            return converter.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId())).findFirst().get());
        }
    }
}
