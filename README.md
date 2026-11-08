# Gestão de Pedidos — Microsserviço

API REST para gerenciar clientes e pedidos de vinho da Vinheria Agnello.

- **Porta:** 8083
- **Stack:** Java 17 + Jersey (JAX-RS) + Grizzly + Oracle JDBC
- **Rede Docker:** vinheria-network

---

## Rotas disponíveis

### Clientes — `/clientes`

| Método | Rota        | Descrição              |
|--------|-------------|------------------------|
| GET    | /clientes   | Lista todos os clientes |
| POST   | /clientes   | Cadastra novo cliente  |

**Body do POST /clientes:**
```json
{
  "nome": "Ana Lima",
  "email": "ana@email.com",
  "assunto": "Duvida sobre entrega",
  "mensagem": "Gostaria de saber o prazo de entrega para o Sul.",
  "dataEnvio": "2026-11-08T18:00:00.000"
}
```

---

### Pedidos — `/pedidos`

| Método | Rota           | Descrição              |
|--------|----------------|------------------------|
| GET    | /pedidos       | Lista todos os pedidos |
| POST   | /pedidos       | Registra novo pedido   |
| DELETE | /pedidos/{id}  | Remove um pedido       |

**Body do POST /pedidos:**
```json
{
  "idVinho": 1,
  "idCliente": 1,
  "canalVenda": "E-commerce",
  "regiaoEntrega": "Sudeste",
  "quantidade": 3,
  "precoUnitario": 129.28,
  "descontoAplicado": 10,
  "dataVenda": "2026-11-08T18:00:00.000",
  "temporada": "Natal",
  "avaliacaoCliente": 5,
  "vendaSucesso": true
}
```

**Canais de venda aceitos:** `Loja Fisica`, `E-commerce`, `WhatsApp`, `Telefone`, `Parceiro`

**Regiões aceitas:** `Norte`, `Sul`, `Sudeste`, `Nordeste`, `Centro-Oeste`

---

## Como configurar o banco de dados

Abra o arquivo abaixo e preencha com suas credenciais Oracle:

```
src/main/java/br/com/vinheria/pedidos/infra/DatabaseConnection.java
```

```java
private static final String URL     = "sua_url_oracle";
private static final String USUARIO = "seu_usuario";
private static final String SENHA   = "sua_senha";
```

### Tabelas necessárias no Oracle

Execute os scripts abaixo antes de subir o serviço:

```sql
-- Tabela de clientes
CREATE TABLE tb_clientes (
    id          NUMBER PRIMARY KEY,
    nome        VARCHAR2(200) NOT NULL,
    email       VARCHAR2(200) NOT NULL,
    assunto     VARCHAR2(200),
    mensagem    VARCHAR2(500) NOT NULL,
    data_envio  TIMESTAMP
);

CREATE SEQUENCE seq_tb_clientes START WITH 1 INCREMENT BY 1;

-- Tabela de pedidos
CREATE TABLE tb_pedidos (
    id                  NUMBER PRIMARY KEY,
    id_vinho            NUMBER NOT NULL,
    id_cliente          NUMBER,
    canal_venda         VARCHAR2(50) NOT NULL,
    regiao_entrega      VARCHAR2(50) NOT NULL,
    quantidade          NUMBER NOT NULL,
    preco_unitario      NUMBER(10,2) NOT NULL,
    desconto_aplicado   NUMBER,
    data_venda          TIMESTAMP NOT NULL,
    temporada           VARCHAR2(50),
    avaliacao_cliente   NUMBER,
    venda_sucesso       NUMBER(1) NOT NULL
);

CREATE SEQUENCE seq_tb_pedidos START WITH 1 INCREMENT BY 1;
```

---

## Como rodar localmente (sem Docker)

```bash
mvn compile exec:java
```

A API sobe em `http://localhost:8083/`

---

## Como configurar o pipeline no Jenkins

> Pré-requisito: Jenkins rodando via docker-compose (veja o README raiz de vinheria7).

### Passo 1 — Acessar o Jenkins

Abra o navegador e acesse:
```
http://localhost:8080
```

Na primeira vez, o Jenkins pede uma senha de desbloqueio.
Execute o comando abaixo para obtê-la:

