package com.examplo.crudpessoas.mapper;

import com.examplo.crudpessoas.web.dto.PessoaDTO;
import com.examplo.crudpessoas.model.Pessoa;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    PessoaDTO toPessoaDTO(Pessoa pessoa);

    Pessoa toPessoa(PessoaDTO pessoaDTO);
}

