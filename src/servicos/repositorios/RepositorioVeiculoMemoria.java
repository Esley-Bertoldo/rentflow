package servicos.repositorios;

import modelo.Veiculo;
import servicos.interfaces.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioVeiculoMemoria implements Repositorio<Veiculo> {

    private Map<String, Veiculo> veiculos = new HashMap<>();

    @Override
    public void salvar(Veiculo veiculo) {
        this.veiculos.put(veiculo.getId(), veiculo);
    }

    @Override
    public Veiculo buscarPorId(String id) {
        return this.veiculos.get(id);
    }

    @Override
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(this.veiculos.values());
    }

    @Override
    public void remover(String id) {
        this.veiculos.remove(id);
    }
}