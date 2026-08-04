package modelo;

import java.util.Objects;

public class Veiculo {

    private String id;
    private String marca;
    private String modelo;
    private Double precoDiario;
    private Categoria categoria;
    private Boolean disponivel;

    public Veiculo() {}

    public Veiculo(String id, String marca, String modelo, Double precoDiario, Categoria categoria, Boolean disponivel) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precoDiario = precoDiario;
        this.categoria = categoria;
        this.disponivel = disponivel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(Double precoDiario) {
        this.precoDiario = precoDiario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return Objects.equals(id, veiculo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public String toString() {
        String disponivel = this.disponivel ? "SIM" : "NÃO";
        return id + " | " +
                modelo + " | " +
                categoria + " | " +
                disponivel;
    }

}
