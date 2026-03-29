package com.ana.agendamento.repository;

import com.ana.agendamento.model.Paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private final String FILE_PATH = "pacientes.txt";

    public void salvar(Paciente p) {
        List<Paciente> pacientes = listarTodos();
        pacientes.add(p);
        sobrescrever(pacientes);
    }

    public Paciente buscarPorId(int id) {
        return listarTodos().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Paciente buscarPorEmail(String email) {
        return listarTodos().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElse(null);
    }

    public Paciente buscarPorCpf(String cpf) {
        return listarTodos().stream()
                .filter(p -> p.getCpf().equals(cpf.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                if (d.length >= 6) {
                    lista.add(new Paciente(
                            Integer.parseInt(d[0]),
                            d[1],
                            d[2],
                            d[3],
                            d[4],
                            d[5]
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler pacientes.");
        }

        return lista;
    }

    public void atualizar(Paciente paciente) {
        List<Paciente> pacientes = listarTodos();
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == paciente.getId()) {
                pacientes.set(i, paciente);
                break;
            }
        }
        sobrescrever(pacientes);
    }

    public void deletar(int id) {
        List<Paciente> pacientes = listarTodos();
        pacientes.removeIf(p -> p.getId() == id);
        sobrescrever(pacientes);
    }

    private void sobrescrever(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Paciente p : pacientes) {
                bw.write(
                        p.getId() + ";" +
                        p.getNome() + ";" +
                        p.getCpf() + ";" +
                        p.getTelefone() + ";" +
                        p.getEmail() + ";" +
                        p.getSenha()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pacientes.");
        }
    }

    public int gerarNovoId() {
        return listarTodos().stream()
                .mapToInt(Paciente::getId)
                .max()
                .orElse(0) + 1;
    }
}