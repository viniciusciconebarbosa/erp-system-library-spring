<p align="center">
  <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" width="90" alt="Spring Logo"/>
  &nbsp;&nbsp;
  <img src="https://www.vectorlogo.zone/logos/postgresql/postgresql-icon.svg" width="90" alt="PostgreSQL Logo"/>
  &nbsp;&nbsp;
  <img src="https://www.vectorlogo.zone/logos/rabbitmq/rabbitmq-icon.svg" width="90" alt="RabbitMQ Logo"/>
  &nbsp;&nbsp;
  <img src="https://www.docker.com/wp-content/uploads/2022/03/vertical-logo-monochromatic.png" width="80" alt="Docker Logo"/>
</p>

<h1 align="center">📚 ERP Biblioteca</h1>

<p align="center">
  <strong>Sistema de Gerenciamento de Livraria Comunitária</strong><br/>
  API RESTful robusta para controle de acervo, empréstimos e usuários,<br/>
  com autenticação JWT, mensageria assíncrona via RabbitMQ e deploy automatizado.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/PostgreSQL-17-336791?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL"/>
  <img src="https://img.shields.io/badge/RabbitMQ-3.x-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ"/>
  <img src="https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
  <img src="https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit"/>
</p>

---

## O Problema que Este Projeto Resolve

Livrarias comunitárias dependem frequentemente de processos manuais — cadernos de empréstimo, listas em planilha e controle de multas feito a mão. Isso gera inconsistência nos dados, perda de histórico e ausência de rastreio sobre o acervo.

O **ERP Biblioteca** resolve isso com uma API centralizada que automatiza o ciclo completo: cadastro de livros, empréstimos, devoluções e notificações — integrando-se com um microserviço de notificações via **RabbitMQ** para alertar usuários em tempo real sobre seus empréstimos e cadastros.

---

## Principais Funcionalidades

| | Funcionalidade | Descrição |
|---|---|---|
| 🔐 | **Autenticação JWT** | Login seguro com tokens de acesso. Roles `ADMIN` e `COMUM` com controle de acesso por endpoint. |
| 📖 | **Gestão de Acervo** | CRUD completo de livros com suporte a upload de capa (imagem), validação de tipo e tamanho. |
| 🔄 | **Controle de Empréstimos** | Empréstimo, devolução e histórico por usuário, com rastreio de status e prazos. |
| 👥 | **Gestão de Usuários** | Cadastro com senha criptografada (BCrypt), edição de perfil e administração de permissões. |
| 📨 | **Mensageria Assíncrona** | Ao cadastrar um usuário, uma mensagem é publicada no **RabbitMQ** para o microserviço de notificações processar e enviar o e-mail de boas-vindas. |
| 🗄️ | **Migrações Versionadas** | Schema do banco gerenciado com **Flyway** — histórico completo de alterações, sem surpresas em produção. |
| 🔍 | **Busca Avançada** | Filtros por título, autor, categoria e outros critérios para localização rápida no acervo. |
| 🎁 | **Cadastro de Doações** | Sistema para gerenciar contribuições ao acervo da livraria. |
| 📊 | **Relatórios** | Estatísticas sobre uso do acervo, empréstimos ativos e histórico de usuários. |
| 📖 | **Documentação Interativa** | Interface **Swagger/OpenAPI** disponível em `/swagger-ui.html` para testes manuais e integração por outros times. |
| 🐳 | **Pronto para Deploy** | Containerização completa com **Docker + Docker Compose** e pipeline CI/CD via **GitHub Actions**. |

---

## Decisões de Arquitetura

### Mensageria desacoplada (RabbitMQ)

