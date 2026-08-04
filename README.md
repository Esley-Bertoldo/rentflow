# RentFlow

Sistema de gerenciamento de aluguel de veículos desenvolvido em Java com arquitetura orientada a objetos.

## Sobre o Projeto

O RentFlow é uma aplicação de console para gerenciamento de aluguel de veículos. O sistema permite o cadastro de clientes e veículos, criação de aluguéis, cálculo de tarifas dinâmicas e geração de relatórios financeiros.

## Funcionalidades

- Gestão de clientes com validação de CPF e e-mail.
- Gestão de veículos por categoria (Econômico, Intermediário e Premium).
- Criação, finalização e cancelamento de aluguéis.
- Cálculo de tarifas utilizando diferentes estratégias.
- Cálculo de impostos (ICMS, PIS, COFINS e Sales Tax).
- Geração de relatórios financeiros.
- Validação de dados em tempo real.

---

# Arquitetura

O projeto foi desenvolvido seguindo boas práticas de Programação Orientada a Objetos e utilizando padrões de projeto.

```text
src/
├── aplicacao/
│   ├── Principal.java
│   └── Sistema.java
├── modelo/
│   ├── Cliente.java
│   ├── Veiculo.java
│   ├── Aluguel.java
│   ├── ItemAluguel.java
│   ├── Categoria.java
│   ├── StatusAluguel.java
│   └── TipoCliente.java
├── excecoes/
│   ├── ClienteDuplicadoException.java
│   ├── VeiculoIndisponivelException.java
│   └── AluguelInvalidoException.java
└── servicos/
    ├── interfaces/
    │   ├── Repositorio.java
    │   ├── EstrategiaTarifa.java
    │   ├── CalculadoraImposto.java
    │   └── ProcessadorAluguel.java
    ├── repositorios/
    │   ├── RepositorioClienteMemoria.java
    │   └── RepositorioVeiculoMemoria.java
    ├── tarifas/
    │   ├── TarifaSimples.java
    │   ├── TarifaPremium.java
    │   └── TarifaProgressiva.java
    ├── impostos/
    │   ├── ICMS.java
    │   ├── PIS.java
    │   ├── COFINS.java
    │   └── SalesTaxEUA.java
    ├── processadores/
    │   ├── ProcessadorAluguelBrasil.java
    │   └── ProcessadorAluguelEUA.java
    ├── ServicoAluguel.java
    └── GeradorRelatorio.java
```

## Padrões de Projeto Utilizados

| Padrão | Aplicação | Benefício |
|--------|-----------|-----------|
| Repository | `Repositorio<T>` | Abstração da persistência de dados |
| Strategy | Tarifas e Impostos | Permite diferentes algoritmos sem alterar o código principal |
| Enum | Categoria, StatusAluguel e TipoCliente | Tipos seguros e organizados |
| Generics | `Repositorio<T>` | Reutilização de código |

---

# Tecnologias Utilizadas

- Java
- Programação Orientada a Objetos (POO)
- Collections Framework
- Exceptions
- Enums
- Generics
- Strategy Pattern
- Repository Pattern
- Template Method

---

# Como Executar

## Pré-requisitos

- Java 11 ou superior
- IntelliJ IDEA, Eclipse ou VS Code

## Clonar o repositório

```bash
git clone https://github.com/seu-usuario/rentflow.git
cd rentflow
```

## Compilar

```bash
javac -d out -sourcepath src src/aplicacao/Principal.java
```

## Executar

```bash
java -cp out aplicacao.Principal
```

---

# Menu Principal

```text
1 - Cadastrar veículo
2 - Cadastrar cliente
3 - Criar aluguel
4 - Finalizar aluguel
5 - Cancelar aluguel
6 - Listar veículos
7 - Listar clientes
8 - Relatórios
9 - Sair
```

---

# Estrutura das Entidades

## Cliente

```java
id: String
nome: String
email: String
cpf: String
tipoCliente: Enum
alugueis: Set
```

## Veículo

```java
id: String
marca: String
modelo: String
precoDiario: double
categoria: Enum
disponivel: boolean
```

## Aluguel

```java
id: String
cliente: Cliente
dataAluguel: Date
itens: List
status: Enum
valorTotal: double
```

---

# Funcionalidades Técnicas

## Validações

- Validação de e-mail utilizando Expressão Regular.
- Validação de CPF único.
- Verificação de disponibilidade do veículo.
- Validação de datas.
- Validação de números inteiros e decimais.
- Tratamento de entradas inválidas.

## Cálculo de Aluguel

```java
valorTotal = quantidadeDias * precoDiario;
```

Exemplo:

```text
3 dias × R$ 50,00 = R$ 150,00
```

## Exceções Personalizadas

```java
ClienteDuplicadoException
VeiculoIndisponivelException
AluguelInvalidoException
```

---

# Autor

**Seu Nome**

GitHub: https://github.com/Esley-Bertoldo

LinkedIn: https://www.linkedin.com/in/esley-bertoldo
