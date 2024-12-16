package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.InserirTrancaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverTrancaDaRedeDto;
import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.model.StatusTranca;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.service.TrancaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tranca")
public class TrancaController {
    private final TrancaService trancaService;

    public TrancaController(TrancaService trancaService) {
        this.trancaService = trancaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<TrancaDTO>> getAllTrancas() {
        return ResponseEntity.ok(trancaService.getAllTrancas());
    }

    @GetMapping("/{idTranca}")
    public ResponseEntity<TrancaDTO> getTranca(@PathVariable Long idTranca) {
        TrancaDTO tranca = new TrancaDTO(trancaService.getTranca(idTranca));
        return ResponseEntity.ok(tranca);
    }

    @PostMapping
    public ResponseEntity<TrancaDTO> createTranca(@RequestBody Tranca tranca) {
        trancaService.addTranca(tranca);
        TrancaDTO trancaCadastrada = new TrancaDTO(trancaService.getTranca(tranca.getNumero()));
        return ResponseEntity.ok(trancaCadastrada);
    }

    @PutMapping("/{idTranca}")
    public ResponseEntity<TrancaDTO> editTranca(@PathVariable Long idTranca, @RequestBody Tranca tranca) {
        trancaService.updateTranca(idTranca, tranca);

        TrancaDTO trancaAtualizada = new TrancaDTO(trancaService.getTranca(idTranca));
        return ResponseEntity.ok(trancaAtualizada);
    }

    @DeleteMapping("/{idTranca}")
    public ResponseEntity<String> deleteTranca(@PathVariable Long idTranca) {
        trancaService.deleteTranca(idTranca);
        return ResponseEntity.ok("Dados removidos.");
    }

    @PostMapping("/{idTranca}/trancar")
    public ResponseEntity<String> trancar(@PathVariable Long idTranca, @RequestBody(required = false) Long idBicicleta) {
        trancaService.trancar(idTranca, idBicicleta);
        return ResponseEntity.ok("Tranca trancada com sucesso.");
    }

    @PostMapping("/{idTranca}/destrancar")
    public ResponseEntity<String> destrancar(@PathVariable Long idTranca, @RequestBody(required = false) Long idBicicleta) {
        trancaService.destrancar(idTranca, idBicicleta);
        return ResponseEntity.ok("Tranca trancada com sucesso.");
    }

    @PostMapping("/integrarNaRede")
    public ResponseEntity<String> integrarNaRede(@RequestBody InserirTrancaNaRedeDTO rede) {
        trancaService.integrarNaRede(rede);
        return ResponseEntity.ok("Bicicleta foi incluída com sucesso.");
    }

    @PostMapping("/retirarDaRede")
    public ResponseEntity<String> retirarDaRede(@RequestBody RemoverTrancaDaRedeDto rede) {
        trancaService.retirarDaRede(rede);
        return ResponseEntity.ok("Bicicleta retirada com sucesso.");
    }

    @PostMapping("/{idTranca}/status/{acao}")
    public ResponseEntity<TrancaDTO> editStatusTranca(@PathVariable Long idTranca, @PathVariable StatusTranca acao){
        trancaService.alterarStatusTranca(idTranca, acao);
        return ResponseEntity.ok(new TrancaDTO(trancaService.getTranca(idTranca)));
    }

}
