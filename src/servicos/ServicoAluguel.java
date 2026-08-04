package servicos;

import excecoes.VeiculoIndisponivelException;
import modelo.*;
import servicos.interfaces.CalculadoraImposto;
import servicos.interfaces.EstrategiaTarifa;
import servicos.interfaces.ProcessadorAluguel;
import servicos.interfaces.Repositorio;
import java.time.LocalDate;
import java.util.*;

// Classe principal de orquestração de aluguéis
// Demonstra Injeção de Dependência: recebe todas as dependências via construtor
// Exemplo de Inversão de Controle (IoC): a classe não cria, recebe pronto
public class ServicoAluguel {

    // Dependências injetadas (SOLID - Dependency Inversion)
    private Repositorio<Veiculo> repositorioVeiculo;
    private Repositorio<Cliente> repositorioCliente;
    private Repositorio<Aluguel> repositorioAluguel;
    private ProcessadorAluguel processador;
    private List<? extends CalculadoraImposto> impostos;
    private EstrategiaTarifa estrategiaTarifa;
    private GerenciadorVeiculos gerenciador;
    private Integer contadorAlugueis = 0;

    // Construtor com Injeção de Dependência
    public ServicoAluguel(
            Repositorio<Veiculo> repositorioVeiculo,
            Repositorio<Cliente> repositorioCliente,
            Repositorio<Aluguel> repositorioAluguel,
            ProcessadorAluguel processador,
            List<? extends CalculadoraImposto> impostos,
            EstrategiaTarifa estrategiaTarifa) {

        this.repositorioVeiculo = repositorioVeiculo;
        this.repositorioCliente = repositorioCliente;
        this.repositorioAluguel = repositorioAluguel;
        this.processador = processador;
        this.impostos = impostos;
        this.estrategiaTarifa = estrategiaTarifa;
        this.gerenciador = new GerenciadorVeiculos(repositorioVeiculo);
    }

    // Cria um novo aluguel com múltiplos itens
    // Calcula tarifa, aplica impostos e processa conforme região
    public Aluguel criarAluguel(Cliente cliente, List<ItemAluguel> itens, EstrategiaTarifa tarifa) {
        // Valida se o cliente existe
        if (cliente == null || repositorioCliente.buscarPorId(cliente.getCpf()) == null) {
            throw new IllegalArgumentException("Cliente não encontrado ou inválido");
        }

        // Valida se há itens no aluguel
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Aluguel deve conter pelo menos 1 veículo");
        }

        // Verifica se todos os veículos estão disponíveis
        for (ItemAluguel item : itens) {
            if (!gerenciador.estaDisponivel(item.getVeiculo().getId())) {
                throw new VeiculoIndisponivelException(
                        "Veículo " + item.getVeiculo().getModelo() + " não disponível"
                );
            }
        }

        // Marca veículos como indisponíveis
        for (ItemAluguel item : itens) {
            gerenciador.marcarIndisponivel(item.getVeiculo().getId());
        }

        // Calcula valor total somando subtotais de cada item
        Double valorBase = 0.0;
        for (ItemAluguel item : itens) {
            valorBase += item.getSubtotal();
        }

        // Aplica estratégia de tarifa escolhida
        Double valorComTarifa = tarifa.calculaTarifa(
                valorBase,
                itens.get(0).getQtdDias(),
                cliente.getTipoCliente()
        );

        // Cria o aluguel
        contadorAlugueis++;
        String idAluguel = "ALG-" + String.format("%03d", contadorAlugueis);

        Aluguel aluguel = new Aluguel(
                idAluguel,
                cliente,
                LocalDate.now(),
                itens,
                StatusAluguel.PENDENTE,
                valorComTarifa
        );

        // Processa o aluguel (aplica impostos e atualiza status)
        processador.processar(aluguel);

        // Salva no repositório
        repositorioAluguel.salvar(aluguel);

        return aluguel;
    }

    // Finaliza um aluguel (status para ENTREGUE)
    public void finalizarAluguel(String idAluguel) {
        Aluguel aluguel = repositorioAluguel.buscarPorId(idAluguel);

        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não encontrado");
        }

        // Marca veículos como disponíveis novamente
        for (ItemAluguel item : aluguel.getItens()) {
            gerenciador.marcarDisponivel(item.getVeiculo().getId());
        }

        // Atualiza status
        aluguel.setStatus(StatusAluguel.ENTREGUE);
    }

    // Cancela um aluguel (verifica regras)
    public void cancelarAluguel(String idAluguel) {
        Aluguel aluguel = repositorioAluguel.buscarPorId(idAluguel);

        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não encontrado");
        }

        // Não permite cancelar se já foi entregue
        if (aluguel.getStatus() == StatusAluguel.ENTREGUE) {
            throw new IllegalArgumentException("Não é possível cancelar um aluguel já entregue");
        }

        // Marca veículos como disponíveis novamente
        for (ItemAluguel item : aluguel.getItens()) {
            gerenciador.marcarDisponivel(item.getVeiculo().getId());
        }

        // Atualiza status
        aluguel.setStatus(StatusAluguel.CANCELADO);
    }

    // Obtém todos os aluguéis de um cliente
    public List<Aluguel> obterAlugueisPorCliente(String cpfCliente) {
        List<Aluguel> todosAlugueis = repositorioAluguel.listarTodos();
        List<Aluguel> alugueisPorCliente = new ArrayList<>();

        for (Aluguel aluguel : todosAlugueis) {
            if (aluguel.getCliente().getCpf().equals(cpfCliente)) {
                alugueisPorCliente.add(aluguel);
            }
        }

        return alugueisPorCliente;
    }

    // Retorna o repositório de aluguéis
    public Repositorio<Aluguel> getRepositorioAluguel() {
        return repositorioAluguel;
    }

    // Retorna o gerenciador de veículos
    public GerenciadorVeiculos getGerenciadorVeiculos() {
        return gerenciador;
    }
}
