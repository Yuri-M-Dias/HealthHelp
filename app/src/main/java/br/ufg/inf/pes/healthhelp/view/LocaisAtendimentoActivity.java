package br.ufg.inf.pes.healthhelp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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

public class LocaisAtendimentoActivity extends AppCompatActivity {

    private LocalAtendimentoService localAtendimentoService;

    private class LocaisListAdapter extends ArrayAdapter<LocalAtendimento> {

        public LocaisListAdapter(Context context,List<LocalAtendimento> locaisAtendimento) {
            super(context,R.layout.item_local, locaisAtendimento);
        }

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
            TextView telefoneLocal = (TextView) convertView.findViewById(R.id.telefone_local);

            nomeLocal.setText(getItem(position).getNome());
            enderecoLocal.setText(getItem(position).getEndereco());
            telefoneLocal.setText(getItem(position).getTelefone());

            return  convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_atendimento);
        initToolbar();
        localAtendimentoService = new LocalAtendimentoService();

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

    /**
     * disparado pelo LocalAtendimentoService após retorno do firebase dos registros de local de atendimento
     * cria um array adapter e converte cada item do arraylist para uma view
     * @param locaisAtendimento a lista de locais de atendimento retornada
     */
    private void carregar(List<LocalAtendimento> locaisAtendimento) {
        Log.w("LocaisAtendimentoAct","os locais cadastrados sao: " + locaisAtendimento);

        // R.layout.simple_list_item_1 é um layout simples de TextView
       /* adapter = new ArrayAdapter<LocalAtendimento>(this,
                android.R.layout.simple_list_item_1,
                locaisAtendimento);*/

        // Cria o adapter para converter o array para views
        LocaisListAdapter adapter =  new LocaisListAdapter(this, locaisAtendimento);

        // anexa o adapter a uma ListView
        final ListView locaisAtendimentoView = (ListView) findViewById(R.id.list_locais_atendimento);

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

        locaisAtendimentoView.setAdapter(adapter);

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarLocaisAtendimento);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hospital(View view){
        Intent intent = new Intent(this, LocalAtendimentoActivity.class);
        startActivity(intent);
    }

    public void novoLocal(View view){
        Intent intent = new Intent(this, NovoLocalAtendimentoActivity.class);
        startActivity(intent);
    }

}
