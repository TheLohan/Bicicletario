package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Tranca;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrancaRepository extends CrudRepository<Tranca, Long> {
    public List<Tranca> findAllByTotemId(long totemId);
    List<Tranca> findAllByTotemId(Long idTotem);
}
