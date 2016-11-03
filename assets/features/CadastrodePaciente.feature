Funcionalidade: Cadastro de pacientes

	Como Paciente
	Eu quero realizar cadastro dos meus dados pessoais.
	Para que eu consiga realizar as operações no sistema.

	Restrições:
			1) O sistema deve estar acessivel.
			2) Somente serão aceitos dados válidos.
			3) Não permitir cadastros de pacientes com os mesmos dados.


	Esquema do Cenário: Cadastrando um paciente

			Quando eu inserir os dados válidos e existentes
			Preenchendo <nome>, <telefones>, <email>, <data de nascimento>, <sexo> e <convênio>
			E clicar em cadastrar;
			Então eu terei realizado o cadastro.

			Exemplos:
			| nome | telefone | email | dataDeNascimento | sexo | convenio |
			| Joao Barros | (62) 9331-1105 | joaobarros@gmail.com | 13/12/1995 | M | IPASGO |
			| Juliana Barbosa | (62) 9451-1255 | julianabarbosa@gmail.com | 15/05/1992 | F | UNIMED |