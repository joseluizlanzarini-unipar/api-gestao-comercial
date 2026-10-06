package br.edu.gestao.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class InicioController {
    @GetMapping("/")
    public Map<String, Object> inicio() {
        return Map.of("aplicacao", "API Gestão Comercial", "status", "online", "swagger", "/swagger-ui.html");
    }
}
