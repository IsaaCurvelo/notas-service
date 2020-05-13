package com.example.notasservice.listener;

import com.example.notasservice.domain.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class LancarNotaListener {

    @Autowired
    private NotaService notaService;

    public void receiveMessage(Long estoqueId) throws NoSuchAlgorithmException, InterruptedException {
        System.out.println("recuperando mensagem: estoqueId = " + estoqueId);

        notaService.lancarNota(estoqueId);
    }

}