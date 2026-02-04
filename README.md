# 👠 Luxo em Passos - Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![SaaS](https://img.shields.io/badge/Multi--Tenancy-Enabled-gold.svg?style=for-the-badge)
![H2](https://img.shields.io/badge/database-H2-blue.svg?style=for-the-badge)

O **Luxo em Passos** é um sistema de gestão SaaS robusto para boutiques de calçados de alto padrão. Ele automatiza o ciclo de venda, integrando o controle de estoque em tempo real com um motor de fidelidade dinâmico e isolamento total de dados entre lojistas.

---

## 🚀 Diferenciais do Projeto

- **Arquitetura Multi-tenancy (SaaS):** Isolamento rigoroso de dados entre lojas utilizando **Hibernate Filters** e **AOP** (Programação Orientada a Aspectos). Cada requisição é blindada por um identificador de inquilino.
- **Integridade de Dados & Unicidade:** Implementação de constraints compostas (`CPF + Tenant` e `Email + Tenant`). O **CPF** atua como âncora obrigatória de unicidade por boutique. O **E-mail**, agora tratado como campo opcional para reduzir o atrito no cadastro, mantém a validação de unicidade sempre que fornecido, impedindo conflitos de identidade dentro de um mesmo lojista.
- **Alta Performance & Desacoplamento:** Uso extensivo de **Java Records (DTOs)** para reduzir o payload das respostas e evitar o problema de N+1 queries, garantindo tempos de resposta sub-50ms.
- **Motor de Fidelidade Dinâmico:** O perfil do cliente (`Standard`, `Gold`, `Black`) evolui automaticamente com base no faturamento acumulado, calculado em tempo real pelo backend.
- **Envelope Pattern:** Padronização global de respostas via `ApiResponse<T>`, facilitando o consumo pelo Frontend e garantindo metadados de auditoria e performance.
- **Serialização Inteligente:** Configuração de Jackson para omitir campos nulos, garantindo payloads leves e otimizados para o consumo mobile/web.
- **Segurança Proativa:** Mitigação de vulnerabilidades (CVEs) através da stack **Spring Boot 3.3.2** e ocultação de IDs de infraestrutura (como `tenant_id`) nas camadas externas.

---

## 🛡️ Contrato de Integração (Multi-tenancy)

Para interagir com a API, é **obrigatório** o envio do identificador da loja no cabeçalho de todas as requisições:

| Header | Descrição | Exemplo |
| :--- | :--- | :--- |
| `X-Tenant-ID` | Identificador único da boutique | `boutique-salvador` |

> **Nota:** Requisições sem este header retornarão erro `422 Unprocessable Entity` para garantir que nenhum dado seja gravado ou lido sem um proprietário definido.

### Exemplo de Resposta (JSON Otimizado)
```json
{
    "sucesso": true,
    "mensagem": "Lista de clientes recuperada com sucesso!",
    "dados": [
        {
            "id": 1,
            "nome": "Sophia Loren",
            "email": "sophia@luxo.com",
            "perfil": "STANDARD",
            "gastoTotalAcumulado": 0
        }
    ],
    "tempoProcessamentoMs": 32
}
```
---

## 🧠 Regras de Negócio (Fidelidade)

| Perfil | Gatilho (Gasto Mensal/Total) | Benefício |
| :--- | :--- | :--- |
| **Standard** | R$ 0,00 | Preço Base |
| **Gold** | > R$ 1.500,00 | **5% de Desconto** |
| **Black** | > R$ 3.000,00 | **10% de Desconto** |

---

## 🛠️ Tecnologias & Patterns

- **Java 17 / Spring Boot 3.3.2**
- **Spring Data JPA & Hibernate Filters** (Isolamento de dados)
- **Spring AOP** (Ativação automática de filtros de segurança)
- **Jakarta Bean Validation** (Integridade de dados)
- **Jackson Customization** (Otimização de JSON `non_null`)
- **H2 Database** (Persistência em memória para desenvolvimento)
- **Design Patterns:** Strategy, ThreadLocal Context, Interceptor, Envelope Pattern.

---

## 📂 Endpoints Principais

### Clientes (`/api/clientes`)
* `GET /`: Lista clientes da loja informada no header.
* `POST /`: Cadastra novo cliente (vincula automaticamente ao Tenant).
* `GET /{id}`: Detalha cliente específico.

### Vendas (`/api/pedidos`)
* `POST /`: Registra nova venda e abate estoque.
* `PUT /{protocolo}/finalizar`: Confirma faturamento e atualiza perfil VIP.
* `DELETE /{protocolo}`: Cancela venda e estorna estoque/fidelidade.

---

## 🔧 Configuração e Execução

### Pré-requisitos
* Java 17+
* Maven 3.8+

### Passos
1. **Clonagem:**
   ```bash
   git clone [https://github.com/cristiano-brito/luxo-em-passos-backend.git](https://github.com/cristiano-brito/luxo-em-passos-backend.git)