package br.ufg.inf.pes.healthhelp.view;

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

import com.google.firebase.database.DatabaseError;

import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.Sessao;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.inf.pes.healthhelp.view.adapters.LocaisAtendimentoAdapter;
import br.ufg.pes.healthhelp.R;

public class LocaisAtendimentoActivity extends AppCompatActivity {

    private LocalAtendimentoService localAtendimentoService;
    private LocaisAtendimentoAdapter locaisAtendimentoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locais_atendimento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        localAtendimentoService = new LocalAtendimentoService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_so_pesquisa, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.acao_pesquisa);
        configurarPesquisa((SearchView) MenuItemCompat.getActionView(searchMenuItem));

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Sessao.estaAtiva(this)) {
            Intent intent = new Intent(this, AutenticacaoActivity.class);
            startActivity(intent);
        } else {
            recarregarLocais();
        }
    }

    private void recarregarLocais() {
        //TODO: Mostrar notificação de carregamento da lista para o usuário
        localAtendimentoService.solicitarListaLocaisAtendimento(new DatabaseCallback<List<LocalAtendimento>>() {

            @Override
            public void onComplete(List<LocalAtendimento> object) {
                carregar(object);
                //TODO: Finalizar carregamento da lista
            }

            @Override
            public void onError(DatabaseError exception) {
                //TODO: Finalizar carregamento da lista
                //TODO: Mostrar erro para o usuário
            }
        });
    }

    private void configurarPesquisa(SearchView searchView) {
        searchView.setQueryHint("Procurar local atendimento...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(this.getClass().getName(), "QUERY: " + query);
                if(locaisAtendimentoAdapter != null) {
                    locaisAtendimentoAdapter.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(this.getClass().getName(), "CHANGE QUERY: " + newText);
                if(locaisAtendimentoAdapter != null) {
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

    public void novoLocal(View view){
        Intent intent = new Intent(this, FormularioLocalAtendimentoActivity.class);
        startActivity(intent);
    }

    public void exibir(LocalAtendimento localAtendimento){
        Intent localAtendimentoIntent = new Intent(LocaisAtendimentoActivity.this, LocalAtendimentoActivity.class);
        localAtendimentoIntent.putExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER, localAtendimento);
        startActivity(localAtendimentoIntent);
    }


}
