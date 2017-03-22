package tpv.cirer.com.marivent.herramientas;

import tpv.cirer.com.marivent.ui.AdaptadorCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JUAN on 14/10/2016.
 */

public class ArrayListAdaptadorCategorias  {
 private List<AdaptadorCategorias> mAdapter;
 public ArrayListAdaptadorCategorias(ArrayList<AdaptadorCategorias> adapter) {
    this.mAdapter = adapter;
}
    public void add(AdaptadorCategorias categoria, int position) {
        mAdapter.add(position, categoria);
    }

    public AdaptadorCategorias get(int position) {
        return mAdapter.get(position);
    }

}