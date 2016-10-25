#language: pt

Funcionalidade: Editar agenda
  Como a Secretaria
  Eu quero visualizar a agenda de um local de atendimento
  Para que eu consiga me informar sobre quais pacientes e quais profissionais devem fazer o que, e quando

  Restrições:
  1) A agenda deve mostrar os horários vagos também.

  Contexto:
    Dado que estou devidamente autenticado
    E faço parte da equipe do local que vou visualizar

  Esquema do Cenário: Visualizar agenda de hospital
    Dado que eu posso selecionar diferentes agendas
    E eu selecionei a agenda de um hospital ao qual sou vinculado
    Quando eu selecionar o hospital "<nomeHospital>"
    E selecionar a data "<dataDeVisualização>"
    Então eu irei visualizar dados na agenda conforme abaixo: "<dadosAgenda>"
    Exemplos:
      | nomeHospital                     | dataDeVisualização                                | dadosAgenda    |
      | Hospital Santa Helena            | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 |
      | Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião         | (62) 9154-3421 |

