package com.ana.agendamento;

import java.time.LocalDateTime;

import com.ana.agendamento.facade.SistemaFacade;
import com.ana.agendamento.model.Agenda;
import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;

public class App {
    public static void main(String[] args) {
        System.out.println("=== CLINICA ANA ELISA - SISTEMA DE AGENDAMENTO ===");

        Paciente paciente = new Paciente("Ana", "111.222.333-44", "ana.user", "123");
        Medico medico = new Medico("Dr. Rodrigo", "Engenharia de Software", "rodrigo.doc", "456");

        SistemaFacade clinica = new SistemaFacade();
        
        clinica.agendarConsulta(paciente, medico, LocalDateTime.now());

        clinica.cancelarConsulta(101);
    }
}
