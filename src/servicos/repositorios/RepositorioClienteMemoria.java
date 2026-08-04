package servicos.repositorios;

import modelo.Cliente;
import servicos.interfaces.Repositorio;


import java.util.*;

public class RepositorioClienteMemoria implements Repositorio<Cliente> {

    private Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public void salvar(Cliente cliente) {
        this.clientes.put(cliente.getCpf(), cliente);
    }

    @Override
    public Cliente buscarPorId(String cpf) {
        return this.clientes.get(cpf);
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(this.clientes.values());
    }

    @Override
    public void remover(String cpf) {
        this.clientes.remove(cpf);
    }
}
