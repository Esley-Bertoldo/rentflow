package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ItemAluguel {

    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int qtdDias;
    private Double precoUnitario;
    private Double subtotal;

    public ItemAluguel() {}

    public ItemAluguel(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, Double precoUnitario) {
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.precoUnitario = precoUnitario;
        calcularDias();
        calcularSubtotal();
    }

    public void calcularDias() {
        if (dataInicio != null && dataFim != null) {
            // Calcula a diferença em dias entre a data inicial e final
            this.qtdDias = (int) ChronoUnit.DAYS.between(dataInicio, dataFim);

            if (this.qtdDias == 0) {
                this.qtdDias = 1;
            }
        }
    }

    public void calcularSubtotal() {
        if (this.precoUnitario != null && this.qtdDias > 0) {
            this.subtotal = this.precoUnitario * this.qtdDias;
        }
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        calcularDias(); // Recalcula se a data mudar
        calcularSubtotal();
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        calcularDias(); // Recalcula se a data mudar
        calcularSubtotal();
    }

    public int getQtdDias() {
        return qtdDias;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemAluguel that = (ItemAluguel) o;
        return Objects.equals(veiculo, that.veiculo) &&
                Objects.equals(dataInicio, that.dataInicio) &&
                Objects.equals(dataFim, that.dataFim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(veiculo, dataInicio, dataFim);
    }
}