O envio de e-mails não é responsabilidade desta API. Ao cadastrar um usuário, a API publica uma mensagem na exchange `notification.exchange` com routing key `notification.routingKey`. O microserviço [Notification Hub](https://github.com/viniciusciconebarbosa/notification-hub) consome essa fila e cuida do envio — incluindo refinamento de conteúdo com IA.

Isso garante:
- **Desacoplamento**: falha no serviço de e-mail não impacta o cadastro
- **Escalabilidade**: o serviço de notificação escala independentemente
- **Reutilização**: qualquer outro serviço pode publicar na mesma fila

### Profiles separados (`dev` / `prod`)

A aplicação usa Spring Profiles para isolar configurações de ambiente. Em `dev`, conecta no PostgreSQL local. Em `prod`, as variáveis são injetadas via Docker Compose. Isso evita configurações hardcoded e facilita o onboarding de novos devs.

### Flyway para migrações

Todo DDL é versionado em `src/main/resources/db/migration`. Nenhuma alteração no schema acontece fora do controle de versão — essencial para times e para rollback seguro em produção.

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/biblioteca/erp_biblioteca/
│   │   ├── config/
│   │   │   ├── RabbitMQConfig.java       # Exchange, Queue e Binding
│   │   │   └── SecurityConfig.java       # Spring Security + JWT filter
│   │   │
│   │   ├── controller/
│   │   │   ├── AuthController.java       # /api/auth (login, registro)
│   │   │   ├── UsuarioController.java    # /api/usuarios
│   │   │   └── LivroController.java      # /api/livros
│   │   │
│   │   ├── service/
│   │   │   ├── AuthService.java          # Registro + publicação no RabbitMQ
│   │   │   ├── UsuarioService.java
│   │   │   ├── LivroService.java
│   │   │   └── NotificationPublisher.java # Publica mensagens na fila
│   │   │
│   │   ├── dto/
│   │   │   ├── MessageBrokerDTO.java     # Contrato da mensagem RabbitMQ
│   │   │   ├── UsuarioDTO.java
│   │   │   └── LoginDTO.java
│   │   │
│   │   ├── model/                        # Entidades JPA
│   │   ├── repository/                   # Interfaces Spring Data JPA
│   │   ├── security/                     # JWT provider e filtros
│   │   └── exception/                    # Handlers globais
│   │
│   └── resources/
│       ├── db/migration/                 # Scripts Flyway versionados
│       ├── application.properties
│       └── application-prod.properties
│
└── test/                                 # Testes unitários e de integração
```

## Fotos

![javaSpring d0b80f42](https://github.com/user-attachments/assets/e690bb8c-4efe-4bbc-bba7-b3b84c8ce520)  
![foto](/example.jpg)

### Fluxo MessageBroke

```
POST /api/auth/registro
        │
        ▼
  Valida dados + verifica e-mail duplicado
        │
        ▼
  Cria usuário (BCrypt na senha)
        │
        ▼
  Salva no PostgreSQL
        │
        ▼
  Publica mensagem no RabbitMQ
  (exchange: notification.exchange)
        │
        ▼                    ┌─────────────────────────┐
  Retorna JWT + usuário      │   Notification Hub      │
                             │  consome a fila e envia │
                             │  e-mail de boas-vindas  │
                             └─────────────────────────┘
```

### Foto - Rabbit

<img width="1898" height="427" alt="dap" src="https://github.com/user-attachments/assets/3742d290-a6f9-4114-bfcf-79b3897c350d" />


---

## Tecnologias Utilizadas

### Backend
- **Java 21** com **Spring Boot 3.x**
- **Spring Security** para autenticação e autorização
- **JWT** para autenticação segura
- **PostgreSQL 17** rodando em container Docker
- **RabbitMQ** para mensageria assíncrona (notificações de cadastro)
- **Flyway** para migração de banco de dados
- **Maven** para gerenciamento de dependências
- **JUnit 5** para testes unitários
- **Swagger/OpenAPI** para documentação da API

### Frontend
- **Next.js 14** com **TypeScript**
- **Tailwind CSS** e **Material-UI** para estilização
- **React Query** para gerenciamento de dados
- **PWA** para funcionalidade offline

### Infraestrutura
- **DigitalOcean** como provedor de nuvem
- **Nginx** para proxy reverso e balanceamento de carga
- **GitHub Actions** para CI/CD
- **Vercel** para deploy do frontend

---

## Arquitetura do Sistema

### Backend
- **API RESTful**: Desenvolvida com Spring Boot, seguindo padrões de microserviços.
- **Autenticação**: JWT para segurança de endpoints.
- **Banco de Dados**: PostgreSQL 17 rodando em container Docker.
- **Mensageria**: RabbitMQ para comunicação assíncrona com o serviço de notificações.
- **Logging**: Sistema centralizado para rastreamento e auditoria.

### Frontend
- **Arquitetura de Componentes**: Componentes reutilizáveis com Next.js.
- **Gerenciamento de Estado**: Otimizado com React Query e contexto.
- **Roteamento Dinâmico**: Navegação fluida com SSR e SSG.
- **PWA**: Suporte a instalação e uso offline.

---

## Stack Técnica

| Camada | Tecnologia | Motivo |
|---|---|---|
| Framework | Spring Boot 3.3 | Ecossistema maduro, DI nativa, autoconfiguração |
| Linguagem | Java 21 | LTS atual, Records, pattern matching, performance |
| Segurança | Spring Security + JWT | Padrão enterprise para autenticação stateless |
| Banco de Dados | PostgreSQL 17 + Spring Data JPA | ORM robusto com suporte a queries complexas |
| Migrações | Flyway | Versionamento de schema integrado ao lifecycle do Spring |
| Mensageria | RabbitMQ + Spring AMQP | Desacoplamento do serviço de notificações |
| Documentação | Swagger / OpenAPI (SpringDoc) | Essencial para integração com frontend e outros times |
| Testes | JUnit 5 + Mockito | Padrão Java para testes unitários e de integração |
| Containerização | Docker + Docker Compose | Zero setup para rodar localmente ou em CI/CD |
| CI/CD | GitHub Actions | Deploy automatizado para DigitalOcean via SSH |

---

## Como Rodar

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 21 (para rodar localmente sem Docker)

### Setup com Docker

> **Pré-requisito:** Crie a rede compartilhada com o Notification Hub uma única vez:
> ```bash
> docker network create notification_network
> ```

```bash
# 1. Clone o repositório
git clone https://github.com/viniciusciconebarbosa/erp-system-library-spring.git
cd erp-system-library-spring

# 2. Configure as variáveis de ambiente
cp .env.example .env
# edite o .env com suas configurações

# 3. Suba os containers
docker compose up -d --build
```

| Serviço | URL |
|---|---|
| API REST | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| API Docs | `http://localhost:8080/api-docs` |

### Setup local (IntelliJ / linha de comando)

```bash
# Sobe apenas o banco
docker compose up -d db

# Roda a aplicação com perfil dev
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
![](https://github.com/user-attachments/assets/ebb82e2c-ff36-4e22-b702-41fc578d85ad)
---

## Integração com o Notification Hub

Este projeto publica mensagens no RabbitMQ que são consumidas pelo [Notification Hub](https://github.com/viniciusciconebarbosa/notification-hub) — microserviço responsável pelo envio de e-mails com refinamento via IA (Google Gemini).

**Contrato da mensagem publicada:**
```json
{
  "appId": "ERP_BIBLIOTECA",
  "recipientEmail": "usuario@email.com",
  "recipientName": "João Silva",
  "subject": "Bem-vindo à Biblioteca!",
  "content": "Olá João, seu cadastro foi realizado com sucesso!",
  "useAI": true
}
```

- **Exchange**: `notification.exchange`
- **Routing Key**: `notification.routingKey`
- **Queue**: `notification.queue`

---

## Testes

```bash
# Todos os testes
./mvnw test

# Com relatório de cobertura
./mvnw test jacoco:report

# Classe específica
./mvnw test -Dtest=UsuarioServiceTest
```
![](https://github.com/user-attachments/assets/ef5c2a81-c84b-48e7-91ce-cf538f9d91b1)

![](https://github.com/user-attachments/assets/6615919d-45d1-44ae-9fea-bc0c1c5a2287)  

Os testes utilizam **H2 em memória** (perfil `test`) — nenhuma dependência de banco externo para rodar a suíte.

---

## Infraestrutura

### Ambiente de Produção
- **Região**: DigitalOcean - NYC3
- **Recursos**:
  - **Banco de Dados**: PostgreSQL 17 rodando em container Docker
  - **Mensageria**: RabbitMQ em container Docker (rede compartilhada com serviço de notificações)
  - **Armazenamento**: Block Storage integrado
  - **Proxy Reverso**: Nginx para segurança e alta disponibilidade

### Configuração do Nginx
- **Portas**:
  - 80 (HTTP, redirecionado para HTTPS)
  - 443 (HTTPS)
  - 8080 (Aplicação Spring Boot)
- **Segurança**:
  - Certificados SSL/TLS
  - Headers de segurança (HSTS, X-Frame-Options)
  - Rate limiting e proteção contra DDoS
  - **Fail2Ban**: Bloqueio de IPs maliciosos, regras para SSH e endpoints da aplicação
- **Otimizações**:
  - Compressão Gzip
  - Cache de arquivos estáticos

### Ambientes
- **Produção**: Alta disponibilidade com monitoramento 24/7.
- **Testes**: Ambiente espelhado para validação.
- **CI/CD**: Integração contínua via GitHub Actions.

---

## Deploy

O pipeline de CI/CD via **GitHub Actions** executa automaticamente a cada push na branch `master`:

1. **CI**: Compila e executa todos os testes
2. **CD**: Se o CI passar, conecta via SSH no servidor DigitalOcean, atualiza o código e recria os containers com `docker compose up -d --build`

---

## Documentação Online

- **Swagger UI**: [api.n8nvinicius.dev/swagger-ui/index.html](https://api.n8nvinicius.dev/swagger-ui/index.html#/)
- **API Docs**: [api.n8nvinicius.dev/api-docs](https://api.n8nvinicius.dev/api-docs)
- **Frontend**: [erp-system-library-front.vercel.app](https://erp-system-library-front.vercel.app/login)
- **Repositório Frontend**: [github.com/viniciusciconebarbosa/erp-system-library-front](https://github.com/viniciusciconebarbosa/erp-system-library-front)

---

## Autor

Desenvolvido por **Vinicius Cicone Barbosa**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-viniciusciconebarbosa-0A66C2?style=flat-square&logo=linkedin)](https://linkedin.com/in/viniciusciconebarbosa)
[![GitHub](https://img.shields.io/badge/GitHub-viniciusciconebarbosa-181717?style=flat-square&logo=github)](https://github.com/viniciusciconebarbosa)
