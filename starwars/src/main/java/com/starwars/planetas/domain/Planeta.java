package com.starwars.planetas.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Planeta {

    @Id
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    private String clima;

    @NotBlank
    private String terreno;

    @NotNull
    private Integer aparicoes;
}
