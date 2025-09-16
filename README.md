# Sistema de Gerenciamento de Livraria

Um sistema ERP robusto projetado para otimizar a gestão de Livraria comunitária, oferecendo controle eficiente de acervo, empréstimos e usuários, com uma interface moderna e escalável.

---

## 📑 Índice
- [Visão Geral](#visão-geral)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Sistema](#arquitetura-do-sistema)
- [Infraestrutura](#infraestrutura)
- [Documentação](#documentação)
- [Configuração e Desenvolvimento](#configuração-e-desenvolvimento)
- [Deploy](#deploy)
- [Monitoramento e Manutenção](#monitoramento-e-manutenção)
- [Suporte](#suporte)
---

## 📖 Visão Geral

O **Sistema de Gerenciamento de Livraria** é uma solução completa para automatizar e otimizar processos em Livraria comunitária. Desenvolvido com tecnologias modernas, o sistema oferece uma interface web responsiva e planos de expansão para aplicativos móveis, garantindo eficiência para administradores e uma experiência fluida para usuários finais.


## Foto

![javaSpring d0b80f42](https://github.com/user-attachments/assets/e690bb8c-4efe-4bbc-bba7-b3b84c8ce520)


---

## ✨ Funcionalidades

- **Gestão de Acervo**: Cadastro, edição e consulta de itens bibliográficos.
- **Controle de Empréstimos**: Automatização de empréstimos, devoluções e multas.
- **Busca Avançada**: Filtros por título, autor, categoria e outros critérios.
- **Gestão de Usuários com Sistema de Login e Registro**: Administração de perfis, permissões e histórico.
- **Sistema de Cadastro de Doações**: Integração para gerenciar contribuições ao acervo.
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
- **Java 21** ou superior
- **Maven 3.6+**
- **MySQL 8.0+** (para desenvolvimento local)
- **Git**

### Passos para Configuração

#### 1. Clone o repositório
```bash
git clone https://github.com/viniciusciconebarbosa/erp-system-library-spring.git
cd erp-system-library-spring
```

#### 2. Configure o banco de dados MySQL
Crie um banco de dados MySQL local:
```sql
CREATE DATABASE erp_biblioteca;
```

#### 3. Configure as variáveis de ambiente
```bash
# Copie o arquivo de exemplo
cp env.example .env

# Edite o arquivo .env com suas configurações
nano .env
```

Configure as seguintes variáveis no arquivo `.env`:
```properties
# Configurações do Banco de Dados
DB_URL=jdbc:mysql://localhost:3306/erp_biblioteca?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
DB_USER=seu_usuario_mysql
DB_PASSWORD=sua_senha_mysql

# Configurações JWT
JWT_SECRET=sua-chave-secreta-super-segura-para-jwt-token
JWT_EXPIRATION=86400000
```




#### 4. Execute o projeto
```bash
# Instalar dependências e executar
./mvnw clean install
./mvnw spring-boot:run
```
<img width="1920" height="263" alt="image" src="https://github.com/user-attachments/assets/baf5bcd1-e839-4138-9448-a438db787c59" />

<img width="1897" height="518" alt="image" src="https://github.com/user-attachments/assets/ebb82e2c-ff36-4e22-b702-41fc578d85ad" />


O projeto estará disponível em:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### 🧪 Executando os Testes

#### Executar todos os testes
```bash
./mvnw test
```

#### Executar testes com relatório detalhado
```bash
./mvnw test -Dtest=*Test
```

#### Executar testes de uma classe específica
```bash
./mvnw test -Dtest=UsuarioServiceTest
```

#### Executar testes com cobertura
```bash
./mvnw test jacoco:report
```

#### Executar apenas testes de integração
```bash
./mvnw test -Dtest=*ControllerTest
```
<img width="1896" height="532" alt="image" src="https://github.com/user-attachments/assets/ef5c2a81-c84b-48e7-91ce-cf538f9d91b1" />


<img width="1903" height="745" alt="image" src="https://github.com/user-attachments/assets/6615919d-45d1-44ae-9fea-bc0c1c5a2287" />


### 📊 Estrutura dos Testes
- **Testes Unitários**: Localizados em `src/test/java/`
- **Testes de Integração**: Testes de controllers e serviços
- **Banco de Teste**: Utiliza H2 em memória para testes
- **Relatórios**: Gerados em `target/surefire-reports/`

### 🔧 Comandos Úteis para Desenvolvimento

#### Limpar e recompilar
```bash
./mvnw clean compile
```

#### Executar em modo de desenvolvimento (com hot reload)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```


#### Verificar dependências
```bash
./mvnw dependency:tree
```

#### Gerar documentação da API
```bash
./mvnw spring-boot:run
# Acesse: http://localhost:8080/swagger-ui.html
```
<img width="1901" height="927" alt="image" src="https://github.com/user-attachments/assets/4a3d0d38-ac64-46e2-8df4-95ab1c944b75" />

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
