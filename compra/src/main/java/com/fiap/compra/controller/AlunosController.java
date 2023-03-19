package com.fiap.compra.controller;

import com.fiap.compra.dto.AlunoDTO;
import com.fiap.compra.dto.CompraAlunoDTO;
import com.fiap.compra.entity.Aluno;
import com.fiap.compra.entity.CompraAluno;
import com.fiap.compra.service.AlunosService;
import com.fiap.compra.utils.HTTPMessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @RequestMapping(value = "/listarAlunos", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Retorna uma lista de pessoas", description  = "Esse método retorna uma lista de alunos: filtro realizado por página e quantidade de itens a serem retornados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Erro ao buscar alunos."),
    })
    public ResponseEntity listAll(@RequestHeader("page") Integer page, @RequestHeader("size") Integer size) {
        try {
            Page<Aluno> alunos = alunosService.list(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(alunos);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HTTPMessageResponse("Erro ao buscar alunos."));
        }
    }

    @GetMapping("{id}")
    @RequestMapping(value = "/filtrarALunoPorId/{id}", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Chama um unico aluno por id", description  = "Esse método retorna um unico aluno: filtro realizado por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Erro ao buscar aluno."),
    })
    public ResponseEntity findById(
            @PathVariable long id) {
        try {
            Optional<Aluno> aluno = alunosService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(aluno.get()._toDto());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HTTPMessageResponse("Nenhum aluno com id={" + id + "} foi encontrado"));
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza o aluno.", description  = "Esse método atualiza o aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informação do aluno atualizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado."),
    })
    public ResponseEntity update(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO) {
        try {
            alunosService.update(id, alunoDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HTTPMessageResponse("Nenhum aluno com id={" + id + "} foi encontrado"));
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta o aluno", description  = "Esse método deleta o aluno do banco de dados de acordo com o id fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado! Erro ao deletar aluno."),
    })
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            alunosService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HTTPMessageResponse("Nenhum aluno com id={" + id + "} foi encontrado"));
        }
    }

    @GetMapping("/{id}/extrato")
    @Operation(summary = "Retorna o extrato de compras do cartão.", description  = "Esse método retorna o extrato do cartão filtrado pelo id do aluno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato gerado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Erro ao gerar extrato."),
    })
    public ResponseEntity<List<CompraAlunoDTO>> getExtrato(@PathVariable Long id) {
        List<CompraAluno> extrato = alunosService.getExtrato(id);
        List<CompraAlunoDTO> extratoDTO = extrato.stream().map(CompraAluno::_toDto).collect(Collectors.toList());
        return ResponseEntity.ok(extratoDTO);
    }


    @PostMapping("{id}/comprar")
    @Operation(summary = "Cadastra uma compra com cartão de crédito no banco.", description  = "Esse método verifica se o limite é maior ou igual o valor da compra e salva a compra no banco de dados com status de cancelado ou aprovado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação de limite realizada!"),
            @ApiResponse(responseCode = "404", description = "Erro ao processar a compra."),
    })
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
    @Operation(summary = "Cadastro de aluno via arquivo .TXT", description  = "Esse método grava no banco de dados todos os alunos contidos no arquivo .TXT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos gravada com sucesso!"),
    })
    public ResponseEntity uploadAlunos(
            @ RequestParam MultipartFile arquivo) throws Exception {
        alunosService.cadastroInicial(arquivo);
        return ResponseEntity.ok("Carga inicial dos alunos executado com sucesso!");
    }


}
