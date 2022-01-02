package com.ereson.toca.web.api;

import com.ereson.toca.Ingredient;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

public class IngredientResource extends ResourceSupport {
    @Getter
    private String name;

    @Getter
    private Ingredient.Type type;

    /**
     * 初始化
     * @param ingredient
     */
    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
