# 👠 Luxo em Passos - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![H2](https://img.shields.io/badge/database-H2-blue.svg?style=for-the-badge)
![Security](https://img.shields.io/badge/Checkmarx-Vulnerability_Free-brightgreen.svg?style=for-the-badge)

O **Luxo em Passos** é um sistema de gestão robusto para sapatarias de alto padrão. Ele automatiza o ciclo de venda de calçados de luxo, integrando o controle de estoque em tempo real com um motor de fidelidade dinâmico e uma API REST segura e padronizada.

---

## 🚀 Diferenciais do Projeto

- **Arquitetura em Camadas:** Separação clara entre Interfaces, Implementações (Services), Repositórios e Controladores.
- **Motor de Fidelidade Reativo:** O perfil do cliente evolui (Upgrade) ou regride (Downgrade) automaticamente conforme as movimentações financeiras são confirmadas ou canceladas.
- **Persistência Relacional:** Utiliza **Spring Data JPA** com banco de dados **H2** para garantir a integridade referencial entre Pedidos, Itens e Clientes.
- **Segurança de Negócio:** Validação rigorosa de estoque e tratamento de exceções customizadas para evitar inconsistências financeiras.
- **Segurança Proativa:** Stack atualizada para **Spring Boot 3.3.2**, mitigando vulnerabilidades críticas (CVEs) de Logback, Spring Core e dependências transitivas (json-smart).
- **Padronização de Resposta (Envelope Pattern):** Todas as requisições utilizam o padrão `ApiResponse`, garantindo consistência no consumo pelo frontend Angular, incluindo metadados de tempo de processamento.
- **Tratamento Global de Exceções:** Implementação de `RestControllerAdvice` para captura automática de erros de validação (Bean Validation), recursos não encontrados e regras de negócio.

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
- **Validation:** Jakarta Bean Validation (Hibernate Validator)
- **Database:** H2 (In-Memory / Database Console)
- **Design Patterns:** Strategy, Records (Java 17), Inversion of Control.

---

## 📂 Estrutura do Projeto

O projeto segue as melhores práticas de organização de pacotes:

* `dto/`: Objetos de transferência e o envelope padronizado de resposta.
* `model/`: Entidades JPA, Records de Perfil e Hooks de ciclo de vida (`@PrePersist`).
* `service/`: Interfaces e implementações contendo a inteligência do negócio.
* `repository/`: Interfaces JPA para comunicação com o banco de dados.
* `controller/`: Camada de exposição REST com validações de entrada via `@Valid`.
* `exception/`: Gerenciamento centralizado de erros e exceções customizadas.

---

## 🔄 Ciclo de Vida do Pedido

O sistema garante a integridade dos dados e do faturamento:
1. **Validação de Entrada:** Uso de `@NotBlank`, `@Email` e `@Positive` para blindar o banco de dados.
2. **Reserva de Estoque:** Garante que o item só seja vendido se houver saldo real.
3. **Faturamento Automático:** Registra o gasto e atualiza o perfil de fidelidade no momento da confirmação.
4. **Estorno Seguro:** Devolve itens ao estoque e recalcula o perfil do cliente em caso de cancelamento.

---

## 🔧 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/cristiano-brito/luxo-em-passos-backend.git](https://github.com/cristiano-brito/luxo-em-passos-backend.git)