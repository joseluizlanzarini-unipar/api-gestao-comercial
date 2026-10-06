package br.edu.gestao.controller;

import br.edu.gestao.entity.Servico;
import br.edu.gestao.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/servicos")
public class ServicoController {
    private final ServicoService service;
    public ServicoController(ServicoService service) { this.service = service; }
    @GetMapping public List<Servico> listar(@RequestParam(required = false) String nome) { return service.listar(nome); }
    @GetMapping("/{id}") public Servico buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Servico> criar(@Valid @RequestBody Servico servico) { return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(servico)); }
    @PutMapping("/{id}") public Servico atualizar(@PathVariable Long id, @Valid @RequestBody Servico servico) { return service.atualizar(id, servico); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id) { service.excluir(id); }
}
