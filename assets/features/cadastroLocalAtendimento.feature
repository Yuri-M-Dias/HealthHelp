#language: pt

Funcionalidade: Cadastro de Local de Atendimento
	Como a Secretaria
	Eu quero cadastrar locais de atendimento
	Para que eu consiga visualizar e manipular a agenda do local

	Restrições:
		1) O local deve ter sua agenda, pacientes e contatos próprios(nome, endereço, e telefone)

	Contexto:
		Dado que estou devidamente autenticado
		E posso cadastrar locais

	Esquema do Cenário: Cadastrando um local
		Quando eu inserir os dados válidos de um local
		E preencher <nome>, <endereço> e <telefone>
		Então eu terei inserido um novo local com sucesso

		Exemplos:
			| nome | endereço | telefone |
			| Hospital Santa Helena | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 |
			| Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião | (62) 9154-3421 |

	Esquema do Cenário: Cadastrando um local inválido
		Quando eu inserir os dados inválidos de um local
		E preencher <nome>, <endereço> e <telefone>
		Então eu serei informado que o cadastro falhou
		# Detalhar os erros aqui depois, usando o mesmo
		E serei informado de quais dados não estão válidos

		Exemplos:
			| nome | endereço | telefone |
			| Hospital Santa Helena | Rua T-68, Nº3565 ao lado do restaurante Bom Sabor | (62) 9254-3421 |
			| Hospital de Urgências de Goiânia | Rua T-62, Nº3564 ao lado do parque Areião | (62) 9154-3421 |

