package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.TrancaDTO;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.model.Totem;
import com.trabalho.bicicletario.model.Tranca;
import com.trabalho.bicicletario.service.BicicletaService;
import com.trabalho.bicicletario.service.TotemService;
import com.trabalho.bicicletario.service.TrancaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TotemControllerTest {

    @Mock
    private TotemService totemService;

    @Mock
    private TrancaService trancaService;

    @Mock
    private BicicletaService bicicletaService;

    @InjectMocks
    private TotemController totemController;

    public TotemControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTotems() {
        Totem totem1 = new Totem();
        Totem totem2 = new Totem();
        when(totemService.getAllTotems()).thenReturn(Arrays.asList(totem1, totem2));

        ResponseEntity<Iterable<Totem>> response = totemController.getAllTotems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, ((List<Totem>) response.getBody()).size());
    }

    @Test
    void testCreateTotem() {
        Totem totem = new Totem();
        totem.setId(1L);

        doNothing().when(totemService).addTotem(totem);
        when(totemService.getTotem(1L)).thenReturn(totem);

        ResponseEntity<Totem> response = totemController.createTotem(totem);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testUpdateTotem() {
        Totem totem = new Totem();
        totem.setId(1L);

        when(totemService.updateTotem(1L, totem)).thenReturn(totem);

        ResponseEntity<Totem> response = totemController.updateTotem(1L, totem);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testDeleteTotem() {
        doNothing().when(totemService).deleteTotem(1L);

        ResponseEntity<String> response = totemController.deleteTotem(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dados removidos.", response.getBody());
    }

    @Test
    void testListarTrancasPorTotem() {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(1L);
        bicicleta.setNumero(1001);

        Tranca tranca1 = new Tranca();
        tranca1.setId(1L);
        tranca1.setNumero(101);
        tranca1.setLocalizacao("Local A");
        tranca1.setModelo("Modelo X");
        tranca1.setBicicleta(bicicleta);

        Tranca tranca2 = new Tranca();
        tranca2.setId(2L);
        tranca2.setNumero(102);
        tranca2.setLocalizacao("Local B");
        tranca2.setModelo("Modelo Y");
        tranca2.setBicicleta(null);

        // Cria os TrancaDTO
        TrancaDTO tranca1DTO = new TrancaDTO(tranca1);
        TrancaDTO tranca2DTO = new TrancaDTO(tranca2);

        // Configura o mock
        when(trancaService.getTrancasByTotem(1L)).thenReturn(Arrays.asList(tranca1DTO, tranca2DTO));

        ResponseEntity<List<TrancaDTO>> response = totemController.listarTrancasPorTotem(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals(1001, response.getBody().get(0).getBicicleta());

        assertEquals(2L, response.getBody().get(1).getId());
        assertNull(response.getBody().get(1).getBicicleta());
    }


    @Test
    void testListarBicicletasPorTotem() {
        Bicicleta bicicleta1 = new Bicicleta();
        Bicicleta bicicleta2 = new Bicicleta();
        when(bicicletaService.getBicicletasByTotem(1L)).thenReturn(Arrays.asList(bicicleta1, bicicleta2));

        ResponseEntity<List<Bicicleta>> response = totemController.listarBicicletasPorTotem(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}
