package com.ana.agendamento.facade;

import com.ana.agendamento.model.*;
import com.ana.agendamento.factory.ConsultaFactory;
import com.ana.agendamento.repository.*;
import com.ana.agendamento.service.*;
import java.time.LocalDateTime;

public class SistemaFacade {
    private AgendamentoService agendamentoService;
    private AuthService authService;
    private PacienteService pacienteService;
    private MedicoService medicoService;

    public SistemaFacade() {
        PacienteRepository pacienteRepo = new PacienteRepository();
        MedicoRepository medicoRepo = new MedicoRepository();
        ConsultaRepository consultaRepo = new ConsultaRepository();

        this.agendamentoService = new AgendamentoService(consultaRepo);
        this.authService = new AuthService();
        this.pacienteService = new PacienteService(pacienteRepo);
        this.medicoService = new MedicoService(medicoRepo);
    }

    public void cadastrarPaciente(String nome, String cpf, String login, String senha) {
        Paciente p = new Paciente(nome, cpf, login, senha);
        pacienteService.cadastrarPaciente(p);
        System.out.println("[SISTEMA] Paciente cadastrado: " + nome);
    }

    public void cadastrarMedico(String nome, String especialidade, String login, String senha) {
        Medico m = new Medico(nome, especialidade, login, senha);
        medicoService.cadastrarMedico(m);
        System.out.println("[SISTEMA] Médico cadastrado: " + nome);
    }

    public void agendarConsulta(Paciente paciente, Medico medico, LocalDateTime data) {
        if (agendamentoService.verificarDisponibilidade(data)) {
            Consulta novaConsulta = ConsultaFactory.criarConsulta(paciente, medico, data);
            agendamentoService.agendar(novaConsulta);
            System.out.println("[SUCESSO] Agendamento realizado: " + novaConsulta);
        }
    }

    public void cancelarConsulta(int id) {
        System.out.println("[FACADE] Consulta ID " + id + " cancelada com sucesso.");
    }

    public Paciente obterPaciente(String cpf) {
        return pacienteService.buscarPorCpf(cpf);
    }
}