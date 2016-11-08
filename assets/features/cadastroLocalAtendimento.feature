#language: pt

Funcionalidade: Cadastro de Local de Atendimento
	Como a Secretaria
	Eu quero cadastrar locais de atendimento
	Para que eu consiga visualizar e manipular a agenda do local

	Restrições:
		1) O local deve ter sua agenda, pacientes e contatos próprios(nome, endereço, e telefone)
		2) O local deve ser válido e existir

	Contexto:
		Dado que estou devidamente autenticado
		E posso cadastrar locais

	Esquema do Cenário: Cadastrando um local
		Quando eu inserir os dados válidos de um local
		E preencher <nome>, <endereço>, <telefone> e <horarioAtendimento>
		Então eu terei inserido um novo local com sucesso
		E terei uma agenda única para ele
		E eu estarei associada ao local que criei
		Exemplos:
			| nome | endereço | telefone | horarioAtendimento |
			| Hospital Santa Helena | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 | 08:00-12:00+14:00-18:00 |
			| Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião | (62) 9154-3421 | 08:00-12:00+14:00-18:00 |


