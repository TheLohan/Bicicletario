package com.trabalho.bicicletario.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Bicicleta {

    private Integer id;

    private String marca;

    private String modelo;

    private String ano;

    private Integer numero;

    private Status status;

    public Bicicleta(Integer id, String marca, String modelo, String ano, Integer numero, Status status) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
    }

    public boolean dadosValidos() {
        return id != null && marca != null && modelo != null && ano != null && numero != null && status != null;
    }

}
