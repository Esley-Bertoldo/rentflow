package servicos.impostos;

import servicos.interfaces.CalculadoraImposto;

// Implementação do imposto PIS (Programa de Integração Social)
// Brasil: alíquota de 1.65%
// Contribuição social sobre a folha de salários e faturamento
public class PIS implements CalculadoraImposto {

    // Alíquota padrão do PIS: 1.65%
    private static final double ALIQUOTA = 0.0165;

    @Override
    public Double calcula(Double valor) {
        // Calcula 1.65% do valor informado
        return valor * ALIQUOTA;
    }

    @Override
    public String getDescricao() {
        // Retorna descrição formatada do imposto
        return "PIS (1.65%)";
    }
}
