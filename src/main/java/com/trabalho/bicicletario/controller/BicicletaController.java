package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.InserirBicicletaNaRedeDTO;
import com.trabalho.bicicletario.dto.RemoverBicicletaDaRedeDTO;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.enums.StatusBicicleta;
import com.trabalho.bicicletario.service.BicicletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bicicleta")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Bicicleta>> getAllBicicletas() {
        return ResponseEntity.ok(bicicletaService.getAllBicicletas());
    }

    @GetMapping("/{idBicicleta}")
    public ResponseEntity<Bicicleta> getBicicleta(@PathVariable Long idBicicleta) {
        Bicicleta bicicleta = bicicletaService.getBicicleta(idBicicleta);
        return ResponseEntity.ok(bicicleta);
    }

    @PostMapping
    public ResponseEntity<Bicicleta> createBicicleta(@RequestBody Bicicleta bicicleta) {
        bicicletaService.addBicicleta(bicicleta);
        Bicicleta bicicletaCadastrada = bicicletaService.getBicicleta(bicicleta.getId());
        return ResponseEntity.ok(bicicletaCadastrada);
    }

    @PostMapping("/integrarNaRede")
    public ResponseEntity<String> integrarNaRede(@RequestBody InserirBicicletaNaRedeDTO rede) {
        bicicletaService.integrarNaRede(rede);
        return ResponseEntity.ok("Dados cadastrados.");
    }

    @PostMapping("/retirarDaRede")
    public ResponseEntity<String> retirarDaRede(@RequestBody RemoverBicicletaDaRedeDTO rede) {
        bicicletaService.retirarDaRede(rede);
        return ResponseEntity.ok("Dados cadastrados.");
    }

    @PutMapping("/{idBicicleta}")
    public ResponseEntity<Bicicleta> editBicicleta(@PathVariable Long idBicicleta, @RequestBody Bicicleta bicicleta) {
        bicicletaService.updateBicicleta(idBicicleta, bicicleta);
        Bicicleta bicicletaAtualizada = bicicletaService.getBicicleta(idBicicleta);
        return ResponseEntity.ok(bicicletaAtualizada);
    }

    @DeleteMapping("/{idBicicleta}")
    public ResponseEntity<String> deleteBicicleta(@PathVariable Long idBicicleta) {
        bicicletaService.deleteBicicleta(idBicicleta);
        return ResponseEntity.ok("Dados removidos.");
    }

    @PostMapping("/{idBicicleta}/status/{acao}")
    public ResponseEntity<Bicicleta> editStatusBicicleta(@PathVariable Long idBicicleta, @PathVariable StatusBicicleta acao){
        bicicletaService.alterarStatusBicicleta(idBicicleta, acao);
        Bicicleta bicicletaAtualizada = bicicletaService.getBicicleta(idBicicleta);
        return ResponseEntity.ok(bicicletaAtualizada);
    }

}
