package com.trabalho.bicicletario.model;

import com.trabalho.bicicletario.Enum.StatusTranca;
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
    private Long id;

    private Integer numero;

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
    @JoinColumn(name = "bicicleta_id")
    private Bicicleta bicicleta;

    public Tranca(Tranca tranca) {
        this.id = tranca.getId();
        this.localizacao = tranca.getLocalizacao();
        this.anoDeFabricacao = tranca.getAnoDeFabricacao();
        this.modelo = tranca.getModelo();
        this.numero = tranca.getNumero();
        this.status = tranca.getStatus();
        this.totem = tranca.getTotem();
        this.bicicleta = tranca.getBicicleta();
    }

    public boolean dadosInvalidos() {
        return localizacao == null || anoDeFabricacao == null || modelo == null || status == null;
    }

    public Integer getBicicletaDaTranca() {
        return (bicicleta != null) ? bicicleta.getNumero() : null;
    }

}
