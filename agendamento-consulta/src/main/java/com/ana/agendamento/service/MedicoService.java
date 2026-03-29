package com.ana.agendamento.service;

import com.ana.agendamento.model.Medico;
import com.ana.agendamento.repository.MedicoRepository;

import java.util.List;

public class MedicoService {
    private MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public Medico cadastrarMedico(Medico dados) {
        repository.salvar(dados);
        return dados;
    }

    public void atualizarMedico(int id, Medico dados) {
        Medico existente = repository.buscarPorId(id);
        if (existente != null) {
            Medico atualizado = new Medico(
                    id,
                    dados.getNome(),
                    dados.getEspecialidade(),
                    dados.getEmail(),
                    dados.getSenha()
            );
            repository.atualizar(atualizado);
        }
    }

    public Medico buscarMedico(int id) {
        return repository.buscarPorId(id);
    }

    public Medico buscarPorEmail(String email) {
        return repository.buscarPorEmail(email);
    }

    public List<Medico> listarMedicos() {
        return repository.listarTodos();
    }
}