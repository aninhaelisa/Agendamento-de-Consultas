package com.ana.agendamento.service;

import com.ana.agendamento.factory.ConsultaFactory;
import com.ana.agendamento.model.Agenda;
import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.observer.Notificador;
import com.ana.agendamento.repository.ConsultaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoService {
    private ConsultaRepository consultaRepository;
    private Agenda agenda;
    private Notificador notificador;

    public AgendamentoService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
        this.agenda = new Agenda();
        this.notificador = new Notificador();

        for (Consulta c : consultaRepository.listarTodos()) {
            agenda.adicionarConsulta(c);
        }
    }

    public boolean verificarDisponibilidade(Medico medico, LocalDateTime dataHora) {
        List<Consulta> consultas = consultaRepository.buscarPorMedico(medico.getId());

        for (Consulta c : consultas) {
            if (c.getDataHora().equals(dataHora) && !c.getStatus().equalsIgnoreCase("CANCELADA")) {
                return false;
            }
        }

        return true;
    }

    public Consulta agendarConsulta(Paciente paciente, Medico medico, LocalDateTime dataHora) {
        if (!verificarDisponibilidade(medico, dataHora)) {
            return null;
        }

        int novoId = consultaRepository.gerarNovoId();
        Consulta consulta = ConsultaFactory.criarConsulta(novoId, paciente, medico, dataHora);

        consultaRepository.salvar(consulta);
        agenda.adicionarConsulta(consulta);
        paciente.adicionarConsulta(consulta);
        medico.adicionarConsulta(consulta);

        notificador.notificar(paciente, "Sua consulta foi agendada para " + dataHora);

        return consulta;
    }

    public void cancelarConsulta(Consulta consulta) {
        consulta.cancelar();
        consultaRepository.atualizar(consulta);
        notificador.notificar(consulta.getPaciente(), "Sua consulta foi cancelada.");
    }

    public boolean reagendarConsulta(Consulta consulta, LocalDateTime novaData) {
        if (!verificarDisponibilidade(consulta.getMedico(), novaData)) {
            return false;
        }

        consulta.reagendar(novaData);
        consultaRepository.atualizar(consulta);
        notificador.notificar(consulta.getPaciente(), "Sua consulta foi reagendada para " + novaData);
        return true;
    }

    public List<LocalDateTime> gerarHorariosDisponiveis(Medico medico, LocalDate data) {
        List<LocalDateTime> horarios = new ArrayList<>();

        for (int hora = 8; hora <= 17; hora++) {
            LocalDateTime horario = data.atTime(hora, 0);
            if (verificarDisponibilidade(medico, horario)) {
                horarios.add(horario);
            }
        }

        return horarios;
    }

    public List<Consulta> listarConsultasPorMedico(int idMedico) {
        return consultaRepository.buscarPorMedico(idMedico);
    }

    public List<Consulta> listarConsultasPorPaciente(int idPaciente) {
        return consultaRepository.buscarPorPaciente(idPaciente);
    }

    public Consulta buscarConsultaPorId(int id) {
        return consultaRepository.buscarPorId(id);
    }
}