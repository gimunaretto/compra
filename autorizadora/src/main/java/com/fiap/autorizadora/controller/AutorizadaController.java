package com.fiap.autorizadora.controller;

import com.fiap.autorizadora.dto.CompraAlunoDTO;
import com.fiap.autorizadora.service.AutorizadaService;
import com.fiap.autorizadora.utils.HTTPMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/autorizacao")
public class AutorizadaController {

    private AutorizadaService autorizadaService;

    public AutorizadaController(AutorizadaService autorizadaService) {
        this.autorizadaService = autorizadaService;
    }

    @PutMapping("{id}")
    public ResponseEntity autorizar(@PathVariable Long id, @RequestBody CompraAlunoDTO compraAluno) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(autorizadaService.Autorizar(id, compraAluno));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HTTPMessageResponse("Erro na Autorizadora!"));
        }
    }

}
