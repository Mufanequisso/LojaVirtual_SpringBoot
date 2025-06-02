package com.example.demo.api;


import org.springframework.web.bind.annotation.RestController;



import com.example.demo.dto.UsuarioDto;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorRestController {
    @Autowired
    private UsuarioService usuarioService;
//    @Autowired
//    private ProdutoService produtoService;

    @GetMapping
    public List<UsuarioDto> listarVendedores() {
        return usuarioService.listarPorTipo("vendedor");
    }

    
}
