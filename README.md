# Sistema de Gerenciamento de Biblioteca

Um sistema ERP robusto projetado para otimizar a gestão de bibliotecas comunitárias, oferecendo controle eficiente de acervo, empréstimos e usuários, com uma interface moderna e escalável.

---

## 📋 Índice
- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Stack Tecnológica](#stack-tecnológica)
- [Arquitetura](#arquitetura)
- [Infraestrutura](#infraestrutura)
- [Documentação](#documentação)
- [Desenvolvimento](#desenvolvimento)
- [Deploy](#deploy)
- [Monitoramento](#monitoramento)
- [Suporte](#suporte)
---

## 📖 Visão Geral

O **Sistema de Gerenciamento de Biblioteca** é uma solução completa para automatizar e otimizar processos em bibliotecas comunitárias. Desenvolvido com tecnologias modernas, o sistema oferece uma interface web responsiva e planos de expansão para aplicativos móveis, garantindo eficiência para administradores e uma experiência fluida para usuários finais.

---

## ✨ Funcionalidades

- **Gestão de Acervo**: Cadastro, edição e consulta de itens bibliográficos.
- **Controle de Empréstimos**: Automatização de empréstimos, devoluções e multas.
- **Busca Avançada**: Filtros por título, autor, categoria e outros critérios.
- **Gestão de Usuários**: Administração de perfis, permissões e histórico.
- **Sistema de Doações**: Integração para gerenciar contribuições ao acervo.
- **Relatórios em Tempo Real**: Estatísticas detalhadas sobre uso e desempenho.
- **Interface Acessível**: Design responsivo e compatível com padrões de acessibilidade.

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21** com **Spring Boot 3.x**
- **Spring Security** para autenticação e autorização
- **JWT** para autenticação segura
- **Oracle Database** com MySQL HeatWave
- **Maven** para gerenciamento de dependências
- **JUnit 5** para testes unitários
- **Swagger/OpenAPI** para documentação da API

### Frontend
- **Next.js 14** com **TypeScript**
- **Tailwind CSS** e **Material-UI** para estilização
- **React Query** para gerenciamento de dados
- **PWA** para funcionalidade offline

### Infraestrutura
- **Oracle Cloud Infrastructure (OCI)** como provedor de nuvem
- **Nginx** para proxy reverso e balanceamento de carga
- **GitHub Actions** para CI/CD
- **Vercel** para deploy do frontend

---

## 🏛️ Arquitetura do Sistema

### Backend
- **API RESTful**: Desenvolvida com Spring Boot, seguindo padrões de microserviços.
- **Autenticação**: JWT para segurança de endpoints.
- **Banco de Dados**: Oracle MySQL HeatWave com índices otimizados.
- **Cache**: Cache distribuído para melhorar desempenho.
- **Logging**: Sistema centralizado para rastreamento e auditoria.

### Frontend
- **Arquitetura de Componentes**: Componentes reutilizáveis com Next.js.
- **Gerenciamento de Estado**: Otimizado com React Query e contexto.
- **Roteamento Dinâmico**: Navegação fluida com SSR e SSG.
- **PWA**: Suporte a instalação e uso offline.

---

## ☁️ Infraestrutura

### Ambiente de Produção
- **Região**: Oracle Cloud São Paulo
- **Recursos**:
  - **Compute**: VM.Standard.E4.Flex
  - **Banco de Dados**: Oracle MySQL HeatWave
  - **Armazenamento**: Block Storage integrado
  - **Proxy Reverso**: Nginx para alta disponibilidade

### Configuração do Nginx
- **Portas**:
  - 80 (HTTP, redirecionado para HTTPS)
  - 443 (HTTPS)
  - 8080 (Aplicação Spring Boot)
- **Segurança**:
  - Certificados SSL/TLS
  - Headers de segurança (HSTS, X-Frame-Options)
  - Rate limiting e proteção contra DDoS
  - **Fail2Ban**:
    - Bloqueio de IPs maliciosos
    - Regras para SSH e endpoints da aplicação
    - Whitelist para IPs confiáveis
- **Otimizações**:
  - Compressão Gzip
  - Cache de arquivos estáticos
  - Balanceamento de carga (quando aplicável)

### Ambientes
- **Produção**: Alta disponibilidade com monitoramento 24/7.
- **Testes**: Ambiente espelhado para validação.
- **CI/CD**: Integração contínua via GitHub Actions.

---

## 📚 Documentação

### API
- **Swagger UI**: [Acesse a documentação interativa](https://minha1api.duckdns.org/swagger-ui/index.html#/)
- **Endpoints**: [Documentação técnica detalhada](endpoints.md)

### Frontend
- **Repositório**: [GitHub](https://github.com/viniciusciconebarbosa/erp-system-library-front)
- **Aplicação**: [Acesse a interface](https://erp-system-library-front.vercel.app/login)

---

## 🔧 Configuração e Desenvolvimento

### Pré-requisitos
- **Java 21**
- **Node.js 18+**
- **Maven**
- **Oracle Database**
- **Git**

### Passos para Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/biblioteca.git
   ```
2. Configure as variáveis de ambiente:
   ```bash
   cp .env.example .env
   ```
3. Configure as credenciais da Oracle Cloud no arquivo `.env`:
   ```properties
   ORACLE_CLOUD_USERNAME=seu_usuario
   ORACLE_CLOUD_PASSWORD=sua_senha
   ORACLE_CLOUD_TENANCY=seu_tenancy
   ORACLE_CLOUD_REGION=sao-paulo
   ```
4. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🚀 Deploy

### Pipeline de Produção
- **Ferramenta**: GitHub Actions
- **Etapas**:
  1. Validação de código (linting e formatação)
  2. Execução de testes unitários e de integração
  3. Build da aplicação
  4. Deploy na Oracle Cloud (backend) e Vercel (frontend)
  5. Validação pós-deploy
  6. Notificação de status via email ou Slack

---

## 📊 Monitoramento e Manutenção

### Métricas Monitoradas
- **Infraestrutura**: CPU, memória, disco e rede
- **Aplicação**: Latência de requisições, taxa de erros, throughput
- **Banco de Dados**: Uso, tempo de resposta e erros

### Alertas
- **CPU**: > 80% por 5 minutos
- **Memória**: > 85% por 5 minutos
- **Latência**: > 500ms em média
- **Erros**: > 1% de requisições com falha

### Backup
- **Frequência**: Diário, às 02:00
- **Retenção**: 30 dias
- **Restauração**: Disponível via console da Oracle Cloud

### Manutenção
- **Janela**: Domingo, 01:00–03:00 (horário de Brasília)
- **Notificação**: Enviada 48 horas antes
- **Rollback**: Estratégia automatizada em caso de falhas

---

## 📞 Suporte

- **Email**: [viniciuscicone@gmail.com](mailto:viniciuscicone@gmail.com)
- **Documentação**: [Wiki do Projeto](link-para-wiki)
- **Issues**: [GitHub Issues](link-para-issues)
- **Horário de Atendimento**: Segunda a sexta, 09:00–17:00 (horário de Brasília)

---

**Desenvolvido por Vinicius Ciccone Barbosa**
