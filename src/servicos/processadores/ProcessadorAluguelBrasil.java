package servicos.processadores;

import excecoes.AluguelInvalidoException;
import modelo.Aluguel;
import modelo.StatusAluguel;
import servicos.interfaces.CalculadoraImposto;
import servicos.interfaces.ProcessadorAluguel;
import java.util.List;

// Implementação específica para processamento de aluguéis no Brasil
// Valida regras de negócio brasileiras e aplica impostos brasileiros
// Exemplo de polimorfismo em ação: mesma interface, implementações diferentes
public class ProcessadorAluguelBrasil implements ProcessadorAluguel {

    // Lista de impostos brasileiros injetados via construtor
    // Wildcards permitem qualquer implementação de CalculadoraImposto
    private List<? extends CalculadoraImposto> impostos;

    public ProcessadorAluguelBrasil(List<? extends CalculadoraImposto> impostos) {
        this.impostos = impostos;
    }

    @Override
    public void processar(Aluguel aluguel) {
        // Primeiro valida o aluguel
        if (!validar(aluguel)) {
            throw new AluguelInvalidoException("Aluguel inválido para o Brasil");
        }

        // Calcula impostos brasileiros sobre o valor total
        Double totalImpostos = 0.0;
        for (CalculadoraImposto imposto : impostos) {
            Double impostoCalculado = imposto.calcula(aluguel.getValorTotal());
            totalImpostos += impostoCalculado;
        }

        // Adiciona os impostos ao valor total do aluguel
        Double valorFinal = aluguel.getValorTotal() + totalImpostos;
        aluguel.setValorTotal(valorFinal);

        // Atualiza status para CONFIRMADO após processamento
        aluguel.setStatus(StatusAluguel.CONFIRMADO);
    }

    @Override
    public Boolean validar(Aluguel aluguel) {
        // Validação 1: Aluguel deve ter pelo menos 1 veículo
        if (aluguel.getItens() == null || aluguel.getItens().isEmpty()) {
            System.out.println("ERRO: Aluguel deve conter pelo menos 1 veículo");
            return false;
        }

        // Validação 2: Cliente não pode ser nulo
        if (aluguel.getCliente() == null) {
            System.out.println("ERRO: Cliente não encontrado");
            return false;
        }

        // Validação 3: Status deve ser PENDENTE
        if (aluguel.getStatus() != StatusAluguel.PENDENTE) {
            System.out.println("ERRO: Aluguel não está em status PENDENTE");
            return false;
        }

        return true;
    }
}
