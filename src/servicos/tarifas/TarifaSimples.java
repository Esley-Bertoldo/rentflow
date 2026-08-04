package servicos.tarifas;

import modelo.TipoCliente;
import servicos.interfaces.EstrategiaTarifa;

// Implementação da estratégia de tarifa simples
// Cálculo: preço diário × quantidade de dias
// Aplica desconto de 5% apenas se o cliente for PESSOA_JURÍDICA
public class TarifaSimples implements EstrategiaTarifa {

    @Override
    public Double calculaTarifa(Double precoBase, Integer dias, TipoCliente tipoCliente) {
        // Calcula o valor base: preço diário × dias
        Double valorTotal = precoBase * dias;

        // Se for Pessoa Jurídica, aplica desconto de 5%
        if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
            valorTotal = valorTotal * 0.95;
        }

        return valorTotal;
    }
}