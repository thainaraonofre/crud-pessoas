package com.examplo.crudpessoas.service;

import com.examplo.crudpessoas.dto.PessoaDTO;
import com.examplo.crudpessoas.exception.ResourceNotFoundException;
import com.examplo.crudpessoas.mapper.PessoaMapper;
import com.examplo.crudpessoas.model.Pessoa;
import com.examplo.crudpessoas.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper = PessoaMapper.INSTANCE;

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
        Pessoa pessoa = pessoaMapper.toPessoa(pessoaDTO);
        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        return pessoaMapper.toPessoaDTO(savedPessoa);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    @Transactional
    public PessoaDTO update(Long id, PessoaDTO pessoaDTO) {
        Pessoa existingPessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        existingPessoa.setNome(pessoaDTO.getNome());
        existingPessoa.setIdade(pessoaDTO.getIdade());
        existingPessoa.setCpf(pessoaDTO.getCpf());

        Pessoa updatedPessoa = pessoaRepository.save(existingPessoa);
        return pessoaMapper.toPessoaDTO(updatedPessoa);
    }
}