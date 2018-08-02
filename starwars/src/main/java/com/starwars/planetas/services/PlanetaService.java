package com.starwars.planetas.services;

import com.maispartners.datamais.services.BaseCrudService;
import com.starwars.planetas.clients.SwApiClient;
import com.starwars.planetas.domain.Planeta;
import com.starwars.planetas.repositories.PlanetaRepository;
import com.starwars.planetas.transitory.commands.Planeta.CriarPlanetaComando;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanetaService extends BaseCrudService<Planeta, String> {

    private PlanetaRepository planetaRepository;
    private static final String PLANETA_JA_EXISTE_NA_BASE = "Este Planeta existe na base de dados";

    @Autowired
    public PlanetaService(PlanetaRepository planetaRepository) {
        super(planetaRepository);
        this.planetaRepository = planetaRepository;
    }

    public Iterable<Planeta> obterPlanetas() {
        return this.obter();
    }

    public Optional<Planeta> obterPlanetaPorId(String id) {
        return this.planetaRepository.findById(id);
    }

    public Planeta obterPlanetaPorNome(String nome) {
        return this.planetaRepository.findByNome(nome);
    }

    public Planeta criarESalvar(CriarPlanetaComando comando) {
        this.validarExistencia(comando);
        Integer aparicoes = this.obterAparicoes(comando);

        Planeta planeta = new Planeta();
        planeta.setNome(comando.getNome());
        planeta.setClima(comando.getClima());
        planeta.setTerreno(comando.getTerreno());
        planeta.setAparicoes(aparicoes);

        this.planetaRepository.save(planeta);

        return planeta;
    }

    private Integer obterAparicoes(CriarPlanetaComando comando) {
        SwApiClient swApiClient = new SwApiClient();
        JSONArray resposta = swApiClient.obterResposta(comando.getNome());
        return resposta.length();
    }

    private void validarExistencia(CriarPlanetaComando comando) {
        if (this.planetaRepository.findByNome(comando.getNome()) != null) {
            throw new RuntimeException(PLANETA_JA_EXISTE_NA_BASE);
        }
    }
}