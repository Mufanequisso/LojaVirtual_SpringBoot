package com.example.demo.controller;

import com.example.demo.dao.PagamentoRepository;
import com.example.demo.model.Cliente;
import com.example.demo.model.Pagamento;
import com.example.demo.model.Pedido;
import com.example.demo.service.ClienteService;
import com.example.demo.service.PagamentoService;
import com.example.demo.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private final PedidoService pedidoService;
    @Autowired
    private final PagamentoService pagamentoService;
    @Autowired
    private final ClienteService clienteService; // para pegar cliente logado
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PagamentoController(PedidoService pedidoService, PagamentoService pagamentoService, ClienteService clienteService) {
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
        this.clienteService = clienteService;
    }

     //Listar pedidos pendentes do cliente logado usando HttpSession
    @GetMapping("/lista")
    public String listarPedidosPendentes(Model model, HttpSession session) {
        // Pega o cliente da sessão
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cliente não logado");
        }

        // Pega os pedidos pendentes usando o ID do cliente logado
        List<Pedido> pedidosPendentes = pedidoService.listarPedidosPendentesPorCliente(clienteLogado.getId());

        // Adiciona no modelo para o Thymeleaf
        model.addAttribute("pedidos", pedidosPendentes);

        return "pagamentos/listar"; // template Thymeleaf
    }
//    @GetMapping("/listar")
//    public String listarPagamentos(Model model) {
//        List<Pagamento> pagamentos = pagamentoRepository.findAll();
//        model.addAttribute("pedidos", pagamentos); // <-- verifique o NOME!
//        return "pagamentos/listar";
//    }






    // Endpoint para pagar um pedido específico
    @PostMapping("/pagar/{id}")
    public String pagarPedido(@PathVariable Integer id, HttpSession session, Model model) {
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            // Pode redirecionar para login ou mostrar mensagem de erro
            model.addAttribute("erro", "Cliente não logado. Faça login para continuar.");
            return "clientes/login"; // ou "redirect:/login" conforme sua rota
        }

        try {
            // Passa o id do cliente para o serviço para garantir segurança
            pagamentoService.pagarPedido(id, clienteLogado.getId());
            model.addAttribute("sucesso", "Pagamento realizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao processar pagamento: " + e.getMessage());
        }
        return "pagamentos/resultado";
    }



//    @GetMapping("/relatorio")
//    public String relatorio() {
//        return "pagamentos/relatorio"; // Nome do seu HTML no templates
//    }

    // Endpoint para deletar/cancelar pedido
    @PostMapping("/cancelar/{pedidoId}")
    public String cancelarPedido(@PathVariable Integer pedidoId, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteService.buscarPorUsername(principal.getName());
            pedidoService.cancelarPedido(pedidoId, cliente.getId());
            redirectAttributes.addFlashAttribute("sucesso", "Pedido cancelado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pagamentos";
    }
}
