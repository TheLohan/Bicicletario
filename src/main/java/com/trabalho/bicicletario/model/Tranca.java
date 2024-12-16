package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tranca {

    @Id
    @Column(name = "id")
    private Long numero;

    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;

    @Enumerated(EnumType.STRING)
    private StatusTranca status;


    @ManyToOne
    @JoinColumn(name = "totem_id", nullable = false)
    private Totem totem;

    @OneToOne
    @JoinColumn(name = "bicicleta_id", nullable = true)
    private Bicicleta bicicleta;


    public Tranca(Long numero, String localizacao, String anoDeFabricacao, String modelo, StatusTranca status) {
        this.numero = numero;
        this.localizacao = localizacao;
        this.anoDeFabricacao = anoDeFabricacao;
        this.modelo = modelo;
        this.status = status;
    }

    public Tranca(Tranca tranca) {
    }

    public boolean dadosValidos() {
        return bicicleta != null && numero != null && localizacao != null && anoDeFabricacao != null && modelo != null && status != null;
    }

}
