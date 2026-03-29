package com.ana.agendamento.model;

public abstract class Usuario {
    private int id;
    private String email;
    private String senha;
    private String tipo;

    public Usuario(int id, String email, String senha, String tipo) {
        this.id = id;
        this.email = email.trim();
        this.senha = senha.trim();
        this.tipo = tipo.trim();
    }

    public int getId() {
        return id;
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

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha.trim();
    }

    public boolean validarLogin(String email, String senha) {
        return this.email.equalsIgnoreCase(email.trim()) && this.senha.equals(senha.trim());
    }
}