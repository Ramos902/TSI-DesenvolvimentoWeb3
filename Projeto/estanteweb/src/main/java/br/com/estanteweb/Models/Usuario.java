package br.com.estanteweb.Models;

import java.io.Serializable;
import java.util.Map;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public static Usuario deMap(Map<String, Object> linha) {
        Usuario usuario = new Usuario();
        usuario.setId(((Number) linha.get("id")).longValue());
        usuario.setNome((String) linha.get("nome"));
        usuario.setEmail((String) linha.get("email"));
        usuario.setSenha((String) linha.get("senha"));
        return usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
