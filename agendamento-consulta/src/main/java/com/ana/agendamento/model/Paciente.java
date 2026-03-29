package com.ana.agendamento.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario {
    private String nome;
    private String cpf;
    private String telefone;
    private List<Consulta> consultas;

    public Paciente(int id, String nome, String cpf, String telefone, String email, String senha) {
        super(id, email, senha, "PACIENTE");
        this.nome = nome.trim();
        this.cpf = cpf.trim();
        this.telefone = telefone.trim();
        this.consultas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void atualizarDados(String nome, String telefone) {
        this.nome = nome.trim();
        this.telefone = telefone.trim();
    }

    public List<Consulta> visualizarConsultas() {
        return new ArrayList<>(consultas);
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }
}