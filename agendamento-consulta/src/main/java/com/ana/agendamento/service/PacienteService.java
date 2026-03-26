package com.ana.agendamento.service;

import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.repository.PacienteRepository;

public class PacienteService {
    private PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public void cadastrarPaciente(Paciente paciente) {
        repository.salvar(paciente);
    }

    public Paciente buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }
}