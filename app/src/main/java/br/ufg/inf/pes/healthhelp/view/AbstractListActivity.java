package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import java.util.List;

import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

public abstract class AbstractListActivity<T> extends AppCompatActivity {

    public final static String ARG_TITULO = "titulo";
    protected String TAG;
    private ProgressDialog progressDialog;

    private ItensSeparadoresAdapter<T> itensAdapter;

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

    public abstract void recarregarItens();

    void iniciarProgresso(String titulo, String mensagem){
        progressDialog = ProgressDialog.show(this, titulo, mensagem, true, true);
    }

    @Subscribe
    public void onDatabaseEvent(DatabaseEvent<List> databaseEvent) {
        progressDialog.dismiss();
        carregar(databaseEvent.getObjeto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThrowableFailureEvent(ThrowableFailureEvent event) {
        progressDialog.dismiss();
        Toast.makeText(this, event.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }

    public abstract ItensSeparadoresAdapter<T> inicializarAdapter(List<T> itens);
    public abstract AdapterView.OnItemClickListener obterAcaoClique();

    private void carregar(List<T> itens) {
        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.listview_itens_genericos);

        locaisAtendimentoView.setOnItemClickListener(obterAcaoClique());

        locaisAtendimentoView.setAdapter(inicializarAdapter(itens));
    }
}
