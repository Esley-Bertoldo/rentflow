package aplicacao;

import modelo.*;
import servicos.GeradorRelatorio;
import servicos.interfaces.Repositorio;
import servicos.repositorios.RepositorioClienteMemoria;
import servicos.repositorios.RepositorioVeiculoMemoria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Sistema {

    private Repositorio<Veiculo> repositorioVeiculo = new RepositorioVeiculoMemoria();
    private Repositorio<Cliente> repositorioCliente = new RepositorioClienteMemoria();
    private List<Aluguel> alugueisCadastrados = new ArrayList<>();
    private int contadorAluguel = 1;

    private Scanner sc = new Scanner(System.in);
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void executar() {
        boolean continuar = true;

        while (continuar) {
            menuPrincipal();
            int escolha = 0;
            try {
                escolha = sc.nextInt();
            } catch (InputMismatchException e) {
                escolha = -1;
            }
            sc.nextLine();

            switch (escolha) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    criarAluguel();
                    break;
                case 4:
                    finalizarAluguel();
                    break;
                case 5:
                    cancelarAluguel();
                    break;
                case 6:
                    listarVeiculos();
                    break;
                case 7:
                    listarClientes();
                    break;
                case 8:
                    relatorios();
                    break;
                case 9:
                    continuar = false;
                    System.out.println();
                    System.out.println("Encerrando Programa...");
                    break;
                default:
                    System.out.println("Opção Inválida. Tente novamente.");
            }

            if (continuar) {
                System.out.print("\nPressione ENTER para continuar...");
                sc.nextLine();
            }
        }
    }

    private void menuPrincipal() {
        System.out.println();
        System.out.println("======== SISTEMA DE ALUGUEL DE VEÍCULOS ========");
        System.out.println();
        System.out.println("1 - Cadastrar veículo\n" +
                "2 - Cadastrar cliente\n" +
                "3 - Criar aluguel\n" +
                "4 - Finalizar aluguel\n" +
                "5 - Cancelar aluguel\n" +
                "6 - Listar veículos\n" +
                "7 - Listar clientes\n" +
                "8 - Relatórios\n" +
                "9 - Sair\n");
        System.out.print("Selecione a opção: ");
    }


    private char lerCaractereValido(String mensagem, String validos) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = sc.nextLine().toUpperCase().trim();

                if (entrada.isEmpty()) {
                    System.out.println(" Entrada não pode ser vazia!");
                    continue;
                }

                char primeiro = entrada.charAt(0);
                if (!validos.contains(String.valueOf(primeiro))) {
                    System.out.println(" Digite um dos valores: " + validos);
                    continue;
                }

                return primeiro;
            } catch (Exception e) {
                System.out.println(" Erro ao ler entrada!");
            }
        }
    }

    private int lerIntValido(String mensagem, int minimo, int maximo) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = sc.nextInt();
                sc.nextLine();

                if (valor < minimo || valor > maximo) {
                    System.out.println(" Digite um valor entre " + minimo + " e " + maximo);
                    continue;
                }

                return valor;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println(" Digite apenas números!");
            }
        }
    }

    private double lerDoubleValido(String mensagem, double minimo) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = sc.nextDouble();
                sc.nextLine();

                if (valor < minimo) {
                    System.out.println(" O valor deve ser maior que " + minimo);
                    continue;
                }

                return valor;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println(" Digite um número decimal válido!");
            }
        }
    }

    private LocalDate lerDataValida(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = sc.nextLine().trim();
                return LocalDate.parse(entrada, fmt);
            } catch (DateTimeParseException e) {
                System.out.println(" Data inválida! Use o formato DD/MM/AAAA");
            }
        }
    }

    private boolean isEmailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }


    private void cadastrarVeiculo() {
        System.out.println("-------- CADASTRO DE VEÍCULOS --------");
        System.out.println();

        System.out.print("ID: ");
        String id = sc.nextLine().toUpperCase().trim();

        Veiculo veiculoExistente = repositorioVeiculo.buscarPorId(id);
        if (veiculoExistente != null) {
            System.out.println("\n ERRO: Já existe um veículo com esse ID.");
            return;
        }

        System.out.print("Marca: ");
        String marca = sc.nextLine().trim();
        if (marca.isEmpty()) {
            System.out.println(" Marca não pode ser vazia!");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = sc.nextLine().trim();
        if (modelo.isEmpty()) {
            System.out.println(" Modelo não pode ser vazio!");
            return;
        }

        double preco = lerDoubleValido("Preço por dia (R$): ", 0.01);

        System.out.println("\nCategorias disponíveis:");
        for (Categoria c : Categoria.values()) {
            System.out.println((c.ordinal() + 1) + " - " + c.name());
        }

        int numCategoria = lerIntValido("Selecione a categoria: ", 1, Categoria.values().length);
        Categoria categoriaVeiculo = Categoria.values()[numCategoria - 1];

        char resposta = lerCaractereValido("Disponível para aluguel? [S/N]: ", "SN");
        boolean disponivel = (resposta == 'S');

        Veiculo veiculo = new Veiculo(id, marca, modelo, preco, categoriaVeiculo, disponivel);
        repositorioVeiculo.salvar(veiculo);

        System.out.println("\n Veículo [" + modelo + "] cadastrado com sucesso!");
    }


    private void cadastrarCliente() {
        System.out.println("-------- CADASTRO DE CLIENTES --------");
        System.out.println();

        System.out.print("ID: ");
        String id = sc.nextLine().toUpperCase().trim();

        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println(" Nome não pode ser vazio!");
            return;
        }

        String email = "";
        while (true) {
            System.out.print("E-mail: ");
            email = sc.nextLine().trim();
            if (isEmailValido(email)) {
                break;
            }
            System.out.println(" Email inválido! Use o formato: usuario@dominio.com");
        }

        String cpf = "";
        while (true) {
            System.out.print("CPF (apenas números): ");
            String entrada = sc.nextLine().trim();

            String cpfLimpo = entrada.replaceAll("\\D", "");
            if (cpfLimpo.length() != 11) {
                System.out.println(" CPF deve ter exatamente 11 dígitos!");
                continue;
            }

            cpf = cpfLimpo.substring(0, 3) + "." +
                    cpfLimpo.substring(3, 6) + "." +
                    cpfLimpo.substring(6, 9) + "-" +
                    cpfLimpo.substring(9, 11);

            System.out.println("CPF formatado: " + cpf);
            char conf = lerCaractereValido("Confirmar? [S/N]: ", "SN");

            if (conf == 'S') {
                break;
            }
        }

        Cliente clienteExistente = repositorioCliente.buscarPorId(cpf);
        if (clienteExistente != null) {
            System.out.println("\n ERRO: Já existe cliente com esse CPF!");
            return;
        }

        System.out.println("\nTipo de cliente:");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        int tipo = lerIntValido("Escolha: ", 1, 2);
        TipoCliente tipoCliente = (tipo == 1) ? TipoCliente.PESSOA_FISICA : TipoCliente.PESSOA_JURIDICA;

        Cliente cliente = new Cliente(id, nome, email, cpf, tipoCliente);
        repositorioCliente.salvar(cliente);

        System.out.println("\n✅ Cliente [" + nome + "] cadastrado com sucesso!");
    }


    private void criarAluguel() {
        System.out.println("\n========= CRIAR ALUGUEL =========");

        Cliente cliente = selecionarCliente();
        if (cliente == null) {
            return;
        }

        List<ItemAluguel> itens = selecionarVeiculos();
        if (itens.isEmpty()) {
            System.out.println(" Aluguel precisa de pelo menos 1 veículo!");
            return;
        }

        finalizarNovoAluguel(cliente, itens);
    }

    private Cliente selecionarCliente() {
        System.out.print("CPF do cliente (apenas números): ");
        String cpf = sc.nextLine().trim();

        String cpfLimpo = cpf.replaceAll("\\D", "");
        if (cpfLimpo.length() == 11) {
            cpf = cpfLimpo.substring(0, 3) + "." +
                    cpfLimpo.substring(3, 6) + "." +
                    cpfLimpo.substring(6, 9) + "-" +
                    cpfLimpo.substring(9, 11);
        }

        Cliente cliente = repositorioCliente.buscarPorId(cpf);
        if (cliente == null) {
            System.out.println(" Cliente não encontrado!");
            return null;
        }

        System.out.println("✅ Cliente encontrado: " + cliente.getNome());
        return cliente;
    }

    private List<ItemAluguel> selecionarVeiculos() {
        List<ItemAluguel> itens = new ArrayList<>();
        boolean adicionarMais = true;

        while (adicionarMais) {
            listarVeiculosDisponiveisSilencioso();

            System.out.print("ID do veículo: ");
            String idVeiculo = sc.nextLine().toUpperCase().trim();

            Veiculo veiculo = repositorioVeiculo.buscarPorId(idVeiculo);
            if (veiculo == null) {
                System.out.println(" Veículo não encontrado!");
                continue;
            }

            if (!veiculo.getDisponivel()) {
                System.out.println(" Veículo não está disponível!");
                continue;
            }

            LocalDate dataInicio = lerDataValida("Data início (DD/MM/AAAA): ");
            if (dataInicio == null) return itens;

            LocalDate dataFim = lerDataValida("Data fim (DD/MM/AAAA): ");
            if (dataFim == null) return itens;

            if (dataFim.isBefore(dataInicio) || dataFim.isEqual(dataInicio)) {
                System.out.println(" Data final deve ser após data inicial!");
                continue;
            }

            ItemAluguel item = new ItemAluguel(veiculo, dataInicio, dataFim, veiculo.getPrecoDiario());
            itens.add(item);
            System.out.println(" Veículo adicionado ao aluguel!");

            adicionarMais = lerCaractereValido("Adicionar outro veículo? [S/N]: ", "SN") == 'S';
        }

        return itens;
    }

    private void listarVeiculosDisponiveisSilencioso() {
        System.out.println("\n--- VEÍCULOS DISPONÍVEIS ---");
        List<Veiculo> todos = repositorioVeiculo.listarTodos();
        boolean temDisponivel = false;

        for (Veiculo v : todos) {
            if (v.getDisponivel()) {
                System.out.println(v);
                temDisponivel = true;
            }
        }

        if (!temDisponivel) {
            System.out.println("Nenhum veículo disponível no momento.");
        }
    }

    private void finalizarNovoAluguel(Cliente cliente, List<ItemAluguel> itens) {
        String idAluguel = "ALG-" + String.format("%03d", contadorAluguel++);

        double valorCalculado = 0.0;
        for (ItemAluguel item : itens) {
            valorCalculado += item.getSubtotal();
        }

        Aluguel novoAluguel = new Aluguel(
                idAluguel,
                cliente,
                LocalDate.now(),
                itens,
                StatusAluguel.CONFIRMADO,
                valorCalculado
        );

        alugueisCadastrados.add(novoAluguel);
        cliente.getAlugueis().add(novoAluguel);

        System.out.println("\n ALUGUEL CONFIRMADO!");
        System.out.println("ID: " + idAluguel);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Qtd. Veículos: " + itens.size());
        System.out.println("Valor Total: R$ " + String.format("%.2f", valorCalculado));
    }


    private void finalizarAluguel() {
        System.out.println("\n========= FINALIZAR ALUGUEL =========");

        if (alugueisCadastrados.isEmpty()) {
            System.out.println(" Nenhum aluguel registrado!");
            return;
        }

        System.out.print("ID do aluguel (ex: ALG-001): ");
        String idBusca = sc.nextLine().toUpperCase().trim();

        for (Aluguel a : alugueisCadastrados) {
            if (a.getId().equals(idBusca)) {
                a.setStatus(StatusAluguel.ENTREGUE);
                System.out.println("\n Aluguel " + idBusca + " finalizado!");
                System.out.println("Status: ENTREGUE");
                return;
            }
        }

        System.out.println(" Aluguel não encontrado!");
    }


    private void cancelarAluguel() {
        System.out.println("\n========= CANCELAR ALUGUEL =========");

        if (alugueisCadastrados.isEmpty()) {
            System.out.println(" Nenhum aluguel registrado!");
            return;
        }

        System.out.print("ID do aluguel (ex: ALG-001): ");
        String idBusca = sc.nextLine().toUpperCase().trim();

        for (Aluguel a : alugueisCadastrados) {
            if (a.getId().equals(idBusca)) {
                if (a.getStatus() == StatusAluguel.ENTREGUE) {
                    System.out.println(" Não é possível cancelar aluguel já entregue!");
                    return;
                }
                if (a.getStatus() == StatusAluguel.CANCELADO) {
                    System.out.println("Aluguel já foi cancelado!");
                    return;
                }

                a.setStatus(StatusAluguel.CANCELADO);
                System.out.println("\n✅ Aluguel " + idBusca + " cancelado!");
                System.out.println("Status: CANCELADO");
                return;
            }
        }

        System.out.println(" Aluguel não encontrado!");
    }


    private void listarVeiculos() {
        List<Veiculo> listaVeiculo = repositorioVeiculo.listarTodos();

        if (listaVeiculo.isEmpty()) {
            System.out.println("\n Nenhum veículo cadastrado!");
            return;
        }

        System.out.println("\n-------- VEÍCULOS --------");
        System.out.println("ID | MODELO | CATEGORIA | DISPONÍVEL");
        System.out.println("--------------------------------------------");
        for (Veiculo v : listaVeiculo) {
            System.out.println(v.toString());
        }
    }

    private void listarClientes() {
        List<Cliente> listaCliente = repositorioCliente.listarTodos();

        if (listaCliente.isEmpty()) {
            System.out.println("\n Nenhum cliente cadastrado!");
            return;
        }

        Set<Cliente> clientes = new TreeSet<>(listaCliente);

        System.out.println("\n-------- CLIENTES --------");
        System.out.println("ID | NOME | E-MAIL | CPF | TIPO");
        System.out.println("----------------------------------------------------");
        for (Cliente c : clientes) {
            System.out.println(c.toString());
        }
    }


    private void relatorios() {
        if (alugueisCadastrados.isEmpty()) {
            System.out.println("\n Nenhum aluguel registrado!");
            return;
        }

        GeradorRelatorio<Aluguel> gerador = new GeradorRelatorio<>(alugueisCadastrados);

        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n======== RELATÓRIOS ========");
            System.out.println("1 - Clientes únicos que alugaram");
            System.out.println("2 - Faturamento total");
            System.out.println("3 - Voltar");
            System.out.print("Escolha: ");

            int op = 0;
            try {
                op = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(" Digite um número válido.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (op) {
                case 1:
                    exibirClientesUnicos(gerador);
                    break;
                case 2:
                    exibirFaturamento();
                    break;
                case 3:
                    voltar = true;
                    break;
                default:
                    System.out.println(" Opção inválida!");
            }
        }
    }

    private void exibirClientesUnicos(GeradorRelatorio<Aluguel> gerador) {
        System.out.println("\n--- CLIENTES ÚNICOS ---");
        Set<Cliente> unicos = gerador.clientesUnicos();

        if (unicos.isEmpty()) {
            System.out.println("Nenhum cliente realizou aluguel ainda.");
            return;
        }

        for (Cliente c : unicos) {
            System.out.println("- " + c.getNome() + " (CPF: " + c.getCpf() + ")");
        }
    }

    private void exibirFaturamento() {
        System.out.println("\n--- FATURAMENTO TOTAL ---");
        double total = 0.0;
        int alugueisCancelados = 0;

        for (Aluguel a : alugueisCadastrados) {
            if (a.getStatus() != StatusAluguel.CANCELADO) {
                total += a.getValorTotal();
            } else {
                alugueisCancelados++;
            }
        }

        System.out.println("Total arrecadado: R$ " + String.format("%.2f", total));
        System.out.println("Aluguéis cancelados: " + alugueisCancelados);
    }
}