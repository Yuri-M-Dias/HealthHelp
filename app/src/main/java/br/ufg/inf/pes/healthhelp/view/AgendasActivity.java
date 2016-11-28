package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.AgendaService;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

public class AgendasActivity extends AbstractListActivity<Atuacao> {

    public static final String ARG_PROFISSIONAL = "profissional";

    private List<LocalAtendimento> locais;
    private AgendaService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        getIntent().putExtra(AbstractListActivity.ARG_TITULO, getString(R.string.agendas_profissional_titulo_activity));
        super.onCreate(savedInstanceState);

        service = new AgendaService();
        locais = criarLocaisAtendimento();
    }

    @Override
    public void recarregarItens() {

        iniciarProgresso(getString(R.string.agendas_profissional_titulo_dialog), getString(R.string.agendas_profissional_mensagem_dialog));
        service.solicitarListaAgendas(locais);
    }

    @Override
    public ItensSeparadoresAdapter<Atuacao> inicializarAdapter(List<Atuacao> itens) {
        return new ItensSeparadoresAdapter<Atuacao>(this, R.layout.item_separador_icone_nome_generico, itens) {
            @Override
            public void preencherItem(int position) {

                Atuacao atuacao = getItem(position);
                String nomeagendaatual = atuacao.getAgendas().get(0).getNome();

                if (position < 1 || !getItem(position - 1).getAgendas().get(position-1).getNome().equals(nomeagendaatual)) {
                    textoSeparador.setText(nomeagendaatual);
                } else {
                    textoSeparador.setVisibility(View.GONE);
                }

                textoDescricao.setText(nomeagendaatual);
                icone.setImageResource(R.drawable.profissional_saude_azul);
            }
        };
    }

    @Override
    public AdapterView.OnItemClickListener obterAcaoClique() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AgendasActivity.this, AgendaCompletaActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public Comparator<Atuacao> obterComparador() {
        return new Comparator<Atuacao>() {
            @Override
            public int compare(Atuacao atuacao, Atuacao outraAtuacao) {
                return atuacao.getLocalAtendimento().getNome().compareTo(outraAtuacao.getLocalAtendimento().getNome());
            }
        };
    }

    private List<LocalAtendimento> criarLocaisAtendimento() {

        List<LocalAtendimento> localAtendimentos = new ArrayList<>();
        LocalAtendimento local = new LocalAtendimento();

        local.setNome("Hospital das Clinicas");
        localAtendimentos.add(local);

        local = new LocalAtendimento();
        local.setNome("Hospital Araújo Jorge");
        localAtendimentos.add(local);

        local = new LocalAtendimento();
        local.setNome("Hospital Santo Agostinho");
        localAtendimentos.add(local);

        return localAtendimentos;
    }

}
