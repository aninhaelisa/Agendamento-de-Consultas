package com.ana.agendamento.service;

import com.ana.agendamento.model.Usuario;

public class AuthService {
    private Usuario usuarioLogado;

    public Usuario login(String email, String senha, Usuario usuario) {
        if (usuario != null && usuario.validarLogin(email, senha)) {
            usuarioLogado = usuario;
            return usuarioLogado;
        }
        return null;
    }

    public void logout() {
        usuarioLogado = null;
        System.out.println("[AUTH] Sessão encerrada.");
    }

    public boolean validarUsuario(Usuario usuario) {
        return usuario != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}