#language: pt

Funcionalidade: Detalhar Local de Atendimento
  Como o Usuario
  Eu quero visualizar locais de atendimento
  Para que eu consiga obter as informacoes do local que procuro

  Contexto:
    Dado que estou devidamente autenticado
    E posso visualizar as informacoes do local

  Esquema do Cenário: Visualizar dados de um local
    Quando eu selecionar o local "<nome>"
    Então eu terei acesso ao "<endereço>", "<telefone>" e "<horarioAtendimento>"
    Exemplos:
      | nome                             | endereço                                          | telefone       | horarioAtendimento      |
      | Hospital Santa Helena            | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 | 08:00-12:00+14:00-18:00 |
      | Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião         | (62) 9154-3421 | 06:00-12:00+12:30-06:00 |


