package com.ana.agendamento.facade;

import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.factory.ConsultaFactory;
import java.time.LocalDateTime;

public class SistemaFacade {
   
    public void agendarConsulta(Paciente paciente, Medico medico, LocalDateTime data) {
       
        var novaConsulta = ConsultaFactory.criarConsulta(paciente, medico, data);
        
        System.out.println("\n[FACADE] Processando novo agendamento...");
        System.out.println("[SUCESSO] " + novaConsulta);
    }

    public void cancelarConsulta(int idConsulta) {
        System.out.println("\n[FACADE] Cancelando consulta ID: " + idConsulta);
        System.out.println("[SUCESSO] Agendamento removido do sistema.");
    }
}