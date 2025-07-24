# Aplicação de Encurtador de URLs

Esta é uma aplicação de encurtador de URLs construída com **Spring Boot**, projetada para criar URLs encurtadas, redirecionar para URLs originais e fornecer estatísticas de acesso. Ela utiliza MongoDB como banco de dados, Caffeine para cache e Docker para containerização. A aplicação segue uma arquitetura baseada em Use Cases para maior modularidade e manutenibilidade.

## Funcionalidades
- **Encurtamento de URL**: Gera URLs encurtadas únicas a partir de URLs longas.
- **Redirecionamento**: Redireciona usuários de URLs encurtadas para suas versões originais.
- **Estatísticas de Acesso**: Registra o número de acessos e calcula a média diária de acessos.
- **Cache**: Implementa cache LRU com Caffeine para otimizar o desempenho.
- **Suporte a Docker**: Containerizada com Docker e orquestrada via Docker Compose.

## Arquitetura
- **Padrão Use Case**: A lógica de negócios é encapsulada em classes de Use Case (ex.: `CadastrarUrlUseCase`, `AcessarUrlUseCase`).
- **API REST**: Exposta via Spring Web, com endpoints para criar, acessar e recuperar estatísticas.
- **Banco de Dados**: MongoDB armazena entidades de URL com campos como `shortUrl`, `originalUrl` e `accessCount`.
- **Cache**: Caffeine com `@Cacheable` e `@CacheEvict` para recuperação eficiente de dados e consistência.

## Pré-requisitos
- **Docker**: Certifique-se de que o Docker e o Docker Compose estão instalados no seu sistema.
- **Java 17**: Requerido para construir a aplicação (incluído na imagem Docker).
- **Maven**: Usado para gerenciamento de dependências (manipulado pelo build do Docker).

## Instalação e Execução

### 1. Clone o Repositório
```bash
git clone https://github.com/JoaoSipauba/encurtador-url
cd encurtador-url
```

### 2. Construa e Execute com Docker Compose
A aplicação é containerizada com Docker. Use o seguinte comando para construir e iniciar os serviços:
```bash
docker-compose up --build
```
- Isso vai:
    - Construir a imagem da aplicação Spring Boot usando o `Dockerfile`.
    - Iniciar uma instância do MongoDB.
    - Ligar a aplicação ao banco de dados via a rede `app_network`.

### 3. Verifique a Aplicação
Após os serviços serem iniciados, a aplicação estará disponível em `http://localhost:8080`.
- Verifique os logs do MongoDB para garantir que ele está pronto.
- Os logs da aplicação indicarão quando o servidor Spring Boot estiver ativo.

### 4. Uso da API
Use os seguintes endpoints para interagir com a aplicação:

- **Criar uma URL Encurtada** (POST)
  ```bash
  curl -X POST http://localhost:8080/ \
       -H "Content-Type: application/json" \
       -d '{"originalUrl":"https://example.com"}'
  ```
    - Resposta: `{"shortUrl":"http://localhost:8080/abc123"}` (status 201)

- **Acessar uma URL Encurtada** (GET)
    - Acesse `http://localhost:8080/abc123` no navegador ou use `curl`.
    - Redireciona para `https://example.com` (status 302).

- **Obter Estatísticas de Acesso** (GET)
  ```bash
  curl http://localhost:8080/abc123/stats
  ```
    - Resposta: `{"accessCount":5,"averageDailyAccess":1.25}` (status 200)

### 5. Parar a Aplicação
Para parar e remover os containers:
```bash
docker-compose down
```
Para remover os volumes (ex.: dados do MongoDB) também:
```bash
docker-compose down -v
```

## Configuração
- **Variáveis de Ambiente**: Definidas no `docker-compose.yml`:
    - `SPRING_DATA_MONGODB_URI`: String de conexão para o MongoDB (`mongodb://root:password@mongodb:27017/encurtador_db?authSource=admin`).
    - `APP_BASE_URL`: URL base para links encurtados (`http://localhost:8080` por padrão).
- **Customização**:
    - Ajuste as variáveis de ambiente no `docker-compose.yml` para produção (ex.: domínio real como `https://encurtador.tds.company` e segredos).

## Notas Adicionais
- **Data e Horário**: A aplicação registra o `createdAt` das URLs com base no horário do sistema, usando o fuso horário GMT (atualmente 04:56 PM -03, quinta-feira, 24 de julho de 2025).
- **Mecanismo de Cache**: O cache foi projetado para funcionar com uma única instância da aplicação. Caso haja mais de uma instância, será necessária uma implementação alternativa, como um cache distribuído (ex.: Redis), para garantir consistência entre os nós.