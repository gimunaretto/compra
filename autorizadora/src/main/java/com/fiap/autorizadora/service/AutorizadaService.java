package com.fiap.autorizadora.service;

import com.fiap.autorizadora.dto.CompraAlunoDTO;
import org.springframework.stereotype.Service;

@Service
public interface AutorizadaService {

    CompraAlunoDTO Autorizar(Long id, CompraAlunoDTO compraAluno);

}
