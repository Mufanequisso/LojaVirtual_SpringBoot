package com.example.demo.controller;

import com.example.demo.model.Cliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/clientes")
public class DesejoController {





    @GetMapping("/desejos")
    public String paginaDesejos(Model model, HttpSession session) {
        // Por exemplo, pegar cliente logado da sessão:
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        if (cliente == null) {
            return "redirect:/clientes/login";
        }

        model.addAttribute("clienteId", cliente.getId());
        return "clientes/item_clientes"; // Nome do arquivo HTML Thymeleaf
    }

    @GetMapping("/wishlist")
    public String verDesejos(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        if (cliente == null) {
            return "redirect:/login";
        }
        model.addAttribute("clienteId", cliente.getId());
        return "clientes/desejados"; // Renderiza desejos.html
    }


}
