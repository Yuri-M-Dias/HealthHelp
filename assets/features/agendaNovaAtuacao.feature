#language: pt

Funcionalidade: Cadastrar uma nova atuação
  Como o profissional de saúde
  Eu quero cadastrar uma nova atuação minha
  Para que eu possa realizar atendimentos nos horários definidos

  Contexto:
    Dado que estou devidamente autenticado como profissinal da saúde
    E que estou cadastrado no hospital "Santa Helena"
    E que não possuo atuações cadastradas

  Cenário: Cadastrar uma nova atuação válida
    Dado que eu posso cadastrar uma atuação no hospital
    Quando eu preencher o nome da agenda como "Agenda da Tarde"
    E o tempo de atendimento como sendo em "Horas"
    E a data de início como sendo "12/02/2016"
    E a hora de início como sendo "08:00"
    E a data de fim como sendo "12/02/2017"
    E a hora de fim como sendo "18:00"
    E completar o cadastro da atuação
    Então eu terei adicionado uma nova atuação para o hospital com sucesso
    E pacientes poderão marcar consultas no horário criado

