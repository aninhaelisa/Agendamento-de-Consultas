package com.ana.agendamento.repository;

import com.ana.agendamento.model.Medico;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {
    private final String FILE_PATH = "medicos.txt";

    public void salvar(Medico medico) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(
                medico.getNome().trim() + ";" +
                medico.getEspecialidade().trim() + ";" +
                medico.getLogin().trim() + ";" +
                medico.getSenha().trim()
            );
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar médico.");
        }
    }

    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                if (dados.length >= 4) {
                    lista.add(new Medico(
                        dados[0].trim(),
                        dados[1].trim(),
                        dados[2].trim(),
                        dados[3].trim()
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler médicos.");
        }

        return lista;
    }
}