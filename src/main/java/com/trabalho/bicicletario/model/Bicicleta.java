package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bicicleta")
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano")
    private String ano;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "status")
    private String status;

    @Column(name = "idFuncionario")
    private Long idFuncionario;

    @Column(name = "statusAcaoReparador")
    private String statusAcaoReparador;

    public Bicicleta(String marca, String modelo, String ano, Integer numero, String status, Long idFuncionario, String statusAcaoReparador) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
        this.idFuncionario = idFuncionario;
        this.statusAcaoReparador = statusAcaoReparador;
    }

    public boolean dadosValidos() {
        return marca != null && modelo != null && ano != null && numero != null && status != null;
    }

}
