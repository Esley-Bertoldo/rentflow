package servicos.interfaces;

import modelo.TipoCliente;

// Interface que define o contrato para diferentes estratégias de cálculo de tarifas
// Implementa o padrão Strategy, permitindo polimorfismo em tempo de execução
// Cada implementação representa uma forma diferente de calcular o valor da diária
public interface EstrategiaTarifa {

    // Calcula a tarifa com base no preço diário, quantidade de dias e tipo de cliente
    // Recebe: preço base da diária, número de dias, tipo de cliente (PESSOA_FISICA ou PESSOA_JURIDICA)
    // Retorna: valor total da tarifa após aplicar os descontos ou acréscimos da estratégia
    Double calculaTarifa(Double precoBase, Integer dias, TipoCliente tipoCliente);
}