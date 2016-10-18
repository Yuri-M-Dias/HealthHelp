# language: pt

Funcionalidade: Possuir várias agendas
  Como um profissional da saúde
  Eu quero possuir uma ou mais agendas em locais distintos ou não
  Para que eu possa ter maior controle e organização nas consultas

  Restrição:
  1- O usuário deve estar devidamente auntenticado como profissional da saúde

Contexto:
  Dado que estou autenticado no sistema
  E desejo criar uma agenda

Esquema do Cenário: Criando agenda no mesmo local de atendimento
  Quando eu clicar no botão "Criar Agenda"
  E inserir um <nome> para a agenda
  Entao será criada uma nova agenda com o nome dado

Contexto:
  Dado que estou autenticado no sistema
  E estou em um determinado local de atendimento

Esquema do Cenário: Criando agenda fora do local de atendimento corrente
  Quando eu voltar para a tela anterior
  E selecionar outro local de atendimento previamente cadastrado em meu nome
  E clicar no botão "Criar Agenda"
  E inserir um nome para essa agenda
  Entao será criada uma nova agenda com o nome dado
