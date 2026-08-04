package aplicacao;

import java.util.Locale;

public class Principal {
    static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Sistema sistema = new Sistema();
        sistema.executar();

    }
}
