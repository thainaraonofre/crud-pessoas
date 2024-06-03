package com.examplo.crudpessoas.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public PessoaMapper pessoaMapper() {
        return new PessoaMapperImpl();
    }
}