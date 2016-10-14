package br.ufg.inf.pes.healthhelp.test.steps;

import android.test.ActivityInstrumentationTestCase2;

import br.ufg.inf.pes.healthhelp.view.NovoLocalAtendimentoActivity;
import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class CadastroLocalAtendimentoSteps extends ActivityInstrumentationTestCase2<NovoLocalAtendimentoActivity> {

    public CadastroLocalAtendimentoSteps(NovoLocalAtendimentoActivity activityClass) {
        super(NovoLocalAtendimentoActivity.class);
    }

    @Quando("^eu selecionar o hospital <nomeHospital>$")
    public void euSelecionarOHospitalNomeHospital() throws Throwable {
        throw new PendingException();
    }

    @Dado("^que estou devidamente autenticado$")
    public void queEstouDevidamenteAutenticado() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @E("^posso cadastrar locais$")
    public void possoCadastrarLocais() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Quando("^eu inserir os dados válidos de um local$")
    public void euInserirOsDadosVálidosDeUmLocal() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @E("^preencher <nome>, <endereço>, <telefone> e <horarioAtendimento>$")
    public void preencherNomeEndereçoTelefoneEHorarioAtendimento() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Então("^eu terei inserido um novo local com sucesso$")
    public void euTereiInseridoUmNovoLocalComSucesso() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @E("^terei uma agenda única para ele$")
    public void tereiUmaAgendaÚnicaParaEle() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @E("^eu estarei associada ao local que criei$")
    public void euEstareiAssociadaAoLocalQueCriei() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
