package com.trabalho.bicicletario.model;

public enum StatusTranca {
    NOVA("Nova"),
    LIVRE("Livre"),
    OCUPADA("Ocupada"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada"),;

    private StatusTranca(String descricao) {
    }
}
