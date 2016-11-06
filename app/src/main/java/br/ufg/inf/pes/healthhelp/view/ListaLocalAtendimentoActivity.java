package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.pes.healthhelp.R;

public class ListaLocalAtendimentoActivity extends AppCompatActivity {

    private LocalAtendimentoService localAtendimentoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_local_atendimento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        localAtendimentoService = new LocalAtendimentoService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.so_pesquisa, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.acao_pesquisa);
        configurarPesquisa((SearchView) MenuItemCompat.getActionView(searchMenuItem));

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(this.getClass().getName(), "CHANGE QUERY: " + newText);
                return false;
            }
        });
    }

    private void carregar(List<LocalAtendimento> locaisAtendimento) {
        Log.w("LocaisAtendimentoAct", "os locais cadastrados sao: " + locaisAtendimento);

        // R.layout.simple_list_item_1 é um layout simples de TextView
       /* adapter = new ArrayAdapter<LocalAtendimento>(this,
                android.R.layout.simple_list_item_1,
                locaisAtendimento);*/

        // Cria o adapter para converter o array para views
        ArrayAdapter<LocalAtendimento> adapter = new ArrayAdapter<LocalAtendimento>(this, R.layout.item_local, locaisAtendimento){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.w("LocaisListAdapter:","item na posicao: " + position + " para view de locais de atendimento: " + getItem(position).getNome());

                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_local, parent, false);
                }

                TextView nomeLocal = (TextView) convertView.findViewById(R.id.nome_local);
                TextView enderecoLocal = (TextView) convertView.findViewById(R.id.endereco_local);

                nomeLocal.setText(getItem(position).getNome());
                enderecoLocal.setText(getItem(position).getEndereco());

                return  convertView;
            }
        };


        // anexa o adapter a uma ListView
        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.listview_locais_atendimento);

        locaisAtendimentoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent localAtendimentoIntent = new Intent(ListaLocalAtendimentoActivity.this, LocalAtendimentoActivity.class);
                LocalAtendimento localAtendimento = (LocalAtendimento) locaisAtendimentoView.getItemAtPosition(i);
                Log.i("Locais Atendimento", "Local de Atendimento selecionado: " + localAtendimento.getNome());
                localAtendimentoIntent.putExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER, localAtendimento);
                startActivity(localAtendimentoIntent);
            }
        });

        locaisAtendimentoView.setAdapter(adapter);

    }

    public void novoLocal(View view){
        Intent intent = new Intent(this, NovoLocalAtendimentoActivity.class);
        startActivity(intent);
    }

}
