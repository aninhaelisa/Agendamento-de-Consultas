package com.ana.agendamento.repository;

import com.ana.agendamento.model.Medico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {
    private final String FILE_PATH = "medicos.txt";

    public void salvar(Medico m) {
        List<Medico> medicos = listarTodos();
        medicos.add(m);
        sobrescrever(medicos);
    }

    public Medico buscarPorId(int id) {
        return listarTodos().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Medico buscarPorEmail(String email) {
        return listarTodos().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] d = linha.split(";");
                if (d.length >= 5) {
                    lista.add(new Medico(
                            Integer.parseInt(d[0]),
                            d[1],
                            d[2],
                            d[3],
                            d[4]
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler médicos.");
        }

        return lista;
    }

    public void atualizar(Medico medico) {
        List<Medico> medicos = listarTodos();
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medico.getId()) {
                medicos.set(i, medico);
                break;
            }
        }
        sobrescrever(medicos);
    }

    public void deletar(int id) {
        List<Medico> medicos = listarTodos();
        medicos.removeIf(m -> m.getId() == id);
        sobrescrever(medicos);
    }

    private void sobrescrever(List<Medico> medicos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Medico m : medicos) {
                bw.write(
                        m.getId() + ";" +
                        m.getNome() + ";" +
                        m.getEspecialidade() + ";" +
                        m.getEmail() + ";" +
                        m.getSenha()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar médicos.");
        }
    }

    public int gerarNovoId() {
        return listarTodos().stream()
                .mapToInt(Medico::getId)
                .max()
                .orElse(0) + 1;
    }
}