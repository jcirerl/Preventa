package tpv.cirer.com.restaurante.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import tpv.cirer.com.restaurante.R;


/**
 * Created by JUAN on 19/09/2016.
 */
public class FragmentoClosePedido extends Fragment {

    public FragmentoClosePedido(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragmento_tarjetas,container,false);
    }
}