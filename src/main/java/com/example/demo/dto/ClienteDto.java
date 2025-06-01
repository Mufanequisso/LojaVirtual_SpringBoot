package com.example.demo.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * DTO for {@link com.example.demo.model.Cliente}
 */
public class ClienteDto implements Serializable {
    private final Integer id;
    private final String nome;
    private final String email;
    private final String senha;
    private final String telefone;
    private final String endereco;
    private final Instant criadoEm;

    public ClienteDto(Integer id, String nome, String email, String senha, String telefone, String endereco, Instant criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
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

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDto entity = (ClienteDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nome, entity.nome) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.senha, entity.senha) &&
                Objects.equals(this.telefone, entity.telefone) &&
                Objects.equals(this.endereco, entity.endereco) &&
                Objects.equals(this.criadoEm, entity.criadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, senha, telefone, endereco, criadoEm);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nome = " + nome + ", " +
                "email = " + email + ", " +
                "senha = " + senha + ", " +
                "telefone = " + telefone + ", " +
                "endereco = " + endereco + ", " +
                "criadoEm = " + criadoEm + ")";
    }
}