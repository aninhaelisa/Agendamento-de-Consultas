package com.ana.agendamento.model;

import java.util.ArrayList;
import java.util.List;

public class Medico extends Usuario {
    private String nome;
    private String especialidade;
    private List<Consulta> consultas;

    public Medico(int id, String nome, String especialidade, String email, String senha) {
        super(id, email, senha, "MEDICO");
        this.nome = nome.trim();
        this.especialidade = especialidade.trim();
        this.consultas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        consultas.remove(consulta);
    }

    public void atualizarDados(String nome, String especialidade) {
        this.nome = nome.trim();
        this.especialidade = especialidade.trim();
    }
}