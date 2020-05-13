package com.example.notasservice.domain.mapper;

import com.example.notasservice.domain.model.Estoque;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EstoqueMapper {

    Estoque porId(Long id);

}
