package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.dto.RedeDTO;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.repository.BicicletaRepository;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BicicletaService {
    private final BicicletaRepository bicicletaRepository;
    private final TrancaRepository trancaRepository;
    private final TrancaService trancaService;

    public BicicletaService(BicicletaRepository bicicletaRepository, TrancaRepository trancaRepository, TrancaService trancaService) {
        this.bicicletaRepository = bicicletaRepository;
        this.trancaRepository = trancaRepository;
        this.trancaService = trancaService;
    }

    public Iterable<Bicicleta> getAllBicicletas() {
        return bicicletaRepository.findAll();
    }

    public Bicicleta getBicicleta(Long id) {
        return bicicletaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
    }

    public void addBicicleta(Bicicleta bicicleta) {
        if(!bicicleta.dadosValidos())
            throw new IllegalArgumentException("Dados invalidos.");

        bicicletaRepository.save(bicicleta);
    }

    public void integrarNaRede(RedeDTO rede ){
        Bicicleta bicicleta = getBicicleta(rede.getIdBicicleta());
        Tranca tranca = trancaService.getTranca(rede.getIdTranca());
        tranca.setBicicleta(bicicleta.getId());
        bicicleta.setIdFuncionario(rede.getIdFuncionario());
    }

    public void updateBicicleta(Long id, Bicicleta bicicleta) {
        Bicicleta bicicletaExistente = getBicicleta(id);

        if(!bicicleta.dadosValidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }

        bicicletaExistente.setAno(bicicleta.getAno());
        bicicletaExistente.setStatus(bicicleta.getStatus());
        bicicletaExistente.setNumero(bicicleta.getNumero());
        bicicletaExistente.setMarca(bicicleta.getMarca());
        bicicletaExistente.setModelo(bicicleta.getModelo());

        bicicletaRepository.save(bicicletaExistente);

    }

    public void deleteBicicleta(Long id) {
        Bicicleta bicicleta = getBicicleta(id);
        bicicletaRepository.delete(bicicleta);
    }

    public void alterarStatusBicicleta(Long id, String status) {
        Bicicleta bicicletaExistente = getBicicleta(id);

        bicicletaExistente.setStatus(status);

        bicicletaRepository.save(bicicletaExistente);
    }

}
