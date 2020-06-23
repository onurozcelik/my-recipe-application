package net.onurozcelik.myrecipeapplication.repositories;

import net.onurozcelik.myrecipeapplication.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
