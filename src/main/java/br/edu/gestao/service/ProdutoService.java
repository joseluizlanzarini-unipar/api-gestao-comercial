package br.edu.gestao.service;

import br.edu.gestao.entity.Produto;
import br.edu.gestao.exception.RecursoNaoEncontradoException;
import br.edu.gestao.exception.RegraNegocioException;
import br.edu.gestao.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    public ProdutoService(ProdutoRepository repository) { this.repository = repository; }
    public List<Produto> listar(String nome) { return nome == null ? repository.findAll() : repository.findByNomeContainingIgnoreCaseOrderByNome(nome); }
    public Produto buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado.")); }
    public Produto salvar(Produto produto) { produto.setId(null); return repository.save(produto); }
    public Produto atualizar(Long id, Produto e) { Produto p = buscar(id); p.setNome(e.getNome()); p.setDescricao(e.getDescricao()); p.setPreco(e.getPreco()); p.setEstoque(e.getEstoque()); p.setAtivo(e.getAtivo()); return repository.save(p); }
    public Produto atualizarEstoque(Long id, Integer estoque) { Produto p = buscar(id); if (estoque < 0) throw new RegraNegocioException("Estoque não pode ser negativo."); p.setEstoque(estoque); return repository.save(p); }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
