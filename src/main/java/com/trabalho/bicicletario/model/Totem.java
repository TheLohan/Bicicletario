package com.trabalho.bicicletario.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Totem {
    private int id;
    private String localizacao;
    private String descricao;

    public Totem(int id, String localizacao, String descricao) {
        this.id = id;
        this.localizacao = localizacao;
        this.descricao = descricao;
    }
}
