package com.fiap.compra.controller;

import com.fiap.compra.dto.AlunoDTO;
import com.fiap.compra.entity.Aluno;
import com.fiap.compra.service.AlunosService;
import com.fiap.compra.utils.HTTPMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("api/alunos")
public class AlunosController {

    private AlunosService alunosService;

    public AlunosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }

    @GetMapping
    public ResponseEntity listAll(@RequestHeader("page") Integer page, @RequestHeader("size") Integer size) {
        try {
            Page<Aluno> alunos = alunosService.list(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(alunos);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(new HTTPMessageResponse("Erro ao buscar alunos."));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity findById(
            @PathVariable long id) {
        try {
            Optional<Aluno> aluno = alunosService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(aluno.get()._toDto());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(new HTTPMessageResponse("Nenhum cliente com id={" + id + "} foi encontrado"));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        try {
            alunosService.update(id, alunoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(new HTTPMessageResponse("Nenhum cliente com id={" + id + "} foi encontrado"));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            alunosService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(new HTTPMessageResponse("Nenhum cliente com id={" + id + "} foi encontrado"));
        }
    }

    @GetMapping("{id}/extrato")
    public ResponseEntity extrato(
            @PathVariable long id) {
        return ResponseEntity.ok("Buscar o extrato do aluno...");
    }

    @PostMapping("{id}/comprar")
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
