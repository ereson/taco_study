package com.ereson.toca.web.api;

import com.ereson.toca.Ingredient;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    /**
     * 对单个Ingredient返回一hal
     * @param ingredient
     * @return
     */
    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }

    /**
     * 对多个Ingredients转换为ingredientsResourc
     * @param ingredients
     * @return
     */
    public List<IngredientResource> toResource(List<Ingredient> ingredients) {
        List<IngredientResource> ingredientResourceList = new ArrayList<>();
        ingredients.forEach(ingredient -> ingredientResourceList.add(createResourceWithId(ingredient.getId(), ingredient)));
        return ingredientResourceList;
    }

    public IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
