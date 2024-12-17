package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Tranca;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrancaRepository extends CrudRepository<Tranca, Long> {
    boolean existsByNumero(long numero);
    List<Tranca> findByTotemId(Long idTotem);
}
