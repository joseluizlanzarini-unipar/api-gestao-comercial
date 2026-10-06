package br.edu.gestao.config;

import br.edu.gestao.entity.*;
import br.edu.gestao.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;

@Configuration
public class DadosIniciais {
    @Bean
    CommandLineRunner carregar(ClienteRepository clientes, ProdutoRepository produtos, ServicoRepository servicos) {
        return args -> {
            if (clientes.count() == 0) {
                Cliente c = new Cliente(); c.setNome("Cliente Demonstração"); c.setDocumento("00000000000");
                c.setEmail("cliente@exemplo.com"); c.setTelefone("(46) 99999-0000"); c.setEndereco("Pato Branco - PR"); clientes.save(c);
            }
            if (produtos.count() == 0) {
                Produto p1 = new Produto(); p1.setNome("Teclado USB"); p1.setDescricao("Teclado para computador"); p1.setPreco(new BigDecimal("89.90")); p1.setEstoque(20); produtos.save(p1);
                Produto p2 = new Produto(); p2.setNome("Mouse sem fio"); p2.setDescricao("Mouse óptico sem fio"); p2.setPreco(new BigDecimal("59.90")); p2.setEstoque(30); produtos.save(p2);
            }
            if (servicos.count() == 0) {
                Servico s = new Servico(); s.setNome("Formatação de computador"); s.setDescricao("Instalação do sistema e configuração inicial"); s.setPreco(new BigDecimal("180.00")); servicos.save(s);
            }
        };
    }
}
