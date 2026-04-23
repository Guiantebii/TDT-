package com.example.tablets_chips.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AlunoRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "EOL é obrigatório")
        String eol,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve estar no passado")
        LocalDate dataNasc,

        @NotBlank(message = "Turma é obrigatória")
        String turma,

        @NotBlank(message = "Telefone 1 é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
        String tel1,

        @Pattern(regexp = "^$|\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")
        String tel2
) {}