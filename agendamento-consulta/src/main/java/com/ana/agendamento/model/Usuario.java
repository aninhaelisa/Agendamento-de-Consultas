package com.ana.agendamento.model;

public class Usuario {
    private String login;
    private String senha;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public boolean validarLogin(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public String getLogin() { return login; }
}