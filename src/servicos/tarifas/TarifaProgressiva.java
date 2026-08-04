package servicos.tarifas;

import modelo.TipoCliente;
import servicos.interfaces.EstrategiaTarifa;

// Implementação da estratégia de tarifa progressiva com desconto por duração
// Quanto mais dias, maior o desconto oferecido
// Escalas: 1-3 dias (100%), 4-7 dias (90%), 8-15 dias (80%), 16+ dias (70%)
// Aplica desconto adicional de 5% se PESSOA_JURÍDICA
public class TarifaProgressiva implements EstrategiaTarifa {

    @Override
    public Double calculaTarifa(Double precoBase, Integer dias, TipoCliente tipoCliente) {
        // Calcula o valor base
        Double valorTotal = precoBase * dias;

        // Aplica desconto progressivo conforme a quantidade de dias
        double fatorDesconto = 1.0; // 100% - sem desconto

        if (dias >= 4 && dias <= 7) {
            // 4 a 7 dias: desconto de 10% (90% do preço)
            fatorDesconto = 0.90;
        } else if (dias >= 8 && dias <= 15) {
            // 8 a 15 dias: desconto de 20% (80% do preço)
            fatorDesconto = 0.80;
        } else if (dias >= 16) {
            // 16+ dias: desconto de 30% (70% do preço)
            fatorDesconto = 0.70;
        }

        valorTotal = valorTotal * fatorDesconto;

        // Desconto adicional de 5% para Pessoa Jurídica
        if (tipoCliente == TipoCliente.PESSOA_JURIDICA) {
            valorTotal = valorTotal * 0.95;
        }

        return valorTotal;
    }
}
