#language:pt

Funcionalidade: Cadastro de pacientes

  Como um usuário do sistema
  Eu quero realizar cadastro dos meus dados pessoais
  Para que eu consiga ter acesso às operações do sistema

  Esquema do Cenário: Cadastrando um paciente
    Dado que eu esteja na tela de cadastro de um paciente
    Quando eu inserir os dados válidos e existentes
    E preenchendo <nome>, <telefones>, <email>, <data de nascimento>, <sexo> e <convênio>
    E clicando em cadastrar
    Então aparecerá uma mensagem me informando "<mensagem>"
    E eu terei realizado o cadastro.

    Exemplos:
      | nome            | telefone       | email                    | dataDeNascimento | sexo | convenio | mensagem                        |
      | Joao Barros     | (62) 9331-1105 | joaobarros@gmail.com     | 13/12/1995       | M    | IPASGO   | Cadastro realizado com sucesso! |
      | Juliana Barbosa | (62) 9451-1255 | julianabarbosa@gmail.com | 15/05/1992       | F    | UNIMED   | Cadastro realizado com sucesso! |

  Esquema de Cenário: Preenchendo campo de forma inválida
    Dado que eu estou na tela de cadastro
    Quando eu inserir um campo inválido
    E prencher <nome>, <profissão>, <email>, <telefone>
    E Clicar em cadastrar
    Então o sistema me informará o campo na qual preenchi de forma inválida
    E me mostrará uma mensagem na tela "<mensagem>"

    Exemplos:
      | nome            | telefone       | email                   | dataDeNascimento | sexo | convenio | mensagem                    |
      | Joao Barros     | (62) 9331-1105 | joaobarros@gmail.com    | 13/12/1995       | M    | 4542     | convenio inválido           |
      | Juliana Barbosa | (62) 9451-1255 | julianabarbosa@         | 15/05/1992       | F    | UNIMED   | email inválido              |
      | Rafael Nunes    | (62) 9451-1255 | rafaelnunes@hotmail.com | /05/192          | F    | UNIMED   | data de nascimento inválida |
