package com.example.demo.service;

import com.example.demo.dao.PagamentoRepository;
import com.example.demo.dao.PedidoRepository;
import com.example.demo.dao.ProdutoRepository;
import com.example.demo.model.Cliente;
import com.example.demo.model.Pagamento;
import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final PagamentoRepository pagamentoRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    private JavaMailSender mailSender;

    public PagamentoService(PedidoRepository pedidoRepository, ClienteService clienteService, PagamentoRepository pagamentoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.pagamentoRepository = pagamentoRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Lista todos os pedidos pendentes do cliente logado.
     */
    public List<Pedido> listarPedidosPendentesDoCliente(String username) {
        Cliente cliente = clienteService.buscarPorUsername(username);
        return pedidoRepository.findByClienteIdAndStatus(cliente.getId(), "pendente");
    }

    /**
     * Marca um pedido como pago (se for do cliente logado e estiver pendente).
     */
//    @Transactional
//    public void pagarPedido(Long pedidoId, String username) {
//        Cliente cliente = clienteService.buscarPorUsername(username);
//        Pedido pedido = pedidoRepository.findByIdAndClienteId(Math.toIntExact(pedidoId), cliente.getId())
//                .orElseThrow(() -> new RuntimeException("Pedido não encontrado para esse cliente"));
//
//        if (!"pendente".equals(pedido.getStatus())) {
//            throw new RuntimeException("Pedido não está pendente");
//        }
//
//        pedido.setStatus("pago");
//        pedido.setDataPedido(Instant.now());
//        pedidoRepository.save(pedido);
//    }
//    public void pagarPedido(Integer pedidoId) {
//        Pedido pedido = pedidoRepository.findById(pedidoId)
//                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
//
//        // Atualiza status
//        pedido.setStatus("pago");
//        pedido.setDataPedido(Instant.now());
//        pedidoRepository.save(pedido);
//
//        // Dados do pagamento
//        String metodoPagamento = "M-Pesa"; // ou outro
//        String referencia = UUID.randomUUID().toString().substring(0, 8);
//
//        // Envia e-mail para cliente e vendedor
//        enviarEmailPagamento(pedido, metodoPagamento, referencia);
//    }
//
//    private void enviarEmailPagamento(Pedido pedido, String metodoPagamento, String referencia) {
//        Cliente cliente = pedido.getCliente();
//        Usuario vendedor = pedido.getItens().get(0).getProduto().getLoja().getVendedor();
//
//        String assunto = "Confirmação de Pagamento - Pedido #" + pedido.getId();
//        String mensagem = String.format(
//                "Detalhes do Pagamento:\n\n" +
//                        "ID do Pedido: %d\n" +
//                        "Método de Pagamento: %s\n" +
//                        "Valor Pago: %.2f MZN\n" +
//                        "Data do Pagamento: %s\n" +
//                        "Status: %s\n" +
//                        "Referência: %s\n\nObrigado por comprar conosco!",
//                pedido.getId(),
//                metodoPagamento,
//                pedido.getTotal(),
//                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault()).format(pedido.getDataPedido()),
//                pedido.getStatus(),
//                referencia
//        );
//
//        // E-mail para cliente
//        SimpleMailMessage emailCliente = new SimpleMailMessage();
//        emailCliente.setTo(cliente.getEmail());
//        emailCliente.setSubject(assunto);
//        emailCliente.setText(mensagem);
//        mailSender.send(emailCliente);
//
//        // E-mail para vendedor
//        SimpleMailMessage emailVendedor = new SimpleMailMessage();
//        emailVendedor.setTo(vendedor.getEmail());
//        emailVendedor.setSubject(assunto);
//        emailVendedor.setText(mensagem);
//        mailSender.send(emailVendedor);
//    }


    @Transactional
    public void pagarPedido(Integer pedidoId, Integer id) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if ("pago".equalsIgnoreCase(pedido.getStatus())) {
            throw new RuntimeException("Este pedido já está pago.");
        }

        pedido.getItens().forEach(item -> {
            int estoqueAtual = item.getProduto().getQuantidade();
            if (estoqueAtual < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }
        });

        pedido.setStatus("pago");
        pedidoRepository.save(pedido);

        pedido.getItens().forEach(item -> {
            int estoqueAtual = item.getProduto().getQuantidade();
            item.getProduto().setQuantidade(estoqueAtual - item.getQuantidade());
            produtoRepository.save(item.getProduto()); // 🚀 garante update do produto
        });

        String referencia = "TXMZ" + (100000 + new Random().nextInt(900000));

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setMetodoPagamento("M-Pesa");
        pagamento.setValorPago(pedido.getTotal());
        pagamento.setDataPagamento(Instant.now());
        pagamento.setStatus("confirmado");
        pagamento.setReferenciaTransacao(referencia);

        pagamentoRepository.save(pagamento); // ⚡ FUNCIONA SE ID FOR @GeneratedValue

        enviarEmailPagamento(pedido, pagamento); // ✅ CHAMA UMA VEZ SÓ


    }

    private void enviarEmailPagamento(Pedido pedido, Pagamento pagamento) {
        Cliente cliente = pedido.getCliente();
        Usuario vendedor = pedido.getItens().get(0).getProduto().getLoja().getVendedor();

        String assunto = "Confirmação de Pagamento - Pedido #" + pedido.getId();
        String mensagem = String.format(
                "Detalhes do Pagamento:\n\n" +
                        "ID do Pedido: %d\n" +
                        "Método de Pagamento: %s\n" +
                        "Valor Pago: %.2f MZN\n" +
                        "Data do Pagamento: %s\n" +
                        "Status: %s\n" +
                        "Referência: %s\n\nObrigado por comprar conosco!",
                pedido.getId(),
                pagamento.getMetodoPagamento(),
                pagamento.getValorPago(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(pagamento.getDataPagamento()),
                pagamento.getStatus(),
                pagamento.getReferenciaTransacao()
        );

        // E-mail para cliente
        SimpleMailMessage emailCliente = new SimpleMailMessage();
        emailCliente.setTo(cliente.getEmail());
        emailCliente.setSubject(assunto);
        emailCliente.setText(mensagem);
        mailSender.send(emailCliente);

        // E-mail para vendedor
        SimpleMailMessage emailVendedor = new SimpleMailMessage();
        emailVendedor.setTo(vendedor.getEmail());
        emailVendedor.setSubject(assunto);
        emailVendedor.setText(mensagem);
        mailSender.send(emailVendedor);
    }



    /**
     * Deleta um pedido do cliente logado.
     */
    @Transactional
    public void deletarPedido(Long pedidoId, String username) {
        Cliente cliente = clienteService.buscarPorUsername(username);
        Pedido pedido = pedidoRepository.findByIdAndClienteId(Math.toIntExact(pedidoId), cliente.getId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado para esse cliente"));

        pedidoRepository.delete(pedido);
    }


}
