# API

Todas as rotas aceitam e produzem _application/json_ .

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
                "scheduledTo": "2013-08-30T13:14:20",
                "amount": 520.5,
                "status": "PENDING"
            },
            {
                "id": 2,
                "scheduledTo": "2022-10-13T21:54:33",
                "amount": 5490.55,
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

### `GET /api/payments/{id}`
* Retorna o agendamento identificado pelo parâmetro de caminho _id_.
* Parâmetros de caminho:
    - _id_ `LONG`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/1`
    - Response:
```JSON
{
    "data": {
        "payment": {
            "id": 1,
            "scheduledTo": "2023-10-13T21:54:54",
            "amount": 2350.55,
            "status": "PENDING"
        }
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
* Cria um agendamento com horário e quantia especificados. Retorna o _id_ do agendamento criado.
* Parâmetros de corpo:
    - _scheduledTo_ `TIMESTAMP`
    - _amount_ `FLOAT`
* Exemplo:
    - Request URL: `localhost:8080/api/payments`
    - Request Body:
```JSON
{
    "scheduledTo": "2022-10-13T21:54:33.000", 
    "amount": 5490.55
}
```
    - Response:
```JSON
{
    "data": {
        "id": 2
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
            "scheduledTo": "2013-08-30T13:14:20",
            "amount": 5000.0,
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

### `PATCH /api/payments/{id}`
* Modifica horário e quantia de um agendamento existente identificado pelo parâmetro de caminho _id_. Novos valores devem vir como parâmetros de corpo. Não é possível alterar agendamentos já pagos. Retorna o item alterado.
* Parâmetros de caminho:
    - _id_ `LONG`
* Parâmetros de corpo:
    - _scheduledTo_ `TIMESTAMP`
    - _amount_ `FLOAT`
* Exemplo:
    - Request URL: `localhost:8080/api/payments/2`
    - Request Body:
```JSON
{
    "scheduledTo" : "2024-11-02T13:21:35.238",
    "amount": 320.55
}
```
    - Response:
```JSON
{
    "data": {
        "payment": {
            "id": 2,
            "scheduledTo": "2024-11-02T13:21:35.238",
            "amount": 320.55,
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

