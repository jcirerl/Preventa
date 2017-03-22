package tpv.cirer.com.marivent.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tpv.cirer.com.marivent.R;

/**
 * Created by JUAN on 19/09/2016.
 */
public class FragmentoPrintPedido extends Fragment {

    public FragmentoPrintPedido() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }


}
