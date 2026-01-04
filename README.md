# 👠 Luxo em Passos - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/license-GPLv3-blue.svg?style=for-the-badge)

O **Luxo em Passos** é um sistema de gestão para sapatarias de alto padrão, focado no controle de estoque de sandálias de luxo, gestão de clientes e processamento de pedidos com suporte a programas de fidelidade dinâmicos.

---

## 🚀 Funcionalidades

- **Gestão de Clientes:** Cadastro completo com endereços e histórico de compras.
- **Sistema de Fidelidade:** Classificação automática em níveis (**Standard, Gold e Black**) com base no volume de gastos acumulados.
- **Processamento de Pedidos:** Validação de estoque, cálculo de subtotais e atualização automática do perfil do cliente.
- **Interface Dupla:** Operação via **Console (Menu Interativo)** e via **API REST**.
- **Tratamento de Erros:** Exceções personalizadas para estoque insuficiente e regras de negócio.

---

## 🧠 Regras de Negócio (Fidelidade)

O sistema utiliza o padrão de estratégia para definir o perfil do cliente conforme o valor total gasto:
* **Standard:** Perfil inicial.
* **Gold:** Gastos acumulados acima de **R$ 5.000,00**.
* **Black:** Gastos acumulados acima de **R$ 10.000,00**.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework:** Spring Boot 3.x
- **Gerenciador de Dependências:** Maven
- **Persistência:** Repositórios em memória (Simulando Banco de Dados com `List` e `Map`).
- **Documentação de API:** Postman Collection inclusa.

---

## 📂 Estrutura do Projeto

O projeto está organizado em camadas para garantir alta coesão e baixo acoplamento:

* `controller/`: Endpoints REST para Clientes e Pedidos.
* `service/`: Lógica de processamento de vendas e regras de fidelidade.
* `repository/`: Gerenciamento de dados (In-memory).
* `model/`: Entidades de domínio (Sandália, Cliente, Pedido, ItemPedido).
* `exception/`: Lógicas de erro como `EstoqueInsuficienteException`.
* `config/`: Carga de dados iniciais para testes rápidos.
* `util/`: Utilitários como `MoedaUtil`.

---

## 🔌 Endpoints Disponíveis (API)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/clientes` | Lista todos os clientes e seus perfis. |
| **GET** | `/pedidos` | Lista o histórico de pedidos realizados. |
| **POST** | `/pedidos` | Cria um novo pedido e atualiza o estoque/fidelidade. |

---

## 🔧 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/cristiano-brito/luxo-em-passos-backend.git](https://github.com/cristiano-brito/luxo-em-passos-backend.git)
