package br.edu.gestao.dto;

import br.edu.gestao.entity.TipoItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemVendaRequest(
        @NotNull TipoItem tipo,
        @NotNull Long referenciaId,
        @NotNull @Min(1) Integer quantidade) {}
