package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.AtuacaoService;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

public class AtuacoesProfissionaisSaudeActivity extends AbstractListActivity<Atuacao> {

    private AtuacaoService atuacaoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AbstractListActivity.ARG_TITULO, "Profissionais de Saúde");
        super.onCreate(savedInstanceState);
        atuacaoService = new AtuacaoService();
    }

    @Override
    public void recarregarItens() {
        Log.i(TAG, "Carregando itens...");
        iniciarProgresso("Meu titulo", "Minha mensagem de carregamento");
        List<LocalAtendimento> locaisAtendimento = new ArrayList<>();
        LocalAtendimento localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local1");
        locaisAtendimento.add(localAtendimento);

        localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local2");
        locaisAtendimento.add(localAtendimento);

        localAtendimento = new LocalAtendimento();
        localAtendimento.setNome("Local3");
        locaisAtendimento.add(localAtendimento);

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
                Log.i(TAG, "Tocando no item " + i);
            }
        };
    }
}
