# Desenvolvimento Web 3

Repositório da disciplina de Programação para Web 3 (IFSP). Contém anotações das aulas e o projeto prático `estanteweb`.

## Estrutura

- `docs/Resumos_Prova/ResumoDesenvolvimentoWeb3.md`: Notas e resumos teóricos (aulas 1 a 9).
- `Projeto/docker-compose.yml`: Orquestração dos containers da aplicação e do MySQL.
- `Projeto/estanteweb/`: Aplicação Spring Boot e Dockerfile.

## Estante Web

Aplicação web para catálogo e gerenciamento de livros, sugestões e reservas.

### Stack

- Java 21
- Spring Boot 4 (Spring MVC)
- Apache FreeMarker (`.ftlh`)
- Spring JDBC (`JdbcTemplate`)
- Spring Session JDBC (`HttpSession`)
- MySQL 8
- Flyway
- Docker & Docker Compose

### Como rodar

#### Opção 1: Com Docker Compose (recomendado)

Inicie os containers da aplicação e do MySQL a partir da pasta `Projeto/`:

```bash
cd Projeto
docker compose up -d --build
```

Acesse em `http://localhost:8081`.

Comandos úteis:
- Ver logs da aplicação: `docker compose logs -f app`
- Recompilar após alterar o código: `docker compose up -d --build app`
- Parar containers: `docker compose down`

#### Opção 2: Localmente (Spring Boot com MySQL no Docker)

Caso prefira rodar com o Spring Boot DevTools ativo na sua máquina:

1. Suba apenas o MySQL:
```bash
cd Projeto
docker compose up -d mysql
```

2. Execute a aplicação via Maven:
```bash
cd estanteweb
./mvnw spring-boot:run
```

### Rotas

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/` | Página inicial | Público |
| GET | `/login` | Formulário de login | Público |
| POST | `/login` | Autentica usuário e salva `usuarioLogado` na sessão | Público |
| POST | `/logout` | Invalida a sessão e redireciona para `/login` | Público |
| GET | `/livros` | Listagem de livros (aceita `?ordem=titulo|autor|ano|preco`) | Público |
| GET | `/livros/{id}` | Ficha detalhada do livro (com links para editar e remover quando logado) | Público |
| GET | `/livros/editar/{id}` | Formulário de edição com dados atuais pré-carregados | Autenticado |
| POST | `/livros/editar/{id}` | Salva alterações com validação e padrão PRG | Autenticado |
| GET | `/livros/excluir/{id}` | Tela de confirmação antes de remover o livro | Autenticado |
| POST | `/livros/excluir/{id}` | Remoção segura do livro no banco de dados | Autenticado |
| GET | `/livros/novo` | Formulário de cadastro de livro | Autenticado |
| POST | `/livros` | Cadastro de livro (com validação e PRG) | Autenticado |
| GET | `/livros/busca` | Busca por título (`?titulo=...`) | Público |
| GET | `/livros/total` | Contagem total de livros | Público |
| GET | `/livros/valor-total` | Soma dos preços dos livros | Público |
| GET | `/sugestoes` | Listagem de sugestões | Público |
| GET | `/sugestoes/nova` | Formulário de sugestão | Público |
| POST | `/sugestoes` | Salvar sugestão no banco | Público |
| GET | `/contato` | Formulário de contato | Público |
| POST | `/contato` | Envio de contato | Público |
| GET | `/reserva` | Formulário de reserva | Público |
| POST | `/reserva` | Processamento de reserva | Público |

### Autenticação e Sessão

- **Login (`LoginController` / `login.ftlh`):** valida e-mail e senha via `UsuarioRepository` e grava o objeto `Usuario` na `HttpSession` sob a chave `usuarioLogado`.
- **Cabeçalho e interface (`cabecalho.ftlh`, `livros.ftlh`, `livro.ftlh`):** com `spring.freemarker.expose-session-attributes=true`, exibe o nome do usuário logado e o botão **Sair** (ou o link **Entrar** para visitantes), além de ocultar botões/links de cadastro, edição e exclusão para quem não está autenticado.
- **Proteção no servidor (`LivroController`):** rotas administrativas de criação, edição e exclusão verificam `usuarioLogado` na sessão e redirecionam para `/login` caso não haja usuário autenticado.
- **Credencial inicial:** `marta@estanteweb.com` / `estante123`.

### Banco de dados

As migrações do Flyway (`Projeto/estanteweb/src/main/resources/db/migration/`) são executadas automaticamente na inicialização:

- `V1__criar_tabela_livro.sql`: DDL da tabela `livro`
- `V2__criar_tabela_sugestao.sql`: DDL da tabela `sugestao`
- `V3__inserir_livros_iniciais.sql`: Carga inicial de livros
- `V4__criar_tabela_usuario.sql`: DDL da tabela `usuario` e inserção da usuária inicial (`Marta`)
