package com.ana.agendamento.repository;

import com.ana.agendamento.model.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private List<Paciente> pacientes = new ArrayList<>();

    public void salvar(Paciente paciente) {
        pacientes.add(paciente);
    }

    public Paciente buscarPorCpf(String cpf) {
        return pacientes.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public List<Paciente> listarTodos() {
        return pacientes;
    }
}