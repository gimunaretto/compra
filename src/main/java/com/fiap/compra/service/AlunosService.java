package com.fiap.compra.service;

import com.fiap.compra.dto.AlunoDTO;
import com.fiap.compra.dto.CompraAlunoDTO;
import com.fiap.compra.entity.Aluno;
import com.fiap.compra.entity.CompraAluno;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Service
public interface AlunosService{

    void cadastroInicial(MultipartFile arquivo) throws IOException;

    Page<Aluno> list(int page, int size);
    Optional<Aluno> get(Long id);

    Aluno create(AlunoDTO alunoDTO);

    List<CompraAluno> getExtrato(Long id);

    void update(Long id, AlunoDTO alunoDTO);
    void delete(Long id);
}
