package br.edu.gestao.controller;

import br.edu.gestao.dto.EstoqueRequest;
import br.edu.gestao.entity.Produto;
import br.edu.gestao.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }
    @GetMapping public List<Produto> listar(@RequestParam(required = false) String nome) { return service.listar(nome); }
    @GetMapping("/{id}") public Produto buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(produto)); }
    @PutMapping("/{id}") public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) { return service.atualizar(id, produto); }
    @PatchMapping("/{id}/estoque") public Produto estoque(@PathVariable Long id, @Valid @RequestBody EstoqueRequest request) { return service.atualizarEstoque(id, request.estoque()); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id) { service.excluir(id); }
}
