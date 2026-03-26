package com.ana.agendamento;

import java.time.LocalDateTime;

import com.ana.agendamento.model.Agenda;
import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Agendamento UNIPAR ===");

        // Criando instâncias do model
        Paciente p = new Paciente("Ana Elisa", "123.456.789-00", "ana.neves", "senha123");
        Medico m = new Medico("Dr. Rodrigo", "Ortopedia", "rodrigo.tomazi", "doc456");

        Consulta c = new Consulta(p, m, LocalDateTime.now());

        Agenda agenda = new Agenda();
        agenda.adicionarConsulta(c);

        // Saída no terminal
        System.out.println("Sucesso! " + agenda.getConsultas().get(0));
    }
}
