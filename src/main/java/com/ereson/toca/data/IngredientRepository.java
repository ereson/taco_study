package com.ereson.toca.data;

import com.ereson.toca.Ingredient;

public interface IngredientRepository  {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
