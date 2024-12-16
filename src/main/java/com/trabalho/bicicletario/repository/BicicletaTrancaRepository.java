package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.BicicletaTranca;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BicicletaTrancaRepository extends CrudRepository<BicicletaTranca, Long> {

}
