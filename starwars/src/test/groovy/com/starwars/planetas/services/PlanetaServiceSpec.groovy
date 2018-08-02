package com.starwars.planetas.services

import com.starwars.planetas.domain.Planeta
import com.starwars.planetas.repositories.PlanetaRepository
import com.starwars.planetas.transitory.commands.Planeta.CriarPlanetaComando
import spock.lang.Specification

class PlanetaServiceSpec extends Specification {

    PlanetaService service

    def planetaRepositoryMock = Mock(PlanetaRepository)

    def setup() {
        this.service = new PlanetaService(
                this.planetaRepositoryMock
        )
    }

    def "criarESalvar(): Quando o planeta não existe na base de dados"() {
        setup:
        def comando = new CriarPlanetaComando(
                nome: "Tatooine",
                terreno: "Montanha, Pradaria, Floresta Tropical",
                clima: "Temperado"
        )

        def planeta = Stub(Planeta)

        when: 'O Service.criarESalvar é invocado'
        def resposta = this.service.criarESalvar(comando)

        then: 'Deve verificar se o planeta existe na base de dados'
        1 * this.planetaRepositoryMock.findByNome(comando.nome) >> null

        and: 'Retorna o planeta que foi salvo com base no comando'
        1 * this.planetaRepositoryMock.save(_) >> planeta

        and: 'Garantir o tipo retornado'
        resposta instanceof Planeta

        and: 'Não há mais interações'
        0 * _
    }

    def "criarESalvar(): Quando já existe o planeta"() {
        setup:
        def comando = new CriarPlanetaComando()
        comando.nome >> "Tatooine"

        def planeta = Stub(Planeta)

        when: 'O Service.criarESalvar é invocado'
        this.service.criarESalvar(comando)

        then: 'Deve verificar se o planeta existe na base de dados'
        1 * this.planetaRepositoryMock.findByNome(comando.nome) >> planeta

        and: 'Verifica mensagem de erro lançada'
        def ex = thrown(RuntimeException)
        ex.message == "Este Planeta existe na base de dados"

        and: 'Não há mais interações'
        0 * _
    }

    def "obterPlanetas()"() {
        setup:
        def planetas = [Stub(Planeta), Stub(Planeta)]

        when: 'O Service.obterPlanetas é invocado'
        def resultado = this.service.obterPlanetas()

        then: 'Deve retornar uma lista de planetas'
        1 * this.planetaRepositoryMock.findAll() >> planetas

        and:
        resultado.size() == 2

        and: 'Garantir o tipo retornado'
        resultado.every {
            it instanceof Planeta
        }

        and: 'Não há mais interações'
        0 * _
    }

    def "obterPlanetaPorId()"() {
        setup:
        def id = "1"
        def planeta = Stub(Planeta)

        when: 'O Service.obterPlanetaPorId é invocado'
        def resultado = this.service.obterPlanetaPorId(id)

        then: 'Deve retornar uma lista de planetas'
        1 * this.planetaRepositoryMock.findById(id) >> Optional.of(planeta)

        and: 'Garantir o tipo retornado'
        resultado instanceof Optional

        and: 'Não há mais interações'
        0 * _
    }

    def "obterPlanetaPorNome()"() {
        setup:
        def nome = "Tatooine"
        def planeta = Stub(Planeta)

        when: 'O Service.obterPlanetaPorNome é invocado'
        def resultado = this.service.obterPlanetaPorNome(nome)

        then: 'Deve retornar uma lista de planetas'
        1 * this.planetaRepositoryMock.findByNome(nome) >> planeta

        and: 'Garantir o tipo retornado'
        resultado instanceof Planeta

        and: 'Não há mais interações'
        0 * _
    }
}