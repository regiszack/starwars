package com.starwars.planetas.transitory.commands.Planeta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarPlanetaComando {
    private String nome;
    private String clima;
    private String terreno;
}