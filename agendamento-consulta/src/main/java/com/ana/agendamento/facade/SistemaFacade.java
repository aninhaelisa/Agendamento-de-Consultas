package com.ana.agendamento.facade;

import com.ana.agendamento.model.*;
import com.ana.agendamento.factory.ConsultaFactory;
import com.ana.agendamento.repository.*;
import com.ana.agendamento.service.*;
import java.time.LocalDateTime;

public class SistemaFacade {
    private AgendamentoService agendamentoService;
    private AuthService authService;

    public SistemaFacade() {
        this.agendamentoService = new AgendamentoService(new ConsultaRepository());
        this.authService = new AuthService();
    }

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDateTime data) {
        if (agendamentoService.verificarDisponibilidade(data)) {
            Consulta novaConsulta = ConsultaFactory.criarConsulta(paciente, medico, data);
            agendamentoService.agendar(novaConsulta);
            System.out.println("[SUCESSO] Agendamento realizado: " + novaConsulta);
        }
    }

    public void cancelarConsulta(int id) {
        System.out.println("[FACADE] Cancelando consulta ID: " + id);
    }
}