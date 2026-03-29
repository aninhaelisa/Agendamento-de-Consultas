package com.ana.agendamento;

import com.ana.agendamento.facade.SistemaFacade;
import com.ana.agendamento.model.Consulta;
import com.ana.agendamento.model.Medico;
import com.ana.agendamento.model.Paciente;
import com.ana.agendamento.model.Usuario;

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
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Usuario logado = null;

        while (true) {
            if (logado == null) {
                System.out.println("\n--- SISTEMA DE AGENDAMENTO ---");
                System.out.println("1. Login");
                System.out.println("2. Cadastrar Paciente");
                System.out.println("3. Cadastrar Medico");
                System.out.println("4. Sair");
                System.out.print("Escolha: ");
                int op = Integer.parseInt(sc.nextLine());

                if (op == 1) {
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();

                    logado = sistema.realizarLogin(email, senha);

                    if (logado == null) {
                        System.out.println("Erro: Usuario nao encontrado ou senha incorreta.");
                    } else {
                        System.out.println("Login realizado com sucesso!");
                    }

                } else if (op == 2) {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();

                    sistema.cadastrarPaciente(nome, cpf, telefone, email, senha);
                    System.out.println("Paciente cadastrado com sucesso!");

                } else if (op == 3) {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Especialidade: ");
                    String especialidade = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();

                    sistema.cadastrarMedico(nome, especialidade, email, senha);
                    System.out.println("Medico cadastrado com sucesso!");

                } else if (op == 4) {
                    break;
                }

            } else {
                System.out.println("\nLogado como: " + logado.getEmail());

                if (logado instanceof Paciente) {
                    Paciente pacienteLogado = (Paciente) logado;

                    System.out.println("1. Agendar Consulta");
                    System.out.println("2. Ver Minhas Consultas");
                    System.out.println("3. Alterar Senha");
                    System.out.println("4. Logout");
                    System.out.print("Escolha: ");
                    int opP = Integer.parseInt(sc.nextLine());

                    if (opP == 1) {
                        List<Medico> medicos = sistema.listarMedicos();

                        if (medicos.isEmpty()) {
                            System.out.println("Nao ha medicos cadastrados.");
                        } else {
                            System.out.println("\n--- MEDICOS DISPONIVEIS ---");
                            for (int i = 0; i < medicos.size(); i++) {
                                System.out.println((i + 1) + ". " + medicos.get(i).getNome() + " ("
                                        + medicos.get(i).getEspecialidade() + ")");
                            }

                            System.out.print("Selecione o medico: ");
                            int sel = Integer.parseInt(sc.nextLine()) - 1;

                            if (sel >= 0 && sel < medicos.size()) {
                                Medico medicoSelecionado = medicos.get(sel);

                                try {
                                    System.out.print("Digite a data da consulta (dd/MM/yyyy): ");
                                    String dataStr = sc.nextLine();
                                    LocalDate data = LocalDate.parse(dataStr, fmtData);

                                    List<LocalDateTime> horarios = sistema.horariosDisponiveis(medicoSelecionado, data);

                                    if (horarios.isEmpty()) {
                                        System.out.println("Nao ha horarios disponiveis para este medico nesta data.");
                                    } else {
                                        System.out.println("\n--- HORARIOS DISPONIVEIS ---");
                                        for (int i = 0; i < horarios.size(); i++) {
                                            System.out.println((i + 1) + ". " + horarios.get(i).format(fmt));
                                        }

                                        System.out.print("Selecione um horario: ");
                                        int horarioEscolhido = Integer.parseInt(sc.nextLine()) - 1;

                                        if (horarioEscolhido >= 0 && horarioEscolhido < horarios.size()) {
                                            LocalDateTime dtSelecionado = horarios.get(horarioEscolhido);
                                            boolean sucesso = sistema.agendarConsulta(pacienteLogado, medicoSelecionado,
                                                    dtSelecionado);

                                            if (sucesso) {
                                                System.out.println("Agendamento realizado com sucesso!");
                                            } else {
                                                System.out.println("Erro ao agendar consulta.");
                                            }
                                        } else {
                                            System.out.println("Horario invalido.");
                                        }
                                    }

                                } catch (Exception e) {
                                    System.out.println("Erro no formato da data.");
                                }

                            } else {
                                System.out.println("Medico invalido.");
                            }
                        }

                    } else if (opP == 2) {
                        List<Consulta> consultas = sistema.verConsultasPaciente(pacienteLogado);

                        System.out.println("\n--- MINHAS CONSULTAS ---");
                        if (consultas.isEmpty()) {
                            System.out.println("Voce nao possui consultas.");
                        } else {
                            for (Consulta c : consultas) {
                                System.out.println(
                                        "ID: " + c.getId() +
                                                " | Medico: " + c.getMedico().getNome() +
                                                " | Data: " + c.getDataHora().format(fmt) +
                                                " | Status: " + c.getStatus());
                            }
                        }

                    } else if (opP == 3) {
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = sc.nextLine();
                        pacienteLogado.alterarSenha(novaSenha);
                        sistema.atualizarPaciente(
                                pacienteLogado.getId(),
                                pacienteLogado.getNome(),
                                pacienteLogado.getCpf(),
                                pacienteLogado.getTelefone(),
                                pacienteLogado.getEmail(),
                                pacienteLogado.getSenha());
                        System.out.println("Senha alterada com sucesso!");

                    } else if (opP == 4) {
                        sistema.logout();
                        logado = null;
                    }

                } else if (logado instanceof Medico) {
                    Medico medicoLogado = (Medico) logado;

                    System.out.println("1. Ver Minha Agenda");
                    System.out.println("2. Cancelar Consulta");
                    System.out.println("3. Reagendar Consulta");
                    System.out.println("4. Alterar Senha");
                    System.out.println("5. Logout");
                    System.out.print("Escolha: ");
                    int opM = Integer.parseInt(sc.nextLine());

                    List<Consulta> agenda = sistema.verAgenda(medicoLogado.getNome());

                    if (opM == 1) {
                        System.out.println("\n--- MINHA AGENDA ---");

                        if (agenda.isEmpty()) {
                            System.out.println("Sua agenda esta vazia.");
                        } else {
                            for (Consulta c : agenda) {
                                System.out.println(
                                        "ID: " + c.getId() +
                                                " | Paciente: " + c.getPaciente().getNome() +
                                                " | Data: " + c.getDataHora().format(fmt) +
                                                " | Status: " + c.getStatus());
                            }
                        }

                    } else if (opM == 2) {
                        System.out.println("\n--- CANCELAR CONSULTA ---");

                        if (agenda.isEmpty()) {
                            System.out.println("Nao ha consultas para cancelar.");
                        } else {
                            for (Consulta c : agenda) {
                                System.out.println(
                                        "ID: " + c.getId() +
                                                " | Paciente: " + c.getPaciente().getNome() +
                                                " | Data: " + c.getDataHora().format(fmt) +
                                                " | Status: " + c.getStatus());
                            }

                            System.out.print("Digite o ID da consulta para cancelar: ");
                            int idConsulta = Integer.parseInt(sc.nextLine());

                            boolean sucesso = sistema.cancelarConsulta(idConsulta);

                            if (sucesso) {
                                System.out.println("Consulta cancelada com sucesso!");
                            } else {
                                System.out.println("Consulta nao encontrada.");
                            }
                        }

                    } else if (opM == 3) {
                        System.out.println("\n--- REAGENDAR CONSULTA ---");

                        if (agenda.isEmpty()) {
                            System.out.println("Nao ha consultas para reagendar.");
                        } else {
                            for (Consulta c : agenda) {
                                System.out.println(
                                        "ID: " + c.getId() +
                                                " | Paciente: " + c.getPaciente().getNome() +
                                                " | Data: " + c.getDataHora().format(fmt) +
                                                " | Status: " + c.getStatus());
                            }

                            System.out.print("Digite o ID da consulta para reagendar: ");
                            int idConsulta = Integer.parseInt(sc.nextLine());

                            Consulta consultaSelecionada = null;
                            for (Consulta c : agenda) {
                                if (c.getId() == idConsulta) {
                                    consultaSelecionada = c;
                                    break;
                                }
                            }

                            if (consultaSelecionada != null) {
                                try {
                                    System.out.print("Digite a nova data (dd/MM/yyyy): ");
                                    String novaDataStr = sc.nextLine();
                                    LocalDate novaData = LocalDate.parse(novaDataStr, fmtData);

                                    List<LocalDateTime> horarios = sistema.horariosDisponiveis(medicoLogado, novaData);

                                    if (horarios.isEmpty()) {
                                        System.out.println("Nao ha horarios disponiveis nessa data.");
                                    } else {
                                        System.out.println("\n--- NOVOS HORARIOS DISPONIVEIS ---");
                                        for (int i = 0; i < horarios.size(); i++) {
                                            System.out.println((i + 1) + ". " + horarios.get(i).format(fmt));
                                        }

                                        System.out.print("Selecione o novo horario: ");
                                        int horarioEscolhido = Integer.parseInt(sc.nextLine()) - 1;

                                        if (horarioEscolhido >= 0 && horarioEscolhido < horarios.size()) {
                                            LocalDateTime novaDataHora = horarios.get(horarioEscolhido);
                                            boolean sucesso = sistema.reagendarConsulta(consultaSelecionada,
                                                    novaDataHora);

                                            if (sucesso) {
                                                System.out.println("Consulta reagendada com sucesso!");
                                            } else {
                                                System.out.println("Erro: horario indisponivel.");
                                            }
                                        } else {
                                            System.out.println("Horario invalido.");
                                        }
                                    }

                                } catch (Exception e) {
                                    System.out.println("Erro no formato da data.");
                                }

                            } else {
                                System.out.println("Consulta invalida.");
                            }
                        }

                    } else if (opM == 4) {
                        System.out.print("Digite a nova senha: ");
                        String novaSenha = sc.nextLine();
                        medicoLogado.alterarSenha(novaSenha);
                        sistema.atualizarMedico(
                                medicoLogado.getId(),
                                medicoLogado.getNome(),
                                medicoLogado.getEspecialidade(),
                                medicoLogado.getEmail(),
                                medicoLogado.getSenha());
                        System.out.println("Senha alterada com sucesso!");

                    } else if (opM == 5) {
                        sistema.logout();
                        logado = null;
                    }
                }
            }
        }

        sc.close();
    }
}