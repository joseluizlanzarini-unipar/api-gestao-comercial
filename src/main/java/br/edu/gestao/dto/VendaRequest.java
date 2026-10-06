package br.edu.gestao.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record VendaRequest(
        @NotNull Long clienteId,
        @NotEmpty List<@Valid ItemVendaRequest> itens) {}
