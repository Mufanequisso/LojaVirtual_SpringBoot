package com.example.demo.controller;

import com.example.demo.model.Loja;
import com.example.demo.model.Produto;
import com.example.demo.service.LojaService;
import com.example.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LojaService lojaService;


//    @GetMapping
//    public String listarTodos(Model model) {
//      model.addAttribute("produtos", produtoService.listarTodos());
//
//        return "produtos/ver_produtos"; // Exemplo de view: src/main/resources/templates/produtos/lista.html
//    }
    @GetMapping("/verP")
    public String verProdutos() {
        return "produtos/ver_produtos"; // Thymeleaf buscará o arquivo menu-cliente.html em /templates
    }

    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        List<Loja> lojas = lojaService.listarTodos(); // Ou filtrar por vendedor se quiser
        model.addAttribute("lojas", lojas);
        return "produtos/produto_form"; // Nome do arquivo HTML sem .html
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        if (produto.getId() != null) {
            produtoService.atualizar(produto);
        } else {
            produtoService.salvar(produto);
        }
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Integer id, Model model) {
        Optional<Produto> produtoOpt = produtoService.buscarPorId(id);
        if (produtoOpt.isPresent()) {
            model.addAttribute("produto", produtoOpt.get());
            return "produtos/form";
        } else {
            return "redirect:/produtos"; // ou uma página de erro
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Integer id) {
        produtoService.remover(id);
        return "redirect:/produtos";
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        model.addAttribute("produtos", produtoService.buscarPorNome(nome));
        return "produtos/lista";
    }

    @GetMapping("/loja/{lojaId}")
    public String listarPorLoja(@PathVariable Integer lojaId, Model model) {
        model.addAttribute("produtos", produtoService.buscarPorLojaId(lojaId));
        return "produtos/lista";
    }
}
