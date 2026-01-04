# 👠 Luxo em Passos - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![H2](https://img.shields.io/badge/database-H2-blue.svg?style=for-the-badge)

O **Luxo em Passos** é um sistema de gestão robusto para sapatarias de alto padrão. Ele automatiza o ciclo de venda de calçados de luxo, integrando o controle de estoque em tempo real com um motor de fidelidade dinâmico.

---

## 🚀 Diferenciais do Projeto

- **Arquitetura em Camadas:** Separação clara entre Interfaces, Implementações (Services), Repositórios e Controladores.
- **Motor de Fidelidade Reativo:** O perfil do cliente evolui (Upgrade) ou regride (Downgrade) automaticamente conforme as movimentações financeiras são confirmadas ou canceladas.
- **Persistência Relacional:** Utiliza **Spring Data JPA** com banco de dados **H2** para garantir a integridade referencial entre Pedidos, Itens e Clientes.
- **Segurança de Negócio:** Validação rigorosa de estoque e tratamento de exceções customizadas para evitar inconsistências financeiras.

---

## 🧠 Regras de Negócio (Fidelidade & Descontos)

O sistema implementa o padrão **Strategy** com **Sealed Interfaces** e **Records** para gerenciar os descontos:

| Perfil | Gatilho (Gasto Acumulado) | Desconto Real |
| :--- | :--- | :--- |
| **Standard** | R$ 0,00 | 0% |
| **Gold** | > R$ 1.500,00 | **5% OFF** |
| **Black** | > R$ 3.000,00 | **10% OFF** |

---

## 🛠️ Tecnologias Utilizadas

- **Core:** Java 17 / Spring Boot 3.x
- **ORM:** Spring Data JPA
- **Database:** H2 (In-Memory / Database Console)
- **Design Patterns:** Strategy, Records (Java 17), Inversion of Control.

---

## 📂 Estrutura do Projeto

O projeto segue as melhores práticas de organização de pacotes:

* `model/`: Entidades JPA e Records de Perfil (Standard, Gold, Black).
* `service/`: Interfaces e implementações (`ServiceImpl`) contendo a inteligência do negócio.
* `repository/`: Interfaces JPA para comunicação com o banco de dados.
* `controller/`: Camada de exposição REST (Endpoints).
* `exception/`: Gerenciamento de erros (ex: `EstoqueInsuficienteException`).
* `MenuConsole.java`: Interface CLI interativa para operação direta do sistema.

---

## 🔄 Ciclo de Vida do Pedido

O sistema garante que o faturamento só seja contabilizado após a confirmação:
1.  **Gerado:** Reserva o estoque e aplica o desconto do perfil atual.
2.  **Finalizado:** Registra o faturamento e atualiza o gasto acumulado do cliente (possível Upgrade).
3.  **Cancelado:** Realiza o estorno financeiro, devolve itens ao estoque e recalcula o perfil do cliente (possível Downgrade).

---

## 🔧 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/cristiano-brito/luxo-em-passos-backend.git](https://github.com/cristiano-brito/luxo-em-passos-backend.git)