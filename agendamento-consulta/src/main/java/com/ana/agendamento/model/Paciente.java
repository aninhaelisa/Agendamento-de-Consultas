package com.ana.agendamento.model;

public class Paciente extends Usuario {
    private String nome;
    private String cpf;

    public Paciente(String nome, String cpf, String login, String senha) {
        super(login, senha);
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
}