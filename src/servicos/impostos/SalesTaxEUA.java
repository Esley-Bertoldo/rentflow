package servicos.impostos;

import servicos.interfaces.CalculadoraImposto;

// Implementação do imposto Sales Tax (Imposto sobre Vendas)
// EUA: alíquota varia por estado, usamos 8.5% como média nacional
// Imposto sobre vendas aplicado em transações comerciais
public class SalesTaxEUA implements CalculadoraImposto {

    // Alíquota média de Sales Tax nos EUA: 8.5%
    private static final double ALIQUOTA = 0.085;

    @Override
    public Double calcula(Double valor) {
        // Calcula 8.5% do valor informado
        return valor * ALIQUOTA;
    }

    @Override
    public String getDescricao() {
        // Retorna descrição formatada do imposto
        return "Sales Tax (8.5%)";
    }
}
