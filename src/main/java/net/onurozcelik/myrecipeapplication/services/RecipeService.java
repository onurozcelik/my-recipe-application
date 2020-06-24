package net.onurozcelik.myrecipeapplication.services;

import net.onurozcelik.myrecipeapplication.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
