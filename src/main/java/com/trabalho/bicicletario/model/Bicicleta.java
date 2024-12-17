package com.trabalho.bicicletario.model;

import com.trabalho.bicicletario.Enum.StatusBicicleta;
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
    @Enumerated(EnumType.STRING)
    private StatusBicicleta status;

    public boolean dadosInvalidos() {
        return marca == null || modelo == null || ano == null || status == null || numero == null;
    }

}
