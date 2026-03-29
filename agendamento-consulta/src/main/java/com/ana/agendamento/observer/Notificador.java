package com.ana.agendamento.observer;

import com.ana.agendamento.model.Paciente;

public class Notificador {
    public void notificar(Paciente paciente, String mensagem) {
        PacienteObserver observer = new PacienteObserver() {
            @Override
            public void atualizar(String mensagem) {
                System.out.println("[NOTIFICAÇÃO] Para: " + paciente.getNome() + " | Mensagem: " + mensagem);
            }
        };
        observer.atualizar(mensagem);
    }
}