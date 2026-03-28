package com.ana.agendamento.model;

public abstract class Usuario {
    private String login;
    private String senha;

    public Usuario(String login, String senha) {
        this.login = login.trim();
        this.senha = senha.trim();
    }

    public boolean validarLogin(String login, String senha) {
        return this.login.equals(login.trim()) && this.senha.equals(senha.trim());
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}