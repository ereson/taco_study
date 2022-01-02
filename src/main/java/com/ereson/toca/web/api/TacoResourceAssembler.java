package com.ereson.toca.web.api;


import com.ereson.toca.Taco;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * taco装配器
 */
public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {

    public List<TacoResource> toResource(List<Taco> tacos) {
        List<TacoResource> tacoResources = new ArrayList<>();
        tacos.forEach(taco -> tacoResources.add(createResourceWithId(taco.getId(), taco)));
        return tacoResources;
    }

    /**
     * 告诉超类在创建TacoResource中的链接时使用DesignTacoController来确定所有URL的基础路径
     */
    public TacoResourceAssembler() {
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateResource(Taco taco) {
        return new TacoResource(taco);
    }

    @Override
    public TacoResource toResource(Taco taco) {
        return createResourceWithId(taco.getId(), taco);
    }
}
