package com.ana.agendamento.service;

import com.ana.agendamento.model.Medico;
import com.ana.agendamento.repository.MedicoRepository;
import java.util.List;

public class MedicoService {
    private MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public void cadastrarMedico(Medico medico) {
        repository.salvar(medico);
    }

    public List<Medico> listarTodos() {
        return repository.listarTodos();
    }
}