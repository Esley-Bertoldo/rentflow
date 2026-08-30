# RentFlow

Sistema de gerenciamento de aluguel de veículos desenvolvido em **Java**, aplicando **Programação Orientada a Objetos, princípios SOLID e padrões de projeto**.

## 📌 Sobre o Projeto

O **RentFlow** é uma aplicação de console para gerenciamento de aluguel de veículos.

O sistema permite cadastrar clientes e veículos, criar e gerenciar aluguéis, calcular tarifas de acordo com diferentes estratégias, aplicar impostos conforme a região e gerar relatórios financeiros.

O projeto foi desenvolvido com foco na aplicação prática de conceitos de **Programação Orientada a Objetos**, **polimorfismo**, **injeção de dependência**, **Generics**, **Collections** e **padrões de projeto**.

## 🚀 Funcionalidades

* Cadastro, consulta e remoção de clientes.
* Cadastro, consulta e remoção de veículos.
* Organização de veículos por categoria.
* Verificação de disponibilidade dos veículos.
* Criação de aluguéis com múltiplos veículos.
* Finalização e cancelamento de aluguéis.
* Cálculo de tarifas utilizando diferentes estratégias.
* Descontos para clientes pessoa jurídica.
* Aplicação de impostos conforme a região.
* Processamento de aluguéis no Brasil e nos EUA.
* Geração de relatórios financeiros.
* Validação de dados e regras de negócio.
* Tratamento de exceções personalizadas.

---

# 🏗️ Arquitetura

O projeto foi organizado em camadas, separando entidades, regras de negócio, repositórios, estratégias de cálculo e processamento de aluguéis.

```text
src/
├── aplicacao/
│   ├── Principal.java
│   └── Sistema.java
│
├── modelo/
│   ├── Cliente.java
│   ├── Veiculo.java
│   ├── Aluguel.java
│   ├── ItemAluguel.java
│   ├── Categoria.java
│   ├── StatusAluguel.java
│   └── TipoCliente.java
│
├── excecoes/
│   ├── ClienteDuplicadoException.java
│   ├── VeiculoIndisponivelException.java
│   └── AluguelInvalidoException.java
│
└── servicos/
    ├── GerenciadorVeiculos.java
    ├── GeradorRelatorio.java
    ├── ServicoAluguel.java
    │
    ├── interfaces/
    │   ├── Repositorio.java
    │   ├── EstrategiaTarifa.java
    │   ├── CalculadoraImposto.java
    │   └── ProcessadorAluguel.java
    │
    ├── repositorios/
    │   ├── RepositorioClienteMemoria.java
    │   └── RepositorioVeiculoMemoria.java
    │
    ├── tarifas/
    │   ├── TarifaSimples.java
    │   ├── TarifaPremium.java
    │   └── TarifaProgressiva.java
    │
    ├── impostos/
    │   ├── ICMS.java
    │   ├── PIS.java
    │   ├── COFINS.java
    │   └── SalesTaxEUA.java
    │
    └── processadores/
        ├── ProcessadorAluguelBrasil.java
        └── ProcessadorAluguelEUA.java
```

---

# 🧩 Padrões de Projeto

## Strategy Pattern

Utilizado para permitir diferentes formas de cálculo de tarifas e impostos sem alterar a lógica principal do sistema.

### Estratégias de tarifa

* `TarifaSimples`
* `TarifaPremium`
* `TarifaProgressiva`

Todas implementam a interface:

```java
EstrategiaTarifa
```

Isso permite que o comportamento de cálculo seja alterado de forma flexível.

### Estratégias de impostos

Os impostos também seguem uma abstração comum através da interface:

```java
CalculadoraImposto
```

Implementações:

* `ICMS`
* `PIS`
* `COFINS`
* `SalesTaxEUA`

---

## Repository Pattern

O acesso aos dados é abstraído através da interface genérica:

```java
Repositorio<T>
```

Implementações utilizadas:

```text
RepositorioClienteMemoria
RepositorioVeiculoMemoria
```

Os dados são armazenados em memória utilizando `HashMap`.

Essa abordagem permite separar a lógica de negócio da forma como os dados são armazenados.

---

## Factory Pattern

O projeto utiliza uma abordagem de fábrica na classe:

```text
Principal
Sistema
```

para centralizar a criação e configuração dos componentes utilizados pela aplicação.

---

# 🧠 Conceitos de Programação Orientada a Objetos

O projeto aplica diversos conceitos fundamentais de POO:

* Encapsulamento.
* Abstração.
* Herança através de implementações de interfaces.
* Polimorfismo.
* Interfaces.
* Composição e associação entre objetos.
* Enumerações.
* Generics.
* Collections.
* Sobrescrita de métodos.

