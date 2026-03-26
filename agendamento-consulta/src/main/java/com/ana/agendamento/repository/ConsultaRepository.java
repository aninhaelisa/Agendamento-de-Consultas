package com.ana.agendamento.repository;

import com.ana.agendamento.model.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    private final String FILE_PATH = "consultas.txt";

    public void salvar(Consulta consulta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(consulta.getPaciente().getNome() + ";" + 
                     consulta.getMedico().getNome() + ";" + 
                     consulta.getDataHora().toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao gravar consulta.");
        }
    }

    public List<Consulta> listarTodas() {
        List<Consulta> lista = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Paciente p = new Paciente(dados[0], "", "", "");
                Medico m = new Medico(dados[1], "", "", "");
                LocalDateTime dt = LocalDateTime.parse(dados[2]);
                lista.add(new Consulta(p, m, dt));
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler consultas.");
        }
        return lista;
    }
}