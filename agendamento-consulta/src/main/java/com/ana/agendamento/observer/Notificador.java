package com.ana.agendamento.observer;

import com.ana.agendamento.model.Paciente;

public class Notificador implements PacienteObserver {
    private Paciente paciente;

    public Notificador(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("[NOTIFICAÇÃO] Para: " + paciente.getNome() + " | Mensagem: " + mensagem);
    }
}