var FirebaseServer = require('firebase-server');

new FirebaseServer(5000, 'test.firebase.localhost', {

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
  }

});
