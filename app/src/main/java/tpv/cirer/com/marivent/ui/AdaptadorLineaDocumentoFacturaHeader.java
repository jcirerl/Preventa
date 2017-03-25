package tpv.cirer.com.marivent.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.IMyLineaDocumentoFacturaViewHolderClicks;
import tpv.cirer.com.marivent.modelo.LineaDocumentoFactura;

/**
 * Created by JUAN on 09/11/2016.
 */

public class AdaptadorLineaDocumentoFacturaHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<LineaDocumentoFactura> mLineaDocumentoFactura;
    private Context mContextLineaDocumentoFactura;
    private String mEstado;
    private OnHeadlineSelectedListenerLineaDocumentoFacturaHeader mCallbackLineaDocumentoFactura;

    /*   public AdaptadorLineaDocumentoFacturaHeader(String[] data) {
           this.data = data;
       }
    */   public AdaptadorLineaDocumentoFacturaHeader(Context context, List<LineaDocumentoFactura> LineaDocumentoFactura, String estado) {
        this.mLineaDocumentoFactura = LineaDocumentoFactura;
        this.mContextLineaDocumentoFactura = context;
        this.mEstado = estado;
        try {
            this.mCallbackLineaDocumentoFactura = ((OnHeadlineSelectedListenerLineaDocumentoFacturaHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_lineadocumentofactura, parent, false);
            return new VHItem(v, new IMyLineaDocumentoFacturaViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller, ImageView imageLineaDocumentoFactura,String idLineaDocumentoFactura, String nombreLineaDocumentoFactura, String articuloLineaDocumentoFactura, String iconLineaDocumentoFactura, String cantLineaDocumentoFactura, String preuLineaDocumentoFactura, String importeLineaDocumentoFactura, String tiva_idLineaDocumentoFactura) {
                    final String nombreLFT = nombreLineaDocumentoFactura.toString();
                    final String articuloLFT = articuloLineaDocumentoFactura.toString();
                    final String cantLFT = cantLineaDocumentoFactura.toString();
                    final String preuLFT = preuLineaDocumentoFactura.toString();
                    final String importeLFT = importeLineaDocumentoFactura.toString();
                    final int tiva_idLFT = Integer.parseInt(tiva_idLineaDocumentoFactura);
                    final int idLFT = Integer.parseInt(idLineaDocumentoFactura);

                    Log.d("Poh-tah-tos", nombreLFT + " " + articuloLFT + " " + iconLineaDocumentoFactura + " " + tiva_idLFT);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext());
                    dialog.setTitle(ActividadPrincipal.getPalabras("Articulo")+": "+articuloLFT);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+nombreLFT +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idLFT +
                            "\n"+ActividadPrincipal.getPalabras("Cantidad")+": "+ cantLFT +
                            "\n"+ActividadPrincipal.getPalabras("Precio")+": "+ preuLFT +
                            "\n"+ActividadPrincipal.getPalabras("Importe")+": "+ importeLFT +
                            "\n"+ActividadPrincipal.getPalabras("Iva")+": "+ Integer.toString(tiva_idLFT)
                    );
                    dialog.setIcon(imageLineaDocumentoFactura.getDrawable());
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Modificar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                if (!mEstado.contains("CLOSE")) {
                                    mCallbackLineaDocumentoFactura.onUpdateLineaDocumentoFacturaSelected(idLFT, preuLFT);
                                }
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();


                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras("Borrar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if (!mEstado.contains("CLOSE")) {
                                    mCallbackLineaDocumentoFactura.onDeleteLineaDocumentoFacturaSelected(idLFT);
                                }
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onAdd(Button callerButton, String idLineaDocumentoFactura ) {
                    Log.d("ADD BUTTON", "+");
                    final int idLFT = Integer.parseInt(idLineaDocumentoFactura);
                    try {
                        if (!mEstado.contains("CLOSE")) {
                            mCallbackLineaDocumentoFactura.onAddCantLineaDocumentoFacturaSelected(idLFT);
                        }
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
                public void onMinus(Button callerButton, String idLineaDocumentoFactura, String cantLineaDocumentoFactura ) {
                    Log.d("MINUS BUTTON", "-");
                    String cValor = cantLineaDocumentoFactura.toString();
                    cValor = cValor.replace(Html.fromHtml("&nbsp;"),"");
                    cValor = cValor.replace(".","");
                    cValor = cValor.replace(",",".");
                    final int idLFT = Integer.parseInt(idLineaDocumentoFactura);
                    final double cantLFT = Double.parseDouble(cValor.toString().trim());
                    try {
                        if (!mEstado.contains("CLOSE")) {
                            if (cantLFT > 1) {
                                mCallbackLineaDocumentoFactura.onMinusCantLineaDocumentoFacturaSelected(idLFT);
                            } else {
                                mCallbackLineaDocumentoFactura.onDeleteLineaDocumentoFacturaSelected(idLFT);

                            }
                        }
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) { Log.d("VEGETABLES", "To-m8-tohs"); }



            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_lineasfacturas, parent, false);
            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String myText;
        String newText;
        String space01 = new String(new char[01]).replace('\0', ' ');
        String space05 = new String(new char[05]).replace('\0', ' ');
        String space10 = new String(new char[10]).replace('\0', ' ');
        String space50 = new String(new char[50]).replace('\0', ' ');

        if (holder instanceof VHItem) {
            Log.i("recview LPD i", Integer.toString(position));

            Log.i("recview LPD vhitem", Integer.toString(mLineaDocumentoFactura.size()));
            try {
            /*para filtro*/
                LineaDocumentoFactura model = mLineaDocumentoFactura.get(position-1);
                Log.i("Imagen: ", model.getLineaDocumentoFacturaUrlimagen());

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                LineaDocumentoFactura LineaDocumentoFactura = mLineaDocumentoFactura.get(position-1);
                Picasso.with(mContextLineaDocumentoFactura)
                        .load(model.getLineaDocumentoFacturaUrlimagen())
                        .resize(60, 60)
                        .centerCrop()
                        .into(((VHItem) holder).iconLineaDocumentoFactura);

                ((VHItem) holder).iconLineaDocumentoFactura.setTag("0");
                /////////////////////////////////////////////////////////////////

                ((VHItem) holder).IdLineaDocumentoFactura.setText(Html.fromHtml(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaId())));

                int lennombre = LineaDocumentoFactura.getLineaDocumentoFacturaNombre().trim().length();
                myText =String.format("%1$-23s",LineaDocumentoFactura.getLineaDocumentoFacturaNombre().substring(0,(lennombre>23 ? 23 : lennombre)));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                Log.i("lMytextNombre",Integer.toString(myText.length()));

                newText=myText;
                for (int ii = 0; ii < (23-myText.length()); ii++) {
                    newText+=space01;
                }

                ((VHItem) holder).NombreLineaDocumentoFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;")).toString());

                myText =String.format("%1$-11s",LineaDocumentoFactura.getLineaDocumentoFacturaArticulo());
                ((VHItem) holder).ArticuloLineaDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText = String.format("%1$,.2f", Float.parseFloat(LineaDocumentoFactura.getLineaDocumentoFacturaCant()));
                Log.i("lMytextCant",Integer.toString(myText.length()));
//                myText = String.format("%1$5s",myText);
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                newText="";
                for (int ii = 0; ii < (6-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                ((VHItem) holder).CantLineaDocumentoFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                myText = String.format("%1$,.2f", Float.parseFloat(LineaDocumentoFactura.getLineaDocumentoFacturaPreu()));
                Log.i("lMytextPreu",Integer.toString(myText.length())+" "+myText);
//                myText = String.format("%1$6s",myText);
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                newText="";
                for (int ii = 0; ii < (6-myText.length()); ii++) {
                    newText+=space01;
                }
             ///       String spacePreu = new String(new char[6-myText.length()]).replace('\0', ' '); //Añadimos espacios
                newText +=myText;
                Log.i("lMytextPNew",Integer.toString(newText.length())+" "+newText);
                ((VHItem) holder).PreuLineaDocumentoFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());
                Log.i("lMytextPuVH",Integer.toString(((VHItem) holder).PreuLineaDocumentoFactura.getText().toString().length())+" "+((VHItem) holder).PreuLineaDocumentoFactura.getText().toString());

                myText = String.format("%1$,.2f", Float.parseFloat(LineaDocumentoFactura.getLineaDocumentoFacturaImporte()));
                Log.i("lMytextImp",Integer.toString(myText.length()));
//                myText = String.format("%1$6s",myText);
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                newText="";
                for (int ii = 0; ii < (6-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                ((VHItem) holder).ImporteLineaDocumentoFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());


                ((VHItem) holder).TivaLineaDocumentoFactura.setText(Html.fromHtml(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaTiva_id())));


                ((VHItem) holder).NombreLineaDocumentoFactura.setTextColor(Color.BLUE);
                ((VHItem) holder).PreuLineaDocumentoFactura.setTextColor(Color.MAGENTA);

                /// TAMANYO LETRA PARA MARIVENT
/*                ((VHItem) holder).CantLineaDocumentoFactura.setTextSize(20);
                ((VHItem) holder).PreuLineaDocumentoFactura.setTextSize(20);;
                ((VHItem) holder).ImporteLineaDocumentoFactura.setTextSize(20);
                ((VHItem) holder).NombreLineaDocumentoFactura.setTextSize(20);;
*/
                 //       LineaDocumentoFacturaRowHolder.NombreLineaDocumentoFactura.setTextSize(16);
                //        LineaDocumentoFacturaRowHolder.ArticuloLineaDocumentoFactura.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview PDD dentro", Integer.toString(mLineaDocumentoFactura.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            myText = ActividadPrincipal.getPalabras("Cantidad")+"     "+
                     ActividadPrincipal.getPalabras("Precio")+ "    "+
                     ActividadPrincipal.getPalabras("Importe")+"   "+
                     ActividadPrincipal.getPalabras("Articulo");
            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_facturas.
            ((VHHeader) holder).headerLineaDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerLineaDocumentoFactura.setTextColor(Color.WHITE);
       /////     ((VHHeader) holder).headerLineaDocumentoFactura.setTextSize(18); /// PARA MARIVENT

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mLineaDocumentoFactura ? mLineaDocumentoFactura.size()+1 : 1);
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
            IMyLineaDocumentoFacturaViewHolderClicks {
        //   TextView description;

        public ImageView iconLineaDocumentoFactura;

        public Button AddCantLineaDocumentoFactura;
        public Button MinCantLineaDocumentoFactura;
        public TextView ArticuloLineaDocumentoFactura;
        public TextView NombreLineaDocumentoFactura;
        public TextView CantLineaDocumentoFactura;
        public TextView IdLineaDocumentoFactura;
        public TextView PreuLineaDocumentoFactura;
        public TextView ImporteLineaDocumentoFactura;
        public TextView TivaLineaDocumentoFactura;


        public IMyLineaDocumentoFacturaViewHolderClicks mListenerLineaDocumentoFactura;

        public VHItem(View itemView,IMyLineaDocumentoFacturaViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerLineaDocumentoFactura = listener;

            this.IdLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.pid);
            this.iconLineaDocumentoFactura = (ImageView) itemView.findViewById(R.id.icon);
            this.CantLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.cant);
            this.ArticuloLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.articulo);
            this.NombreLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.nombre);
            this.PreuLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.preu);
            this.ImporteLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.importe);
            this.TivaLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.tipoiva);


            this.AddCantLineaDocumentoFactura = (Button) itemView.findViewById(R.id.btnAdd);
            this.MinCantLineaDocumentoFactura = (Button) itemView.findViewById(R.id.btnMinus);

            this.AddCantLineaDocumentoFactura.setOnClickListener(this);
            this.MinCantLineaDocumentoFactura.setOnClickListener(this);

            //       this.MinCant.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }
        public void bind(LineaDocumentoFactura LineaDocumentoFactura) {
            IdLineaDocumentoFactura.setText(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaId()));
            CantLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaCant());
            ArticuloLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaArticulo());
            NombreLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaNombre());
            PreuLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaPreu());
            ImporteLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaImporte());
            TivaLineaDocumentoFactura.setText(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaTiva_id()));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnAdd:
                        mListenerLineaDocumentoFactura.onAdd(
                                (Button) v,
                                String.valueOf(this.IdLineaDocumentoFactura.getText())
                        );
                        break;
                    case R.id.btnMinus:
                        mListenerLineaDocumentoFactura.onMinus(
                                (Button) v,
                                String.valueOf(this.IdLineaDocumentoFactura.getText()),
                                String.valueOf(this.CantLineaDocumentoFactura.getText())
                        );
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro",v.getClass().getName().toString());
                mListenerLineaDocumentoFactura.onPotato(
                        v,
                        this.iconLineaDocumentoFactura,
                        String.valueOf(this.IdLineaDocumentoFactura.getText()),
                        String.valueOf(this.NombreLineaDocumentoFactura.getText()),
                        String.valueOf(this.ArticuloLineaDocumentoFactura.getText()),
                        String.valueOf(this.iconLineaDocumentoFactura.getTag().toString()),
                        String.valueOf(this.CantLineaDocumentoFactura.getText()),
                        String.valueOf(this.PreuLineaDocumentoFactura.getText()),
                        String.valueOf(this.ImporteLineaDocumentoFactura.getText()),
                        String.valueOf(this.TivaLineaDocumentoFactura.getText())
                );
                //           mListener.onPotato(v);
            }
        }

        @Override
        public void onPotato(View caller,
                             ImageView imageLineaDocumentoFactura,
                             String idLineaDocumentoFactura,
                             String nombreLineaDocumentoFactura,
                             String articuloLineaDocumentoFactura,
                             String iconLineaDocumentoFactura,
                             String cantLineaDocumentoFactura,
                             String preuLineaDocumentoFactura,
                             String importeLineaDocumentoFactura,
                             String tiva_idLineaDocumentoFactura) {

        }

        @Override
        public void onAdd(Button callerButton,
                          String idLineaDocumentoFactura) {

        }

        @Override
        public void onMinus(Button callerButton,
                            String idLineaDocumentoFactura,
                            String cantLineaDocumentoFactura) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerLineaDocumentoFactura;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerLineaDocumentoFactura = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerLineaDocumentoFacturaHeader {
        void onUpdateLineaDocumentoFacturaSelected(int id, String preu);
        void onDeleteLineaDocumentoFacturaSelected(int id);
        void onAddCantLineaDocumentoFacturaSelected(int id);
        void onMinusCantLineaDocumentoFacturaSelected(int id);

    }
    /*para filtro*/
    public void setFilter(List<LineaDocumentoFactura> LineaDocumentoFacturas) {
        mLineaDocumentoFactura = new ArrayList<>();
        mLineaDocumentoFactura.addAll(LineaDocumentoFacturas);
        notifyDataSetChanged();
    }

}