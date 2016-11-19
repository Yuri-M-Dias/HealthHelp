package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.Sessao;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.inf.pes.healthhelp.view.adapters.LocaisAtendimentoAdapter;
import br.ufg.pes.healthhelp.R;

public class LocaisAtendimentoActivity extends AppCompatActivity {

    private LocalAtendimentoService localAtendimentoService;
    private LocaisAtendimentoAdapter locaisAtendimentoAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locais_atendimento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        localAtendimentoService = new LocalAtendimentoService();

        Log.i("ksjdhksjdhksjdhksjdh", "On create!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_so_pesquisa, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.acao_pesquisa);
        configurarPesquisa((SearchView) MenuItemCompat.getActionView(searchMenuItem));

        return true;
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
        if (!Sessao.estaAtiva(this)) {
            Intent intent = new Intent(this, AutenticacaoActivity.class);
            startActivity(intent);
        } else {
            recarregarLocais();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((FloatingActionMenu) findViewById(R.id.fab)).close(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void recarregarLocais() {
        progressDialog = ProgressDialog.show(this, getString(R.string.titulo_carregamento_locais), getString(R.string.mensagem_carregamento_locais), true, true);
        localAtendimentoService.solicitarListaLocaisAtendimento();
    }

    @Subscribe
    public void onDatabaseEvent(DatabaseEvent<List<LocalAtendimento>> databaseEvent) {
        progressDialog.dismiss();
        carregar(databaseEvent.getObjeto());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThrowableFailureEvent(ThrowableFailureEvent event) {
        progressDialog.dismiss();
        Toast.makeText(this, event.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }

    private void configurarPesquisa(SearchView searchView) {
        searchView.setQueryHint("Procurar local atendimento...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(this.getClass().getName(), "QUERY: " + query);
                if (locaisAtendimentoAdapter != null) {
                    locaisAtendimentoAdapter.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(this.getClass().getName(), "CHANGE QUERY: " + newText);
                if (locaisAtendimentoAdapter != null) {
                    locaisAtendimentoAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    private void carregar(List<LocalAtendimento> locaisAtendimento) {
        Log.w("LocaisAtendimentoAct", "os locais cadastrados sao: " + locaisAtendimento);

        locaisAtendimentoAdapter = new LocaisAtendimentoAdapter(this, R.layout.item_local, locaisAtendimento);

        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.listview_locais_atendimento);

        locaisAtendimentoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent localAtendimentoIntent = new Intent(LocaisAtendimentoActivity.this, LocalAtendimentoActivity.class);
                LocalAtendimento localAtendimento = (LocalAtendimento) locaisAtendimentoView.getItemAtPosition(i);
                Log.i("Locais Atendimento", "Local de Atendimento selecionado: " + localAtendimento.getNome());
                localAtendimentoIntent.putExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER, localAtendimento);
                startActivity(localAtendimentoIntent);
            }
        });

        locaisAtendimentoView.setAdapter(locaisAtendimentoAdapter);
    }

    public void novoLocal(View view) {
        Intent intent = new Intent(this, FormularioLocalAtendimentoActivity.class);
        startActivity(intent);
    }

    public void exibir(LocalAtendimento localAtendimento) {
        Intent localAtendimentoIntent = new Intent(LocaisAtendimentoActivity.this, LocalAtendimentoActivity.class);
        localAtendimentoIntent.putExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER, localAtendimento);
        startActivity(localAtendimentoIntent);
    }


}
