package com.ana.agendamento.repository;

import com.ana.agendamento.model.Consulta;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    private List<Consulta> consultas = new ArrayList<>();

    public void salvar(Consulta consulta) {
        consultas.add(consulta);
    }

    public List<Consulta> listarTodas() {
        return consultas;
    }

    public void deletar(Consulta consulta) {
        consultas.remove(consulta);
    }
}