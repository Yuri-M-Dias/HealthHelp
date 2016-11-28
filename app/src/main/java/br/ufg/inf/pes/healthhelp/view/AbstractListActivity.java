package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

/**
 * Essa classe define e implementa grande parte das operações para activities que mostram listam seccionadas.
 *
 * @param <T> Tipo de objeto que será exibido na lista seccionada.
 */
public abstract class AbstractListActivity<T> extends AppCompatActivity {

    public final static String ARG_TITULO = "titulo_activity";
    protected String TAG;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getCanonicalName();

        setContentView(R.layout.activity_abstract_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getIntent().getStringExtra(ARG_TITULO));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        recarregarItens();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Executa as operações que antecedem o carregamento da lista de itens a partir do banco de dados. Nessa implementação, a ação básica a ser executada é a exibição de um {@link ProgressDialog}, que será finalizado quando a resposta da requisição for recebida.
     *
     * @param titulo   Título a ser exibido dentro do dialog.
     * @param mensagem Mensagem a ser exibida dentro do dialog.
     */
    void iniciarProgresso(String titulo, String mensagem) {
        progressDialog = ProgressDialog.show(this, titulo, mensagem, true, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(DatabaseEvent<List> databaseEvent) {
        progressDialog.dismiss();
        carregar(databaseEvent.getObjeto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThrowableFailureEvent(ThrowableFailureEvent event) {
        progressDialog.dismiss();
        Toast.makeText(this, event.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * Carrega uma lista de itens na activity.
     *
     * @param itens lista de itens a ser carregada.
     */
    private void carregar(List<T> itens) {
        Collections.sort(itens, obterComparador());
        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.listview_itens_genericos);

        locaisAtendimentoView.setOnItemClickListener(obterAcaoClique());

        locaisAtendimentoView.setAdapter(inicializarAdapter(itens));
    }

    /**
     * Executa a operação de recarregamento da lista de itens que são exibidos na activity. Para a maioria das implemetações, este método deverá solicitar à classe de serviço que busque a lista de itens a serem exibidos.
     */
    public abstract void recarregarItens();

    /**
     * Inicializa o adapter responsável pelo controle da exibição dos itens da lista. Na implementação desse método, o método {@link ItensSeparadoresAdapter#preencherItem(int)} do adapter é implementado para que seja possível apresentar os itens de forma correta.
     *
     * @param itens Lista de itens a serem exibidos pelo adapater.
     * @return adapter responsável pelo controle da exibição dos itens da lista.
     */
    public abstract ItensSeparadoresAdapter<T> inicializarAdapter(List<T> itens);

    /**
     * Define o listener responsável por definir a ação a ser executada quando um determinado iem da lista é selecionado.
     *
     * @return listener de controle de seleção de item da lista.
     */
    public abstract AdapterView.OnItemClickListener obterAcaoClique();

    /**
     * Define o comparador utilizado para se ordenar os itens da lista seccionada de forma correta.
     *
     * @return comparador implementado para ordenação da lista seccionada.
     */
    public abstract Comparator<T> obterComparador();

}
