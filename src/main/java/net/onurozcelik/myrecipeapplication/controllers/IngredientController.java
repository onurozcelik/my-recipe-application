package net.onurozcelik.myrecipeapplication.controllers;

import lombok.extern.slf4j.Slf4j;
import net.onurozcelik.myrecipeapplication.commands.IngredientCommand;
import net.onurozcelik.myrecipeapplication.commands.RecipeCommand;
import net.onurozcelik.myrecipeapplication.services.IngredientService;
import net.onurozcelik.myrecipeapplication.services.RecipeService;
import net.onurozcelik.myrecipeapplication.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String showIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand command = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", command);
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                , Long.valueOf(id));
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                , Long.valueOf(id));
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureService.findAllUoMs());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
}
