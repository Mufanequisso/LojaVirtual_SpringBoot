package com.example.demo.controller;

import com.example.demo.dao.PedidoRepository;
import com.example.demo.dto.PedidoDto2;
import com.example.demo.model.Cliente;
import com.example.demo.model.Pedido;
import com.example.demo.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;


    public PedidoController(PedidoRepository pedidoRepository, PedidoService pedidoService) {

        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pendente")
    public String listarPendentes(Model model) {
        List<Pedido> pedidosPendentes = pedidoRepository.findByStatus("pendente");
        model.addAttribute("pedidos", pedidosPendentes);
        return "pedidos/pendente"; // teu HTML deve estar em templates/pedidos-pendentes.html
    }

    @GetMapping("/pendent")
    public String listarPedidosCliente(Model model, HttpSession session) {
        // Pegue o ID do cliente logado da sessão
        Cliente clienteId = (Cliente) session.getAttribute("clienteLogado");
        if (clienteId == null) {
            return "redirect:/clientes/login"; // ou outro fluxo
        }

        List<PedidoDto2> pedidos = pedidoService.listarPedidosPorCliente(clienteId.getId());
        model.addAttribute("clienteId", pedidos);

        return "pedidos/pendente"; // caminho para o seu HTML
    }

    @GetMapping("/pendentes")
    public String mostrarPedidosPendentes(HttpSession session, Model model) {
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            // Se não estiver logado, redireciona para a página de login
            return "redirect:/clientes/login";
        }

        Integer clienteId = clienteLogado.getId();
        model.addAttribute("pedidos", pedidoService.listarPedidosPendentesPorCliente(clienteId));
        return "pedidos/pendente";
    }

}
