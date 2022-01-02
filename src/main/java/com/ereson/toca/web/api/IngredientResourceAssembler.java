package com.ereson.toca.web.api;

import com.ereson.toca.Ingredient;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }

    public IngredientResourceAssembler() {
        super(IngredientController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
