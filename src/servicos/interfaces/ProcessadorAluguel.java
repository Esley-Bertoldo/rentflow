package servicos.interfaces;

import modelo.Aluguel;

// Interface que define o contrato para processamento de aluguéis
// Diferentes regiões (Brasil, EUA, etc) podem ter processamentos diferentes
// Exemplo: impostos diferentes, regras de negócio diferentes
public interface ProcessadorAluguel {

    // Processa um aluguel aplicando as regras específicas da região
    // Valida, calcula impostos e atualiza o status do aluguel
    // Recebe: o aluguel a ser processado
    void processar(Aluguel aluguel);

    // Valida se um aluguel atende aos requisitos mínimos
    // Recebe: o aluguel a ser validado
    // Retorna: true se válido, false caso contrário
    Boolean validar(Aluguel aluguel);
}