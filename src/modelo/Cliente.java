package modelo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cliente implements Comparable<Cliente> {

    private String id;
    private String nome;
    private String email;
    private String cpf;
    private TipoCliente tipoCliente;

    private Set<Aluguel> alugueis =  new HashSet<>();

    public Cliente() {}

    public Cliente(String id, String nome, String email, String cpf, TipoCliente tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.tipoCliente = tipoCliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Set<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(Set<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }

    @Override
    public int compareTo(Cliente o) {
        return this.cpf.compareTo(o.cpf);
    }

    @Override
    public String toString() {
        return id + " | " +
                nome + " | " +
                email + " | " +
                cpf + " | " +
                tipoCliente;
    }
}
