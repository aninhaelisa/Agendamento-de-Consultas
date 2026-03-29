package com.ana.agendamento.facade;

import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.model.Usuario;
import com.ana.agendamento.repository.ConsultaRepository;
import com.ana.agendamento.repository.MedicoRepository;
import com.ana.agendamento.repository.PacienteRepository;
import com.ana.agendamento.service.AgendamentoService;
import com.ana.agendamento.service.AuthService;
import com.ana.agendamento.service.MedicoService;
import com.ana.agendamento.service.PacienteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SistemaFacade {
    private PacienteRepository pacienteRepository;
    private MedicoRepository medicoRepository;
    private ConsultaRepository consultaRepository;

    private PacienteService pacienteService;
    private MedicoService medicoService;
    private AuthService authService;
    private AgendamentoService agendamentoService;

    public SistemaFacade() {
        pacienteRepository = new PacienteRepository();
        medicoRepository = new MedicoRepository();
        consultaRepository = new ConsultaRepository(pacienteRepository, medicoRepository);

        pacienteService = new PacienteService(pacienteRepository);
        medicoService = new MedicoService(medicoRepository);
        authService = new AuthService();
        agendamentoService = new AgendamentoService(consultaRepository);
    }

    public void cadastrarPaciente(String nome, String cpf, String telefone, String email, String senha) {
        int id = pacienteRepository.gerarNovoId();
        Paciente paciente = new Paciente(id, nome, cpf, telefone, email, senha);
        pacienteService.cadastrarPaciente(paciente);
    }

    public void atualizarPaciente(int id, String nome, String cpf, String telefone, String email, String senha) {
        Paciente paciente = new Paciente(id, nome, cpf, telefone, email, senha);
        pacienteService.atualizarPaciente(id, paciente);
    }

    public Paciente buscarPaciente(int id) {
        return pacienteService.buscarPaciente(id);
    }

    public void cadastrarMedico(String nome, String especialidade, String email, String senha) {
        int id = medicoRepository.gerarNovoId();
        Medico medico = new Medico(id, nome, especialidade, email, senha);
        medicoService.cadastrarMedico(medico);
    }

    public void atualizarMedico(int id, String nome, String especialidade, String email, String senha) {
        Medico medico = new Medico(id, nome, especialidade, email, senha);
        medicoService.atualizarMedico(id, medico);
    }

    public Medico buscarMedico(int id) {
        return medicoService.buscarMedico(id);
    }

    public List<Medico> listarMedicos() {
        return medicoService.listarMedicos();
    }

    public Usuario realizarLogin(String email, String senha) {
        Paciente paciente = pacienteService.buscarPorEmail(email);
        if (paciente != null) {
            return authService.login(email, senha, paciente);
        }

        Medico medico = medicoService.buscarPorEmail(email);
        if (medico != null) {
            return authService.login(email, senha, medico);
        }

        return null;
    }

    public void logout() {
        authService.logout();
    }

    public boolean validarUsuario(Usuario usuario) {
        return authService.validarUsuario(usuario);
    }

    public boolean agendarConsulta(Paciente paciente, Medico medico, LocalDateTime dataHora) {
        return agendamentoService.agendarConsulta(paciente, medico, dataHora) != null;
    }

    public void agendarConsulta(Object dados) {
    }

    public boolean cancelarConsulta(int idConsulta) {
        Consulta consulta = agendamentoService.buscarConsultaPorId(idConsulta);
        if (consulta != null) {
            agendamentoService.cancelarConsulta(consulta);
            return true;
        }
        return false;
    }

    public boolean cancelarConsulta(Consulta consulta) {
        if (consulta != null) {
            agendamentoService.cancelarConsulta(consulta);
            return true;
        }
        return false;
    }

    public boolean reagendarConsulta(Consulta consulta, LocalDateTime novaData) {
        return agendamentoService.reagendarConsulta(consulta, novaData);
    }

    public List<LocalDateTime> horariosDisponiveis(Medico medico, LocalDate data) {
        return agendamentoService.gerarHorariosDisponiveis(medico, data);
    }

    public List<Consulta> verAgenda(String nomeMedico) {
        for (Medico medico : medicoService.listarMedicos()) {
            if (medico.getNome().equalsIgnoreCase(nomeMedico.trim())) {
                return agendamentoService.listarConsultasPorMedico(medico.getId());
            }
        }
        return List.of();
    }

    public List<Consulta> verConsultasPaciente(Paciente paciente) {
        return agendamentoService.listarConsultasPorPaciente(paciente.getId());
    }
}