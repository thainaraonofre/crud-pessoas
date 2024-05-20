package com.examplo.crudpessoas.service;

import com.examplo.crudpessoas.model.Pessoa;
import com.examplo.crudpessoas.repository.PessoaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void deleteById(Long id) {
        pessoaRepository.deleteById(id);
    }
}
