package net.onurozcelik.myrecipeapplication.controllers;

import lombok.extern.slf4j.Slf4j;
import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.commands.RecipeCommand;
import net.onurozcelik.myrecipeapplication.services.IngredientService;
import net.onurozcelik.myrecipeapplication.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand command = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", command);
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{recipeId}/ingredients/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                , Long.valueOf(id));
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }
}
