package br.edu.gestao.repository;

import br.edu.gestao.entity.Venda;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Carrega o cliente e os itens antes que a sessão seja encerrada.
    // Evita LazyInitializationException durante a conversão para JSON.
    @Override
    @EntityGraph(attributePaths = {"cliente", "itens"})
    Optional<Venda> findById(Long id);

    // Também carrega os relacionamentos ao listar todas as vendas.
    @Override
    @EntityGraph(attributePaths = {"cliente", "itens"})
    List<Venda> findAll();

    // Carrega os relacionamentos no filtro por cliente.
    @EntityGraph(attributePaths = {"cliente", "itens"})
    List<Venda> findByClienteIdOrderByDataHoraDesc(Long clienteId);
}
