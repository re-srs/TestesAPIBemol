# language: pt

@produto
Funcionalidade: /produtos

  @criarProduto
  Cenario: Criar produto com sucesso
    Dado que preenchi o body com sucesso
    Quando envio a solicitação para criar um produto
    Então valido a mensagem de sucesso da criação do produto


  @listarProduto
  Cenario: Listar produto com sucesso
    Dado que desejo consultar um produto com sucesso passando ID
    Quando envio a solicitação para listar um produto
    Então faço a validação da mensagem de retorno da consulta do produto


