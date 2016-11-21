package br.ufg.inf.pes.healthhelp.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
    private final int numeroAbasAAdicionar = 7;
    private Calendar dataMudanca;
    private boolean precisaNovoCarregamento = false;

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

    public Calendar getDataMudanca() {
        return dataMudanca;
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
    public Object instantiateItem(ViewGroup container, int position) {
        analisarNovoCarregamento(position);
        return super.instantiateItem(container, position);
    }

    private void analisarNovoCarregamento(int ultimaPosicaoCarregada){
        if (ultimaPosicaoCarregada >= intervaloVisualizacao.size() - 1) {
            precisaNovoCarregamento = true;
        } else if (ultimaPosicaoCarregada == 0 && permiteVerPassado) {
            precisaNovoCarregamento = true;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        Log.i(TAG, "Updating 1");
        super.finishUpdate(container);
        Log.i(TAG, "Updating 2");
        if(precisaNovoCarregamento) {
            precisaNovoCarregamento = false;
            Log.i(TAG, "Precisa de carregamento");
            ViewPager viewPager = (ViewPager) container;
            viewPager.getCurrentItem();
            int currentItem = ((ViewPager) container).getCurrentItem();
            Log.i(TAG, "Item atual = " + currentItem);
            dataMudanca = intervaloVisualizacao.get(currentItem);
            if(carregarNovasDatas(currentItem)){
                Log.i(TAG, "Notificando a galera");
                notifyDataSetChanged();
            }
            /*
            if(currentItem >= intervaloVisualizacao.size() - 2){
                Log.i(TAG, "Adicionando abas ao futuro");
                dataMudanca = intervaloVisualizacao.get(currentItem);
                Calendar ultimaData = (Calendar) intervaloVisualizacao.getLast().clone();
                for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                    ultimaData.add(Calendar.DAY_OF_MONTH, 1);
                    intervaloVisualizacao.addLast((Calendar) ultimaData.clone());
                }
                Log.i(TAG, "Notificando a galera");
                notifyDataSetChanged();
            } else if (currentItem < 1) {
                Log.i(TAG, "Adicionando abas ao passado");
                dataMudanca = intervaloVisualizacao.get(posicaoRequisitada + 1);
                Calendar primeiraData = (Calendar) intervaloVisualizacao.getFirst().clone();
                for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                    primeiraData.add(Calendar.DAY_OF_MONTH, -1);
                    intervaloVisualizacao.addFirst((Calendar) primeiraData.clone());
                }
                Log.i(TAG, "Notificando a galera");
                notifyDataSetChanged();
            }*/
        }
    }

    private boolean carregarNovasDatas(int indiceDataAtual){
        boolean resultado;
        if(indiceDataAtual >= intervaloVisualizacao.size() - 2){
            Log.i(TAG, "Adicionando abas ao futuro");
            Calendar ultimaData = (Calendar) intervaloVisualizacao.getLast().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                ultimaData.add(Calendar.DAY_OF_MONTH, 1);
                intervaloVisualizacao.addLast((Calendar) ultimaData.clone());
            }
            resultado = true;
        } else if (indiceDataAtual < 1) {
            Log.i(TAG, "Adicionando abas ao passado");
            Calendar primeiraData = (Calendar) intervaloVisualizacao.getFirst().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                primeiraData.add(Calendar.DAY_OF_MONTH, -1);
                intervaloVisualizacao.addFirst((Calendar) primeiraData.clone());
            }
            resultado = true;
        }else {
            Log.i(TAG, "De acordo com o item atual, não é necessário carregar novas datas.");
            resultado = false;
        }

        return resultado;
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
