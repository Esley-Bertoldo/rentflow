package servicos.impostos;

import servicos.interfaces.CalculadoraImposto;

// Implementação do imposto ICMS (Imposto sobre Circulação de Mercadorias e Serviços)
// Brasil: alíquota de 18%
// Utilizado em serviços de aluguel de veículos
public class ICMS implements CalculadoraImposto {

    // Alíquota padrão do ICMS no Brasil: 18%
    private static final double ALIQUOTA = 0.18;

    @Override
    public Double calcula(Double valor) {
        // Calcula 18% do valor informado
        return valor * ALIQUOTA;
    }

    @Override
    public String getDescricao() {
        // Retorna descrição formatada do imposto
        return "ICMS (18%)";
    }
}
