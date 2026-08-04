package servicos.interfaces;

// Interface que define o contrato para cálculo de impostos
// Implementa o padrão Strategy com método default para comportamento comum
// Permite adicionar novos impostos sem modificar código existente (Open/Closed Principle)
public interface CalculadoraImposto {

    // Calcula o valor do imposto sobre uma base de valor
    // Recebe: valor base para cálculo do imposto
    // Retorna: valor do imposto calculado (ex: 18% de 1000 = 180)
    Double calcula(Double valor);

    // Retorna a descrição/nome do imposto (ex: "ICMS 18%")
    // Útil para exibição em relatórios e logs
    String getDescricao();

    // Método default que exibe o imposto de forma formatada
    // Default methods permitem comportamento padrão em interfaces
    default void exibir() {
        System.out.println(getDescricao());
    }
}