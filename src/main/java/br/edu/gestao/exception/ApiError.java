package br.edu.gestao.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        LocalDateTime dataHora,
        int status,
        String erro,
        String mensagem,
        Map<String, String> campos) {}
