package com.examplo.crudpessoas.controller;


import com.examplo.crudpessoas.dto.PessoaDTO;
import com.examplo.crudpessoas.exception.ResourceNotFoundException;
import com.examplo.crudpessoas.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pessoas", description = "Operações para gerenciar pessoas.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Operation(summary = "Listar todas as pessoas", description = "Retorna uma lista de todas as pessoas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pessoas recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class)))
            })
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAllPessoas() {
        List<PessoaDTO> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @Operation(summary = "Buscar uma pessoa pelo ID", description = "Retorno de uma pessoa pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        try {
            PessoaDTO pessoa = pessoaService.findById(id);
            return ResponseEntity.ok(pessoa);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Cria uma nova pessoa", description = "Cria um novo registro de pessoa",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<PessoaDTO> createPessoa(@Valid @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO createdPessoa = pessoaService.save(pessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPessoa);
    }

    @Operation(summary = "Atualiza uma pessoa existente", description = "Atualiza os dados de uma pessoa existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        try {
            PessoaDTO updatedPessoa = pessoaService.update(id, pessoaDTO);
            return ResponseEntity.ok(updatedPessoa);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deleta uma pessoa pelo ID", description = "Deleta o registro de uma pessoa pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        try {
            pessoaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}