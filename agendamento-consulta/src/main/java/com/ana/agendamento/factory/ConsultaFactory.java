package com.ana.agendamento.factory;

import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;

import java.time.LocalDateTime;

public class ConsultaFactory {
    public static Consulta criarConsulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
        return new Consulta(id, paciente, medico, dataHora);
    }
}