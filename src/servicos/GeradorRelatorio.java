package servicos;

import modelo.Aluguel;
import modelo.Cliente;
import modelo.StatusAluguel;

import java.util.*;

// A declaração <T> torna esta classe Genérica.
// Ela pode gerar relatórios para listas de Veiculos, Clientes ou Alugueis.
public class GeradorRelatorio<T> {

    // Wildcard (? extends T): Aceita uma lista de T ou qualquer classe filha de T
    private List<? extends T> dados;

    // Construtor que recebe a lista de dados na hora de instanciar o gerador
    public GeradorRelatorio(List<? extends T> dados) {
        this.dados = dados;
    }

    /**
     * 1. ORDENAÇÃO COM COMPARATOR
     * Recebe um critério de ordenação customizado (Comparator) e retorna a lista ordenada.
     * O 'super T' garante que podemos usar comparadores da classe T ou de suas superclasses.
     */
    public List<T> listarOrdenado(Comparator<? super T> comparator) {
        // Cria uma cópia da lista original para não bagunçar os dados da memória
        List<T> copiaOrdenada = new ArrayList<>(dados);

        // Aplica o comparator (ex: ordenação por valor ou data)
        copiaOrdenada.sort(comparator);

        return copiaOrdenada;
    }

    /**
     * 2. AGRUPAMENTO COM MAP E SET
     * Agrupa os aluguéis pelo seu Status (PENDENTE, CONFIRMADO, etc).
     * Retorna um Map onde a CHAVE é o Status e o VALOR é um conjunto (Set) de aluguéis.
     */
    public Map<StatusAluguel, Set<Aluguel>> agruparPorStatus() {
        Map<StatusAluguel, Set<Aluguel>> mapa = new HashMap<>();

        for (T item : dados) {
            // Verifica de forma segura se o item genérico é realmente um Aluguel
            if (item instanceof Aluguel) {
                Aluguel aluguel = (Aluguel) item;
                StatusAluguel status = aluguel.getStatus();

                // Se o Map ainda não tem esse status, cria uma lista (Set) vazia para ele
                mapa.putIfAbsent(status, new HashSet<>());

                // Adiciona o aluguel dentro do Set correspondente àquele status
                mapa.get(status).add(aluguel);
            }
        }
        return mapa;
    }

    /**
     * 3. EXTRAÇÃO DE ÚNICOS COM SET E TREESET
     * Varre a lista de aluguéis e extrai apenas os clientes, eliminando duplicatas.
     * Como usamos TreeSet, os clientes já sairão ordenados pelo CPF (devido ao Comparable).
     */
    public Set<Cliente> clientesUnicos() {
        Set<Cliente> clientes = new TreeSet<>();

        for (T item : dados) {
            if (item instanceof Aluguel) {
                Aluguel aluguel = (Aluguel) item;
                // O Set ignora automaticamente se o cliente já existir lá dentro
                clientes.add(aluguel.getCliente());
            }
        }
        return clientes;
    }

    /**
     * 4. MAPA GENÉRICO (AGRUPAR POR CLIENTE)
     * Retorna um mapa onde a chave é o CPF do Cliente e o valor é a lista de seus Aluguéis.
     * Representação fiel do "Map<K, V>" do UML.
     */
    public Map<String, List<Aluguel>> gerarMapaPorCliente() {
        Map<String, List<Aluguel>> mapa = new HashMap<>();

        for (T item : dados) {
            if (item instanceof Aluguel) {
                Aluguel aluguel = (Aluguel) item;
                String cpfCliente = aluguel.getCliente().getCpf();

                // Se o CPF não estiver no mapa, cria uma nova lista vazia
                mapa.putIfAbsent(cpfCliente, new ArrayList<>());

                // Adiciona o aluguel na lista daquele CPF
                mapa.get(cpfCliente).add(aluguel);
            }
        }
        return mapa;
    }
}