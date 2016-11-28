package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.service.AgendaService;
import br.ufg.inf.pes.healthhelp.view.adapters.ItensSeparadoresAdapter;
import br.ufg.pes.healthhelp.R;

public class AgendasProfissionalActivity extends AbstractListActivity<Agenda> {

    private List<Agenda> agendas;
    private AgendaService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        getIntent().putExtra(AbstractListActivity.ARG_TITULO, getString(R.string.agendas_profissional_titulo_activity));
        super.onCreate(savedInstanceState);

        service = new AgendaService();
        agendas = criarAgendasProfissionais();
    }

    @Override
    public void recarregarItens() {
        iniciarProgresso(getString(R.string.agendas_profissional_titulo_dialog), getString(R.string.agendas_profissional_mensagem_dialog));

        for (Agenda agenda : agendas) {
            service.solicitarAgenda(agenda.getId());
        }
    }

    @Override
    public ItensSeparadoresAdapter<Agenda> inicializarAdapter(List<Agenda> itens) {
        return new ItensSeparadoresAdapter<Agenda>(this, R.layout.item_separador_icone_nome_generico, itens) {
            @Override
            public void preencherItem(int position) {

                Agenda agendaatual = getItem(position);
                String nomeagendaatual = agendaatual.getNome();

                if (position < 1 || !getItem(position - 1).getNome().equals(nomeagendaatual)) {
                    textoSeparador.setText(agendaatual.getNome());
                } else {
                    textoSeparador.setVisibility(View.GONE);
                }

                textoDescricao.setText(agendaatual.getNome());
                icone.setImageResource(R.drawable.profissional_saude_azul);
            }
        };
    }

    @Override
    public AdapterView.OnItemClickListener obterAcaoClique() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AgendasProfissionalActivity.this, AgendaCompletaActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public Comparator<Agenda> obterComparador() {
        return new Comparator<Agenda>() {
            @Override
            public int compare(Agenda agenda, Agenda rhs) {
                return agenda.getNome().compareTo(rhs.getNome());
            }
        };
    }

    private List<Agenda> criarAgendasProfissionais() {

        List<Agenda> agendasprofissional = new ArrayList<>();
        Agenda agenda = new Agenda();

        agenda.setNome("Cirurgias");
        agenda.setId("22");
        agendasprofissional.add(agenda);

        agenda = new Agenda();
        agenda.setNome("Consultas");
        agenda.setId("24");
        agendasprofissional.add(agenda);

        agenda = new Agenda();
        agenda.setNome("Pessoal");
        agenda.setId("120");
        agendasprofissional.add(agenda);

        return agendasprofissional;
    }

}
