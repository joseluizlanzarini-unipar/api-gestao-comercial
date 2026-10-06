package br.edu.gestao.repository;

import br.edu.gestao.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCaseOrderByNome(String nome);
}
