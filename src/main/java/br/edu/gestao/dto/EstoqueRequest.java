package br.edu.gestao.dto;

import jakarta.validation.constraints.NotNull;

public record EstoqueRequest(@NotNull Integer estoque) {}
