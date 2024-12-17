package com.trabalho.bicicletario.enums;

import lombok.Getter;

@Getter
public enum StatusTranca {
    NOVA("Nova"),
    LIVRE("Livre"),
    OCUPADA("Ocupada"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada"),;

    private final String descricao;

    StatusTranca(String descricao) {
        this.descricao = descricao;
    }
}
