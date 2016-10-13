# language: pt

Funcionalidade: Atualizar dados cadastrais do Usuário
  Como o Paciente
  Ou Profissionais da Saúde
  Ou Secretária
  Eu quero poder editar os meus dados pessoais
  Para que eu possa atualiza-los

  Restrição:
  1 - O usuário deve existir
  2 - O usuário deve estar devidamente autenticado

Contexto:
  Dado que estou autenticado
  E estou visualizando a tela destinada para atualizar os dados

Esquema do Cenário: Atualizar dados de Paciente
  Quando eu selecionar os campos <nome>, <telefone>, <email>, <dataNascimento>, <sexo>, <convenio>
  E alterá-los para que esteja atualizado
  E clicar no botão "Atualizar"
  Então os meus dados estarão atualizados

Exemplos:
  | nome | telefone | email | dataNascimento | sexo | convenio |
  | José Ferreira Martins | 62992554689 | jose.martins@gmail.com | 12/08/1976 | masculino | IPASGO |
  | Maria Almeida de Jesus | 11935468985 | nao tem | 22/07/1950 | feminino | Unimed |

Esquema do Cenário: Atualizar dados de Profissionais de Saúde
  Quando eu selecionar os campos <nome>, <profissao>, <numeroConselhoRegional>, <email>, <telefone>, <ultimoAcesso>, <status>
  E alterá-los para que esteja atualizado
  E clicar no botão "Atualizar"
  Então os meus dados estarão atualizados

Exemplos:
  | nome | profissao | numeroConselhoRegional | email | telefone | ultimoAcesso | status |
  | Marcelo Camargo Corrêa | Medico | CRM-2101 | marcelocamargo2003@hotmail.com | 6432588482 | 01/10/2016 - 18:53 | ativo |
  | Ana Luiza Guimaraes | Odontologa | CRO-1245 | anaguimaraes@hotmail.com | 6432546698 | 11/10/2015 - 19:40 | ativo |

  Esquema do Cenário: Atualizar dados de Secretária
    Quando eu selecionar os campos <nome>, <email>, <telefone>, <ultimoAcesso>, <status>
    E alterá-los para que esteja atualizado
    E clicar no botão "Atualizar"
    Então os meus dados estarão atualizados

  Exemplos:
    | nome | email | telefone | ultimoAcesso | status |
    | Joana Marcela de Sousa | joana2324sousa@hotmail.com | 6432588482 | 01/10/2016 - 10:53 | ativo |
    | Anibal Pereira Cruz | anibalcruz.gmail.com | 6432546698 | 11/10/2015 - 20:40 | inativo |


Funcionalidade: Deletar usuário e dados obsoletos
  Como a Secretaria
  Desejo ter a possibilidade de poder deletar dados obsoletos de usuários

  Restrição:
  1 - O usuário deve existir
  2 - O usuário deve estar devidamente autenticado

  Contexto:
    Dado que estou autenticado
    E estou visualizando a tela destinada a exclusão de usuário

    Cenario: Deletar usuário não pertencente mais ao negócio
      Quando eu digitar o nome do usuário destinado a ser deletado do sistema
      E clicar no botão "Deletar"
      Então todos os dados referentes ao usuário são deletados do sistema
