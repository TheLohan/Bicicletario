package com.trabalho.bicicletario.Enum;

import lombok.Getter;

@Getter
public enum StatusBicicleta {
    DISPONIVEL("Disponível"),
    EM_USO("Em uso"),
    REPARO_SOLICITADO("Reparo solicitado"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada");

    private final String descricao;

    StatusBicicleta(String descricao) {
        this.descricao = descricao;
    }

}
