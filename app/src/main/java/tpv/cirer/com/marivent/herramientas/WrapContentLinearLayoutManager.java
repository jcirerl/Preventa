package tpv.cirer.com.marivent.herramientas;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by JUAN on 21/03/2016.
 */

public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }
   //... constructor
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("probe", "meet a IOOBE in RecyclerView");
        }
    }
}