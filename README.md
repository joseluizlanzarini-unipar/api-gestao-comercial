# API Gestão Comercial — Spring Boot + PostgreSQL

API REST didática pronta para ser consumida por um front-end Flutter. Inclui clientes, produtos, serviços, vendas, atualização de estoque, cancelamento, validações, CORS, Swagger, dados de exemplo e implantação no Render.

## 1. Requisitos locais

- Java 17
- Maven 3.9 ou superior

## 2. Executar localmente

O perfil padrão usa H2 em memória, portanto não exige PostgreSQL instalado.

```bash
mvn spring-boot:run
```

Acesse:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- Console H2: `http://localhost:8080/h2-console`

No H2 Console, use JDBC URL `jdbc:h2:mem:gestao`, usuário `sa` e senha vazia.

## 3. Endpoints

| Método | Endpoint | Função |
|---|---|---|
| GET/POST | `/api/clientes` | Listar/criar clientes |
| GET/PUT/DELETE | `/api/clientes/{id}` | Consultar/alterar/excluir |
| PATCH | `/api/clientes/{id}/status` | Alternar ativo/inativo |
| GET/POST | `/api/produtos` | Listar/criar produtos |
| GET/PUT/DELETE | `/api/produtos/{id}` | Consultar/alterar/excluir |
| PATCH | `/api/produtos/{id}/estoque` | Informar estoque absoluto |
| GET/POST | `/api/servicos` | Listar/criar serviços |
| GET/PUT/DELETE | `/api/servicos/{id}` | Consultar/alterar/excluir |
| GET/POST | `/api/vendas` | Listar/criar vendas |
| GET | `/api/vendas/{id}` | Consultar venda |
| PATCH | `/api/vendas/{id}/cancelamento` | Cancelar e devolver produtos ao estoque |

As listagens aceitam filtros: `/api/clientes?nome=ana`, `/api/produtos?nome=mouse`, `/api/servicos?nome=instalação` e `/api/vendas?clienteId=1`.

## 4. Exemplos JSON

### Criar cliente

```json
{
  "nome": "Maria da Silva",
  "documento": "12345678901",
  "email": "maria@email.com",
  "telefone": "(46) 99999-9999",
  "endereco": "Francisco Beltrão - PR",
  "ativo": true
}
```

### Criar produto

```json
{
  "nome": "Monitor 24 polegadas",
  "descricao": "Monitor Full HD",
  "preco": 899.90,
  "estoque": 10,
  "ativo": true
}
```

### Criar serviço

```json
{
  "nome": "Instalação de software",
  "descricao": "Instalação e configuração",
  "preco": 120.00,
  "ativo": true
}
```

### Criar venda

O preço é consultado no banco pela API; o front-end não informa o total.

```json
{
  "clienteId": 1,
  "itens": [
    { "tipo": "PRODUTO", "referenciaId": 1, "quantidade": 2 },
    { "tipo": "SERVICO", "referenciaId": 1, "quantidade": 1 }
  ]
}
```

## 5. Publicar no Render

1. Crie um repositório no GitHub e envie a pasta deste projeto.
2. No Render, selecione **New + → Blueprint**.
3. Conecte o repositório e confirme o arquivo `render.yaml`.
4. O Blueprint criará o PostgreSQL e o Web Service.
5. Aguarde o deploy e abra `https://SEU-SERVICO.onrender.com/swagger-ui.html`.

O Blueprint injeta automaticamente a variável `DATABASE_URL` usando a conexão interna e segura do PostgreSQL criado no mesmo ambiente.

Se o plano `free` não estiver disponível na conta/região, escolha manualmente um plano oferecido pelo Render e mantenha as mesmas variáveis de ambiente.

## 6. Consumir no Flutter

```dart
const baseUrl = 'https://SEU-SERVICO.onrender.com/api';
```

Para testar no emulador Android com a API local, use `http://10.0.2.2:8080/api`. No Flutter Web local, use `http://localhost:8080/api`.

## Observação didática

O CORS está liberado com `*` para facilitar o laboratório. Em produção real, altere `CORS_ALLOWED_ORIGINS` para o domínio autorizado. A API não possui autenticação porque o foco desta versão é CRUD, HTTP, JSON, consumo de API e gerenciamento de estado no Flutter.
