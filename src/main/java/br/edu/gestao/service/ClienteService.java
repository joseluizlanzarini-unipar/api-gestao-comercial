package br.edu.gestao.service;

import br.edu.gestao.entity.Cliente;
import br.edu.gestao.exception.RecursoNaoEncontradoException;
import br.edu.gestao.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    public ClienteService(ClienteRepository repository) { this.repository = repository; }
    public List<Cliente> listar(String nome) { return nome == null ? repository.findAll() : repository.findByNomeContainingIgnoreCaseOrderByNome(nome); }
    public Cliente buscar(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado.")); }
    public Cliente salvar(Cliente cliente) { cliente.setId(null); return repository.save(cliente); }
    public Cliente atualizar(Long id, Cliente entrada) {
        Cliente atual = buscar(id);
        atual.setNome(entrada.getNome()); atual.setDocumento(entrada.getDocumento()); atual.setEmail(entrada.getEmail());
        atual.setTelefone(entrada.getTelefone()); atual.setEndereco(entrada.getEndereco()); atual.setAtivo(entrada.getAtivo());
        return repository.save(atual);
    }
    public Cliente alternarStatus(Long id) { Cliente c = buscar(id); c.setAtivo(!c.getAtivo()); return repository.save(c); }
    public void excluir(Long id) { repository.delete(buscar(id)); }
}
