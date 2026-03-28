package com.ana.agendamento.facade;

import com.ana.agendamento.model.*;
import com.ana.agendamento.repository.*;
import com.ana.agendamento.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaFacade {

    private PacienteRepository pacienteRepo = new PacienteRepository();
    private MedicoRepository medicoRepo = new MedicoRepository();
    private ConsultaRepository consultaRepo = new ConsultaRepository();
    private AgendamentoService agendamentoService = new AgendamentoService(consultaRepo);

    public void cadastrarPaciente(String n, String c, String l, String s) {
        pacienteRepo.salvar(new Paciente(n, c, l, s));
    }

    public void cadastrarMedico(String n, String e, String l, String s) {
        medicoRepo.salvar(new Medico(n, e, l, s));
    }

    public Usuario realizarLogin(String login, String senha) {
        for (Paciente p : pacienteRepo.listarTodos()) {
            if (p.getLogin().equals(login) && p.validarLogin(login, senha)) return p;
        }

        for (Medico m : medicoRepo.listarTodos()) {
            if (m.getLogin().equals(login) && m.validarLogin(login, senha)) return m;
        }

        return null;
    }

    public boolean agendar(Paciente p, Medico m, LocalDateTime dt) {
        if (agendamentoService.verificarDisponibilidade(m.getNome(), dt)) {
            agendamentoService.agendar(new Consulta(p, m, dt));
            return true;
        }
        return false;
    }

    public List<Consulta> verAgenda(String nomeMedico) {
        return consultaRepo.listarTodas().stream()
                .filter(c -> c.getMedico().getNome().trim().equalsIgnoreCase(nomeMedico.trim()))
                .collect(Collectors.toList());
    }

    public List<Medico> listarMedicos() {
        return medicoRepo.listarTodos();
    }

    // NOVO MÉTODO AQUI
    public List<LocalDateTime> horariosDisponiveis(Medico medico, LocalDate data) {
        List<LocalDateTime> horarios = new ArrayList<>();

        int[] horas = {8, 9, 10, 11, 14, 15, 16, 17};

        for (int hora : horas) {
            LocalDateTime dt = data.atTime(hora, 0);

            if (agendamentoService.verificarDisponibilidade(medico.getNome(), dt)) {
                horarios.add(dt);
            }
        }

        return horarios;
    }
}