package com.fiap.autorizadora.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CompraAlunoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDate data;
    private String estabelecimento;
    private Double valor;
    private StatusCompra statusCompra;

    public String getMotivoStatus() {
        return MotivoStatus;
    }

    public void setMotivoStatus(String motivoStatus) {
        MotivoStatus = motivoStatus;
    }

    private String MotivoStatus;


    private AlunoDTO aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public void setStatusCompra(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }
    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }
    public AlunoDTO getAluno() {
        return aluno;
    }

    public CompraAlunoDTO compraAluno() {
        CompraAlunoDTO compraAluno = new CompraAlunoDTO();
        compraAluno.setId(id);
        compraAluno.setAluno(aluno);
        compraAluno.setData(data);
        compraAluno.setStatusCompra(statusCompra);
        compraAluno.setValor(valor);
        compraAluno.setEstabelecimento(estabelecimento);
        return compraAluno;
    }

}
