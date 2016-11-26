package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.AtuacaoService;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

/**
 * Esta classe define a activity que exibe a lista de profissionais de saúde para os locais de atendimento onde uma secretária trabalha.
 */
public class AtuacoesProfissionaisSaudeActivity extends AbstractListActivity<Atuacao> {

    public final static String ARG_LOCAIS_ATENDIMENTO = "lista_locais_atendimento";

    private List<LocalAtendimento> locaisAtendimento;
    private AtuacaoService atuacaoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AbstractListActivity.ARG_TITULO, getString(R.string.atuacoes_profissionais_saude_titulo_activity));
        super.onCreate(savedInstanceState);
        atuacaoService = new AtuacaoService();
        //TODO: Descomentar a linha abaixo para correta atribuição quando essa classe for chamada
        //locaisAtendimento = (List<LocalAtendimento>) getIntent().getSerializableExtra(ARG_LOCAIS_ATENDIMENTO);
        locaisAtendimento = criarLocaisAtendimento();
    }

    /**
     * Cria uma lista de locais de atendimento para teste da activity.
     * @return lista de locais de atendimento.
     */
    private List<LocalAtendimento> criarLocaisAtendimento() {
        List<LocalAtendimento> locaisAtendimento = new ArrayList<>();
        LocalAtendimento localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local Abacaxi");
        locaisAtendimento.add(localAtendimento);

        localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local Abacate");
        locaisAtendimento.add(localAtendimento);

        localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local AAA");
        locaisAtendimento.add(localAtendimento);

        return locaisAtendimento;
    }

    @Override
    public void recarregarItens() {
        iniciarProgresso(getString(R.string.atuacoes_profissionais_saude_titulo_dialog), getString(R.string.atuacoes_profissionais_saude_mensagem_dialog));
        atuacaoService.solicitarListaProfissionaisSaude(locaisAtendimento);
    }

    @Override
    public ItensSeparadoresAdapter<Atuacao> inicializarAdapter(List<Atuacao> itens) {
        return new ItensSeparadoresAdapter<Atuacao>(this, R.layout.item_separador_icone_nome_generico, itens) {
            @Override
            public void preencherItem(int position) {
                Atuacao atuacaoAtual = getItem(position);

                if(position < 1 || !getItem(position-1).getLocalAtendimento().getNome().equals(atuacaoAtual.getLocalAtendimento().getNome())) {
                    textoSeparador.setText(atuacaoAtual.getLocalAtendimento().getNome());
                } else {
                    textoSeparador.setVisibility(View.GONE);
                }

                textoDescricao.setText(atuacaoAtual.getProfissional().getUsuario().getNome());
                icone.setImageResource(R.drawable.profissional_saude_azul);
            }
        };
    }

    @Override
    public AdapterView.OnItemClickListener obterAcaoClique() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AtuacoesProfissionaisSaudeActivity.this, AgendasActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public Comparator<Atuacao> obterComparador() {
        return new Comparator<Atuacao>() {
            @Override
            public int compare(Atuacao atuacao, Atuacao t1) {
                return atuacao.getLocalAtendimento().getNome().compareTo(t1.getLocalAtendimento().getNome());
            }
        };
    }
}
