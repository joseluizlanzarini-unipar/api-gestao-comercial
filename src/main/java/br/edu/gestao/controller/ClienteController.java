package br.edu.gestao.controller;

import br.edu.gestao.entity.Cliente;
import br.edu.gestao.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }
    @GetMapping public List<Cliente> listar(@RequestParam(required = false) String nome) { return service.listar(nome); }
    @GetMapping("/{id}") public Cliente buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) { return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cliente)); }
    @PutMapping("/{id}") public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) { return service.atualizar(id, cliente); }
    @PatchMapping("/{id}/status") public Cliente status(@PathVariable Long id) { return service.alternarStatus(id); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id) { service.excluir(id); }
}
