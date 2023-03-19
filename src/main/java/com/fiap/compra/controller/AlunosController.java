package com.fiap.compra.controller;

import com.fiap.compra.service.AlunosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class AlunosController {

    private AlunosService alunosService;

    public AlunosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }

    @GetMapping
    public ResponseEntity listAll() {
        return ResponseEntity.ok("Lista todos os Alunos...");
    }

    @GetMapping("{id}")
    public ResponseEntity findById(
            @PathVariable long id) {
        return ResponseEntity.ok("Buscar aluno pelo ID...");
    }

    @GetMapping("{id}/extrato")
    public ResponseEntity extrato(
            @PathVariable long id) {
        return ResponseEntity.ok("Buscar o extrato do aluno...");
    }

    @PostMapping("{id}/compra")
    public ResponseEntity comprar(
            @PathVariable long id) {
        return ResponseEntity.ok("Estourar o cartão - COMPRAR!!!...");
    }

    @PostMapping("upload")
    public ResponseEntity uploadAlunos(
            @ RequestParam MultipartFile arquivo) throws Exception {
        alunosService.cadastroInicial(arquivo);
        return ResponseEntity.ok("Carga inicial dos alunos, em arquivo txt..");
    }


}
