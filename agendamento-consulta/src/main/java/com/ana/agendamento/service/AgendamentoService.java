package com.ana.agendamento.service;

import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.repository.ConsultaRepository;
import java.time.LocalDateTime;

public class AgendamentoService {
    private ConsultaRepository repository;

    public AgendamentoService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public boolean verificarDisponibilidade(LocalDateTime data) {
        return true; 
    }

    public void agendar(Consulta consulta) {
        repository.salvar(consulta);
    }
}