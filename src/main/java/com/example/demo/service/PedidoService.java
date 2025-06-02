package com.example.demo.service;

import com.example.demo.dao.ClienteRepository;
import com.example.demo.dao.PedidoRepository;
import com.example.demo.dao.ProdutoRepository;
import com.example.demo.dto.ItemPedidoDto;
import com.example.demo.dto.PedidoDto2;
import com.example.demo.model.ItensPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Cliente;

import com.example.demo.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PedidoService {



    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;



//    @Transactional
//    public Pedido criarPedido(Integer clienteId, List<ItemPedidoDto> itensDTO) {
//        Cliente cliente = clienteRepository.findById(clienteId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//
//        Pedido pedido = new Pedido();
//        pedido.setCliente(cliente);
//        pedido.setDataPedido(Instant.now());
//        pedido.setStatus("pendente");
//
//        List<ItensPedido> itens = new ArrayList<>();
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (ItemPedidoDto dto : itensDTO) {
//            Produto produto = produtoRepository.findById(Math.toIntExact(dto.getProdutoId()))
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + dto.getProdutoId()));
//
//            // ✅ Verificar estoque disponível
//            if (dto.getQuantidade() > produto.getQuantidade()) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        "Estoque insuficiente para o produto: " + produto.getNome());
//            }
//
//            // ✅ Criar item do pedido
//            ItensPedido item = new ItensPedido();
//            item.setPedido(pedido);
//            item.setProduto(produto);
//            item.setQuantidade(dto.getQuantidade());
//            item.setPreco(produto.getPreco());
//            itens.add(item);
//
//
//            // ✅ Somar subtotal
//            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade())));
//        }
//
//        pedido.setItens(itens);
//        pedido.setTotal(total);
//
//        return pedidoRepository.save(pedido);
//    }

    @Transactional
    public PedidoDto2 criarPedido(Integer clienteId, List<ItemPedidoDto> itensDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(Instant.now());
        pedido.setStatus("pendente");

        List<ItensPedido> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoDto dto : itensDTO) {
            Produto produto = produtoRepository.findById(dto.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + dto.getProdutoId()));

            if (dto.getQuantidade() > produto.getQuantidade()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItensPedido item = new ItensPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(dto.getQuantidade());
            item.setPreco(produto.getPreco());
            itens.add(item);

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade())));
        }

        pedido.setItens(itens);
        pedido.setTotal(total);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return toDto2(pedidoSalvo);
    }

    public PedidoDto2 toDto2(Pedido pedido) {
        PedidoDto2 dto = new PedidoDto2();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus());
        dto.setTotal(pedido.getTotal());

        AtomicInteger soma = new AtomicInteger();
        List<PedidoDto2.ItemPedidoDTO> itensDto = pedido.getItens().stream().map(item -> {
            PedidoDto2.ItemPedidoDTO itemDto = new PedidoDto2.ItemPedidoDTO();
            itemDto.setProdutoId(item.getProduto().getId());
            itemDto.setProdutoNome(item.getProduto().getNome());
            itemDto.setQuantidade(item.getQuantidade());
            soma.addAndGet(item.getQuantidade());
            return itemDto;
        }).toList();

        dto.setItensPedidos(itensDto);
        dto.setQuantidadeTotal(soma.get());

        return dto;
    }




