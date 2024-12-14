package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.service.TotemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/totem")
public class TotemController {
    private final TotemService totemService;

    public TotemController(TotemService totemService) {
        this.totemService = totemService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Totem>> getAllTotems() {
        return ResponseEntity.ok(totemService.getAllTotems());
    }

    @PostMapping
    public ResponseEntity<Totem> createTotem(@RequestBody Totem totem) {
        totemService.addTotem(totem);
        Totem totemCadastrado = totemService.getTotem(totem.getId());
        return ResponseEntity.ok(totemCadastrado);
    }

    @PutMapping("/{idTotem}")
    public ResponseEntity<Totem> updateTotem(@PathVariable int idTotem, @RequestBody Totem totem) {
        totemService.updateTotem(idTotem, totem);
        Totem totemAtualizado = totemService.getTotem(totem.getId());
        return ResponseEntity.ok(totemAtualizado);
    }

    @DeleteMapping("/{idTotem}")
    public ResponseEntity<String> deleteTotem(@PathVariable int idTotem) {
        totemService.deleteTotem(idTotem);
        return ResponseEntity.ok("Dados removidos.");
    }


}