---

# 🔄 Injeção de Dependência

O `ServicoAluguel` recebe suas dependências através do construtor, em vez de criá-las internamente.

Entre as dependências estão:

```java
Repositorio<Veiculo>
Repositorio<Cliente>
Repositorio<Aluguel>
ProcessadorAluguel
EstrategiaTarifa
```

Isso reduz o acoplamento entre as classes e facilita a substituição das implementações.

Exemplo:

```java
public ServicoAluguel(
    Repositorio<Veiculo> repositorioVeiculo,
    Repositorio<Cliente> repositorioCliente,
    Repositorio<Aluguel> repositorioAluguel,
    ProcessadorAluguel processador,
    List<? extends CalculadoraImposto> impostos,
    EstrategiaTarifa estrategiaTarifa
)
```

---

# 🌎 Processamento por Região

O sistema permite utilizar diferentes regras de processamento de acordo com a região.

```text
              ProcessadorAluguel
                      │
             ┌────────┴────────┐
             │                 │
             ▼                 ▼
ProcessadorAluguelBrasil  ProcessadorAluguelEUA
             │                 │
             ▼                 ▼
     Impostos Brasil       Sales Tax
```

### Brasil

Utiliza:

* ICMS
* PIS
* COFINS

### Estados Unidos

Utiliza:

* Sales Tax

As duas implementações seguem o mesmo contrato definido pela interface:

```java
ProcessadorAluguel
```

Isso demonstra o uso de **polimorfismo** para aplicar regras diferentes através da mesma abstração.

---

# 💰 Estratégias de Tarifas

O sistema possui três estratégias de cálculo.

### Tarifa Simples

Calcula o valor com base no preço diário e na quantidade de dias.

```text
preço diário × quantidade de dias
```

Clientes pessoa jurídica recebem desconto de 5%.

### Tarifa Premium

Aplica um acréscimo de 50% sobre o valor base.

```text
preço diário × dias × 1.5
```

Clientes pessoa jurídica recebem desconto adicional de 5%.

### Tarifa Progressiva

Aplica descontos conforme a duração do aluguel:

| Período   | Desconto |
| --------- | -------: |
| 1–3 dias  |       0% |
| 4–7 dias  |      10% |
| 8–15 dias |      20% |
| 16+ dias  |      30% |

Clientes pessoa jurídica recebem mais 5% de desconto.

---

# 🚗 Gerenciamento de Veículos

A classe `GerenciadorVeiculos` é responsável por operações relacionadas à disponibilidade dos veículos.

Entre suas responsabilidades estão:

* Listar veículos disponíveis.
* Filtrar veículos por categoria.
* Marcar veículos como disponíveis.
* Marcar veículos como indisponíveis.
* Verificar disponibilidade.
* Agrupar veículos por categoria.

As operações utilizam `Stream API`, `Collections` e `Generics`.

---

# 📊 Relatórios

O sistema possui um `GeradorRelatorio` responsável pela geração de informações relacionadas aos aluguéis.

Os relatórios permitem visualizar informações financeiras e dados dos aluguéis processados.

---

# 🛡️ Exceções Personalizadas

O projeto possui exceções específicas para representar situações de negócio:

```text
ClienteDuplicadoException
VeiculoIndisponivelException
AluguelInvalidoException
```

Isso permite separar erros de negócio de erros genéricos e tornar o tratamento das situações excepcionais mais organizado.

---

# 🖥️ Menu Principal

A aplicação possui uma interface de console para interação com o usuário.

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

# 🛠️ Tecnologias Utilizadas

* **Java 11+**
* **Programação Orientada a Objetos**
* **Collections Framework**
* **Stream API**
* **Generics**
* **Enums**
* **Interfaces**
* **Exception Handling**
* **SOLID**
* **Strategy Pattern**
* **Repository Pattern**
* **Factory Pattern**

---

# ⚙️ Como Executar

## Pré-requisitos

* Java 11 ou superior.
* IntelliJ IDEA, Eclipse ou VS Code.

## Clonar o repositório

```bash
git clone https://github.com/Esley-Bertoldo/rentflow.git
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

# 🎯 Objetivo do Projeto

O RentFlow foi desenvolvido com o objetivo de praticar e consolidar conhecimentos de **Java e Programação Orientada a Objetos**, aplicando conceitos de arquitetura, padrões de projeto e princípios de desenvolvimento de software em um sistema com regras de negócio reais.

---

# 👨‍💻 Autor

**Esley Bertoldo**

GitHub: [Esley-Bertoldo](https://github.com/Esley-Bertoldo)

LinkedIn: [Esley Bertoldo](https://www.linkedin.com/in/esley-bertoldo)
