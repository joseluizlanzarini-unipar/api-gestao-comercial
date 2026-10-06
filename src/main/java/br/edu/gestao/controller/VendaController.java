package br.edu.gestao.controller;

import br.edu.gestao.dto.VendaRequest;
import br.edu.gestao.entity.Venda;
import br.edu.gestao.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/vendas")
public class VendaController {
    private final VendaService service;
    public VendaController(VendaService service) { this.service = service; }
    @GetMapping public List<Venda> listar(@RequestParam(required = false) Long clienteId) { return service.listar(clienteId); }
    @GetMapping("/{id}") public Venda buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Venda> criar(@Valid @RequestBody VendaRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request)); }
    @PatchMapping("/{id}/cancelamento") public Venda cancelar(@PathVariable Long id) { return service.cancelar(id); }
}
