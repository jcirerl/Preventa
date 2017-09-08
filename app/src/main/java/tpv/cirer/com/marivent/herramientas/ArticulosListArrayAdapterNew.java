package tpv.cirer.com.marivent.herramientas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.ArticulosNew;

/**
 * Created by JUAN on 06/09/2017.
 */

public class ArticulosListArrayAdapterNew extends RecyclerView.Adapter<ArticulosRowHolder> {

    private List<ArticulosNew> mArticulosNew;
    private Context mContext;
    private OnHeadlineSelectedListenerArticulos mCallback;


    public ArticulosListArrayAdapterNew(Context context, ArrayList<ArticulosNew> ArticulosNew) {
        this.mArticulosNew = ArticulosNew;
        this.mContext = context;

        try {
            this.mCallback = ((OnHeadlineSelectedListenerArticulos) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public ArticulosRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View vArticulosNew = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_articuloscode_row_new, null);
        //  ArticulosRowHolder mh = new ArticulosRowHolder(v);
        ArticulosRowHolder mhArticulosNew = new ArticulosRowHolder(vArticulosNew, new ArticulosRowHolder.IMyArticulosViewHolderClicks() {
            public void onPotato(View caller, final String action, final String codigoArticulo, final String nombreArticulo) {

                Log.i("onPotato",action + " " + codigoArticulo + " " + nombreArticulo);
                switch (action){
                    case "IMAGEN":
                        try {
                            mCallback.onArticulosNewSelected(nombreArticulo);
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;
                    case "CHECKBOX":
                        try {
                            mCallback.onArticulosNewChecked(codigoArticulo,Integer.parseInt(nombreArticulo));
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;
                    case "SPINNER":
                        try {
                            mCallback.onArticulosNewTipoplato(codigoArticulo,Integer.parseInt(nombreArticulo));
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;
                }

            };

        });
        return mhArticulosNew;
    }

    @Override
    public void onBindViewHolder(ArticulosRowHolder ArticulosRowHolder, int i) {
        String myText;
        Log.i("recview comida fuera", Integer.toString(mArticulosNew.size()));
        try {
            ArticulosNew ArticulosNew = mArticulosNew.get(i);
            ArticulosRowHolder.bind(ArticulosNew);
            Log.i("recview seleccionado", ArticulosNew.getName());
/*            Picasso.with(mContext)
                    .load(ArticulosNew.getUrlimagen())
                    .resize(60, 60)
                    .centerCrop()
                    .into(ArticulosRowHolder.imagen);
*/            Glide.with(mContext)
                    .load(ArticulosNew.getUrlimagen())
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(60,60)
                    .into(ArticulosRowHolder.imagen);


        } catch (Exception e) {
            Log.i("recview Articulos", Integer.toString(mArticulosNew.size()));
            // do something
        }

    }

    @Override
    public int getItemCount() {
        return (null != mArticulosNew ? mArticulosNew.size() : 0);
    }

    public Object getItem(int pos) {
        return mArticulosNew.get(pos);
    }

    public long getItemId(int pos) {
        return mArticulosNew.indexOf(getItem(pos));
    }

    ///////////////////////////////////////////
// La actividad contenedora debe implementar esta interfaz
     public interface OnHeadlineSelectedListenerArticulos {
        void onArticulosNewSelected(String nombre);
        void onArticulosNewChecked(String codigo, int check);
        void onArticulosNewTipoplato(String codigo, int pos);

    }

    public void add(ArticulosNew ArticulosNew, int position) {
        mArticulosNew.add(position, ArticulosNew);
        notifyItemInserted(position);
    }

    public void remove(ArticulosNew ArticulosNew) {
        int position = mArticulosNew.indexOf(ArticulosNew);
        mArticulosNew.remove(position);
        notifyItemRemoved(position);
    }

}
