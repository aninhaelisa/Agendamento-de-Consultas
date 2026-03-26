package com.ana.agendamento;

import com.ana.agendamento.facade.SistemaFacade;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;
import java.time.LocalDateTime;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        SistemaFacade sistema = new SistemaFacade();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 6) {
            System.out.println("\n--- SISTEMA UNIPAR: AGENDAMENTO ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Médico");
            System.out.println("3. Agendar Consulta");
            System.out.println("4. Cancelar Consulta");
            System.out.println("5. Login (Simulação)");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Paciente: ");
                    String nomeP = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    sistema.cadastrarPaciente(nomeP, cpf, "user." + nomeP.toLowerCase(), "123");
                    break;

                case 2:
                    System.out.print("Nome do Médico: ");
                    String nomeM = scanner.nextLine();
                    System.out.print("Especialidade: ");
                    String esp = scanner.nextLine();
                    sistema.cadastrarMedico(nomeM, esp, "doc." + nomeM.toLowerCase(), "456");
                    break;

                case 3:
                    System.out.print("CPF do Paciente: ");
                    String buscaCpf = scanner.nextLine();
                    Paciente p = sistema.obterPaciente(buscaCpf);
                    if (p != null) {
                        // Usando um médico genérico para o teste rápido
                        Medico m = new Medico("Dr. Rodrigo", "Geral", "doc", "123");
                        sistema.agendarConsulta(p, m, LocalDateTime.now().plusDays(1));
                    } else {
                        System.out.println("Erro: Paciente não encontrado!");
                    }
                    break;

                case 4:
                    System.out.print("ID da Consulta para cancelar: ");
                    int id = scanner.nextInt();
                    sistema.cancelarConsulta(id);
                    break;

                case 5:
                    System.out.println("Funcionalidade de Login integrada ao AuthService!");
                    break;

                case 6:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}