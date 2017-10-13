package tpv.cirer.com.restaurante.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.PopularRowHolder;
import tpv.cirer.com.restaurante.modelo.Popular;

/**
 * Created by JUAN on 18/11/2016.
 */

public class AdaptadorPopulares extends RecyclerView.Adapter<PopularRowHolder> implements Filterable {

    private ArrayList<Popular> mPopular;
    private ArrayList<Popular> mfilterList;
    private CustomFilter filter;
    private Context mContext;
    private OnHeadlineSelectedListener mCallback;
    ValueFilter valueFilter;


    public AdaptadorPopulares(Context context, ArrayList<Popular> Popular) {
        this.mPopular = Popular;
        this.mfilterList = Popular;
        this.mContext = context;
        try {
            this.mCallback = ((OnHeadlineSelectedListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public PopularRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View vPopular = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_inicio, viewGroup,false);
        //  PopularRowHolder mh = new PopularRowHolder(v);
        PopularRowHolder mhPopular = new PopularRowHolder(vPopular, new PopularRowHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, final String articuloPopular, final String nombrePopular, final String precioPopular, final String tivaPopular) {


                Log.d("Poh-tah-tos", nombrePopular + " " + precioPopular );
                try {
                    mCallback.onPopularSelected(articuloPopular, nombrePopular, precioPopular, tivaPopular);
                } catch (ClassCastException exception) {
                    // do something
                }

            }

            ;

            public void onTomato(ImageView callerImage) {
                Log.d("To-m8-tohs", "");
            }
        });
        return mhPopular;
    }

    @Override
    public void onBindViewHolder(PopularRowHolder PopularRowHolder, int i) {
        String myText;

        Log.i("recview comida fuera", Integer.toString(mPopular.size()));
        try {
            Popular item = mPopular.get(i);
            PopularRowHolder.bind(item);
 ////           Popular Popular = mPopular.get(i);
            Log.i("Popular seleccionado", item.getNombre());

/*            Glide.with(PopularRowHolder.itemView.getContext())
                    .load(item.getIdDrawable())
                    .centerCrop()
                    .into(PopularRowHolder.imagen);
*/            Glide.with(mContext)
                    .load(item.getUrlimagen())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(PopularRowHolder.imagen);
/*            Glide.with(mContext)
                    .load(item.getUrlimagen())
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(PopularRowHolder.imagen);
*/            myText =String.format("%1$-50s",item.getNombre());
            PopularRowHolder.nombre.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            PopularRowHolder.precio.setText(Html.fromHtml(String.format("%1$,.2f", item.getPrecio())+" "+ Filtro.getSimbolo()));
            PopularRowHolder.articulo.setText(item.getArticulo());
            PopularRowHolder.tiva_id.setText(Integer.toString(item.getTiva_id()));

        } catch (Exception e) {
            Log.i("recview LFT dentro", Integer.toString(mPopular.size()));
            // do something
        }

    }

    @Override
    public int getItemCount() {
        return (null != mPopular ? mPopular.size() : 0);
    }

    public Object getItem(int pos){
        return mPopular.get(pos);
    }

    public long getItemId(int pos){
        return mPopular.indexOf(getItem(pos));
    }

    /*    public Filter getFilter() {
            if (filter == null){
                filter = new CustomFilter();
            }
    
            return filter;
        }
    */    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    ///////////////////////////////////////////
// La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onPopularSelected(String articulo, String nombre, String precio, String tiva);

    }

    public void add(Popular Popular, int position) {
        mPopular.add(position, Popular);
        notifyItemInserted(position);
    }

    public void remove(Popular Popular) {
        int position = mPopular.indexOf(Popular);
        mPopular.remove(position);
        notifyItemRemoved(position);
    }

    public void addPopulars(List<Popular> list) {
        mPopular.addAll(0, list);
        notifyDataSetChanged();
    }
    /*para filtro*/
    public void setFilter(List<Popular> Popular) {
        mPopular = new ArrayList<>();
        mPopular.addAll(Popular);
        notifyDataSetChanged();
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering (CharSequence constraint ) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length()>0){
                // CONSTRAINT TO UPPER
                constraint = constraint.toString();
                ArrayList<Popular> filters = new ArrayList<>();

                //FILTERING
                for(int i=0;i<mfilterList.size();i++){
                    if (mfilterList.get(i).getNombre().contains(constraint)){
                        Popular p= new Popular(mfilterList.get(i).getPrecio(),mfilterList.get(i).getNombre(),mfilterList.get(i).getIdDrawable(),mfilterList.get(i).getUrlimagen());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else{
                results.count = mfilterList.size();
                results.values = mfilterList;

            }
            return results;
        }
        @Override
        protected void publishResults (CharSequence constraint, FilterResults results  ) {
            mPopular = (ArrayList<Popular>) results.values;
            notifyDataSetChanged();
        }


    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String str = constraint.toString().toUpperCase();
            Log.e("constraint", str);
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Popular> filterList = new ArrayList<Popular>();
                for (int i = 0; i < mfilterList.size(); i++) {
                    if ((mfilterList.get(i).getNombre())
                            .contains(constraint.toString())) {
                        Popular comida = mfilterList.get(i);
                        filterList.add(comida);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mfilterList.size();
                results.values = mfilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPopular = (ArrayList<Popular>) results.values;
            notifyDataSetChanged();
        }

    }
}
