#language: pt

Funcionalidade: Permitir multiplas agendas para o profissional de saúde
    Como o profissional de saúde
    Eu quero me cadastrar nos locais de atendimento
    Escolher criar nova agenda ou utilizar agenda existente
    Quero visualizar e gerenciar as minhas agendas.

Restrições:
  1- O usuário deve existir
  1- Profissional de saúde deverá ter permissão de acesso
  2- A agenda ja deve estar adicionada

#Fluxo principal passo2
Contexto 1:
  Dado que estou devidamente autenticado como profissinal da saúde
  E  após clicar "Adicionar Agenda" estou visualizando a tela de Agenda

Esquema do Cenário : Inserindo campos validos
  Quando eu preencher nos campos os dados <nomeDaAgenda>,<tempoAtendimento>,<dataInicio>,<horaInicio>,<dataFim>,<horaFim>
  E clicar em "Adicionar Horario de Atendimento"
  Então eu terei configurado a agenda para o local de atendimento
  E aparecerá na uma mensagem padrão informativa <mensagem>

Exemplos:
  | nomeDaAgenda | tempoAtendimento |dataInicio | horaInicio |dataFim | horaFim |
 	| Consultas | Minuntos | 12/02/2016 |08:00 AM | 12/02/2017 | 6:00 PM |Cadastro realizado com sucesso |
  | Cirugias | Horas | 18/02/2016 |02:00 PM | 18/02/2017 | 6:00 PM |Cadastro realizado com sucesso |

Esquema do Cenário : Inserindo campos invalidos
  Quando eu preencher dados invalidos nos campos <nomeDaAgenda>,<tempoAtendimento>,<dataInicio>,<horaInicio>,<dataFim>,<horaFim>
  E clicar em "Adicionar Horario de Atendimento"
  Então será sinalizado com a mensagem de campo inválido <mensagem>

Exemplos:
| nomeDaAgenda | tempoAtendimento |dataInicio | horaInicio |dataFim | horaFim |
| Consultas | Minuntos | 12022016 |08:00 AM | 12/02/2017 | 6:00 PM | campo dataInicio inválido |
| Cirugias | Horas | 18/02/2016 |14:00 PM | 18/02/2017 | 6:00 PM | campo horaInicio inválido |
