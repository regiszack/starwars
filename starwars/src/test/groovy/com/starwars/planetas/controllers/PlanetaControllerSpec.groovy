package com.starwars.planetas.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.starwars.planetas.domain.Planeta
import com.starwars.planetas.services.PlanetaService
import com.starwars.planetas.transitory.commands.Planeta.CriarPlanetaComando
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.nio.charset.Charset

import static java.lang.String.format
import static java.util.Arrays.asList
import static org.apache.http.HttpStatus.SC_NO_CONTENT
import static org.apache.http.HttpStatus.SC_OK
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

class PlanetaControllerSpec extends Specification {

    final private String URL = "/api/planeta"
    final private MediaType CONTENT_TYPE_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    MockMvc mockMvc

    def planetaServiceMock = Mock(PlanetaService)

    def setup() {
        def controller = new PlanetaController(
                this.planetaServiceMock
        )
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "obterPlanetas()"() {
        setup:
        List<Planeta> planetas = asList(new Planeta(), new Planeta())

        when: "Requisição feita para o controller obterPlanetas"
        def resultado = mockMvc.perform(get(format(URL)))
                .andReturn()
                .response

        then: "Retorna a lista de Planetas"
        1 * planetaServiceMock.obterPlanetas() >> planetas

        and: "Verifica se a resposta foi obtida com sucesso"
        resultado.status == SC_OK
    }

    def "criarPlaneta()"() {
        setup:
        def comando = Stub(CriarPlanetaComando)

        def planeta = Stub(Planeta)

        when: "Requisição feita para o controller criarESalvar"
        def resultado = mockMvc.perform(post(format(URL))
                .content(asJsonString(comando))
                .contentType(CONTENT_TYPE_JSON_UTF8))
                .andReturn()
                .response

        then: "Retorna o planeta criado"
        1 * planetaServiceMock.criarESalvar(_) >> planeta

        and: "Verifica se a resposta foi obtida com sucesso"
        resultado.status == SC_OK
    }

    def "obterPlanetaPorId()"() {
        setup:
        def ID = "1"

        def planeta = new Planeta()

        final String LOCAL_RESOURCE_URL = "/id/%s"
        final String NESTED_RESOURCE_URL = format(LOCAL_RESOURCE_URL, ID)

        when: "Requisição feita para o controller obterPlanetaPorId"
        def resultado = mockMvc.perform(get(URL + NESTED_RESOURCE_URL))
                .andReturn()
                .response

        then: "Retorna o Planeta obtido pelo ID"
        1 * planetaServiceMock.obterPlanetaPorId(ID) >> Optional.of(planeta)

        and: "Verifica se a resposta foi obtida com sucesso"
        resultado.status == SC_OK
    }

    def "obterPlanetaPorNome()"() {
        setup:
        def nome = "Tatooine"

        def planeta = new Planeta()

        final String LOCAL_RESOURCE_URL = "/nome/%s"
        final String NESTED_RESOURCE_URL = format(LOCAL_RESOURCE_URL, nome)

        when: "Requisição feita para o controller obterPlanetaPorNome"
        def resultado = mockMvc.perform(get(URL + NESTED_RESOURCE_URL))
                .andReturn()
                .response

        then: "Retorna o Planeta obtido pelo Nome"
        1 * planetaServiceMock.obterPlanetaPorNome(nome) >> planeta

        and: "Verifica se a resposta foi obtida com sucesso"
        resultado.status == SC_OK
    }

    def "deletarPlaneta()"() {
        setup:
        def ID = "1"

        when: "Requisição feita para o controller deletarPlaneta"
        def resultado = mockMvc.perform(delete(URL + "/" + ID))
                .andReturn()
                .response

        then: "Exclui o Planeta obtido pelo ID"
        1 * planetaServiceMock.deletar(ID)

        and: "Verifica se a resposta não tem conteúdo"
        resultado.status == SC_NO_CONTENT
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}