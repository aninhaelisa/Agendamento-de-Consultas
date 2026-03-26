package com.ana.agendamento.repository;

import com.ana.agendamento.model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private final String FILE_PATH = "pacientes.txt";

    public void salvar(Paciente paciente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(paciente.getNome() + ";" + paciente.getCpf() + ";" + paciente.getLogin() + ";123");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar paciente.");
        }
    }

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                lista.add(new Paciente(dados[0], dados[1], dados[2], dados[3]));
            }
        } catch (IOException e) {  }
        return lista;
    }

    public Paciente buscarPorCpf(String cpf) {
        return listarTodos().stream().filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
    }
}