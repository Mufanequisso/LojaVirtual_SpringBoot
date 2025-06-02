package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private UsuarioService usuarioService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Exibe o formulário de login
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String erro,
                               @RequestParam(required = false) String email,
                               Model model) {
        if (erro != null) {
            model.addAttribute("erro", "Email ou senha inválidos.");
        }
        model.addAttribute("email", email);
        return "vendedor/vendedor_login_form"; // src/main/resources/templates/vendedor-login.html
    }

    // Processa o login
    @PostMapping("/login")
    public String processarLogin(@RequestParam String email,
                                 @RequestParam String senha,
                                 HttpSession session) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null && "vendedor".equalsIgnoreCase(usuario.getTipo())
                && passwordEncoder.matches(senha, usuario.getSenha())) {

            session.setAttribute("vendedorLogado", usuario.getId());
            return "vendedor/dashboard"; // Página inicial do vendedor
        }
        return "redirect:/vendedor/vendedor_login_form?erro=true&email=" + email;
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/vendedor/vendedor_login_form";
    }

}
