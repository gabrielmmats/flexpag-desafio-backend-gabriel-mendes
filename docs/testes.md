# Testes

Alguns testes unitários foram desenvolvidos para o controlador da API Rest:
- _shouldCreatePayment_ : Testa a criação de um agentamento bem sucedida.
- _shouldReturnListOfPayments_ : Testa a busca de uma lista de agendamentos bem sucedida.
- _shouldReturnListOfPaymentsWithFilter_ : Testa a busca de uma lista de agendamentos com filtro de status bem sucedida.
- _shouldReturnPaymentStatus_ : Testa a busca pelo status de um agendamento bem sucedida.
- _shouldReturnNotFoundPayment_ : Testa a busca pelo status de um agendamento mal sucedida quando o item não foi encontrado.
- _shouldUpdatePaymentStatus_ : Testa a atualização do status de um agendamento bem sucedida.
- _shouldNotUpdatePaymentStatus_ : Testa a atualização do status de um agendamento mal sucedida quando o item não pode ser alterado.
- _shouldUpdatePayment_ : Testa a atualização do horário e quantia de um agendamento bem sucedida.
- _shouldNotUpdatePayment_ : Testa a atualização do horário e quantia de um agendamento mal sucedida quando o item não pode ser alterado.
- _shouldDeletePayment_ : Testa a remoção de um agendamento bem sucedida.
- _shouldNotDeletePayment_ : Testa a remoção de um agendamento  mal sucedida quando o item não pode ser deletado.
