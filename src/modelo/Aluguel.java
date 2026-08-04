package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Aluguel {

    private String id;
    private Cliente cliente;
    private LocalDate dataAluguel;
    private List<ItemAluguel> itens;
    private StatusAluguel status;
    private Double valorTotal;

    public Aluguel() {}

    public Aluguel(String id, Cliente cliente, LocalDate dataAluguel, List<ItemAluguel> itens, StatusAluguel status, Double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.dataAluguel = dataAluguel;
        this.itens = itens;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public List<ItemAluguel> getItens() {
        return itens;
    }

    public void setItens(List<ItemAluguel> itens) {
        this.itens = itens;
    }

    public StatusAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusAluguel status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aluguel aluguel = (Aluguel) o;
        return Objects.equals(id, aluguel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
