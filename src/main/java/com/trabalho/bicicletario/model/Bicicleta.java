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
    private Long numero;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano")
    private String ano;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusBicicleta status;

    @Column(name = "idFuncionario")
    private Long idFuncionario;

    @Column(name = "statusAcaoReparador")
    private String statusAcaoReparador;

    @OneToOne(mappedBy = "bicicleta")
    private Tranca tranca;

    public Bicicleta(String marca, String modelo, String ano, Long numero, StatusBicicleta status, Long idFuncionario, String statusAcaoReparador) {
        this.numero = numero;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.status = status;
        this.idFuncionario = idFuncionario;
        this.statusAcaoReparador = statusAcaoReparador;
    }

    public boolean dadosValidos() {
        return marca != null && modelo != null && ano != null && numero != null && status != null;
    }

}
