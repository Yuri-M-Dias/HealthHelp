
#language:pt

Funcionalidade: Cadastro de profissionais da saúde

	Como Profissional da Saúde
	Eu quero realizar cadastro dos meus dados pessoais/ profissionais.
	Para que eu consiga realizar as operações no sistema.

	Restrições:
		1) O sistema deve estar acessivel.
		2) Somente serão aceitos dados válidos.
		3) Não permitir cadastros de pacientes com os mesmos dados.


	Esquema do Cenário: Cadastrando um profissional da saúde

		Quando eu inserir os meus dados (sendo válidos)
		Preenchendo <nome>, <profissão>, <numero de conselho regional de saúde>, <email>, <telefone>
		E clicar em Cadastrar
		Então eu terei realizado o cadastro

		Exemplos:
			| nome | profissão | numeroDeConselhoRegional | email | telefone |
			| Joao Medeiros | Clinico Geral | 2.2-GO-785-25 | drjoaomedeiros@gmail.com | (62) 9245-5896 |
			| Katia Fernandes | Pediatra | 8.2-GO-585-22 | drkatiafernandes@gmail.com | (62) 8198-8958 |