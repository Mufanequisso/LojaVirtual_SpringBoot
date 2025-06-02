package com.example.demo.controller;

import com.example.demo.dao.ClienteRepository;
import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    // Formulário de cadastro
    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/cliente_form";
    }

    // Processa o envio do formulário de cadastro
    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteService.salvar(cliente);
        return "redirect:/clientes/novo?sucesso";
    }

    // Listar clientes
    @GetMapping("/listar")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        return "cliente-lista";
    }

    // Editar cliente pelo ID
    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable Integer id, Model model) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
            return "cliente-form";
        }
        return "redirect:/clientes/listar?erro";
    }

    // Exibir página de login (tratando possíveis parâmetros de erro e sucesso)
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "erro", required = false) String erro,
                               @RequestParam(value = "sucesso", required = false) String sucesso,
                               Model model) {
        if (erro != null) model.addAttribute("erro", true);
        if (sucesso != null) model.addAttribute("sucesso", true);
//        model.addAttribute("cliente", new Cliente());
        // Salva o ID na sessão

        return "clientes/login"; // nome da view login.html
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Optional<Cliente> optionalCliente = clienteRepository.findByEmailAndSenha(email, senha);
        if (optionalCliente.isPresent()) {
            session.setAttribute("clienteLogado", optionalCliente.get());
            return "redirect:/clientes/dashboard";
        } else {
            // Credenciais inválidas
            return "clientes/login"; // ou mensagem de erro
        }

    }

    @GetMapping("/dashboardd")
    public String dashboard(HttpSession session, Model model) {
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            return "redirect:/clientes/login";
        }

        // Se quiser passar info do cliente para exibir na dashboard:
        model.addAttribute("cliente", clienteLogado);

        return "clientes/dashboard"; // isto deve ser o nome do teu HTML Thymeleaf
    }

//    @PostMapping("/logout")
//    public String logout(HttpSession session) {
//        // Remove o atributo da sessão
//        session.invalidate(); // encerra toda a sessão do cliente
//
//        // Redireciona para a página de login ou página inicial
//        return "redirect:/clientes/login";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("clienteLogado");
        return "redirect:/clientes/login";
    }









//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String senha,
//                        HttpSession session) {
//        Optional<Cliente> cliente = clienteService.autenticar(email, senha);
//        if (cliente == null) {
//            // Erro de login
//        }
//        session.setAttribute("clienteLogado", cliente);
//        return "redirect:/clientes/dashboard";
//    }

//    @PostMapping("/login")
//    public String login(@RequestParam String email,
//                        @RequestParam String senha,
//                        HttpSession session,
//                        Model model) {
//        Optional<Cliente> cliente = clienteService.autenticar(email, senha);
//        if (cliente.isPresent()) {
//            Integer clienteId = cliente.get().getId();
//            System.out.println("Login bem-sucedido. Salvando clienteId na sessão: " + clienteId);
//            session.setAttribute("clienteId", clienteId);
//            return "redirect:/clientes/dashboard";
//        } else {
//            model.addAttribute("erro", "Credenciais inválidas");
//            return "clientes/login";
//        }
//    }


//@PostMapping("/login")
//public String login(@RequestParam String email,
//                    @RequestParam String senha,
//                    HttpSession session) {
//
//    Cliente cliente = clienteRepository.findByEmailAndSenha(email, senha)
//            .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
//
//    // Salva o ID na sessão
//    session.setAttribute("clienteId", cliente.getId());
//
//    // Redireciona para página inicial
//     return "redirect:/clientes/dashboard";
//}

    // Processar submissão do login
//    @PostMapping("/login")
//    public String processarLogin(@RequestParam String email, @RequestParam String senha, Model model) {
//        Optional<Cliente> cliente = clienteService.autenticar(email, senha);
//        if (cliente.isPresent()) {
//            // lógica pós-login, ex: guardar cliente na sessão
//            return "redirect:/clientes/dashboard";
//        } else {
//            return "redirect:/clientes/login?erro=true";
//        }
//    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "clientes/dashboard"; // carrega templates/clientes/dashboard.html
    }

    // Remover cliente pelo ID
    @GetMapping("/remover/{id}")
    public String removerCliente(@PathVariable Integer id) {
        clienteService.remover(id);
        return "redirect:/clientes/listar?removido";
    }

    // Atualizar cliente (save/update)
    @PostMapping("/actualizar")
    public String actualizarCliente(@ModelAttribute Cliente cliente) {
        clienteService.salvar(cliente);
        return "redirect:/clientes/listar?sucesso";
    }


}



