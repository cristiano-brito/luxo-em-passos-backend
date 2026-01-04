# 👠 Luxo em Passos - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/license-GPLv3-blue.svg?style=for-the-badge)

O **Luxo em Passos** é um sistema de gestão para sapatarias de alto padrão, focado no controle de estoque de sandálias de luxo, gestão de clientes e processamento de pedidos com suporte a programas de fidelidade.

---

## 🚀 Funcionalidades

- **Gestão de Produtos:** Cadastro e controle de estoque de sandálias por categoria.
- **Sistema de Fidelidade:** Classificação automática de clientes em níveis (**Standard, Gold e Black**) com base no volume de compras.
- **Processamento de Pedidos:** Cálculo automático de subtotais e validação de estoque.
- **Interface Dupla:** Suporte para operações via **Console (Terminal)** e endpoints **REST API**.
- **Tratamento de Exceções:** Sistema robusto de erros para estoque insuficiente e regras de negócio.

---

## 🧠 Regras de Negócio (Fidelidade)

O sistema aplica automaticamente o perfil do cliente baseado no seu histórico de gastos:
* **Standard:** Nível inicial para todos os novos clientes.
* **Gold:** Clientes com gastos acumulados acima de **R$ 5.000,00**.
* **Black:** Nível VIP para clientes com gastos acima de **R$ 10.000,00**.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework:** Spring Boot 3.x
- **Gerenciador de Dependências:** Maven
- **Persistência:** Repositórios em memória (Simulando Banco de Dados).

---

## 📂 Estrutura do Projeto

O projeto segue uma arquitetura em camadas para facilitar a manutenção:

* `controller/`: Camada de entrada (API REST).
* `service/`: Regras de negócio e lógica do sistema.
* `repository/`: Simulação de persistência de dados.
* `model/`: Entidades principais (Cliente, Produto, Pedido).
* `exception/`: Tratamento de erros personalizados.

---

## 🔌 Endpoints Principais (API)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/clientes` | Lista todos os clientes e seus níveis. |
| **GET** | `/produtos` | Lista o catálogo de sandálias e estoque. |
| **POST** | `/pedidos` | Registra uma nova venda e atualiza estoque. |

---

## 🔧 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/cristiano-brito/luxo-em-passos-backend.git](https://github.com/cristiano-brito/luxo-em-passos-backend.git)
