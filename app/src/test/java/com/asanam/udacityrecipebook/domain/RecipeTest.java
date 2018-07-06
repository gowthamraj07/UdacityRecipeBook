package com.asanam.udacityrecipebook.domain;

import com.asanam.udacityrecipebook.dto.Ingredient;
import com.asanam.udacityrecipebook.dto.RecipeDto;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeTest {

    @Test
    public void isNameEqual() {
        RecipeDto dto = new RecipeDto();
        dto.setName("any name");

        Recipe domain = new Recipe(dto);

        assertEquals(dto.getName(), domain.getName());
    }

    @Test
    public void shouldReturnIngredientsList() {
        RecipeDto dto = new RecipeDto();
        dto.setIngredients(getMockIngredientList());

        Recipe domain = new Recipe(dto);

        assertEquals(dto.getIngredients().size(), domain.getIngredients().size());

    }

    private List<Ingredient> getMockIngredientList() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setIngredient("any ingredient");
        ingredient1.setMeasure("any measure");
        ingredient1.setQuantity("any quantity");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setIngredient("any other ingredient");
        ingredient2.setMeasure("any other measure");
        ingredient2.setQuantity("any other quantity");

        return Arrays.asList(ingredient1, ingredient2);
    }
}