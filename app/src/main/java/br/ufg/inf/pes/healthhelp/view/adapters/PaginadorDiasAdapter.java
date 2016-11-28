package br.ufg.inf.pes.healthhelp.view.adapters;

import android.os.Bundle;
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

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.view.AgendaCompletaFragment;
import br.ufg.inf.pes.healthhelp.view.AgendaDisponivelFragment;
import br.ufg.inf.pes.healthhelp.view.AgendaFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PaginadorDiasAdapter extends FragmentStatePagerAdapter {

    private final String TAG = PaginadorDiasAdapter.class.getName();
    private final int numeroAbasAAdicionar = 7;
    private final Calendar hoje;
    private LinkedList<Calendar> intervaloVisualizacao;
    private boolean permiteVerPassado;
    private final int QUANTIDADE_DIAS_MES_PADRAO = 30;
    private int indiceUltimaDataCarregada = -1;
    private Atuacao atuacao;
    private Class<AgendaFragment> tipoAgenda;

    public PaginadorDiasAdapter(FragmentManager fm, boolean permiteVerPassado, Calendar contextoTemporal, Atuacao atuacao, Class<AgendaFragment> tipoAgenda) {
        super(fm);
        hoje = Calendar.getInstance();
        hoje.set(Calendar.HOUR_OF_DAY, 0);
        hoje.set(Calendar.MINUTE, 0);
        hoje.set(Calendar.SECOND, 0);
        hoje.set(Calendar.MILLISECOND, 0);

        this.permiteVerPassado = permiteVerPassado;
        this.atuacao = atuacao;
        this.tipoAgenda = tipoAgenda;

        construirIntervaloPadraoVisualizacao(contextoTemporal);
    }

    /**
     * Constrói o intervalo padrão de tempo em dias que serão visualizados nas abas.
     * @param contextoTemporal Dia dado como referência para construção do intervalo de tempo de visualização.
     */
    private void construirIntervaloPadraoVisualizacao(Calendar contextoTemporal) {
        intervaloVisualizacao = new LinkedList<>();
        Calendar dataInicial = (Calendar) contextoTemporal.clone();
        Calendar dataFinal = (Calendar) contextoTemporal.clone();

        dataInicial.add(Calendar.DAY_OF_MONTH, -(QUANTIDADE_DIAS_MES_PADRAO/2));
        dataFinal.add(Calendar.DAY_OF_MONTH, (QUANTIDADE_DIAS_MES_PADRAO/2));

        if(!permiteVerPassado && dataInicial.before(hoje)){
            int diasAdicionais = (int) (hoje.getTimeInMillis() - dataInicial.getTimeInMillis())/(1000*60*60*24);
            dataFinal.add(Calendar.DAY_OF_MONTH, diasAdicionais);
            dataInicial = hoje;
        }

        for (Calendar diaCorrente = dataInicial; diaCorrente.before(dataFinal); diaCorrente.add(Calendar.DAY_OF_MONTH, 1)) {
            intervaloVisualizacao.add((Calendar) diaCorrente.clone());
        }

    }

    /**
     * Obtém a data selecionada (atual) no paginador.
     * @param container Container contendo informações sobre o paginador.
     * @return data selecionada (atual) no paginador.
     */
    public Calendar getDataSelecionada(ViewPager container){
        return intervaloVisualizacao.get(container.getCurrentItem());
    }

    @Override
    public int getItemPosition(Object object) {
        int posicao = super.getItemPosition(object);
        if (object instanceof Calendar) {
            Calendar dataProcurada = (Calendar) object;
            int posicaoIteradora = 0;
            for (Calendar data : intervaloVisualizacao) {
                if (dataProcurada.get(Calendar.YEAR) == data.get(Calendar.YEAR)
                    && dataProcurada.get(Calendar.MONTH) == data.get(Calendar.MONTH)
                    && dataProcurada.get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH)) {
                    posicao = posicaoIteradora;
                    break;
                }
                posicaoIteradora++;
            }
        }

        return posicao;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        carregarNovasDatas((ViewPager) container);

    }

    /**
     * Carrega novas abas no início ou fim da lista de abas com novas datas, caso necessário.
     * @param container container que contém as abas e informações de gerenciamento das mesmas.
     */
    private void carregarNovasDatas(ViewPager container) {
        int indiceDataAtual = container.getCurrentItem();

        if (indiceDataAtual >= intervaloVisualizacao.size() - 2) {
            Log.i(TAG, "Carregando mais abas no FINAL da lista");

            Calendar ultimaData = (Calendar) intervaloVisualizacao.getLast().clone();
            for (int contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                ultimaData.add(Calendar.DAY_OF_MONTH, 1);
                intervaloVisualizacao.addLast((Calendar) ultimaData.clone());
            }

            notifyDataSetChanged();
            container.setCurrentItem(indiceDataAtual);

        } else if (indiceDataAtual < 2) {
            if(indiceUltimaDataCarregada >= indiceDataAtual){
                container.computeScroll();
                container.setCurrentItem(indiceUltimaDataCarregada);

                indiceUltimaDataCarregada = -1;
                Log.d(TAG, "Carregamento de abas no início finalizado.");
            } else {
                Log.i(TAG, "Carregando mais abas no INÍCIO da lista");
                Calendar primeiraData = (Calendar) intervaloVisualizacao.getFirst().clone();
                indiceDataAtual = getItemPosition(primeiraData) + indiceDataAtual;

                int contadorPosicao;
                boolean intervaloFoiModificado = false;
                for (contadorPosicao = 0; contadorPosicao < numeroAbasAAdicionar; contadorPosicao++) {
                    primeiraData.add(Calendar.DAY_OF_MONTH, -1);
                    if (primeiraData.before(hoje) && !permiteVerPassado) {
                        if(intervaloFoiModificado) {
                            Log.i(TAG, "Apenas algumas dias foram adicionados ao início da lista.");
                        } else {
                            Log.i(TAG, "Limite inferior (data corrente) já atingido.");
                        }
                        break;
                    } else {
                        intervaloVisualizacao.addFirst((Calendar) primeiraData.clone());
                        intervaloFoiModificado = true;
                    }
                }

                indiceDataAtual += contadorPosicao;

                if (intervaloFoiModificado) {
                    indiceUltimaDataCarregada = indiceDataAtual;
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public int getCount() {
        return intervaloVisualizacao.size();
    }

    @Override
    public Fragment getItem(int position) {
        //TODO: Provavelmente existe uma forma melhor de fazer isso aqui. Pesquisar e modificar
        AgendaFragment agendaFragment = null;
        Bundle args = new Bundle();
        args.putSerializable(AgendaFragment.ARG_DATA, intervaloVisualizacao.get(position));
        args.putSerializable(AgendaFragment.ARG_ATUACAO, atuacao);

        try {
            agendaFragment = tipoAgenda.newInstance();
            agendaFragment.setArguments(args);
        } catch (InstantiationException e) {
            Log.e(TAG, "Erro ao instanciar " + tipoAgenda.getName() + ". Erro: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Erro ao instanciar " + tipoAgenda.getName() + ". Erro: " + e.getMessage());
        }

        return agendaFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        return simpleDateFormat.format(intervaloVisualizacao.get(position).getTime());
    }
}
