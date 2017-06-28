package tpv.cirer.com.marivent.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.ComidaRowHolder;
import tpv.cirer.com.marivent.modelo.Comida;

/**
 * Created by JUAN on 21/09/2016.
 */

public class AdaptadorCategorias extends RecyclerView.Adapter<ComidaRowHolder> implements Filterable {

        private ArrayList<Comida> mComida;
        private ArrayList<Comida> mfilterList;
        private String mEstado;
        private CustomFilter filter;
        private Context mContext;
        private int mIndice;
        private OnHeadlineSelectedListener mCallback;
        ValueFilter valueFilter;



        public AdaptadorCategorias(Context context, ArrayList<Comida> Comida, int indice, String estado) {
            this.mComida = Comida;
            this.mfilterList = Comida;
            this.mContext = context;
            this.mIndice = indice;
            this.mEstado = estado;

            try {
                this.mCallback = ((OnHeadlineSelectedListener) context);
            } catch (ClassCastException e) {
                throw new ClassCastException("Activity must implement AdapterCallback.");
            }
        }

        @Override
        public ComidaRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View vComida = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_categorias, null);
            //  ComidaRowHolder mh = new ComidaRowHolder(v);
            ComidaRowHolder mhComida = new ComidaRowHolder(vComida, new ComidaRowHolder.IMyViewHolderClicks() {
                public void onPotato(View caller,
                                     ImageView imagenComida,
                                     final String articuloComida,
                                     final String nombreComida,
                                     final String precioComida,
                                     final String tivaComida,
                                     final String individualComida ) {


                    Log.d("Poh-tah-tos", nombreComida + " " + precioComida );
                    try {
                        mCallback.onComidaSelected(imagenComida, articuloComida, nombreComida, precioComida, tivaComida, mEstado, individualComida);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                ;

                public void onTomato(ImageView callerImage) {
                    Log.d("To-m8-tohs", "");
                }
            });
            return mhComida;
        }

        @Override
        public void onBindViewHolder(ComidaRowHolder ComidaRowHolder, int i) {
            String myText;
/*            final DrawableRequestBuilder<String> req = Glide
                    .with(mContext)
                    .fromString()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE) // disable network delay for demo
                    .skipMemoryCache(true) // make sure transform runs for demo
                    .crossFade(2000) // default, just stretch time for noticability
                    ;
*/
            Log.i("recview comida fuera", Integer.toString(mComida.size()));
            try {
                Comida item = mComida.get(i);
                ComidaRowHolder.bind(item);
                Comida Comida = mComida.get(i);
                Log.i("recview seleccionado", Comida.getNombre());

/*            Glide.with(ComidaRowHolder.itemView.getContext())
                    .load(item.getIdDrawable())
                    .centerCrop()
                    .into(ComidaRowHolder.imagen);
*/
                Glide.with(mContext)
                        .load(Comida.getUrlimagen())
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ComidaRowHolder.imagen);
/*                Glide.with(mContext)
                        .load(Comida.getUrlimagen())
                        .override(640,426) //640,426
                        .centerCrop()
                        .into(ComidaRowHolder.imagen);*/
/*                Picasso.with(mContext)
                        .load(Comida.getUrlimagen())
                        .error(R.drawable.placeholder)
                        .fit()
                        .centerCrop()
                        .into(ComidaRowHolder.imagen);
*/
/*                req.clone()
                        .load(Comida.getUrlimagen())
                        .placeholder(R.drawable.placeholder)
                        .transform(new Delay(1000))
                        //.animate(R.anim.abc_fade_in) // also solves the problem
                        .into(ComidaRowHolder.imagen);
*/
//            Picasso.with(mContext).load(Comida.getUrlimagen()).into(ComidaRowHolder.imagen);
//              myText =String.format("%1$-50s",Comida.getNombre());
//              ComidaRowHolder.nombre.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
//              ComidaRowHolder.precio.setText(Html.fromHtml(String.format("%1$,.2f", Double.valueOf(Comida.getPrecio()))));

//            ComidaRowHolder.nombre.setText(item.getNombre());
//            ComidaRowHolder.precio.setText("$" + item.getPrecio());

            } catch (Exception e) {
                Log.i("recview LFT dentro", Integer.toString(mComida.size()));
                // do something
            }

        }
    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
        @Override
        public int getItemCount() {
            return (null != mComida ? mComida.size() : 0);
        }

    public Object getItem(int pos){
        return mComida.get(pos);
    }

    public long getItemId(int pos){
        return mComida.indexOf(getItem(pos));
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
        void onComidaSelected(ImageView imagen, String articulo, String nombre, String precio, String tiva, String estado, String individual);

    }

    public void add(Comida Comida, int position) {
        mComida.add(position, Comida);
        notifyItemInserted(position);
    }

    public void remove(Comida Comida) {
        int position = mComida.indexOf(Comida);
        mComida.remove(position);
        notifyItemRemoved(position);
    }

    public void addComidas(List<Comida> list) {
        mComida.addAll(0, list);
        notifyDataSetChanged();
    }
    public void removeComidas() {
        mComida.clear();
        notifyDataSetChanged();
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering (CharSequence constraint ) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length()>0){
                // CONSTRAINT TO UPPER
                constraint = constraint.toString();
                ArrayList<Comida> filters = new ArrayList<>();

                //FILTERING
                for(int i=0;i<mfilterList.size();i++){
                    if (mfilterList.get(i).getNombre().contains(constraint)){
                        Comida p= new Comida(mfilterList.get(i).getPrecio(),mfilterList.get(i).getNombre(),mfilterList.get(i).getIdDrawable(),mfilterList.get(i).getUrlimagen(),mfilterList.get(i).getIndividual());
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
            mComida = (ArrayList<Comida>) results.values;
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
                ArrayList<Comida> filterList = new ArrayList<Comida>();
                for (int i = 0; i < mfilterList.size(); i++) {
                    if ((mfilterList.get(i).getNombre())
                            .contains(constraint.toString())) {
                        Comida comida = mfilterList.get(i);
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
            mComida = (ArrayList<Comida>) results.values;
            notifyDataSetChanged();
        }

    }
    class Delay extends UnitTransformation {
        private final int sleepTime;
        public Delay(int sleepTime) { this.sleepTime = sleepTime; }
        @Override
        public Resource transform(Resource resource, int outWidth, int outHeight) {
            try { Thread.sleep(sleepTime); } catch (InterruptedException ex) {}
            return super.transform(resource, outWidth, outHeight);
        }
    }
}
