package servicos.interfaces;

import java.util.List;

public interface Repositorio<T> {

    void salvar(T entidade);

    T buscarPorId(String id);

    List<T> listarTodos();

    void remover(String id);
}