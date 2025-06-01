package com.example.demo.api;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Loja;
import com.example.demo.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
@CrossOrigin(origins = "*")

class LojaRestController {

    @Autowired
    private LojaService lojaService;

    @GetMapping
    public List<Loja> listarPorVendedor(@RequestParam Integer vendedorId) {
        return lojaService.getLojasByVendedorId(vendedorId);
    }


}
