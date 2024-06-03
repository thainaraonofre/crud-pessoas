package com.examplo.crudpessoas.service;

import com.examplo.crudpessoas.web.dto.PessoaDTO;
import com.examplo.crudpessoas.exception.CPFInvalido;
import com.examplo.crudpessoas.exception.ResourceNotFoundException;
import com.examplo.crudpessoas.model.Pessoa;
import com.examplo.crudpessoas.repository.PessoaRepository;
import com.examplo.crudpessoas.mapper.PessoaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        return pessoaRepository.findAll().stream()
                .map(pessoaMapper::toPessoaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaDTO findById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));
        return pessoaMapper.toPessoaDTO(pessoa);
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        validateCPF(pessoaDTO.getCpf());
        Pessoa pessoa = pessoaMapper.toPessoa(pessoaDTO);
        return pessoaMapper.toPessoaDTO(pessoaRepository.save(pessoa));
    }

    @Transactional
    public PessoaDTO update(Long id, PessoaDTO pessoaDTO) {
        validateCPF(pessoaDTO.getCpf());
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        Pessoa pessoa = pessoaMapper.toPessoa(pessoaDTO);
        pessoa.setId(id);
        return pessoaMapper.toPessoaDTO(pessoaRepository.save(pessoa));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    private void validateCPF(String cpf) {
        if (cpf.length() != 11) {
            throw new CPFInvalido("O CPF deve ter 11 caracteres");
        }
    }

}