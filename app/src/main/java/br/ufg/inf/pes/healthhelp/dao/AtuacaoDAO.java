package br.ufg.inf.pes.healthhelp.dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.ProfissionalSaude;
import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Atuacao}.
 */
public class AtuacaoDAO extends AbstractDAO<Atuacao> {
    public AtuacaoDAO() {
        super(AtuacaoDAO.class.getCanonicalName(), "atuacao");
    }

    @Override
    public void buscarTodos() {
        //TODO
    }

    @Override
    public void buscarPelaId(String id) {
        //TODO
    }

    /**
     * Busca todas as atuações de um usuário.
     *
     * @param usuario usuário pelo qual as ocupações serão procuradas.
     */
    public void buscarPorUsuario(Usuario usuario) {
        //TODO
    }

    @Override
    public void inserir(Atuacao objeto) {
        //TODO
    }

    @Override
    public void remover(Atuacao objeto) {
        //TODO
    }

    @Override
    public void atualizar(Atuacao objeto) {
        //TODO
    }


    /**
     * Busca todos todas as atuações de profissionais de saúde que trabalham em determinados locais de atendimento.
     * @param locaisAtendimento Lista de locais de atendimento que será utilizada para se buscar as atuações de profissionais de saúde.
     */
    public void buscarPorLocais(final List<LocalAtendimento> locaisAtendimento){
        //TODO: Substituir a implementação stub abaixo pela implementação correta quando a DAO for desenvolvida.
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    List<Atuacao> atuacoes = new LinkedList<Atuacao>();
                    ProfissionalSaude profissionalSaude;
                    Atuacao atuacao;
                    atuacao = new Atuacao();
                    atuacao.setLocalAtendimento(locaisAtendimento.get(0));
                    profissionalSaude = new ProfissionalSaude();
                    profissionalSaude.setUsuario(new Usuario());
                    profissionalSaude.getUsuario().setNome("Joao Pereira");
                    atuacao.setProfissional(profissionalSaude);
                    atuacoes.add(atuacao);

                    atuacao = new Atuacao();
                    atuacao.setLocalAtendimento(locaisAtendimento.get(0));
                    profissionalSaude = new ProfissionalSaude();
                    profissionalSaude.setUsuario(new Usuario());
                    profissionalSaude.getUsuario().setNome("Paulo Roberto");
                    atuacao.setProfissional(profissionalSaude);
                    atuacoes.add(atuacao);

                    atuacao = new Atuacao();
                    atuacao.setLocalAtendimento(locaisAtendimento.get(1));
                    profissionalSaude = new ProfissionalSaude();
                    profissionalSaude.setUsuario(new Usuario());
                    profissionalSaude.getUsuario().setNome("Mário Roberto");
                    atuacao.setProfissional(profissionalSaude);
                    atuacoes.add(atuacao);

                    atuacao = new Atuacao();
                    atuacao.setLocalAtendimento(locaisAtendimento.get(1));
                    profissionalSaude = new ProfissionalSaude();
                    profissionalSaude.setUsuario(new Usuario());
                    profissionalSaude.getUsuario().setNome("Roberto Alves");
                    atuacao.setProfissional(profissionalSaude);
                    atuacoes.add(atuacao);

                    atuacao = new Atuacao();
                    atuacao.setLocalAtendimento(locaisAtendimento.get(2));
                    profissionalSaude = new ProfissionalSaude();
                    profissionalSaude.setUsuario(new Usuario());
                    profissionalSaude.getUsuario().setNome("Joao Pereira");
                    atuacao.setProfissional(profissionalSaude);
                    atuacoes.add(atuacao);

                    Thread.sleep(2000);

                    EventBus.getDefault().post(new DatabaseEvent<>(atuacoes));
                }
            }
        );


    }
}
