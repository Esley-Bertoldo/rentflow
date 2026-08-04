package servicos.tarifas;

import modelo.TipoCliente;
import servicos.interfaces.EstrategiaTarifa;

// Implementação da estratégia de tarifa premium
// Cálculo: preço diário × dias × 1.5 (acréscimo de 50%)
// Direcionada para clientes que desejam serviços premium
// Aplica desconto de 5% se PESSOA_JURÍDICA
public class TarifaPremium implements EstrategiaTarifa {

    @Override
    public Double calculaTarifa(Double precoBase, Integer dias, TipoCliente tipoCliente) {
        // Calcula o valor com multiplicador premium (1.5 = 50% de acréscimo)
        Double valorTotal = precoBase * dias * 1.5;

        // Desconto de 5% para Pessoa Jurídica
        if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
            valorTotal = valorTotal * 0.95;
        }

        return valorTotal;
    }
}
