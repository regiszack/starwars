package com.starwars.planetas.controllers;

import com.starwars.planetas.domain.Planeta;
import com.starwars.planetas.services.PlanetaService;
import com.starwars.planetas.transitory.commands.Planeta.CriarPlanetaComando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RepositoryRestController
@RequestMapping("api")
public class PlanetaController {

    private PlanetaService planetaService;

    @Autowired
    public PlanetaController(PlanetaService planetaService) {
        this.planetaService = planetaService;
    }

    @RequestMapping(value = "planeta", method = GET)
    public @ResponseBody
    Iterable<Planeta> obterPlanetas() {
        return this.planetaService.obterPlanetas();
    }

    @RequestMapping(value = "planeta", method = POST)
    public @ResponseBody
    Planeta criarPlaneta(@RequestBody CriarPlanetaComando criarPlanetaComando) {
        return this.planetaService.criarESalvar(criarPlanetaComando);
    }

    @RequestMapping(value = "planeta/id/{id}", method = GET)
    public @ResponseBody
    Optional<Planeta> obterPlanetaPorId(@PathVariable String id) {
        return this.planetaService.obterPlanetaPorId(id);
    }

    @RequestMapping(value = "planeta/nome/{nome}", method = GET)
    public @ResponseBody
    Planeta obterPlanetaPorNome(@PathVariable String nome) {
        return this.planetaService.obterPlanetaPorNome(nome);
    }

    @RequestMapping(value = "planeta/{id}", method = DELETE)
    public ResponseEntity deletarPlaneta(@PathVariable String id) {
        this.planetaService.deletar(id);
        return noContent().build();
    }
}