package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
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
        model.addAttribute("cliente", new Cliente());
        return "clientes/login"; // nome da view login.html
    }

    // Processar submissão do login
    @PostMapping("/login")
    public String processarLogin(@RequestParam String email, @RequestParam String senha, Model model) {
        Optional<Cliente> cliente = clienteService.autenticar(email, senha);
        if (cliente.isPresent()) {
            // lógica pós-login, ex: guardar cliente na sessão
            return "redirect:/clientes/dashboard";
        } else {
            return "redirect:/clientes/login?erro=true";
        }
    }

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
