package tpv.cirer.com.marivent.herramientas;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.ArticulosNew;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;

/**
 * Created by JUAN on 06/09/2017.
 */

public class AdaptadorArticulosHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<ArticulosNew> mArticulosNew;
    private Context mContextArticulosNew;
    private OnHeadlineSelectedListenerArticulosNewHeader mCallbackArticulosNew;

    /*   public AdaptadorArticulosNewHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorArticulosHeader(Context context, List<ArticulosNew> ArticulosNew) {
        this.mArticulosNew = ArticulosNew;
        this.mContextArticulosNew = context;
        try {
            this.mCallbackArticulosNew = ((OnHeadlineSelectedListenerArticulosNewHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_articuloscode_row, parent, false);
            return new VHItem(v, new IMyArticulosNewViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller, final ImageView imageArticulo, final String action, final String codigoArticulo, final String nombreArticulo, final String positionArticulo) {

                    Log.i("onPotato","OK");
                    try {
                        mCallbackArticulosNew.onArticulosNewSelected(nombreArticulo);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                };

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_facturas, parent, false);
            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final String DOUBLE_BYTE_SPACE = "\u3000";
        String fixString = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1
                && android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            fixString = DOUBLE_BYTE_SPACE;
        }
        String myText;
        String space01 = new String(new char[01]).replace('\0', ' ');

        Log.i("recview comida fuera", Integer.toString(mArticulosNew.size()));
        if (holder instanceof VHItem) {
//            String dataItem = getItem(position);
            //cast holder to VHItem and set data
//            ((VHItem) holder).description.setText(dataItem);
            try {
                ArticulosNew ArticulosNew = mArticulosNew.get(position-1);
                ((VHItem) holder).bind(ArticulosNew);
                Log.i("recview seleccionado", ArticulosNew.getName());
                Picasso.with(mContextArticulosNew)
                        .load(ArticulosNew.getUrlimagen())
                        .resize(60, 60)
                        .centerCrop()
                        .into(((VHItem) holder).imagen);
                switch(ArticulosNew.getCode()){
                    case "BUFFET":
                        ((VHItem) holder).cant.setText(String.valueOf(ArticulosNew.getCant()));
                        ((VHItem) holder).cant.setTextColor(Color.RED);
                        ((VHItem) holder).cant.setTextSize(30);

                        ((VHItem) holder).importe.setTextColor(Color.RED);
                        ((VHItem) holder).importe.setTextSize(30);

                        myText = String.format("%1$,.2f", ArticulosNew.getImporte());
                        Log.i("lMytextImp",Integer.toString(myText.length()));
                        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                        String newText="";
                        for (int ii = 0; ii < (8-myText.length()); ii++) {
                            newText+=space01;
                        }
                        newText +=myText;
                        ((VHItem) holder).importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                        if (((VHItem) holder).checkbox.isChecked()) {
                            ((VHItem) holder).cant.setVisibility(View.VISIBLE);
                            ((VHItem) holder).add.setVisibility(View.VISIBLE);
                            ((VHItem) holder).minus.setVisibility(View.VISIBLE);
                            ((VHItem) holder).importe.setVisibility(View.VISIBLE);
                        } else{
                            ((VHItem) holder).cant.setVisibility(View.GONE);
                            ((VHItem) holder).add.setVisibility(View.GONE);
                            ((VHItem) holder).minus.setVisibility(View.GONE);
                            ((VHItem) holder).importe.setVisibility(View.GONE);
                        }
                        ((VHItem) holder).name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                        ((VHItem) holder).checkbox.setText(ArticulosNew.getName());
                        ((VHItem) holder).name.setVisibility(View.GONE);
                        ((VHItem) holder).cmbtoolbarplato.setVisibility(View.GONE);
                        ((VHItem) holder).preu.setVisibility(View.GONE);
                        break;
                    case "ARTICULO":
                        ((VHItem) holder).name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                        ((VHItem) holder).checkbox.setVisibility(View.GONE);
                        ((VHItem) holder).cant.setVisibility(View.GONE);
                        ((VHItem) holder).add.setVisibility(View.GONE);
                        ((VHItem) holder).minus.setVisibility(View.GONE);
                        ((VHItem) holder).cmbtoolbarplato.setVisibility(View.GONE);
                        ((VHItem) holder).preu.setVisibility(View.GONE);
                        ((VHItem) holder).importe.setVisibility(View.GONE);
                        break;
                    case "GRUPO":
                        ((VHItem) holder).checkbox.setChecked(true);
                        ArticulosNew.setChecked(true);
                        ((VHItem) holder).name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                        ((VHItem) holder).cant.setVisibility(View.GONE);
                        ((VHItem) holder).add.setVisibility(View.GONE);
                        ((VHItem) holder).minus.setVisibility(View.GONE);
                        ((VHItem) holder).preu.setVisibility(View.GONE);
                        ((VHItem) holder).importe.setVisibility(View.GONE);

                        ((VHItem) holder).cmbtoolbarplato.setAdapter(ArticulosNew.getAdapter_plato());
                        ((VHItem) holder).cmbtoolbarplato.setSelection(ArticulosNew.getPosicionplato());

                        break;
                }


            } catch (Exception e) {
                Log.i("recview Articulos", Integer.toString(mArticulosNew.size()));
                // do something
            }



        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.

            /// SONY //
            myText = " "+
                    getPalabras("Articulos");


            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_facturas.
            ((VHHeader) holder).headerArticulosNew.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerArticulosNew.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mArticulosNew ? mArticulosNew.size()+1 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    /*   private String getItem(int position) {
           return data[position - 1];
       }
   */
    class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener,
            IMyArticulosNewViewHolderClicks {
        //   TextView description;

        public TextView name;
        public ImageView imagen;
        public CheckBox checkbox;
        public TextView cant;
        public Button add;
        public Button minus;
        public Spinner cmbtoolbarplato;
        public TextView importe;
        public TextView preu;

        public IMyArticulosNewViewHolderClicks mListenerArticulosNew;

        public VHItem(View view,IMyArticulosNewViewHolderClicks listener) {
            super(view);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerArticulosNew = listener;
            this.name = (TextView) view.findViewById(R.id.name);
            this.imagen = (ImageView) view.findViewById(R.id.imagen);
            this.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            this.cant = (TextView) view.findViewById(R.id.cant);
            this.add = (Button) view.findViewById(R.id.btnAdd);
            this.minus = (Button) view.findViewById(R.id.btnMinus);
            this.cmbtoolbarplato = (Spinner) view.findViewById(R.id.CmbToolbarPlato);
            this.preu = (TextView) view.findViewById(R.id.preu);
            this.importe = (TextView) view.findViewById(R.id.importe);


            this.imagen.setOnClickListener(this);
            view.setOnClickListener(this);
       }
        public void bind(ArticulosNew ArticulosNew) {
            name.setText(ArticulosNew.getName());
            cant.setText(ArticulosNew.getCant());
            preu.setText(String.valueOf(ArticulosNew.getPreu()));
            importe.setText(String.valueOf(ArticulosNew.getImporte()));

        }
        public String padRight(String s, int n) {
            return String.format("%1$-" + n + "s", s);
        }

        public String padLeft(String s, int n) {
            return String.format("%1$" + n + "s", s);
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

        }

        @Override
        public void onPotato(View caller, ImageView imageArticulo, String action, String codigoArticulo, String nombreArticulo, String positionArticulo) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerArticulosNew;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerArticulosNew = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerArticulosNewHeader {
        void onArticulosNewSelected(String nombre);
    }
    /*para filtro*/
    public void setFilter(List<ArticulosNew> ArticulosNews) {
        mArticulosNew = new ArrayList<>();
        mArticulosNew.addAll(ArticulosNews);
        notifyDataSetChanged();
    }
}
