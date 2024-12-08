package com.trabalho.bicicletario.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tranca {
    private Integer id;
    private Integer bicicleta;
    private Integer numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private String status;

    public Tranca(Integer id, Integer bicicleta, Integer numero, String localizacao, String anoDeFabricacao, String modelo, String status) {
        this.id = id;
        this.bicicleta = bicicleta;
        this.numero = numero;
        this.localizacao = localizacao;
        this.anoDeFabricacao = anoDeFabricacao;
        this.modelo = modelo;
        this.status = status;
    }

}
