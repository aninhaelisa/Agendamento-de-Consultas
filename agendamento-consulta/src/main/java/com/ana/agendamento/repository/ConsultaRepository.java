package com.ana.agendamento.repository;

import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    private final String FILE_PATH = "consultas.txt";
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaRepository(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public void salvar(Consulta c) {
        List<Consulta> consultas = listarTodos();
        consultas.add(c);
        sobrescrever(consultas);
    }

    public Consulta buscarPorId(int id) {
        return listarTodos().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Consulta> listarTodos() {
        List<Consulta> lista = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                if (d.length >= 5) {
                    int id = Integer.parseInt(d[0]);
                    int idPaciente = Integer.parseInt(d[1]);
                    int idMedico = Integer.parseInt(d[2]);
                    LocalDateTime dataHora = LocalDateTime.parse(d[3]);
                    String status = d[4];

                    Paciente paciente = pacienteRepository.buscarPorId(idPaciente);
                    Medico medico = medicoRepository.buscarPorId(idMedico);

                    if (paciente != null && medico != null) {
                        Consulta consulta = new Consulta(id, paciente, medico, dataHora);
                        consulta.setStatus(status);
                        lista.add(consulta);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler consultas.");
        }

        return lista;
    }

    public void atualizar(Consulta consulta) {
        List<Consulta> consultas = listarTodos();
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getId() == consulta.getId()) {
                consultas.set(i, consulta);
                break;
            }
        }
        sobrescrever(consultas);
    }

    public void deletar(int id) {
        List<Consulta> consultas = listarTodos();
        consultas.removeIf(c -> c.getId() == id);
        sobrescrever(consultas);
    }

    public List<Consulta> buscarPorMedico(int idMedico) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : listarTodos()) {
            if (c.getMedico().getId() == idMedico) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Consulta> buscarPorPaciente(int idPaciente) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : listarTodos()) {
            if (c.getPaciente().getId() == idPaciente) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    private void sobrescrever(List<Consulta> consultas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Consulta c : consultas) {
                bw.write(
                        c.getId() + ";" +
                        c.getPaciente().getId() + ";" +
                        c.getMedico().getId() + ";" +
                        c.getDataHora() + ";" +
                        c.getStatus()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar consultas.");
        }
    }

    public int gerarNovoId() {
        return listarTodos().stream()
                .mapToInt(Consulta::getId)
                .max()
                .orElse(0) + 1;
    }
}