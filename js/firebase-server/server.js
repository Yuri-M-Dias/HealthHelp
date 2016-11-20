/* para executar utilize node ./node_modules/.bin/firebase-server.
 mais informações em
 https://firebase.googleblog.com/2015/04/end-to-end-testing-with-firebase-server_16.html
 */
var FirebaseServer = require('firebase-server');

new FirebaseServer(5000, 'test.firebase.localhost', {

	health-help-localhost {
		localAtendimento : {
			KUQ87Pkpw3MoId84EU: {
				endereco : "R. 227, 395 - Setor Leste Universitário, Goiânia - GO, 74605-080",
				horariosAtendimento : [ {
					dataFim : "13-10-2016",
					dataInicio : "13-10-2016",
					diasSemana : "23456",
					horaFim : "22",
					horaInicio : "8"
				} ],
				nome : "Hospital Goiânia Leste",
				telefone : "6230931888"
			}
		},
		agenda : {
			nome : "Agenda X"
		}
	}

});


