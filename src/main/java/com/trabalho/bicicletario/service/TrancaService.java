package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.config.Email;
import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.repository.TotemTrancaRepository;
import com.trabalho.bicicletario.repository.TrancaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TrancaService {
    private final TrancaRepository trancaRepository;
    private final TotemService totemService;
    private final TotemTrancaRepository totemTrancaRepository;
    private final BicicletaService bicicletaService;

    public TrancaService(TrancaRepository trancaRepository, TotemService totemService, TotemTrancaRepository totemTrancaRepository, BicicletaService bicicletaService) {
        this.trancaRepository = trancaRepository;
        this.totemService = totemService;
        this.totemTrancaRepository = totemTrancaRepository;
        this.bicicletaService = bicicletaService;
    }

    public Tranca getTranca(Long id) {
        Tranca tranca = trancaRepository.findByNumero(id);
        if (tranca == null) {
            throw new EntityNotFoundException("Não encontrado");
        }
        return new Tranca(tranca);
    }

    public List<Tranca> getByTotemId(Long idTotem) {
        return trancaRepository.findByTotemId(idTotem);
    }

    public Iterable<TrancaDTO> getAllTrancas(){
        Iterable<Tranca> trancas = trancaRepository.findAll();
        return StreamSupport.stream(trancas.spliterator(), false)
                .map(TrancaDTO::new)
                .toList();
    }

    public void addTranca(Tranca tranca) {
        if(tranca.dadosValidos())
            throw new IllegalArgumentException("Dados invalidos.");

        trancaRepository.save(tranca);
    }

    public void updateTranca(Long id, Tranca tranca) {
        Tranca trancaExistente = getTranca(id);

        if(tranca.dadosValidos()){
            throw new IllegalArgumentException("Dados invalidos.");
        }

        trancaExistente.setAnoDeFabricacao(tranca.getAnoDeFabricacao());
        trancaExistente.setModelo(tranca.getModelo());
        trancaExistente.setStatus(tranca.getStatus());
        trancaExistente.setLocalizacao(tranca.getLocalizacao());
        trancaExistente.setBicicleta(tranca.getBicicleta());

        trancaRepository.save(trancaExistente);

    }

    public void deleteTranca(Long id) {
        Tranca tranca = getTranca(id);
        trancaRepository.delete(tranca);
    }

    public List<TrancaDTO> getTrancasByTotem(Long idTotem) {
        List<Tranca> trancas = trancaRepository.findByTotemId(idTotem);
        return trancas.stream()
                .map(TrancaDTO::new)
                .toList();
    }

    public void trancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = getTranca(idTranca);
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);
        if(idBicicleta != null){
            bicicletaService.alterarStatusBicicleta(idBicicleta, StatusBicicleta.DISPONIVEL);
        }
    }

    public void destrancar(Long idTranca, Long idBicicleta) {
        Tranca tranca = getTranca(idTranca);
        tranca.setStatus(StatusTranca.OCUPADA);
        trancaRepository.save(tranca);
        if(idBicicleta != null){
            bicicletaService.alterarStatusBicicleta(idBicicleta, StatusBicicleta.EM_USO);
        }
    }

    public void integrarNaRede(InserirTrancaNaRedeDTO rede ){

        Tranca tranca = getTranca(rede.getIdTranca());
        Totem totem = totemService.getTotem(rede.getIdTotem());

        if(tranca.getStatus() != StatusTranca.NOVA && tranca.getStatus() != StatusTranca.EM_REPARO)
            throw new IllegalArgumentException("Status inválido da tranca.");

        tranca.setTotem(totem);
        tranca.setStatus(StatusTranca.LIVRE);
        trancaRepository.save(tranca);

        Email.enivarEmail();

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        tranca.getNumero(),
                        LocalDateTime.now(),
                        "INSERIR NA REDE",
                        null
                )
        );


    }

    public void retirarDaRede(RemoverTrancaDaRedeDto rede ){

        Tranca tranca = getTranca(rede.getIdTranca());

        if(tranca.getBicicleta() != null){
            throw new IllegalArgumentException("Tranca possui bicicleta.");
        }

        if(rede.getStatusAcaoReparador().equalsIgnoreCase("reparo")){
            alterarStatusTranca(tranca, StatusTranca.EM_REPARO);
        }
        else if (rede.getStatusAcaoReparador().equalsIgnoreCase("aposentadoria")){
            alterarStatusTranca(tranca, StatusTranca.APOSENTADA);
        }

        tranca.setTotem(null);
        trancaRepository.save(tranca);

        totemTrancaRepository.save(
                new TotemTranca(
                        tranca.getNumero(),
                        rede.getIdFuncionario(),
                        tranca.getNumero(),
                        LocalDateTime.now(),
                        "REMOVER DA REDE",
                        rede.getStatusAcaoReparador()
                )
        );

        Email.enivarEmail();

    }

    public void alterarStatusTranca(Long id, StatusTranca status) {
        Tranca trancaExistente = getTranca(id);

        trancaExistente.setStatus(status);

        trancaRepository.save(trancaExistente);
    }

    public void alterarStatusTranca(Tranca tranca, StatusTranca status) {
        tranca.setStatus(status);
        trancaRepository.save(tranca);
    }

}
