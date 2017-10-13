package tpv.cirer.com.restaurante.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tpv.cirer.com.restaurante.R;

/**
 * Created by JUAN on 20/07/2017.
 */

public class FragmentoInicioNew  extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicioNew adaptador;
    private static FragmentoInicioNew fragmentinicionew = null;

    public FragmentoInicioNew() {
    }
    public static FragmentoInicioNew newInstance() {
        FragmentoInicioNew fragmentinicionew = new FragmentoInicioNew();
        return fragmentinicionew;
    }
    public static FragmentoInicioNew getInstance(){
        if(fragmentinicionew == null){
            fragmentinicionew = new FragmentoInicioNew();
        }
        return fragmentinicionew;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio_new, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorInicioNew();
        reciclador.setAdapter(adaptador);
        return view;
    }

}
