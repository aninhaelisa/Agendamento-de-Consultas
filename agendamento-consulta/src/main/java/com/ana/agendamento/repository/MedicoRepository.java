package com.ana.agendamento.repository;

import com.ana.agendamento.model.Medico;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {
    private final String FILE_PATH = "medicos.txt";

    public void salvar(Medico medico) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(medico.getNome() + ";" + medico.getEspecialidade() + ";" + medico.getLogin() + ";" + "456");
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
                    lista.add(new Medico(dados[0], dados[1], dados[2], dados[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler médicos.");
        }
        return lista;
    }
}