```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Cole a senha na tela do Jenkins e clique em **Continue**.

---

### Passo 2 — Instalar os plugins sugeridos

Na tela seguinte, clique em **"Install suggested plugins"** e aguarde.
Isso instala automaticamente os plugins mais usados, incluindo o de Pipeline e Git.

---

### Passo 3 — Criar o usuário admin

Preencha nome, senha e email do seu usuário admin e clique em **Save and Continue**.

Na próxima tela, mantenha a URL `http://localhost:8080/` e clique em **Save and Finish**.

---

### Passo 4 — Permitir que o Jenkins use o Docker

O Jenkins precisa ter permissão para executar o Docker do host.
Execute o comando abaixo **no terminal do seu PC** (não dentro do container):

```bash
docker exec -u root jenkins chmod 666 /var/run/docker.sock
```

> Esse passo é necessário porque o Jenkins roda como usuário não-root dentro do container.
> Sem isso, os comandos `docker build` e `docker run` no Jenkinsfile vão falhar com erro de permissão.

---

### Passo 5 — Instalar o Maven no Jenkins

O pipeline usa `mvn` para compilar. Vamos configurar o Maven dentro do Jenkins.

1. No painel do Jenkins, clique em **Manage Jenkins** (menu lateral)
2. Clique em **Tools**
3. Role a página até encontrar a seção **Maven**
4. Clique em **Add Maven**
5. Preencha:
   - **Name:** `Maven 3`
   - Marque a opção **"Install automatically"**
   - Selecione a versão mais recente disponível
6. Clique em **Save**

---

### Passo 6 — Criar o job do pipeline

1. No painel principal, clique em **"New Item"**
2. Preencha o nome: `gestao-pedidos`
3. Selecione **"Pipeline"** e clique em **OK**

---

### Passo 7 — Configurar o pipeline

Você vai ver a tela de configuração do job. Faça o seguinte:

**Na seção "Build Triggers":**
- Marque a opção **"Poll SCM"**
- No campo **Schedule**, coloque:
  ```
  H/2 * * * *
  ```
  Isso faz o Jenkins verificar o repositório a cada 2 minutos.

**Na seção "Pipeline":**
- Em **Definition**, selecione: `Pipeline script from SCM`
- Em **SCM**, selecione: `Git`
- Em **Repository URL**, coloque a URL do seu repositório Git do `gestao-pedidos`
  - Exemplo GitHub: `https://github.com/seu-usuario/gestao-pedidos.git`
  - Exemplo local: `file:///caminho/para/gestao-pedidos`
- Em **Branch Specifier**, coloque: `*/master`
- Em **Script Path**, deixe: `Jenkinsfile`

Clique em **Save**.

---

### Passo 8 — Rodar o pipeline pela primeira vez

1. Na página do job `gestao-pedidos`, clique em **"Build Now"** (menu lateral esquerdo)
2. Vai aparecer um item em **"Build History"** com um número (ex: #1)
3. Clique no número e depois em **"Console Output"** para acompanhar a execução em tempo real

Você vai ver algo assim:
```
Started by user admin
[Pipeline] Start of Pipeline
[Pipeline] stage (Checkout)
Clonando repositorio gestao-pedidos...
[Pipeline] stage (Build)
Compilando gestao-pedidos com Maven...
[INFO] BUILD SUCCESS
[Pipeline] stage (Docker Build)
Gerando imagem Docker...
[Pipeline] stage (Deploy)
Realizando deploy do gestao-pedidos...
gestao-pedidos
Finished: SUCCESS
```

---

### Passo 9 — Verificar se o serviço subiu

Após o pipeline terminar com **SUCCESS**, teste a API:

```bash
curl http://localhost:8083/clientes
```

Deve retornar um array JSON (vazio se o banco estiver sem dados):
```json
[]
```

---

## O que acontece quando você faz git push

1. Você altera algum arquivo e faz `git push`
2. Em até 2 minutos, o Jenkins detecta a mudança (Poll SCM)
3. O pipeline roda automaticamente: build → imagem Docker → deploy
4. O container antigo é parado e substituído pelo novo
5. A API já está atualizada sem você fazer nada manualmente

---

## Erros comuns

| Erro | Causa | Solução |
|------|-------|---------|
| `Got permission denied while trying to connect to Docker` | Jenkins sem permissão no socket | Execute o Passo 4 novamente |
| `mvn: command not found` | Maven não configurado | Refaça o Passo 5 |
| `Connection refused` na porta 8083 | Container não subiu | Verifique o Console Output do pipeline |
| `ORA-01017: invalid username/password` | Credenciais Oracle erradas | Corrija o DatabaseConnection.java e refaça o push |
