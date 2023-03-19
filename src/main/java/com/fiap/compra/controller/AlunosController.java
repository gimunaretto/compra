package com.fiap.compra.controller;

import com.fiap.compra.dto.AlunoDTO;
import com.fiap.compra.dto.CompraAlunoDTO;
import com.fiap.compra.entity.Aluno;
import com.fiap.compra.entity.CompraAluno;
import com.fiap.compra.service.AlunosService;
import com.fiap.compra.utils.HTTPMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<CompraAlunoDTO>> getExtrato(@PathVariable Long id) {
        List<CompraAluno> extrato = alunosService.getExtrato(id);
        List<CompraAlunoDTO> extratoDTO = extrato.stream().map(CompraAluno::_toDto).collect(Collectors.toList());
        return ResponseEntity.ok(extratoDTO);
    }


    @PostMapping("{id}/comprar")
    public ResponseEntity comprar(
            @PathVariable long id) {
        // QUAL O VALOR DA COMPRA??? BODY: { valor:
        // buscar Aluno pelo ID; Optional<Aluno> aluno = alunosService.get(id);
        // montar obj: ComprasAlunos; ComprasAlunos compra = new ComprasAlunos()
        // chamar localhost:8085/api/autorizacao/{id} passando ComprasAlunos  *** PASSAR UM BODY, PUT
        // a API retorna o ComprasAlunos com o statusCompra: CANCELADO//APROVADO.
        // se aprovado: GRAVAR  no mysql O OBJETO ComprasAlunos;
        // SE NÃO: NÃO GRAVAR
        // RETORNA statusCompra
        return ResponseEntity.ok("Estourar o cartão - COMPRAR!!!...");
    }

    @PostMapping("upload")
    public ResponseEntity uploadAlunos(
            @ RequestParam MultipartFile arquivo) throws Exception {
        alunosService.cadastroInicial(arquivo);
        return ResponseEntity.ok("Carga inicial dos alunos executado com sucesso!");
    }


}
