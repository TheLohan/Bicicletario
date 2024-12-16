package com.trabalho.bicicletario.model;

public enum StatusBicicleta {
    DISPONIVEL("Disponível"),
    EM_USO("Em uso"),
    REPARO_SOLICITADO("Reparo solicitado"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada");

    private StatusBicicleta(String descricao) {

    }

}
