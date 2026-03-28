package com.ana.agendamento;

import com.ana.agendamento.facade.SistemaFacade;
import com.ana.agendamento.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        SistemaFacade sistema = new SistemaFacade();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Usuario logado = null;

        while (true) {
            if (logado == null) {
                System.out.println("\n--- SISTEMA DE AGENDAMENTO ---");
                System.out.println("1. Login");
                System.out.println("2. Cadastrar Paciente");
                System.out.println("3. Cadastrar Medico");
                System.out.println("4. Sair");
                System.out.print("Escolha: ");

                int op = sc.nextInt();
                sc.nextLine();

                if (op == 1) {
                    System.out.print("Login: ");
                    String l = sc.nextLine();
                    System.out.print("Senha: ");
                    String s = sc.nextLine();
                    logado = sistema.realizarLogin(l, s);
                    if (logado == null)
                        System.out.println("Erro: Usuario nao encontrado.");
                } else if (op == 2) {
                    System.out.print("Nome: ");
                    String n = sc.nextLine();
                    System.out.print("CPF: ");
                    String c = sc.nextLine();
                    System.out.print("Login: ");
                    String lg = sc.nextLine();
                    System.out.print("Senha: ");
                    String sn = sc.nextLine();
                    sistema.cadastrarPaciente(n, c, lg, sn);
                    System.out.println("Paciente cadastrado com sucesso!");
                } else if (op == 3) {
                    System.out.print("Nome: ");
                    String n = sc.nextLine();
                    System.out.print("Especialidade: ");
                    String e = sc.nextLine();
                    System.out.print("Login: ");
                    String lg = sc.nextLine();
                    System.out.print("Senha: ");
                    String sn = sc.nextLine();
                    sistema.cadastrarMedico(n, e, lg, sn);
                    System.out.println("Medico cadastrado com sucesso!");
                } else if (op == 4) {
                    break;
                }
            } else {
                System.out.println("\nLogado como: " + logado.getLogin());

                if (logado instanceof Paciente) {
                    System.out.println("1. Agendar Consulta");
                    System.out.println("2. Logout");
                    System.out.print("Escolha: ");
                    int opP = sc.nextInt();
                    sc.nextLine();

                    if (opP == 1) {
                        List<Medico> meds = sistema.listarMedicos();

                        if (meds.isEmpty()) {
                            System.out.println("Nao ha medicos cadastrados.");
                        } else {
                            for (int i = 0; i < meds.size(); i++) {
                                System.out.println((i + 1) + ". " + meds.get(i).getNome() + " ("
                                        + meds.get(i).getEspecialidade() + ")");
                            }

                            System.out.print("Selecione o medico: ");
                            int sel = sc.nextInt() - 1;
                            sc.nextLine();

                            if (sel >= 0 && sel < meds.size()) {
                                Medico medicoSelecionado = meds.get(sel);

                                System.out.print("Digite a data da consulta (dd/MM/yyyy): ");
                                try {
                                    DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    String dataStr = sc.nextLine();
                                    LocalDate data = LocalDate.parse(dataStr, fmtData);

                                    List<LocalDateTime> horarios = sistema.horariosDisponiveis(medicoSelecionado, data);

                                    if (horarios.isEmpty()) {
                                        System.out.println("Nao ha horarios disponiveis para este medico nesta data.");
                                    } else {
                                        System.out.println("\nHorarios disponiveis:");
                                        for (int i = 0; i < horarios.size(); i++) {
                                            System.out.println((i + 1) + ". " + horarios.get(i).format(fmt));
                                        }

                                        System.out.print("Selecione um horario: ");
                                        int horarioEscolhido = sc.nextInt() - 1;
                                        sc.nextLine();

                                        if (horarioEscolhido >= 0 && horarioEscolhido < horarios.size()) {
                                            LocalDateTime dtSelecionado = horarios.get(horarioEscolhido);

                                            boolean sucesso = sistema.agendar((Paciente) logado, medicoSelecionado, dtSelecionado);

                                            if (sucesso) {
                                                System.out.println("Agendamento realizado!");
                                            } else {
                                                System.out.println("Erro: este medico ja possui consulta nesse horario.");
                                            }
                                        } else {
                                            System.out.println("Horario invalido.");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Erro no formato da data.");
                                }
                            }
                        }
                    } else {
                        logado = null;
                    }
                } else if (logado instanceof Medico) {
                    System.out.println("1. Ver Minha Agenda");
                    System.out.println("2. Logout");
                    System.out.print("Escolha: ");
                    int opM = sc.nextInt();
                    sc.nextLine();

                    if (opM == 1) {
                        List<Consulta> agenda = sistema.verAgenda(((Medico) logado).getNome());
                        if (agenda.isEmpty()) {
                            System.out.println("Sua agenda esta vazia.");
                        } else {
                            agenda.forEach(System.out::println);
                        }
                    } else {
                        logado = null;
                    }
                }
            }
        }
    }
}