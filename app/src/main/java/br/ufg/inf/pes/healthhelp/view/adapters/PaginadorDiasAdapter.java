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
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.view.AgendaFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PaginadorDiasAdapter extends FragmentStatePagerAdapter {

    private final String TAG = PaginadorDiasAdapter.class.getName();
    private final int numeroAbasAAdicionar = 7;
    private LinkedList<Calendar> intervaloVisualizacao;
    private boolean permiteVerPassado;
    private final int QUANTIDADE_DIAS_MES_PADRAO = 30;
    private Calendar dataMudanca;
    private boolean precisaNovoCarregamento = false;
    private List<Agenda> agendas;

    public PaginadorDiasAdapter(FragmentManager fm, boolean permiteVerPassado, Calendar contextoTemporal, List<Agenda> agendas) {
        super(fm);
        this.permiteVerPassado = permiteVerPassado;
        this.agendas = agendas;
        construirIntervaloVisualizacao(contextoTemporal);
    }

    /**
     * Constrói o intervalo de tempo em dias que serão visualizados nas abas.
     * @param contextoTemporal Dia dado como referência para construção do intervalo de tempo de visualização.
     */
    private void construirIntervaloVisualizacao(Calendar contextoTemporal) {
        intervaloVisualizacao = new LinkedList<>();
        Calendar dataInicial = (Calendar) contextoTemporal.clone();
        Calendar dataFinal = (Calendar) contextoTemporal.clone();

        Calendar hoje = Calendar.getInstance();

        dataInicial.add(Calendar.DAY_OF_MONTH, -(QUANTIDADE_DIAS_MES_PADRAO/2));
        dataFinal.add(Calendar.DAY_OF_MONTH, (QUANTIDADE_DIAS_MES_PADRAO/2));

        if(!permiteVerPassado && dataInicial.before(hoje)){ //Esse before pode dar false
            int diasAdicionais = (int) (hoje.getTimeInMillis() - dataInicial.getTimeInMillis())/(1000*60*60*24);
            dataFinal.add(Calendar.DAY_OF_MONTH, diasAdicionais);
            dataInicial = hoje;
        }

        for (Calendar diaCorrente = dataInicial; diaCorrente.before(dataFinal); diaCorrente.add(Calendar.DAY_OF_MONTH, 1)) {
            intervaloVisualizacao.add((Calendar) diaCorrente.clone());
        }

    }

    public Calendar getDataMudanca() {
        return dataMudanca;
    }

    @Override
    public int getItemPosition(Object object) {
        int posicao = super.getItemPosition(object);
        if (object instanceof Calendar) {
            Calendar dataProcurada = (Calendar) object;
            for (Calendar data : intervaloVisualizacao) {
                if (dataProcurada.get(Calendar.YEAR) == data.get(Calendar.YEAR)
                    && dataProcurada.get(Calendar.MONTH) == data.get(Calendar.MONTH)
                    && dataProcurada.get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH)) {
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

    /**
     * Verifica se é necessário carregar mais abas de datas.
     * @param ultimaPosicaoCarregada Índice da aba da última data visualizada.
     */
    private void analisarNovoCarregamento(int ultimaPosicaoCarregada) {
        if (ultimaPosicaoCarregada >= intervaloVisualizacao.size() - 1) {
            precisaNovoCarregamento = true;
        } else if (ultimaPosicaoCarregada == 0 && permiteVerPassado) {
            precisaNovoCarregamento = true;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        if (precisaNovoCarregamento) {
            precisaNovoCarregamento = false;
            ViewPager viewPager = (ViewPager) container;
            viewPager.getCurrentItem();
            int currentItem = ((ViewPager) container).getCurrentItem();
            dataMudanca = intervaloVisualizacao.get(currentItem);
            if (carregarNovasDatas(currentItem)) {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Carrega novas abas com novas datas.
     * @param indiceDataAtual Índice da aba da última data visualizada.
     * @return TRUE se as abas foram carregadas com sucesso, FALSE caso contrário.
     */
    private boolean carregarNovasDatas(int indiceDataAtual) {
        boolean resultado;
        if (indiceDataAtual >= intervaloVisualizacao.size() - 2) {
            Calendar ultimaData = (Calendar) intervaloVisualizacao.getLast().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                ultimaData.add(Calendar.DAY_OF_MONTH, 1);
                intervaloVisualizacao.addLast((Calendar) ultimaData.clone());
            }
            resultado = true;
        } else if (indiceDataAtual < 1) {
            Calendar primeiraData = (Calendar) intervaloVisualizacao.getFirst().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                primeiraData.add(Calendar.DAY_OF_MONTH, -1);
                intervaloVisualizacao.addFirst((Calendar) primeiraData.clone());
            }
            resultado = true;
        } else {
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
        return AgendaFragment.newInstance(intervaloVisualizacao.get(position), agendas.toArray(new Agenda[agendas.size()]));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        return simpleDateFormat.format(intervaloVisualizacao.get(position).getTime());
    }
}
