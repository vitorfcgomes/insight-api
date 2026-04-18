# 📊 Insight API

> Sistema backend de gestão e análise inteligente de projetos com integração de IA generativa.

---

## 💡 Conceito do Projeto

O **Insight API** nasceu para simular o core de negócio de uma empresa especializada em **Business Intelligence e Inteligência Artificial**. O sistema resolve um problema real: gestores que precisam acompanhar dezenas de projetos simultâneos de múltiplos clientes não têm visibilidade clara sobre o que está atrasado, qual cliente gera mais demanda ou qual é a receita consolidada.

A solução combina três camadas:

1. **Gestão** — CRUD completo de clientes e projetos com autenticação segura via JWT
2. **Análise** — Queries analíticas no PostgreSQL que transformam dados brutos em métricas de BI
3. **Inteligência** — Integração com IA generativa que interpreta as métricas e gera um resumo executivo em linguagem natural

O resultado é um endpoint que, ao ser chamado, entrega automaticamente um parágrafo como:

> *"A empresa possui 10 projetos distribuídos entre 5 clientes, com receita total de R$ 269.000. Existem 2 projetos atrasados que requerem atenção imediata. Os clientes com maior volume de projetos são Carlos Mendes e Fernanda Souza..."*

---

## 🏗️ Arquitetura

O projeto segue a **arquitetura em camadas** (Layered Architecture), padrão do ecossistema Spring Boot:

```
Request HTTP
     │
     ▼
┌─────────────────┐
│   Controller    │  ← Recebe a requisição e devolve a resposta
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    Service      │  ← Lógica de negócio, validações, orquestração
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Repository    │  ← Acesso ao banco de dados via Spring Data JPA
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   PostgreSQL    │  ← Banco de dados relacional
└─────────────────┘
```

### Fluxo de Autenticação (JWT)

```
POST /auth/login
      │
      ▼
AuthController
      │ cria UsernamePasswordAuthenticationToken
      ▼
AuthenticationManager
      │ chama
      ▼
UserDetailsServiceImpl ──── busca usuário ────▶ PostgreSQL
      │
      ▼ credenciais válidas
JwtUtil.generateToken()
      │
      ▼
{ "token": "eyJhbGci..." }
```

### Fluxo de Requisição Autenticada

```
GET /projects  +  Authorization: Bearer <token>
      │
      ▼
JwtAuthFilter (OncePerRequestFilter)
      │ extrai token do header
      │ chama JwtUtil.extractUsername()
      │ busca usuário no banco
      │ valida token com JwtUtil.isTokenValid()
      │ seta autenticação no SecurityContextHolder
      ▼
ProjectController → ProjectService → ProjectRepository → PostgreSQL
      │
      ▼
List<ProjectResponse>
```

### Fluxo do Insight com IA

```
GET /insight/executive  +  Authorization: Bearer <token>
      │
      ▼
InsightController → InsightService
      │
      ├── DashboardService.countByStatus()        → { IN_PROGRESS: 4, COMPLETED: 3... }
      ├── DashboardService.findDelayedProject()   → [ Projeto A, Projeto B ]
      ├── DashboardService.topClientsByProjects() → [ Carlos: 2, Fernanda: 2... ]
      └── DashboardService.totalRevenue()         → R$ 269.000,00
      │
      ▼ monta prompt formatado
RestClient → POST https://api.groq.com/openai/v1/chat/completions
      │       model: llama-3.3-70b-versatile
      ▼
{ "insight": "Resumo executivo gerado pela IA em português..." }
```

---

## 🛠️ Stack Tecnológica

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.5 | Framework base |
| Spring Security | 7.0.4 | Autenticação e autorização |
| Spring Data JPA | 4.0.4 | ORM e acesso ao banco |
| PostgreSQL | 18 | Banco de dados relacional |
| jjwt | 0.12.6 | Geração e validação de tokens JWT |
| Groq API | — | Inferência do modelo LLaMA 3.3 70B |
| SpringDoc OpenAPI | 3.0.2 | Documentação Swagger UI |
| Lombok | 1.18.44 | Redução de boilerplate |

---

## 📁 Estrutura do Projeto

