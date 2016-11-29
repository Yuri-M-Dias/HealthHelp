package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.service.AtuacaoService;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

import static br.ufg.pes.healthhelp.R.string.atuacoes;
import static br.ufg.pes.healthhelp.R.string.atuacoes_anteriores;
import static br.ufg.pes.healthhelp.R.string.atuacoes_atuais;
import static br.ufg.pes.healthhelp.R.string.atuacoes_mensagem_dialog;
import static br.ufg.pes.healthhelp.R.string.atuacoes_titulo_dialog;

public class AtuacoesActivity extends AbstractListActivity<Atuacao> {

    private AtuacaoService atuacaoService;
    private List<LocalAtendimento> locaisAtendimento;
    private Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AbstractListActivity.ARG_TITULO, getString(atuacoes));
        super.onCreate(savedInstanceState);
        atuacaoService = new AtuacaoService();
    }


    @Override
    public void recarregarItens() {
        super.iniciarProgresso(getString(atuacoes_titulo_dialog), getString(atuacoes_mensagem_dialog));
        Usuario usuarioStub = new Usuario();
        atuacaoService.solicitarListaAtuacoesPorUsuario(usuarioStub);
    }

    @Override
    public ItensSeparadoresAdapter<Atuacao> inicializarAdapter(List<Atuacao> itens) {
        return new ItensSeparadoresAdapter<Atuacao>(this,
            R.layout.item_separador_icone_nome_generico, ordenaAtuacoesDataFinalDecrescente(itens)) {
            @Override
            public void preencherItem(int position) {
                Atuacao atuacaoAtual = getItem(position);
                Date hoje = new Date(System.currentTimeMillis());

                if ((position < 1) &&
                    (removeHora(getItem(position).getDataFim()).compareTo(removeHora(hoje)) == 0)) {
                    textoSeparador.setText(getString(atuacoes_atuais));
                    flag = false;
                } else if ((removeHora(getItem(position).getDataFim()).compareTo(removeHora(hoje)) != 0) && !flag) {
                    flag = true;
                    textoSeparador.setText(getString(atuacoes_anteriores));
                } else {
                    textoSeparador.setVisibility(View.GONE);
                }

                textoDescricao.setText(geraSubTituloAtuacao(atuacaoAtual));
                icone.setImageResource(R.drawable.atuacao_vermelho);
            }
        };
    }

    @Override
    public AdapterView.OnItemClickListener obterAcaoClique() {
        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.listview_itens_genericos);
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent localAtendimentoIntent = new Intent(AtuacoesActivity.this, LocalAtendimentoActivity.class);
                Atuacao atuacao = (Atuacao) locaisAtendimentoView.getItemAtPosition(i);
                LocalAtendimento localAtendimento = atuacao.getLocalAtendimento();
                Log.i("Locais Atendimento", "Local de Atendimento selecionado: " + localAtendimento.getNome());
                localAtendimentoIntent.putExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER, localAtendimento);
                startActivity(localAtendimentoIntent);
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

    private List<Atuacao> ordenaAtuacoesDataFinalDecrescente(List<Atuacao> atuacoes) {
        Collections.sort(atuacoes, new Comparator<Atuacao>() {
            @Override
            public int compare(Atuacao lhs, Atuacao rhs) {
                return lhs.getDataFim().compareTo(rhs.getDataFim());
            }
        });
        Collections.reverse(atuacoes);
        return atuacoes;
    }

    private Date removeHora(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        Calendar dateCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return dateCalendar.getTime();
    }

    private String abreviaLocalAtuacao(String localAtuacao) {
        if (localAtuacao.length() > 30)
            return localAtuacao.substring(0, 30).concat("...");
        else
            return localAtuacao;
    }

    private String geraResumoSubTituloAtuacaoData(Date dataInicio, Date dataFinal) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String saida = "De: ";
        saida = saida.concat(format.format(removeHora(dataInicio)));
        saida = saida.concat(" a ");
        saida = saida.concat(format.format(removeHora(dataFinal)));
        saida = saida.concat(".");
        return saida;
    }

    private String geraSubTituloAtuacao(Atuacao atuacao) {
        return abreviaLocalAtuacao(atuacao.getLocalAtendimento().getNome())
            + '\n' + geraResumoSubTituloAtuacaoData(atuacao.getDataInicio(), atuacao.getDataFim());
    }
}
