# Endpoints

Todos os endpoints aceitam e produzem _application/json_ .

### `GET /api/payments/`
* Retorna todos os agendamentos. Use o parâmetro opcional de busca _status_ para filtrar por status. 
* Parâmetros de busca:
    - _status_ `PENDING | PAID` (opcional)
 
* Exemplo:
    - Request URL: `localhost:8080/api/payments?status=PENDING`
    - Response:
```JSON
{
    "data": {
        "payments": [
            {
                "id": 1,
                "scheduledTo": "2065-04-02T03:01:40.212",
                "status": "PENDING"
            },
            {
                "id": 2,
                "scheduledTo": "2022-04-02T03:01:40.212",
                "status": "PENDING"
            }
        ]
    }
}
```

* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _OK_ | A busca foi bem sucedida e retornou resultados |
| _NO_CONTENT_ | A busca foi bem sucedida mas não retornou resultados |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |

### `GET /api/payments/status/{id}`
* Retorna o status do agendamento identificado pelo parâmetro de caminho _id_.
* Parâmetros de caminho:
    - _id_ `LONG`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/status/1`
    - Response:
```JSON
{
    "data": {
        "status": "PENDING"
    }
}
```
* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _OK_ | A busca foi bem sucedida e retornou resultados |
| _NOT_FOUND_ | A busca não encontrou o item requisitado |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |

### `POST /api/payments/`
* Cria um agendamento com o horário especificado. Retorna o _id_ do agendamento criado.
* Parâmetros de corpo:
    - _scheduledTo_ `TIMESTAMP`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/status/1`
    - Request Body:
```JSON
{
    "scheduledTo": "2023-08-30T13:14:20.000"
}
```
    - Response:
```JSON
{
    "data": {
        "id": 3
    }
}
```
* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _CREATED_ | O item foi criado com sucesso |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |

### `PATCH /api/payments/status/{id}`
* Modifica o status de um agendamento existente identificado pelo parâmetro de caminho _id_. Novo valor deve vir no parâmetro de corpo _status_. Não é possível alterar agendamentos já pagos. Retorna o item alterado.
* Parâmetros de caminho:
    - _id_ `LONG`
* Parâmetros de corpo:
    - _status_ `PENDING | PAID`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/status/1`
    - Request Body:
```JSON
{
    "status" : "PAID"
}
```
    - Response:
```JSON
{
    "data": {
        "payment": {
            "id": 1,
            "scheduledTo": "2065-04-02T03:01:40.212",
            "status": "PAID"
        }
    }
}
```
* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _OK_ | O item foi alterado com sucesso |
| _NOT_FOUND_ | A busca não encontrou o item requisitado |
| _FORBIDDEN_ | O item não pode ser alterado |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |

### `PATCH /api/payments/schedule/{id}`
* Modifica o horário de um agendamento existente identificado pelo parâmetro de caminho _id_. Novo valor deve vir no parâmetro de corpo _scheduledTo_. Não é possível alterar agendamentos já pagos. Retorna o item alterado.
* Parâmetros de caminho:
    - _id_ `LONG`
* Parâmetros de corpo:
    - _scheduledTo_ `TIMESTAMP`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/schedule/1`
    - Request Body:
```JSON
{
    "scheduledTo" : "2023-04-02T10:54:33.212"
}
```
    - Response:
```JSON
{
    "data": {
        "payment": {
            "id": 1,
            "scheduledTo": "2023-04-02T10:54:33.212",
            "status": "PENDING"
        }
    }
}
```
* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _OK_ | O item foi alterado com sucesso |
| _NOT_FOUND_ | A busca não encontrou o item requisitado |
| _FORBIDDEN_ | O item não pode ser alterado |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |

### `DELETE /api/payments/{id}`
* Deleta um agendamento existente identificado pelo parâmetro de caminho _id_. Não é possível deletar agendamentos já pagos.
* Parâmetros de caminho:
    - _id_ `LONG`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/1`
* Possíveis HTTP Status de resposta:

| HTTP Status | Ocorrido |
| ------ | ------ |
| _NO_CONTENT_ | O item foi deletado com sucesso |
| _NOT_FOUND_ | A busca não encontrou o item requisitado |
| _FORBIDDEN_ | O item não pode ser deletado |
| _INTERNAL_SERVER_ERROR_ | Erro no processamento da requisição pelo server |
