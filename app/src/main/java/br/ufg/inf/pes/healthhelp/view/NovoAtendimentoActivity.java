package br.ufg.inf.pes.healthhelp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.pes.healthhelp.R;

public class NovoAtendimentoActivity extends AppCompatActivity {

    private LocalAtendimentoService localAtendimentoService;
    private AutoCompleteTextView instituicao;
    private AutoCompleteTextView profissional;
    private TextView dataHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_atendimento);
        initToolbar();

        instituicao = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocalAtendimento);
        profissional = (AutoCompleteTextView) findViewById(R.id.autoCompleteProfissionalSaude);
        dataHorario = (TextView) findViewById(R.id.textView_dados_data_horario_atendimento);

        //essas variáveis serão instanciadas chamando o método: getNomesLocaisAtendimentos e getNomesProfissionais.
        String[] instituicoes = new String[] {"Hospital das Clínicas", "Hospinal Santa Genoveva",
            "Hospital HGG", "Hospital do Cancer", "Novo Hospital"};
        String[] profissionais = new String[] {"João não sei das quantas", "José Maria",
            "Açogueiro do pará", "Médico bom", "Enfermeiro"};

        instituicao.setAdapter(adapter(instituicoes));
        profissional.setAdapter(adapter(profissionais));
        dataHorario.setText(getHorarioAtendimento());
    }

    public ArrayAdapter<String> adapter(String[] listaDados){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, listaDados);
        return adaptador;
    }

    public String[] getNomesLocaisAtendimentos(String dado){
        //TODO buscar no banco todos os nomes dos locais para ser usado pelo adapter do AutoCompleteTextView
        localAtendimentoService.solicitarListaLocaisAtendimento();
        return null;
    }

    public String[] getNomesProfissionais(String dado){
        //TODO buscar no banco todos os nomes dos profissionais para ser usado pelo adapter do AutoCompleteTextView
        return null;
    }

    public String getHorarioAtendimento(){
        //TODO buscar o horário de atendimento do hospital/profissional selecionado nas AutoCompleteTestView
        return "";
    }

    public void marcar(){
        //TODO mandar salvar no banco o novo atendimento, este método é chamado no menu "Marcar"
    }

    public void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_novo_atendimento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_unica_simple, menu);

        MenuItem marcarMenuItem = menu.findItem(R.id.acao_unica);
        marcarMenuItem.setTitle(R.string.acao_marcar);
        marcarMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                marcar();
                return true;
            }
        });
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

}
