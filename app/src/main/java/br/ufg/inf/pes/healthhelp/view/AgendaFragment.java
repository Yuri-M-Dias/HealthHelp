package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.ufg.pes.healthhelp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AgendaFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public AgendaFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AgendaFragment newInstance(Calendar data) {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_agenda_disponivel, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        Calendar data = (Calendar) getArguments().getSerializable(ARG_SECTION_NUMBER);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        textView.setText(simpleDateFormat.format(data.getTime()));
        return rootView;
    }
}
