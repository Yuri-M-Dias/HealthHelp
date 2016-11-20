#language: pt
Funcionalidade: Permitir multiplas agendas para o profissional de saúde
    Como o profissional de saúde
    Eu quero me cadastrar nos locais de atendimento
    Escolher criar nova agenda ou utilizar agenda existente
    Quero visualizar e gerenciar as minhas agendas.

Restrições:
  1- O usuário deve existir
  1- Profissional de saúde deverá ter permissão de acesso
  2- O local de atendimento estar disponível para cadastro
  2- O horário de atendimento disponível
 	4- Nao restringir cadastro de local em mais de uma agenda
 	5- Nao permitir cadastro do mesmo local de atendimento na mesma agenda
#Fluxo principal passo1
Contexto 1:
  Dado que estou devidamente autenticado como profissinal da saúde
  E estou visualizando a tela de Nova Atuação

Esquema do Cenário : Inserindo campos validos
  Quando eu preencher nos campos os dados <nomeDoLocal>,<dataInicio>,<dataFim>(campo opcional)
  E clicar em "Adicionar Agenda"
  Então eu terei criado uma nova agenda para o local de atendimento
  E aparecerá na uma mensagem padrão informativa <mensagem>

Exemplos:
  | nomeDoLocal | dataInicio |dataFim | mensagem |
 	| Hospital Santa Helena | 30/11/2016| | Cadastro realizado com sucesso |
 	| Hospital de Urgências de Goiânia | 03/01/2017 | 03/01/2018 | Cadastro realizado com sucesso |
  | Hospital Jardim America | 05/06/2014 | 06/12/2017 | Cadastro realizado com sucesso |

Esquema do Cenário : Inserindo campos invalidos
      Quando eu preencher dados invalidos nos campos <nomeDoLocal>,<dataInicio>,<dataFim>(campo opcional)
      E clicar em "Adicionar Agenda"
      Então será sinalizado com a mensagem de campo inválido <mensagem>
Exemplos:
| nomeDoLocal | dataInicio |dataFim | mensagem |
| Hospital SantaHelea | 30/11/2016| | campo nomeDoLocal inválido |
| Hospital de Urgências de Goiânia | 03012017 | 03/01/2018 | campo dataInicio inválido |
| Hospital Jardim America | 05/06/2014 | 0612/2017 | campo dataFim inválido |
