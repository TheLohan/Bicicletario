package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tranca")
public class Tranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long numero;

    private String localizacao;

    @Column(name = "ano")
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

    public Tranca(Tranca tranca) {
        this.numero = tranca.getNumero();
        this.localizacao = tranca.getLocalizacao();
        this.anoDeFabricacao = tranca.getAnoDeFabricacao();
        this.modelo = tranca.getModelo();
        this.status = tranca.getStatus();
        this.totem = tranca.getTotem();
        this.bicicleta = tranca.getBicicleta();
    }

    public boolean dadosValidos() {
        return localizacao == null || anoDeFabricacao == null || modelo == null || status == null;
    }

}