//    public Pedido criarPedido(Integer clienteId, List<ItemPedidoDto> itensDTO) {
//        Cliente cliente = clienteRepository.findById(clienteId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//
//        Pedido pedido = new Pedido();
//        pedido.setCliente(cliente);
//        pedido.setDataPedido(Instant.now());
//        pedido.setStatus("pendente");
//
//        List<ItensPedido> itens = new ArrayList<>();
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (ItemPedidoDto dto : itensDTO) {
//            Produto produto = produtoRepository.findById(Math.toIntExact(dto.getProdutoId()))
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + dto.getProdutoId()));
//
//            ItensPedido item = new ItensPedido();
//            item.setPedido(pedido);
//            item.setProduto(produto);
//            item.setQuantidade(dto.getQuantidade());
//            item.setPreco(produto.getPreco());
//
//            itens.add(item);
//
//            // ✅ Somar subtotal: preco * quantidade
//            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(dto.getQuantidade())));
//        }
//
//        pedido.setItens(itens);
//
//        // ✅ Definir total antes de salvar!
//        pedido.setTotal(total);
//
//        return pedidoRepository.save(pedido);
//    }

    // Listar todos os pedidos
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Buscar pedido por ID
    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    // Listar pedidos de um cliente específico
    public List<Pedido> listarPorCliente(Integer clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<PedidoDto2> listarPedidosPorCliente(Integer clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        return pedidos.stream().map(p -> {
            PedidoDto2 dto = new PedidoDto2();
            dto.setId(p.getId());
            dto.setStatus(p.getStatus());
            dto.setTotal(p.getTotal());

            List<PedidoDto2.ItemPedidoDTO> itens = p.getItens().stream().map(i -> {
                PedidoDto2.ItemPedidoDTO itemDto = new PedidoDto2.ItemPedidoDTO();
                itemDto.setProdutoId(Math.toIntExact(i.getProduto().getId()));
                itemDto.setProdutoNome(i.getProduto().getNome()); // Não esqueça esse!
                itemDto.setQuantidade(i.getQuantidade());
                return itemDto;
            }).toList();

            dto.setItensPedidos(itens);
            return dto;
        }).toList();
    }

    public List<PedidoDto2> listarPedidosPendentes() {
        List<PedidoDto2> lista = new ArrayList<>();

        // Simulando pedido
        PedidoDto2 pedido = new PedidoDto2();
        pedido.setId(1);
        pedido.setStatus("pendente");
        pedido.setTotal(new BigDecimal("500"));

        List<PedidoDto2.ItemPedidoDTO> itens = new ArrayList<>();
        PedidoDto2.ItemPedidoDTO item1 = new PedidoDto2.ItemPedidoDTO();
        item1.setProdutoId(101);
        item1.setProdutoNome("Produto A");
        item1.setQuantidade(2);

        PedidoDto2.ItemPedidoDTO item2 = new PedidoDto2.ItemPedidoDTO();
        item2.setProdutoId(102);
        item2.setProdutoNome("Produto B");
        item2.setQuantidade(1);

        itens.add(item1);
        itens.add(item2);

        pedido.setItensPedidos(itens);

        // Calcular quantidade total no backend
        int soma = itens.stream().mapToInt(PedidoDto2.ItemPedidoDTO::getQuantidade).sum();
        pedido.setQuantidadeTotal(soma);

        lista.add(pedido);

        return lista;
    }
    public List<Pedido> listarPedidosPendentesPorCliente(int clienteId) {
        List<Pedido> lista = pedidoRepository.buscarPedidosPendentesPorCliente(clienteId);

        // Calcular quantidadeTotal dentro de cada pedido
        for (Pedido p : lista) {
            BigDecimal soma = BigDecimal.valueOf(p.getItens().stream().mapToInt(i -> i.getQuantidade()).sum());
            // Você pode setar num campo transient se quiser ou passar para DTO depois.
            p.setTotal(soma); // Precisa ter getQuantidadeTotal() no Pedido
        }
        return lista;
    }


    public List<Pedido> listarPedidosPendentesPorCliente(Integer clienteId) {
        return pedidoRepository.findByClienteIdAndStatus(clienteId, "pendente");
    }

    public void pagarPedido(Integer pedidoId, Integer clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou não pertence ao cliente"));

        if (!pedido.getStatus().equals("pendente")) {
            throw new RuntimeException("Pedido já foi pago ou não está pendente");
        }

        // Lógica real de pagamento aqui (ex: integração com gateway)
        pedido.setStatus("pago");
        pedidoRepository.save(pedido);
    }

    public void cancelarPedido(Integer pedidoId, Integer clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(pedidoId, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou não pertence ao cliente"));

        if (!pedido.getStatus().equals("pendente")) {
            throw new RuntimeException("Não pode cancelar pedido já pago ou finalizado");
        }

        pedidoRepository.delete(pedido);
    }

}
