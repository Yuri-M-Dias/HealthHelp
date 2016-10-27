#language: pt

Funcionalidade: O  sistema  deve  permitir  que  cada  profissional  de  saude  tenha  mais  de  uma agenda,
                seja no mesmo ou em outro local de atendimento.

    Como o profissional de saúde
    Esteja disponivel a criacao de mais de uma agenda.
   	Eu quero me cadastrar nos locais de atendimento em uma ou mais agendas.
   	Quero visualizar e manipular as minhas agendas.

Restrições:

 	1) Profissional de saúde deverá ter permissão de acesso.
 	2) Cadastrar local de atendimento em agenda de acordo com o horário de atendimento disponível.
 	3) Nao restringir cadastro de local em mais de uma agenda.
 	4) Nao permitir cadastro do mesmo local de atendimento na mesma agenda.

 Contexto:

 	Dado que estou devidamente autenticado.
 	E Posso cadastras locais de atendimentos em minhas agendas.
  E Posso criar agendas.


 Esquema do Cenário:
        Cadastrando local de atendimento na agenda
      	Quando eu abrir uma agenda,
      	E inserir os dados válidos de um local de atendimento,
        Então eu terei inserido na agenda um novo local com sucesso.

   Exemplos:

 		| nome | endereço | telefone | horarioAtendimento |

 		| Hospital Santa Helena | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 | 08:00-12:0014:00-18:00 |

 		| Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião | (62) 9154-3421 | 08:00-12:0014:00-18:00 |
