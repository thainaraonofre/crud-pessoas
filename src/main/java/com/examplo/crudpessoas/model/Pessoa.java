package com.examplo.crudpessoas.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    @NotNull
    @Column(name = "idade", nullable = false)
    private int idade;

    @NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;


}
