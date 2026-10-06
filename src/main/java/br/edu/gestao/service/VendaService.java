package br.edu.gestao.service;

import br.edu.gestao.dto.*;
import br.edu.gestao.entity.*;
import br.edu.gestao.exception.*;
import br.edu.gestao.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class VendaService {
    private final VendaRepository vendas;
    private final ClienteRepository clientes;
    private final ProdutoRepository produtos;
    private final ServicoRepository servicos;

    public VendaService(VendaRepository vendas, ClienteRepository clientes, ProdutoRepository produtos, ServicoRepository servicos) {
        this.vendas = vendas; this.clientes = clientes; this.produtos = produtos; this.servicos = servicos;
    }

    public List<Venda> listar(Long clienteId) {
        return clienteId == null ? vendas.findAll() : vendas.findByClienteIdOrderByDataHoraDesc(clienteId);
    }

    public Venda buscar(Long id) {
        return vendas.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Venda não encontrada."));
    }

    @Transactional
    public Venda criar(VendaRequest request) {
        Cliente cliente = clientes.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));
        if (!cliente.getAtivo()) throw new RegraNegocioException("Não é possível vender para um cliente inativo.");

        Venda venda = new Venda();
        venda.setCliente(cliente);
        BigDecimal total = BigDecimal.ZERO;

        for (ItemVendaRequest entrada : request.itens()) {
            ItemVenda item = entrada.tipo() == TipoItem.PRODUTO
                    ? criarItemProduto(entrada) : criarItemServico(entrada);
            venda.adicionarItem(item);
            total = total.add(item.getSubtotal());
        }
        venda.setValorTotal(total);
        return vendas.save(venda);
    }

    private ItemVenda criarItemProduto(ItemVendaRequest entrada) {
        Produto produto = produtos.findById(entrada.referenciaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + entrada.referenciaId()));
        if (!produto.getAtivo()) throw new RegraNegocioException("Produto inativo: " + produto.getNome());
        if (produto.getEstoque() < entrada.quantidade()) throw new RegraNegocioException("Estoque insuficiente para: " + produto.getNome());
        produto.setEstoque(produto.getEstoque() - entrada.quantidade());
        produtos.save(produto);
        return montarItem(TipoItem.PRODUTO, produto.getId(), produto.getNome(), entrada.quantidade(), produto.getPreco());
    }

    private ItemVenda criarItemServico(ItemVendaRequest entrada) {
        Servico servico = servicos.findById(entrada.referenciaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado: " + entrada.referenciaId()));
        if (!servico.getAtivo()) throw new RegraNegocioException("Serviço inativo: " + servico.getNome());
        return montarItem(TipoItem.SERVICO, servico.getId(), servico.getNome(), entrada.quantidade(), servico.getPreco());
    }

    private ItemVenda montarItem(TipoItem tipo, Long id, String descricao, Integer quantidade, BigDecimal valor) {
        ItemVenda item = new ItemVenda();
        item.setTipo(tipo); item.setReferenciaId(id); item.setDescricao(descricao);
        item.setQuantidade(quantidade); item.setValorUnitario(valor);
        item.setSubtotal(valor.multiply(BigDecimal.valueOf(quantidade)));
        return item;
    }

    @Transactional
    public Venda cancelar(Long id) {
        Venda venda = buscar(id);
        if (venda.getStatus() == StatusVenda.CANCELADA) throw new RegraNegocioException("A venda já está cancelada.");
        for (ItemVenda item : venda.getItens()) {
            if (item.getTipo() == TipoItem.PRODUTO) {
                Produto produto = produtos.findById(item.getReferenciaId()).orElse(null);
                if (produto != null) produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            }
        }
        venda.setStatus(StatusVenda.CANCELADA);
        return vendas.save(venda);
    }
}
