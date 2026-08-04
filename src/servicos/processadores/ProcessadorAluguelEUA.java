package servicos.processadores;

import excecoes.AluguelInvalidoException;
import modelo.Aluguel;
import modelo.StatusAluguel;
import servicos.interfaces.CalculadoraImposto;
import servicos.interfaces.ProcessadorAluguel;
import java.util.List;

// Implementação específica para processamento de aluguéis nos EUA
// Valida regras de negócio americanas e aplica impostos americanos
// Demonstra como diferentes regiões podem ter processamentos diferentes
public class ProcessadorAluguelEUA implements ProcessadorAluguel {

    // Lista de impostos americanos injetados via construtor
    private List<? extends CalculadoraImposto> impostos;

    public ProcessadorAluguelEUA(List<? extends CalculadoraImposto> impostos) {
        this.impostos = impostos;
    }

    @Override
    public void processar(Aluguel aluguel) {
        // Primeiro valida o aluguel
        if (!validar(aluguel)) {
            throw new AluguelInvalidoException("Aluguel inválido para EUA");
        }

        // Calcula impostos americanos sobre o valor total
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
            System.out.println("ERROR: Rental must contain at least 1 vehicle");
            return false;
        }

        // Validação 2: Cliente não pode ser nulo
        if (aluguel.getCliente() == null) {
            System.out.println("ERROR: Customer not found");
            return false;
        }

        // Validação 3: Status deve ser PENDENTE
        if (aluguel.getStatus() != StatusAluguel.PENDENTE) {
            System.out.println("ERROR: Rental is not in PENDING status");
            return false;
        }

        return true;
    }
}
