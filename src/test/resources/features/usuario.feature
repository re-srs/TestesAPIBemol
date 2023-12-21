# language: pt

@usuario
Funcionalidade: /usuarios

  @criarUsuario
  Cenario: Criar usuario com sucesso
    Dado que preenchi o body
    Quando envio a solicitação para criar um usuario
    Então valido a mensagem de sucesso da criação do usuario


  @listarUsuario
  Cenario: Listar usuario com sucesso
    Dado que desejo consultar um usuario com sucesso passando ID
    Quando envio a solicitação para listar um usuario
    Então faço a validação da mensagem de retorno da consulta


