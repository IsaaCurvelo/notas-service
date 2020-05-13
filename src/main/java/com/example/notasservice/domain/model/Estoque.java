package com.example.notasservice.domain.model;

import lombok.Data;

@Data
public class Estoque {

    private Long id;

    private Filial filial;

    private Produto produto;

    private Long quantidade;

}
