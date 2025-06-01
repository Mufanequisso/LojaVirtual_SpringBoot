package com.example.demo.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.example.demo.model.Usuario}
 */
public class UsuarioDto1 implements Serializable {
    private final Integer id;
    private final String nome;
    private final String email;
    private final String senha;
    private final String tipo;
    private final String telefone;
    private final Instant criadoEm;

    public UsuarioDto1(Integer id, String nome, String email, String senha, String tipo, String telefone, Instant criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.telefone = telefone;
        this.criadoEm = criadoEm;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefone() {
        return telefone;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDto1 entity = (UsuarioDto1) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nome, entity.nome) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.senha, entity.senha) &&
                Objects.equals(this.tipo, entity.tipo) &&
                Objects.equals(this.telefone, entity.telefone) &&
                Objects.equals(this.criadoEm, entity.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, senha, tipo, telefone, criadoEm);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nome = " + nome + ", " +
                "email = " + email + ", " +
                "senha = " + senha + ", " +
                "tipo = " + tipo + ", " +
                "telefone = " + telefone + ", " +
                "criadoEm = " + criadoEm + ")";
    }
}