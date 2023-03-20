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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/alunos")
public class AlunosController {

    private final AlunosService alunosService;

    public AlunosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }
    @GetMapping
    @RequestMapping(value = "/listarAlunos", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Retorna uma lista de pessoas.", description  = "Esse método retorna uma lista de alunos: filtro realizado por página e quantidade de itens a serem retornados")
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
    @RequestMapping(value = "/filtrarAlunoPorId/{id}", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Chama um único aluno por id.", description  = "Esse método retorna um unico aluno: filtro realizado por id.")
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
    @RequestMapping(value = "/atualizarAluno/{id}", method = RequestMethod.PUT)
    @Operation(summary = "Atualiza o cadastro do aluno.", description  = "Esse método atualiza o cadastro do aluno: filtro ocorre por id.")
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
    @RequestMapping(value = "/deletarAluno/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Deleta o cadastro do aluno do banco.", description  = "Esse método deleta o aluno do banco de dados de acordo com o id fornecido.")
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
    public ResponseEntity<?> getExtrato(@PathVariable Long id) {
        List<CompraAluno> extrato = alunosService.getExtrato(id);
        List<CompraAlunoDTO> extratoDTO = extrato.stream().map(CompraAluno::_toDto).collect(Collectors.toList());
        try {
            byte[] csv = criarArquivoCSV(extratoDTO);
            ByteArrayResource resource = new ByteArrayResource(csv);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=extrato.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            headers.add(HttpHeaders.PRAGMA, "no-cache");
            headers.add(HttpHeaders.EXPIRES, "0");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o arquivo CSV");
        }
    }
    // public ResponseEntity<List<CompraAlunoDTO>> getExtrato(@PathVariable Long id) {
    //     List<CompraAluno> extrato = alunosService.getExtrato(id);
    //     List<CompraAlunoDTO> extratoDTO = extrato.stream().map(CompraAluno::_toDto).collect(Collectors.toList());
    //     return ResponseEntity.ok(extratoDTO);
    // }

    private static byte[] criarArquivoCSV(List<CompraAlunoDTO> compras) throws IOException {
        StringWriter writer = new StringWriter();
        // Escreve o cabeçalho no arquivo CSV
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Id", "Data", "Estabelecimento", "Valor", "Status"));
        // Escreve as compras no arquivo CSV
        for (CompraAlunoDTO compra : compras) {
            csvPrinter.printRecord(compra.getId(), compra.getData(), compra.getEstabelecimento(), compra.getValor(), compra.getStatusCompra());
        }
        csvPrinter.flush();
        csvPrinter.close();
        // Converte o arquivo CSV para array de bytes
        return writer.toString().getBytes(StandardCharsets.UTF_8);
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
    @RequestMapping(value = "/cadastrarAlunos", method = RequestMethod.POST, consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cadastro de aluno via arquivo .TXT.", description  = "Esse método grava no banco de dados todos os alunos contidos no arquivo .TXT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos gravada com sucesso!"),
    })
    public ResponseEntity uploadAlunos(
            @ RequestParam MultipartFile arquivo) throws Exception {
        alunosService.cadastroInicial(arquivo);
        return ResponseEntity.ok("Carga inicial dos alunos executado com sucesso!");
    }


}
