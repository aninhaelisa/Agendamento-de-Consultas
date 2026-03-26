package com.ana.agendamento.model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Consulta> consultas = new ArrayList<>();

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
    }

    public List<Consulta> getConsultas() { return consultas; }
}