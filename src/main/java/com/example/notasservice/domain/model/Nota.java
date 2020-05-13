package com.example.notasservice.domain.model;

import lombok.Data;

@Data
public class Nota {

    private Long id;
    private String numeroNotaFiscal;
    private Produto produto;

}
