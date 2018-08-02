package com.starwars.planetas.domain

import spock.lang.Specification
import spock.lang.Unroll

import static com.starwars.planetas.helpers.AssertionHelper.*
import static java.lang.Long.parseLong
import static java.lang.Math.random
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic
import static org.apache.commons.lang.RandomStringUtils.randomNumeric
import static org.codehaus.groovy.syntax.Numbers.parseInteger

class PlanetaSpec extends Specification {

    def dominio = new Planeta()

    @Unroll
    def 'Testa constraints do campo #campo para o valor #valor'() {
        setup:
        dominio."$campo" = valor

        expect:
        assertValidationError(dominio, "$campo", "$erroValidacao")

        where:
        campo       | valor || erroValidacao
        'nome'      | null  || NOT_BLANK
        'clima'     | null  || NOT_BLANK
        'terreno'   | null  || NOT_BLANK
        'aparicoes' | null  || NOT_NULL
    }

    def 'Domínio válido'() {
        when:
        def planeta = new Planeta(
                id: gerarId(),
                nome: randomAlphabetic(10),
                clima: randomAlphabetic(10),
                terreno: randomAlphabetic(10),
                aparicoes: gerarInteiro()
        )

        then: 'Não há erros de validação'
        assertValidationHasNoErrors(planeta)
    }

    def gerarId() {
        parseLong(randomNumeric(10))
    }

    def gerarValor() {
        random() * 100
    }

    def gerarInteiro() {
        parseInteger(randomNumeric(3))
    }
}