package com.example.notasservice.domain.mapper;

import com.example.notasservice.domain.model.Nota;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotaMapper {

    void inserir(Nota nota);

}
