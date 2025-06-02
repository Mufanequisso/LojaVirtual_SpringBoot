//package com.example.demo.api;
//
//import com.example.demo.dto.UsuarioDto;
//import com.example.demo.service.UsuarioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/usuarios")
//public class UsuarioRestController {
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @GetMapping("/tipo/{tipo}")
//    public List<UsuarioDto> listarPorTipo(@PathVariable String tipo) {
//        return usuarioService.listarPorTipo(tipo);
//    }
//}