package net.onurozcelik.myrecipeapplication.domain;

import static org.junit.Assert.*;

public class CategoryTest {
    private Category category;

    @org.junit.Before
    public void setUp() throws Exception {
        category = new Category();
        category.setId(4L);
    }

    @org.junit.Test
    public void getId() {
        Long idValue = 4L;
        assertEquals(idValue, category.getId());
    }

    @org.junit.Test
    public void getDescription() {
        String description = "Desc 1";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @org.junit.Test
    public void getRecipes() {

    }
}