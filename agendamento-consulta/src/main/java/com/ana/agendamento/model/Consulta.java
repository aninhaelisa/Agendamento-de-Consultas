package com.ana.agendamento.model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String status;

    public Consulta(int id, Paciente paciente, Medico medico, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = "AGENDADA";
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void confirmar() {
        this.status = "CONFIRMADA";
    }

    public void cancelar() {
        this.status = "CANCELADA";
    }

    public void reagendar(LocalDateTime novaData) {
        this.dataHora = novaData;
        this.status = "REAGENDADA";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}