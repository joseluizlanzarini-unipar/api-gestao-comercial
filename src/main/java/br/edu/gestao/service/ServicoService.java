package br.edu.gestao.service;

import br.edu.gestao.entity.Servico;
import br.edu.gestao.exception.RecursoNaoEncontradoException;
import br.edu.gestao.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository repository;
    public ServicoService(ServicoRepository repository) { this.repository = repository; }
    public List<Servico> listar(String nome) { return nome == null ? repository.findAll() : repository.findByNomeContainingIgnoreCaseOrderByNome(nome); }
    public Servico buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado.")); }
    public Servico salvar(Servico servico) { servico.setId(null); return repository.save(servico); }
    public Servico atualizar(Long id, Servico e) { Servico s = buscar(id); s.setNome(e.getNome()); s.setDescricao(e.getDescricao()); s.setPreco(e.getPreco()); s.setAtivo(e.getAtivo()); return repository.save(s); }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
