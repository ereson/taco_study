package com.ereson.toca.web.api;

import com.ereson.toca.Taco;
import com.ereson.toca.data.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

;import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path="/design", produces={"application.json", "text/xml"})
@CrossOrigin(origins="*")
public class DesignTacoController {

    private TacoRepository tacoRepo;


    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
// 不带超链接的返回形式
//    public Iterable<Taco> recetTacos() {
//        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//        return tacoRepo.findAll(pageRequest).getContent();
//    }
    public Resources<TacoResource> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        List<Taco> tacos = tacoRepo.findAll(page).getContent();
//        Resources<Resource<Taco>> recentResources = Resources.wrap(tacos);

//        recentResources.add(ControllerLinkBuilder.linkTo(DesignTacoController.class)
//                .slash("recent")
//                .withRel("recents"));

        List<TacoResource> tacoResources = new TacoResourceAssembler().toResource(tacos);
        Resources<TacoResource> recentResources = new Resources<TacoResource>(tacoResources);
        recentResources.add(linkTo(methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));

        return recentResources;
    }
    @GetMapping("{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }
}
