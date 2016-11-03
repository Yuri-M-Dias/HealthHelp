#language:pt
Funcionalidade: Cadastro de secretária

	Como Secretária
	Eu quero realizar cadastro dos meus dados pessoais/profissionais.
	Para que eu consiga realizar as operações no sistema.

	Restrições:
		1) O sistema deve estar acessivel.
		2) Somente serão aceitos dados válidos.
		3) Não permitir cadastros de pacientes com os mesmos dados.

Esquema de Cenário: Cadastrando uma secretária
		Dado que eu estou na tela de cadastro de secretáriaS
		Quando eu inserir os dados válidos
		E preenchendo <nome>, <profissão>, <email>, <telefone>
		E clicando em cadastrar
	 	Então aparecerá uma mensagem me informando "<mensagem>"
		E eu terei realizado o cadastro.

		Exemplos:
		| nome | profissão | email | telefone | mensagem |
		| Beatriz Silveira | Secretária Balcão | secbeatrizsilveira@gmail.com | (62) 9248-9865 | Cadastro realizado com sucesso! |
		| Lucia Lira | Secretária Particular | seclucialira@gmail.com | (62) 9145-8788 | Cadastro realizado com sucesso! |

Esquema de Cenário: Preenchendo campo de forma inválida
		Dado que eu estou na tela de cadastro
		Quando eu inserir um campo inválido
		E prencher <nome>, <profissão>, <email>, <telefone>
		E clicar em cadastrar
		Então o sistema me informará o campo na qual preenchi de forma inválida, com uma mensagem na tela "<mensagem>"

		Exemplos:
		| nome | profissão | email | telefone | mensagem |
		| Beatriz Silveira | 4553454 | secbeatrizsilveira@gmail.com | (62) 9248-9865 | profissão inválida |
		| Lucia Lira | Secretária Particular | seclucialira@gmail.com | (88) 9145-8 | telefone inválido |
		| Maria Cardoso | Secretária Comum | secmariacardoso@ | (88) 9145-8 | email inválido |
