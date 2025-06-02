package com.example.demo.service;

import com.example.demo.dao.ProdutoRepository;
import com.example.demo.dao.UsuarioRepository;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.dto.UsuarioDto1;
import com.example.demo.model.Produto;
import com.example.demo.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<UsuarioDto> listarPorTipo(String tipo) {
        List<Usuario> usuarios = usuarioRepository.findByTipo(tipo.toLowerCase());
        return usuarios.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UsuarioDto convertToDTO(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(),
                usuario.getTipo(), usuario.getTelefone(), usuario.getCriadoEm());

        return dto;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }




}
