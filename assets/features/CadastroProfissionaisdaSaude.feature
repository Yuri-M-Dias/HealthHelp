#language:pt
Funcionalidade: Cadastro de profissionais da saúde

	Como Profissional da Saúde
	Eu quero realizar cadastro dos meus dados pessoais/ profissionais.
	Para que eu consiga realizar as operações no sistema.

	Restrições:
		1) O sistema deve estar acessivel.
		2) Somente serão aceitos dados válidos.
		3) Não permitir cadastros de pacientes com os mesmos dados.

Esquema de Cenário: Cadastrando um profissional da saúde
		Dado que eu esteja na tela de cadastro de profissionais da saúde
		Quando eu inserir os meus dados (sendo válidos)
		E preenchendo <nome>, <profissão>, <numero de conselho regional de saúde>, <email>, <telefone>
		Então aparecerá uma mensagem me informando "<mensagem>"
		E eu terei realizado o cadastro.

		Exemplos:
		| nome | profissão | numeroDeConselhoRegional | email | telefone | mensagem |
		| Joao Medeiros | Clinico Geral | 2.2-GO-785-25 | drjoaomedeiros@gmail.com | (62) 9245-5896 | Cadastro realizado com sucesso! |
		| Katia Fernandes | Pediatra | 8.2-GO-585-22 | drkatiafernandes@gmail.com | (62) 8198-8958 | Cadastro realizado com sucesso! |

Esquema de Cenário: Preenchendo um campo de forma inválida
		Dado que eu estou na tela de cadastro de profissionais da saúde
		Quando eu inserir um campo inválido
		E prencher <nome>, <profissão>, <numero de conselho regional de saúde>, <email>, <telefone>
		E clicar em cadastrar
		Então o sistema me informará o campo na qual preenchi de forma inválida
		E me mostrára uma mensagem na tela "<mensagem>"

		Exemplos:
		| nome | profissão | numeroDeConselhoRegional | email | telefone | mensagem |
		| 45412 | Clinico Geral | 2.2-GO-785-25 | drjoaomedeiros@gmail.com | (62) 9245-5896 | nome inválido |
		| Katia Fernandes | 145265 | 8.2-GO-585-22 | drkatiafernandes@gmail.com | (62) 8198-8958 | dado profissão inválida |
		| Luana Sousa | Pediatra | Doutora | drkatiafernandes@gmail.com | (62) 8198-8958 | número de conselho regional inválido |
		| Flávia Mariana | Demartologista | 2.2-GO-885-28 | drflaviamariana@ | (62) 8198-8958 | email inválido |
		| Ricardo Borges | Clinico Geral | 2.2-GO-885-28 | drflaviamariana@ | (22) 8198-8 | telefone inválido |
