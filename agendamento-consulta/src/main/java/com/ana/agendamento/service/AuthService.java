package com.ana.agendamento.service;

import com.ana.agendamento.model.Usuario;

public class AuthService {
    public boolean login(Usuario usuario, String login, String senha) {
        return usuario.validarLogin(login, senha);
    }

    public void logout() {
        System.out.println("[AUTH] Sessão encerrada.");
    }
}