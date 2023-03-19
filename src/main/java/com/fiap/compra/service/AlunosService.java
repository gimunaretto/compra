package com.fiap.compra.service;

import com.fiap.compra.dto.AlunosDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlunosService{

    List<AlunosDTO> list();

    void cadastroInicial(MultipartFile arquivo) throws IOException;


}
