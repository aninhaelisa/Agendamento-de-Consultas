package com.ana.agendamento.service;

import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.repository.PacienteRepository;

public class PacienteService {
    private PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente cadastrarPaciente(Paciente dados) {
        repository.salvar(dados);
        return dados;
    }

    public void atualizarPaciente(int id, Paciente dados) {
        Paciente existente = repository.buscarPorId(id);
        if (existente != null) {
            Paciente atualizado = new Paciente(
                    id,
                    dados.getNome(),
                    dados.getCpf(),
                    dados.getTelefone(),
                    dados.getEmail(),
                    dados.getSenha()
            );
            repository.atualizar(atualizado);
        }
    }

    public Paciente buscarPaciente(int id) {
        return repository.buscarPorId(id);
    }

    public Paciente buscarPorEmail(String email) {
        return repository.buscarPorEmail(email);
    }

    public Paciente buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }
}