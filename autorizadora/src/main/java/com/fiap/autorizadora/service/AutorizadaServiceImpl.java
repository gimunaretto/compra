package com.fiap.autorizadora.service;

import com.fiap.autorizadora.dto.CompraAlunoDTO;
import com.fiap.autorizadora.dto.StatusCompra;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AutorizadaServiceImpl implements AutorizadaService {


    public AutorizadaServiceImpl() {};

    @Override
    public CompraAlunoDTO Autorizar(Long id, CompraAlunoDTO compraAluno) {
        if (!Objects.isNull(compraAluno) &&
                !Objects.isNull(compraAluno.getAluno()) &&
                compraAluno.getAluno().getLimite() >= compraAluno.getValor()){
            compraAluno.setMotivoStatus("COMPRA DENTRO DO LIMITE DO CARTÃO");
            compraAluno.setStatusCompra(StatusCompra.APROVADO);
        } else {
            compraAluno.setMotivoStatus("COMPRA ACIMA DO LIMITE DO CARTÃO");
            compraAluno.setStatusCompra(StatusCompra.CANCELADO);
        }
        return compraAluno;
    }

}
