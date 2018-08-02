package com.starwars.planetas.repositories;

import com.starwars.planetas.domain.Planeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "planeta", path = "planeta")
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

    Planeta findByNome(String nome);

    Optional<Planeta> findById(String id);

    List<Planeta> findAll();
}