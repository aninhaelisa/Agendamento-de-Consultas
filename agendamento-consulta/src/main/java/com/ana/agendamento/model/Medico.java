package com.ana.agendamento.model;

public class Medico extends Usuario {
    private String nome;
    private String especialidade;

    public Medico(String nome, String especialidade, String login, String senha) {
        super(login, senha);
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}