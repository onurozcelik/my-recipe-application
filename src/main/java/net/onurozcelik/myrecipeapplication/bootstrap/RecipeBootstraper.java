package net.onurozcelik.myrecipeapplication.bootstrap;

import net.onurozcelik.myrecipeapplication.domain.*;
import net.onurozcelik.myrecipeapplication.repositories.CategoryRepository;
import net.onurozcelik.myrecipeapplication.repositories.RecipeRepository;
import net.onurozcelik.myrecipeapplication.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstraper implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    private final List<Recipe> recipes = new ArrayList<>();

    public RecipeBootstraper(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    private List<Recipe> getRecipes() {
        Optional<UnitOfMeasure> teaspoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!teaspoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }
        Optional<UnitOfMeasure> tablepoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!tablepoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }
        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }
        Optional<UnitOfMeasure> pinchUomOptional = unitOfMeasureRepository.findByDescription("Pinch");
        if (!pinchUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }
        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");
        if (!ounceUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        UnitOfMeasure tablespoonUom = tablepoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();
        UnitOfMeasure pinchUom = pinchUomOptional.get();
        UnitOfMeasure ounceUom = ounceUomOptional.get();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found");
        }

        if(!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe recipe1 = new Recipe();
        recipe1.getCategories().add(americanCategory);
        recipe1.getCategories().add(mexicanCategory);
        recipe1.setDescription("Recipe 1");
        recipe1.setCookTime(10);
        recipe1.setPrepTime(5);
        recipe1.setServings(2);
        recipe1.setDifficulty(Difficulty.EASY);
        recipe1.setDirections("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam at nulla sed mi faucibus tincidunt sed vitae odio. Ut elementum arcu at scelerisque consequat. " +
                "Cras dictum ornare euismod. Mauris malesuada pellentesque neque, sit amet posuere lacus accumsan sed. Cras lacus lectus, ultricies ut magna et, varius viverra ex. Nulla eget vehicula mi. Fusce ac tincidunt tortor, id viverra lacus. " +
                "Donec vel felis viverra, interdum nibh at, porttitor enim. Phasellus faucibus metus at nisi mollis bibendum. Morbi mauris augue, viverra at dictum id, eleifend sed sapien. Pellentesque non ante id odio congue sollicitudin." +
                "\n\n" +
                "Sed imperdiet tellus eget velit consectetur elementum. Donec non ornare tortor. Sed ac neque lorem. Pellentesque ante odio, egestas nec ante tempus, tempus accumsan lorem. " +
                        "Aenean rhoncus magna vitae erat elementum, sit amet malesuada massa pulvinar. Aenean vel nisl aliquam, euismod nisi porta, scelerisque urna. " +
                        "Vivamus ac efficitur velit, at luctus nunc. Duis nec odio pharetra felis volutpat aliquam. Nam id orci hendrerit, auctor purus accumsan, ultricies eros. " +
                        "\n\n" +
                "Praesent mauris erat, vulputate et eros id, sagittis suscipit augue. Donec eu tincidunt dui, a accumsan lectus. Etiam quis tellus arcu. Nullam eu dolor lorem. Nunc fermentum, orci ut ullamcorper egestas, tortor purus mattis quam, ac sollicitudin erat arcu vel turpis. " +
                        "Sed dapibus maximus rutrum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer quis cursus nibh. Nam sed dignissim dolor, vestibulum laoreet tortor. In tristique orci libero, ac consequat est lobortis a. " +
                        "Sed sem ligula, lacinia id orci at, lobortis sollicitudin tortor. Proin posuere dui nec magna mattis luctus. Cras eu turpis tempus, iaculis eros at, aliquet nisi.");

        Notes notes1 = new Notes();
        notes1.setRecipeNotes("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam at nulla sed mi faucibus tincidunt sed vitae odio. Ut elementum arcu at scelerisque consequat. " +
                "Cras dictum ornare euismod. Mauris malesuada pellentesque neque, sit amet posuere lacus accumsan sed. Cras lacus lectus, ultricies ut magna et, varius viverra ex. Nulla eget vehicula mi. Fusce ac tincidunt tortor, id viverra lacus. " +
                "Donec vel felis viverra, interdum nibh at, porttitor enim. Phasellus faucibus metus at nisi mollis bibendum. Morbi mauris augue, viverra at dictum id, eleifend sed sapien. Pellentesque non ante id odio congue sollicitudin." +
                "\n\n" +
                "Sed imperdiet tellus eget velit consectetur elementum. Donec non ornare tortor. Sed ac neque lorem. Pellentesque ante odio, egestas nec ante tempus, tempus accumsan lorem. " +
                "Aenean rhoncus magna vitae erat elementum, sit amet malesuada massa pulvinar. Aenean vel nisl aliquam, euismod nisi porta, scelerisque urna. " +
                "Vivamus ac efficitur velit, at luctus nunc. Duis nec odio pharetra felis volutpat aliquam. Nam id orci hendrerit, auctor purus accumsan, ultricies eros. " +
                "\n\n" +
                "Praesent mauris erat, vulputate et eros id, sagittis suscipit augue. Donec eu tincidunt dui, a accumsan lectus. Etiam quis tellus arcu. Nullam eu dolor lorem. Nunc fermentum, orci ut ullamcorper egestas, tortor purus mattis quam, ac sollicitudin erat arcu vel turpis. " +
                "Sed dapibus maximus rutrum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer quis cursus nibh. Nam sed dignissim dolor, vestibulum laoreet tortor. In tristique orci libero, ac consequat est lobortis a. " +
                "Sed sem ligula, lacinia id orci at, lobortis sollicitudin tortor. Proin posuere dui nec magna mattis luctus. Cras eu turpis tempus, iaculis eros at, aliquet nisi.");
        notes1.setRecipe(recipe1);

        recipe1.setNotes(notes1);

        recipe1.getIngredients().add(new Ingredient("Ingredient 1", new BigDecimal(2), cupUom, recipe1));
        recipe1.getIngredients().add(new Ingredient("Ingredient 2", new BigDecimal(3), teaspoonUom, recipe1));
        recipe1.getIngredients().add(new Ingredient("Ingredient 3", new BigDecimal(4), tablespoonUom, recipe1));
        recipe1.getIngredients().add(new Ingredient("Ingredient 4", new BigDecimal(5), ounceUom, recipe1));
        recipe1.getIngredients().add(new Ingredient("Ingredient 1", new BigDecimal(1), pinchUom, recipe1));

        recipes.add(recipe1);

        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }
}