```
src/main/java/management/insight_api/
├── controller/
│   ├── AuthController.java          # POST /auth/login e /auth/register
│   ├── ClientController.java        # CRUD de clientes
│   ├── ProjectController.java       # CRUD de projetos
│   ├── DashboardController.java     # Métricas analíticas de BI
│   └── InsightController.java       # GET /insight/executive (IA)
│
├── service/
│   ├── UserService.java             # Lógica de criação de usuários
│   ├── UserDetailsServiceImpl.java  # Integração com Spring Security
│   ├── ClientService.java           # Lógica de negócio de clientes
│   ├── ProjectService.java          # Lógica de negócio de projetos
│   ├── DashboardService.java        # Orquestração das métricas
│   └── InsightService.java          # Integração com API Groq
│
├── repository/
│   ├── UserRepository.java          # findByEmail
│   ├── ClientRepository.java        # CRUD padrão
│   ├── ProjectRepository.java       # CRUD padrão
│   └── DashboardRepository.java     # Queries analíticas @Query JPQL
│
├── model/
│   ├── User.java                    # Entidade usuário (implements UserDetails)
│   ├── Client.java                  # Entidade cliente
│   ├── Project.java                 # Entidade projeto
│   └── enums/
│       ├── UserRole.java            # ADMIN, ANALYST
│       └── ProjectStatus.java       # IN_PROGRESS, COMPLETED, DELAYED, CANCELLED
│
├── dto/
│   ├── request/
│   │   ├── LoginRequest.java
│   │   ├── RegisterUserRequest.java
│   │   ├── ClientRequest.java
│   │   ├── ProjectRequest.java
│   │   └── GroqRequest.java
│   └── response/
│       ├── LoginResponse.java
│       ├── RegisterUserResponse.java
│       ├── ClientResponse.java
│       ├── ProjectResponse.java
│       ├── ProjectStatusCount.java
│       ├── ClientProjectCount.java
│       ├── RevenueResponse.java
│       ├── GroqResponse.java
│       └── InsightResponse.java
│
├── config/
│   ├── SecurityConfig.java          # Rotas públicas, filtros, beans de segurança
│   ├── JwtUtil.java                 # Geração e validação JWT
│   ├── JwtAuthFilter.java           # Intercepta e valida cada requisição
│   └── SwaggerConfig.java           # Swagger com suporte a Bearer Auth
│
└── exception/
    ├── GlobalExceptionHandler.java  # @ControllerAdvice centralizado
    └── ErrorResponse.java           # Padrão de resposta de erro

src/main/resources/
├── application.properties           # Configurações via variáveis de ambiente
└── data.sql                         # Dados fictícios carregados na inicialização
```

---

## 🚀 Como Rodar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- PostgreSQL instalado e rodando
- Conta gratuita no [Groq Cloud](https://console.groq.com) para obter a API Key

### 1. Clone o repositório

```bash
git clone https://github.com/vitorfcgomes/insight-api.git
cd insight-api
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE insight_api;
```

### 3. Configure as variáveis de ambiente

Configure as seguintes variáveis no seu sistema ou na IDE (IntelliJ: `Run > Edit Configurations > Environment Variables`):

```env
DB_URL=jdbc:postgresql://localhost:5432/insight_api
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua-chave-secreta-longa-e-aleatoria-aqui
JWT_EXPIRATION=86400000
GROQ_API_KEY=sua_chave_groq_aqui
GROQ_API_URL=https://api.groq.com/openai/v1/chat/completions
```

> ⚠️ **Nota de segurança:** O endpoint `/auth/register` está público para facilitar os testes. Em produção, seria protegido ou removido, permitindo criação de usuários apenas por um ADMIN.

### 4. Execute o projeto

```bash
./mvnw spring-boot:run
```

O Hibernate cria as tabelas automaticamente e o `data.sql` insere os dados fictícios na primeira execução.

### 5. Acesse o Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Autenticação no Swagger

1. Faça `POST /auth/login` com as credenciais abaixo
2. Copie o valor do campo `token` na resposta
3. Clique no botão **Authorize** (cadeado) no topo do Swagger
4. Cole o token no campo e clique em **Authorize**
5. Todos os endpoints enviarão o token automaticamente

### Credenciais de Teste

| Usuário | Email | Senha | Role |
|---|---|---|---|
| Admin | `admin@prs.com` | `password` | ADMIN |
| Analista | `ana.lima@prs.com` | `password` | ANALYST |

---

## 📡 Endpoints

### Autenticação (público)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/register` | Cadastra novo usuário |
| `POST` | `/auth/login` | Autentica e retorna JWT |

### Clientes (requer JWT)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/clients` | Cria novo cliente |
| `GET` | `/clients` | Lista todos os clientes |
| `GET` | `/clients/{id}` | Busca cliente por ID |
| `PUT` | `/clients/{id}` | Atualiza cliente |
| `DELETE` | `/clients/{id}` | Remove cliente |

### Projetos (requer JWT)

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/projects` | Cria novo projeto |
| `GET` | `/projects` | Lista todos os projetos |
| `GET` | `/projects/{id}` | Busca projeto por ID |
| `PUT` | `/projects/{id}` | Atualiza projeto |
| `DELETE` | `/projects/{id}` | Remove projeto |

### Dashboard — BI (requer JWT)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/dashboard/projects/status` | Quantidade de projetos por status |
| `GET` | `/dashboard/projects/delayed` | Projetos com prazo vencido |
| `GET` | `/dashboard/clients/top` | Clientes com mais projetos |
| `GET` | `/dashboard/revenue` | Receita total consolidada |

### Insight com IA (requer JWT)

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/insight/executive` | Resumo executivo gerado por IA |

---

## 🤖 Exemplo de Resposta — Insight com IA

```json
{
  "insight": "**Resumo Executivo**\n\nA empresa possui 10 projetos distribuídos entre 5 clientes, com receita total consolidada de R$ 269.000,00. Do total de projetos, 3 foram concluídos com sucesso, 4 estão em andamento e 2 encontram-se atrasados, representando um ponto de atenção significativo. Recomenda-se priorizar a entrega dos projetos 'BI Financeiro' e 'Análise de Estoque', que já ultrapassaram o prazo previsto..."
}
```

---

## 📊 Dados Fictícios Incluídos

O sistema vem com dados pré-carregados para demonstração imediata:

- **2 usuários** — Admin e Analista (senha: `password`)
- **5 clientes** de diferentes setores (tecnologia, logística, varejo, saúde, indústria)
- **10 projetos** com status variados (COMPLETED, IN_PROGRESS, DELAYED, CANCELLED)
- **Receita total** de R$ 269.000,00
- **2 projetos atrasados** para demonstrar alertas


