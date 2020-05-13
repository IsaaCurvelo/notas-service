package com.example.notasservice.domain.service;

import com.example.notasservice.NotasServiceApplication;
import com.example.notasservice.domain.mapper.EstoqueMapper;
import com.example.notasservice.domain.mapper.NotaMapper;
import com.example.notasservice.domain.model.Estoque;
import com.example.notasservice.domain.model.Nota;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class NotaService {

    @Autowired
    private EstoqueMapper estoqueMapper;

    @Autowired
    private NotaMapper notaMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    public void lancarNota(Long estoqueId) throws NoSuchAlgorithmException {
        Estoque estoque = estoqueMapper.porId(estoqueId);

        // tive que colocar o while porque por algum motivo o find retorna null
        if (estoque == null) {
            System.out.println(String.format("ERROR: não foi possível recuperar estoque de id %d, " +
                    "então reencaminhando para o fim da fila", estoqueId));

            rabbitTemplate.convertAndSend(NotasServiceApplication.topicExchangeName,
                    "lancamento.nota",
                    estoqueId);
            return;
        }

        String numeroNotaFiscal = LocalDateTime.now().toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        numeroNotaFiscal = bytesToHex(digest.digest(numeroNotaFiscal.getBytes(StandardCharsets.UTF_8)));

        System.out.println("gerada nota fiscal de número " + numeroNotaFiscal);

        Nota nota = new Nota();
        nota.setProduto(estoque.getProduto());

        nota.setNumeroNotaFiscal(numeroNotaFiscal);

        System.out.println("persistindo nota fiscal no banco...");

        notaMapper.inserir(nota);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
