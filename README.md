# Cliente REST — Imobiliárias

Aplicação web **cliente** que consome a API REST do servidor *Imobiliária* para
fazer o **CRUD de imobiliárias** (cadastrar, listar, ver detalhes, editar e
remover). Não tem banco de dados próprio: toda a persistência acontece no
servidor. Aqui ficam apenas as telas e as chamadas HTTP à API.

Construída com **Spring Boot 4**, **Spring MVC**, **Thymeleaf** (telas) e
**RestClient** (chamadas à API). Segue a ideia do exemplo do Roteiro 09.

## Arquitetura

```
Navegador  ─►  Cliente (:8081)  ─►  API do servidor Imobiliária (:8080)
              Thymeleaf + MVC        /api/imobiliarias (GET/POST/PUT/DELETE)
              RestClient
```

- O **servidor** (projeto Imobiliária) roda em `http://localhost:8080` e expõe
  `/api/imobiliarias` — liberado (`permitAll`) e com CORS habilitado.
- Este **cliente** roda em `http://localhost:8081`, renderiza as telas com
  Thymeleaf e conversa com a API usando o `RestClient`.

| Camada | Arquivo |
| ------ | ------- |
| Model (DTO JSON) | `model/Imobiliaria.java` |
| RestClient (base URL) | `config/RestClientConfig.java` |
| Acesso à API (CRUD) | `service/ImobiliariaService.java` |
| Telas (Spring MVC) | `controller/ImobiliariaController.java` |
| Views | `templates/index.html`, `templates/imobiliarias/*.html` |

## Rotas do cliente

| Método | Rota | Ação | Chamada à API |
| ------ | ---- | ---- | ------------- |
| GET  | `/imobiliarias`             | Lista             | GET `/api/imobiliarias` |
| GET  | `/imobiliarias/novo`        | Formulário novo   | — |
| POST | `/imobiliarias`             | Cria              | POST `/api/imobiliarias` |
| GET  | `/imobiliarias/{id}`        | Detalhe           | GET `/api/imobiliarias/{id}` |
| GET  | `/imobiliarias/{id}/editar` | Formulário editar | GET `/api/imobiliarias/{id}` |
| POST | `/imobiliarias/{id}`        | Atualiza          | PUT `/api/imobiliarias/{id}` |
| POST | `/imobiliarias/{id}/remover`| Remove            | DELETE `/api/imobiliarias/{id}` |

## Pré-requisitos

- **Java 17** ou superior
- O **servidor Imobiliária** rodando em `http://localhost:8080`
- Não precisa instalar o Maven: use o wrapper (`mvnw`) que já vem no projeto

## Como usar

1. Suba o **servidor Imobiliária** em `http://localhost:8080`.

2. Suba este cliente a partir da raiz do projeto:

   ```powershell
   # Windows (PowerShell)
   .\mvnw.cmd spring-boot:run
   ```

   ```bash
   # Linux / macOS
   ./mvnw spring-boot:run
   ```

3. Abra **http://localhost:8081** no navegador e clique em
   *Gerenciar Imobiliárias (CRUD)*.

A partir daí você pode:

- **Listar** todas as imobiliárias cadastradas;
- **Cadastrar** uma nova (botão *+ Nova imobiliária*);
- **Ver detalhes** clicando no nome;
- **Editar** os dados;
- **Excluir** (com confirmação).

## Configuração

A URL da API pode ser ajustada em
`src/main/resources/application.properties`, na propriedade `api.base-url`
(padrão: `http://localhost:8080`). Útil se o servidor estiver em outra porta
ou máquina.

## Build

Para gerar o `.jar` executável:

```powershell
.\mvnw.cmd clean package
java -jar target\cliente-0.0.1-SNAPSHOT.jar
```
