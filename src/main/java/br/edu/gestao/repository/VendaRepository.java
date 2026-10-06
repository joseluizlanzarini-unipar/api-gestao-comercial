package br.edu.gestao.repository;

import br.edu.gestao.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByClienteIdOrderByDataHoraDesc(Long clienteId);
}
