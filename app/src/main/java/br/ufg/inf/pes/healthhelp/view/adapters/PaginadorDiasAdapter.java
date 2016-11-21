package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import br.ufg.inf.pes.healthhelp.view.AgendaFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PaginadorDiasAdapter extends FragmentStatePagerAdapter {

    private final String TAG = PaginadorDiasAdapter.class.getName();

    private LinkedList<Calendar> intervaloVisualizacao;
    private boolean permiteVerPassado;
    private int tamanhoPadraoIntervaloVisualizao = 4;
    private int limiteParaRequisicao = 1;
    private final int numeroAbasAAdicionar = 2;

    public PaginadorDiasAdapter(FragmentManager fm, boolean permiteVerPassado, Context contexto) {
        super(fm);
        this.permiteVerPassado = permiteVerPassado;
        construirIntervaloAtualizao();
    }

    private void construirIntervaloAtualizao() {
        Calendar dataCorrente = Calendar.getInstance();
        intervaloVisualizacao = new LinkedList<>();

        if(permiteVerPassado) {
            dataCorrente.add(Calendar.DAY_OF_MONTH, -(tamanhoPadraoIntervaloVisualizao / 2));
        }

        for(int diaCorrente = 0; diaCorrente < tamanhoPadraoIntervaloVisualizao; diaCorrente++) {
            intervaloVisualizacao.add((Calendar) dataCorrente.clone());
            dataCorrente.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public int getCount() {
        return intervaloVisualizacao.size();
    }

    @Override
    public Fragment getItem(int position) {
        AgendaFragment agendaFragment = AgendaFragment.newInstance(intervaloVisualizacao.get(position));
        return agendaFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        processarMudanca(container, position);
        return object;
    }

    private void processarMudanca(ViewGroup container, int novaPosicao){

        if (novaPosicao + limiteParaRequisicao >= intervaloVisualizacao.size()) {
            Calendar ultimaData = (Calendar) intervaloVisualizacao.getLast().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                ultimaData.add(Calendar.DAY_OF_MONTH, 1);
                intervaloVisualizacao.addLast((Calendar) ultimaData.clone());
            }
            notifyDataSetChanged();
        } else if (permiteVerPassado && novaPosicao - limiteParaRequisicao <= 0) {
            Calendar primeiraData = (Calendar) intervaloVisualizacao.getFirst().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                primeiraData.add(Calendar.DAY_OF_MONTH, -1);
                intervaloVisualizacao.addFirst((Calendar) primeiraData.clone());
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        return simpleDateFormat.format(intervaloVisualizacao.get(position).getTime());
    }
}
