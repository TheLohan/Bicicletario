package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.config.Email;
import com.trabalho.bicicletario.dto.InserirBicicletaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverBicicletaDaRedeDTO;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.repository.BicicletaRepository;
import com.trabalho.bicicletario.repository.BicicletaTrancaRepository;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BicicletaService {
    private final BicicletaRepository bicicletaRepository;
    private final BicicletaTrancaRepository bicicletaTrancaRepository;
    private final TrancaService trancaService;
    private final TrancaRepository trancaRepository;

    public BicicletaService(BicicletaRepository bicicletaRepository, BicicletaTrancaRepository bicicletaTrancaRepository, TrancaService trancaService, TrancaRepository trancaRepository) {
        this.bicicletaRepository = bicicletaRepository;
        this.trancaService = trancaService;
        this.bicicletaTrancaRepository = bicicletaTrancaRepository;
        this.trancaRepository = trancaRepository;
    }

    public Iterable<Bicicleta> getAllBicicletas() {
        return bicicletaRepository.findAll();
    }

    public Bicicleta getBicicleta(Long id) {
        return bicicletaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));
    }

    public void addBicicleta(Bicicleta bicicleta) {
        if(bicicleta.dadosValidos())
            throw new IllegalArgumentException("Dados invalidos.");

        bicicletaRepository.save(bicicleta);
    }

    public void integrarNaRede(InserirBicicletaNaRedeDTO rede ){

        Bicicleta bicicleta = getBicicleta(rede.getIdBicicleta());
        Tranca tranca = trancaService.getTranca(rede.getIdTranca());

        if(bicicleta.getStatus() == StatusBicicleta.EM_USO || bicicleta.getStatus() == StatusBicicleta.EM_REPARO)
            throw new IllegalArgumentException("Status inválido da bicicleta.");

        tranca.setBicicleta(bicicleta);
        trancaRepository.save(tranca);

        bicicletaTrancaRepository.save(
                new BicicletaTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        bicicleta.getNumero(),
                        LocalDateTime.now(),
                        "INSERIR NA REDE",
                        null
                )
        );

        alterarStatusBicicleta(bicicleta.getNumero(), StatusBicicleta.DISPONIVEL);

    }

    public void retirarDaRede(RemoverBicicletaDaRedeDTO rede ){

        Bicicleta bicicleta = getBicicleta(rede.getIdBicicleta());
        Tranca tranca = trancaService.getTranca(rede.getIdTranca());

        if(bicicleta.getStatus() != StatusBicicleta.REPARO_SOLICITADO || tranca.getStatus() == StatusTranca.LIVRE)
            throw new IllegalArgumentException("Status inválido");

        if(rede.getStatusAcaoReparador().equalsIgnoreCase("reparo"))
            alterarStatusBicicleta(bicicleta.getNumero(), StatusBicicleta.EM_REPARO);
        else if (rede.getStatusAcaoReparador().equalsIgnoreCase("aposentadoria"))
            alterarStatusBicicleta(bicicleta.getNumero(), StatusBicicleta.APOSENTADA);

        tranca.setBicicleta(null);
        trancaRepository.save(tranca);

        bicicletaTrancaRepository.save(
                new BicicletaTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        bicicleta.getNumero(),
                        LocalDateTime.now(),
                        "REMOVER DA REDE",
                        rede.getStatusAcaoReparador()
                )
        );

        alterarStatusBicicleta(bicicleta.getNumero(), StatusBicicleta.DISPONIVEL);
        Email.enivarEmail();

    }

    public Bicicleta updateBicicleta(Long id, Bicicleta bicicleta) {

        Bicicleta bicicletaExistente = getBicicleta(id);

        if(bicicleta.dadosValidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }

        bicicletaExistente.setAno(bicicleta.getAno());
        bicicletaExistente.setStatus(bicicleta.getStatus());
        bicicletaExistente.setMarca(bicicleta.getMarca());
        bicicletaExistente.setModelo(bicicleta.getModelo());

        bicicletaRepository.save(bicicletaExistente);
        return bicicletaExistente;
    }

    public void deleteBicicleta(Long id) {
        Bicicleta bicicleta = getBicicleta(id);
        bicicletaRepository.delete(bicicleta);
    }

    public void alterarStatusBicicleta(Long id, StatusBicicleta status) {
        Bicicleta bicicletaExistente = getBicicleta(id);

        bicicletaExistente.setStatus(status);

        bicicletaRepository.save(bicicletaExistente);
    }

    public List<Bicicleta> getBicicletasByTotem(Long idTotem) {
        List<Tranca> trancas = trancaService.getByTotemId(idTotem);
        return trancas.stream()
                .map(Tranca::getBicicleta)
                .filter(Objects::nonNull)
                .toList();
    }

}
