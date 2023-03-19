package com.fiap.compra.service;

import com.fiap.compra.dto.AlunosDTO;
import com.fiap.compra.utils.Utilitarios;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AlunosServiceImpl implements AlunosService {


    public AlunosServiceImpl() {
    }

    @Override
    public List<AlunosDTO> list() {
        return null;
    }

    @Override
    public void cadastroInicial(MultipartFile arquivo) throws IOException {

        try {
            AtomicReference<Long> i = new AtomicReference<>(0L);
            BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));

            List<AlunosDTO> alunos =
                reader.lines().collect(Collectors.toList())
                        .stream()
                        .filter( linha -> !linha.trim().isEmpty() && !linha.startsWith("------"))
                        .map( linha -> {
                            AlunosDTO aluno = new AlunosDTO();
                            aluno.setId(i.getAndSet(i.get() + 1));
                            aluno.setNome(linha.substring(0, 41).trim());
                            aluno.setRa(linha.substring(42, 55).trim().replace(" ", "").replace("-", ""));
                            aluno.setCartao(Utilitarios.gerarCartao());
                            aluno.setLimite(Utilitarios.gerarLimite());
                            return aluno;
                        })
                        .collect(Collectors.toList());

            reader.close();

        }
        catch (Exception e) {
            throw e;
        }

    }


}
