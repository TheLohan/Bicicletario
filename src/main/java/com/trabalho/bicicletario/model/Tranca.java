package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tranca {

    @Id
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
