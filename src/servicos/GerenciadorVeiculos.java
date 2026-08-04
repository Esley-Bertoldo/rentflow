package servicos;

import modelo.Categoria;
import modelo.Veiculo;
import servicos.interfaces.Repositorio;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// Classe responsável por gerenciar disponibilidade e categorias de veículos
// Utiliza injeção de dependência para o repositório
// Exemplo de separação de responsabilidades (SOLID - Single Responsibility)
public class GerenciadorVeiculos {

    // Repositório genérico de veículos
    private Repositorio<Veiculo> repositorioVeiculo;

    public GerenciadorVeiculos(Repositorio<Veiculo> repositorioVeiculo) {
        this.repositorioVeiculo = repositorioVeiculo;
    }

    // Retorna todos os veículos disponíveis
    public List<Veiculo> obterVeiculosDisponiveis() {
        return repositorioVeiculo.listarTodos()
                .stream()
                .filter(Veiculo::getDisponivel)
                .collect(Collectors.toList());
    }

    // Retorna veículos disponíveis de uma categoria específica
    public List<Veiculo> obterVeiculosPorCategoria(Categoria categoria) {
        return repositorioVeiculo.listarTodos()
                .stream()
                .filter(v -> v.getCategoria() == categoria && v.getDisponivel())
                .collect(Collectors.toList());
    }

    // Marca um veículo como indisponível (quando é alugado)
    public void marcarIndisponivel(String idVeiculo) {
        Veiculo veiculo = repositorioVeiculo.buscarPorId(idVeiculo);
        if (veiculo != null) {
            veiculo.setDisponivel(false);
        }
    }

    // Marca um veículo como disponível (quando aluguel é finalizado/cancelado)
    public void marcarDisponivel(String idVeiculo) {
        Veiculo veiculo = repositorioVeiculo.buscarPorId(idVeiculo);
        if (veiculo != null) {
            veiculo.setDisponivel(true);
        }
    }

    // Verifica se um veículo específico está disponível
    public Boolean estaDisponivel(String idVeiculo) {
        Veiculo veiculo = repositorioVeiculo.buscarPorId(idVeiculo);
        return veiculo != null && veiculo.getDisponivel();
    }

    // Retorna um Set de veículos disponíveis (remove duplicatas automaticamente)
    public Set<Veiculo> obterConjuntoVeiculosDisponiveis() {
        return new HashSet<>(obterVeiculosDisponiveis());
    }

    // Agrupa veículos disponíveis por categoria
    public Map<Categoria, List<Veiculo>> agruparPorCategoria() {
        return obterVeiculosDisponiveis()
                .stream()
                .collect(Collectors.groupingBy(Veiculo::getCategoria));
    }
}
