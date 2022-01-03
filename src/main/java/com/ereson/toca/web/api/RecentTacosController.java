package com.ereson.toca.web.api;


import com.ereson.toca.Taco;
import com.ereson.toca.data.TacoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @RepositoryRestController注解会确保控制器中的路径会以spring data REST 的基础路径为前缀
 * 但是无法保证处理器方法的返回值会写入响应体中
 * 要么为方法添加@ResponseBody，或者放入ResponseEntity中
 */
@RepositoryRestController
public class RecentTacosController {

    private TacoRepository tacoRepo;

    public RecentTacosController(TacoRepository tacoRepository) {
        this.tacoRepo = tacoRepository;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<Resources<TacoResource>> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(pageRequest).getContent();
        List<TacoResource> tacoResources = new TacoResourceAssembler().toResource(tacos);
        Resources<TacoResource> recentResources = new Resources<TacoResource>(tacoResources);
        recentResources.add(linkTo(methodOn(RecentTacosController.class).recentTacos()).withRel("recents"));

        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }

    /**
     * 声明为spring上下文中自动创建的bean，spring HATEOAS会自动发现这个bean
     * @param links
     * @return
     */
    @Bean
    public ResourceProcessor<PagedResources<Resources<Taco>>> tacoProcessor(EntityLinks links) {
        //匿名类
        return new ResourceProcessor<PagedResources<Resources<Taco>>> () {
            @Override
            public PagedResources<Resources<Taco>> process(PagedResources<Resources<Taco>> resources) {
                resources.add(
                        links.linkFor(Taco.class)
                                .slash("recent")
                                .withRel("recents")
                );

                return resources;
            }
        };
    }
}
