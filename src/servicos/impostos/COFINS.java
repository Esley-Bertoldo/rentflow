package servicos.impostos;

import servicos.interfaces.CalculadoraImposto;

// Implementação do imposto COFINS (Contribuição para o Financiamento da Seguridade Social)
// Brasil: alíquota de 7.6%
// Contribuição federal para financiar seguridade social
public class COFINS implements CalculadoraImposto {

    // Alíquota padrão do COFINS: 7.6%
    private static final double ALIQUOTA = 0.076;

    @Override
    public Double calcula(Double valor) {
        // Calcula 7.6% do valor informado
        return valor * ALIQUOTA;
    }

    @Override
    public String getDescricao() {
        // Retorna descrição formatada do imposto
        return "COFINS (7.6%)";
    }
}

