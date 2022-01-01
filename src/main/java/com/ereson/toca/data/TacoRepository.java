package com.ereson.toca.data;

import com.ereson.toca.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Taco save(Taco design);

    Optional<Taco> findById(Long id);

    Page<Taco> findAll(PageRequest pageRequest);
}
