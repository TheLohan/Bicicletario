package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Bicicleta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicicletaRepository extends CrudRepository<Bicicleta, Integer> {

}
