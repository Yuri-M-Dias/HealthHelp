package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
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

    public PaginadorDiasAdapter(FragmentManager fm, boolean permiteVerPassado, Calendar contextoTemporal) {
        super(fm);
        this.permiteVerPassado = permiteVerPassado;
        construirIntervaloVisualizacao(contextoTemporal);
    }

    private void construirIntervaloVisualizacao(Calendar contextoTemporal) {
        intervaloVisualizacao = new LinkedList<>();
        Calendar dataInicial = (Calendar) contextoTemporal.clone();
        int diaFinal = contextoTemporal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if(contextoTemporal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
            && contextoTemporal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
            if(permiteVerPassado) {
                dataInicial.set(Calendar.DAY_OF_MONTH, contextoTemporal.getActualMinimum(Calendar.DAY_OF_MONTH));
            } else {
                dataInicial.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }
        } else if(contextoTemporal.before(Calendar.getInstance())) {
            if(permiteVerPassado) {
                dataInicial.set(Calendar.DAY_OF_MONTH, contextoTemporal.getActualMinimum(Calendar.DAY_OF_MONTH));
            } else {
                diaFinal = 0;
            }
        } else {
            dataInicial.set(Calendar.DAY_OF_MONTH, contextoTemporal.getActualMinimum(Calendar.DAY_OF_MONTH));
        }

        for(int diaCorrente = dataInicial.get(Calendar.DAY_OF_MONTH); diaCorrente <= diaFinal; diaCorrente++) {
            intervaloVisualizacao.add((Calendar) dataInicial.clone());
            dataInicial.add(Calendar.DAY_OF_MONTH, 1);
        }

    }

    @Override
    public int getItemPosition(Object object) {
        int posicao = super.getItemPosition(object);
        if(object instanceof Calendar) {
            Calendar dataProcurada = (Calendar) object;
            for(Calendar data: intervaloVisualizacao) {
                if(dataProcurada.get(Calendar.YEAR) == data.get(Calendar.YEAR)
                    && dataProcurada.get(Calendar.MONTH) == data.get(Calendar.MONTH)
                    && dataProcurada.get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH)){
                    posicao = intervaloVisualizacao.indexOf(data);
                    break;
                }
            }
        }

        return posicao;
    }

    @Override
    public int getCount() {
        return intervaloVisualizacao.size();
    }

    @Override
    public Fragment getItem(int position) {
        return AgendaFragment.newInstance(intervaloVisualizacao.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        return simpleDateFormat.format(intervaloVisualizacao.get(position).getTime());
    }
}
