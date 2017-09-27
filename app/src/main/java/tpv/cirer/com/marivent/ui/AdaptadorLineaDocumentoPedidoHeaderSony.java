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

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.UnitTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.IMyLineaDocumentoPedidoViewHolderClicks;
import tpv.cirer.com.marivent.modelo.LineaDocumentoPedido;

/**
 * Created by JUAN on 08/07/2017.
 */

public class AdaptadorLineaDocumentoPedidoHeaderSony  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<LineaDocumentoPedido> mLineaDocumentoPedido;
    private Context mContextLineaDocumentoPedido;
    private OnHeadlineSelectedListenerLineaDocumentoPedidoHeader mCallbackLineaDocumentoPedido;
    private String mEstado;

    /*   public AdaptadorLineaDocumentoPedidoHeader(String[] data) {
           this.data = data;
       }
    */   public AdaptadorLineaDocumentoPedidoHeaderSony(Context context, List<LineaDocumentoPedido> LineaDocumentoPedido, String estado) {
        this.mLineaDocumentoPedido = LineaDocumentoPedido;
        this.mEstado = estado;
        this.mContextLineaDocumentoPedido = context;
        try {
            this.mCallbackLineaDocumentoPedido = ((OnHeadlineSelectedListenerLineaDocumentoPedidoHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_lineadocumentopedido, parent, false);
            return new VHItem(v, new IMyLineaDocumentoPedidoViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller,
                                     ImageView imageLineaDocumentoPedido,
                                     String idLineaDocumentoPedido,
                                     String nombreLineaDocumentoPedido,
                                     String articuloLineaDocumentoPedido,
                                     String iconLineaDocumentoPedido,
                                     String cantLineaDocumentoPedido,
                                     String obsLineaDocumentoPedido,
                                     String swfacturaLineaDocumentoPedido,
                                     String individualLineaDocumentoPedido,
                                     String tipoplatoLineaDocumentoPedido,
                                     String nombreplatoLineaDocumentoPedido) {
                    final String nombreLPD = nombreLineaDocumentoPedido;
                    final String articuloLPD = articuloLineaDocumentoPedido;
                    final String cantLPD = cantLineaDocumentoPedido;
                    final String obsLPD = obsLineaDocumentoPedido;
                    final int idLPD = Integer.parseInt(idLineaDocumentoPedido);
                    final ImageView imageLPD = imageLineaDocumentoPedido;
                    final int individualLPD = Integer.parseInt(individualLineaDocumentoPedido);
                    final String tipoplatoLPD = tipoplatoLineaDocumentoPedido;
                    final String nombreplatoLPD = nombreplatoLineaDocumentoPedido;

                    Log.d("Poh-tah-tos", nombreLPD + " " + articuloLPD + " " + iconLineaDocumentoPedido);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext());
                    dialog.setTitle(ActividadPrincipal.getPalabras("Articulo")+": "+articuloLPD);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+nombreLPD +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idLPD +
                            "\n"+ActividadPrincipal.getPalabras("Cantidad")+": "+ cantLPD +
                            "\n"+ActividadPrincipal.getPalabras("Observaciones")+": "+ obsLPD +
                            "\n"+ActividadPrincipal.getPalabras("Plato")+": "+ nombreplatoLPD +
                            "\n"+ActividadPrincipal.getPalabras("Tipo")+": "+ (individualLPD == 0 ? ActividadPrincipal.getPalabras("Grupo"): ActividadPrincipal.getPalabras("Individual"))
                    );
                    dialog.setIcon(imageLineaDocumentoPedido.getDrawable());
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Modificar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!mEstado.contains("CLOSE")) {
                                String cArticulo = articuloLPD.toString();
                                cArticulo = cArticulo.replace(Html.fromHtml("&nbsp;"),"");

                                try {
                                    mCallbackLineaDocumentoPedido.onUpdateLineaDocumentoPedidoSelected(idLPD, obsLPD, cArticulo, mEstado);
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras("Borrar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!mEstado.contains("CLOSE")) {
                                try {
                                    mCallbackLineaDocumentoPedido.onDeleteLineaDocumentoPedidoSelected(idLPD, individualLPD, mEstado);
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.setNeutralButton(ActividadPrincipal.getPalabras("Grupo"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!mEstado.contains("CLOSE")) {
                                try {
                                    String cArticulo = articuloLPD.toString();
                                    cArticulo = cArticulo.replace(Html.fromHtml("&nbsp;"),"");
                                    String cNombre = nombreLPD.toString();
                                    cNombre = cNombre.replace(Html.fromHtml("&nbsp;"),"");

                                    mCallbackLineaDocumentoPedido.onArticulosLineaDocumentoPedidoSelected(imageLPD, cArticulo, nombreLPD);
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onAdd(Button callerButton,
                                  String idLineaDocumentoPedido,
                                  String swfacturaLineaDocumentoPedido,
                                  String individualLineaDocumentoPedido ) {
                    Log.d("ADD BUTTON", "+");
                    final int idLPD = Integer.parseInt(idLineaDocumentoPedido);
                    final int individualLPD = Integer.parseInt(individualLineaDocumentoPedido);
                    try {
                        if (!mEstado.contains("CLOSE")) {
                            mCallbackLineaDocumentoPedido.onAddCantLineaDocumentoPedidoSelected(idLPD,individualLPD, mEstado);
                        }
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
                public void onMinus(Button callerButton,
                                    String idLineaDocumentoPedido,
                                    String cantLineaDocumentoPedido,
                                    String swfacturaLineaDocumentoPedido,
                                    String individualLineaDocumentoPedido ) {
                    Log.d("MINUS BUTTON", "-");
                    String cValor = cantLineaDocumentoPedido.toString();
                    cValor = cValor.replace(Html.fromHtml("&nbsp;"),"");
                    cValor = cValor.replace(".","");
                    cValor = cValor.replace(",",".");

                    final int idLPD = Integer.parseInt(idLineaDocumentoPedido);
                    final double cantLPD = Double.parseDouble(cValor.toString());
                    final int individualLPD = Integer.parseInt(individualLineaDocumentoPedido);
                    try {
                        if (!mEstado.contains("CLOSE")) {
                            if (cantLPD > 1) {
                                mCallbackLineaDocumentoPedido.onMinusCantLineaDocumentoPedidoSelected(idLPD,individualLPD,mEstado);
                            } else {
                                mCallbackLineaDocumentoPedido.onDeleteLineaDocumentoPedidoSelected(idLPD,individualLPD,mEstado);

                            }
                        }
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) { Log.d("VEGETABLES", "To-m8-tohs"); }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_lineaspedidos, parent, false);
            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DrawableRequestBuilder<String> req = Glide
                .with(mContextLineaDocumentoPedido)
                .fromString()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // disable network delay for demo
                .skipMemoryCache(true) // make sure transform runs for demo
                .crossFade(2000) // default, just stretch time for noticability
                ;
        final String DOUBLE_BYTE_SPACE = "\u3000";
        String fixString = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1
                && android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            fixString = DOUBLE_BYTE_SPACE;
        }
        String myText;
        String newText;
        String space01 = new String(new char[01]).replace('\0', ' ');
        String space05 = new String(new char[05]).replace('\0', ' ');
        String space10 = new String(new char[10]).replace('\0', ' ');
        String space50 = new String(new char[50]).replace('\0', ' ');
        if (holder instanceof VHItem) {
//            String dataItem = getItem(position);
            //cast holder to VHItem and set data
//            ((VHItem) holder).description.setText(dataItem);

            Log.i("recview LPD i", Integer.toString(position));

//        if (LineaDocumentoPedidoRowHolder == vhitem) {
            Log.i("recview LPD vhitem", Integer.toString(mLineaDocumentoPedido.size()));
            try {
            /*para filtro*/
                LineaDocumentoPedido model = mLineaDocumentoPedido.get(position-1);
                Log.i("Imagen: ", model.getLineaDocumentoPedidoUrlimagen());

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                LineaDocumentoPedido LineaDocumentoPedido = mLineaDocumentoPedido.get(position-1);
                Picasso.with(mContextLineaDocumentoPedido)
                        .load(model.getLineaDocumentoPedidoUrlimagen())
                        .resize(60, 60)
                        .centerCrop()
                        .into(((VHItem) holder).iconLineaDocumentoPedido);
/*                req.clone()
                        .load(model.getLineaDocumentoPedidoUrlimagen())
                        .placeholder(R.drawable.placeholder)
                        .transform(new Delay(1000))
                        //.animate(R.anim.abc_fade_in) // also solves the problem
                        .into(((VHItem) holder).iconLineaDocumentoPedido);
*/
/*                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.placeholder)
                        .resize(imgWidth, imgHeight)
                        .centerCrop()
                        .into(image);
*/
                ((VHItem) holder).iconLineaDocumentoPedido.setTag("0");
/*                Glide.with(mContextLineaDocumentoPedido)
                        .load(new DownloadImageTask(((VHItem) holder).iconLineaDocumentoPedido)
                                .execute(model.getLineaDocumentoPedidoUrlimagen()))
                        .override(60,60)
                        .into(((VHItem) holder).iconLineaDocumentoPedido);
*/
                /////////////////////////////////////////////////////////////////

                ((VHItem) holder).IdLineaDocumentoPedido.setText(Html.fromHtml(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoId())));
                ((VHItem) holder).SwFacturaLineaDocumentoPedido.setText(Html.fromHtml(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoSwFactura())));
                ((VHItem) holder).SwPedidoLineaDocumentoPedido.setText(Html.fromHtml(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoSwPedido())));

/*                myText =String.format("%1$-21s",LineaDocumentoPedido.getLineaDocumentoPedidoNombre().substring(0,21));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                newText=myText;
                 ((VHItem) holder).NombreLineaDocumentoPedido.setText(Html.fromHtml(newText.replace(" ", "&nbsp;")).toString()+space01);
*/
                int lennombre = LineaDocumentoPedido.getLineaDocumentoPedidoNombre().trim().length();
                myText =LineaDocumentoPedido.getLineaDocumentoPedidoNombre().substring(0,(lennombre>23 ? 23 : lennombre));
                ((VHItem) holder).NombreLineaDocumentoPedido.setText(myText);
                int lennombreplato = LineaDocumentoPedido.getLineaDocumentoPedidoNombre_plato().trim().length();
                myText =LineaDocumentoPedido.getLineaDocumentoPedidoNombre_plato().substring(0,(lennombreplato>15 ? 15 : lennombreplato));
                ((VHItem) holder).NombrePlatoLineaDocumentoPedido.setText(myText);

/////                ((VHItem) holder).NombreLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoNombre().trim()+fixString);

                myText =String.format("%1$-11s",LineaDocumentoPedido.getLineaDocumentoPedidoArticulo());
                ((VHItem) holder).ArticuloLineaDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
                myText =String.format("%1$-20s",LineaDocumentoPedido.getLineaDocumentoPedidoTipoPlato());
                ((VHItem) holder).TipoPlatoLineaDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                ((VHItem) holder).CantLineaDocumentoPedido.setText(Html.fromHtml(String.format("%1$,.2f", Double.valueOf(LineaDocumentoPedido.getLineaDocumentoPedidoCant()))));

/*                myText =String.format("%1$-21s",LineaDocumentoPedido.getLineaDocumentoPedidoObs().substring(0,21));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                Log.i("lMytextNombre",Integer.toString(myText.length()));

                newText=myText;
                for (int ii = 0; ii < (21-myText.length()); ii++) {
                    newText+=space01;
                }

                ((VHItem) holder).ObsLineaDocumentoPedido.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());
*/
                int lenobs = LineaDocumentoPedido.getLineaDocumentoPedidoObs().trim().length();
                myText =String.format("%1$-25s",LineaDocumentoPedido.getLineaDocumentoPedidoObs().substring(0,(lenobs>48 ? 48 : lenobs)));
                ((VHItem) holder).ObsLineaDocumentoPedido.setText(myText);

                ((VHItem) holder).CantLineaDocumentoPedido.setTextColor(Color.BLACK);
                ((VHItem) holder).NombreLineaDocumentoPedido.setTextColor(Color.BLUE);
                ((VHItem) holder).ObsLineaDocumentoPedido.setTextColor(Color.MAGENTA);

                ((VHItem) holder).ObsLineaDocumentoPedido.setBackgroundColor(Color.TRANSPARENT);
                if (LineaDocumentoPedido.getLineaDocumentoPedidoObs().trim().length()==0) {
                    if(mEstado.contains("OPEN")) {
                        ((VHItem) holder).ObsLineaDocumentoPedido.setBackgroundColor(Color.MAGENTA);
                    }
                }
                if (LineaDocumentoPedido.getLineaDocumentoPedidoSwPedido()==0){
                    ((VHItem) holder).CantLineaDocumentoPedido.setBackgroundColor(Color.parseColor("#bdbdbd"));
                    ((VHItem) holder).NombreLineaDocumentoPedido.setBackgroundColor(Color.parseColor("#bdbdbd"));
                }else{
                    ((VHItem) holder).CantLineaDocumentoPedido.setBackgroundColor(Color.TRANSPARENT);
                    ((VHItem) holder).NombreLineaDocumentoPedido.setBackgroundColor(Color.TRANSPARENT);

                }
                //        LineaDocumentoPedidoRowHolder.NombreLineaDocumentoPedido.setTextSize(16);
                //        LineaDocumentoPedidoRowHolder.ArticuloLineaDocumentoPedido.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview PDD dentro", Integer.toString(mLineaDocumentoPedido.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            myText = ActividadPrincipal.getPalabras("Cantidad")+"  "+
                    ActividadPrincipal.getPalabras("Articulo")+ "               "+
                    ActividadPrincipal.getPalabras("Tipo")+" "+ActividadPrincipal.getPalabras("Plato")+" "+
                    ActividadPrincipal.getPalabras("Observaciones");
            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_pedidos.
            ((VHHeader) holder).headerLineaDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerLineaDocumentoPedido.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mLineaDocumentoPedido ? mLineaDocumentoPedido.size()+1 : 1);
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
            IMyLineaDocumentoPedidoViewHolderClicks {
        //   TextView description;

        public ImageView iconLineaDocumentoPedido;

        public Button AddCantLineaDocumentoPedido;
        public Button MinCantLineaDocumentoPedido;
        public TextView ArticuloLineaDocumentoPedido;
        public TextView NombreLineaDocumentoPedido;
        public TextView CantLineaDocumentoPedido;
        public TextView TipoPlatoLineaDocumentoPedido;
        public TextView NombrePlatoLineaDocumentoPedido;
        public TextView ObsLineaDocumentoPedido;
        public TextView IdLineaDocumentoPedido;
        public TextView SwFacturaLineaDocumentoPedido;
        public TextView SwPedidoLineaDocumentoPedido;
        public TextView IndividualLineaDocumentoPedido;


        public IMyLineaDocumentoPedidoViewHolderClicks mListenerLineaDocumentoPedido;

        public VHItem(View itemView,IMyLineaDocumentoPedidoViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerLineaDocumentoPedido = listener;

            this.IdLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.pid);
            this.iconLineaDocumentoPedido = (ImageView) itemView.findViewById(R.id.icon);
            this.CantLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.cant);
            this.ArticuloLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.articulo);
            this.NombreLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.nombre);
            this.TipoPlatoLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.tipoplato);
            this.NombrePlatoLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.nombreplato);
            this.ObsLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.obs);
            this.SwFacturaLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.swfactura);
            this.SwPedidoLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.swpedido);
            this.IndividualLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.individual);


            this.AddCantLineaDocumentoPedido = (Button) itemView.findViewById(R.id.btnAdd);
            this.MinCantLineaDocumentoPedido = (Button) itemView.findViewById(R.id.btnMinus);

            this.AddCantLineaDocumentoPedido.setOnClickListener(this);
            this.MinCantLineaDocumentoPedido.setOnClickListener(this);

            //       this.MinCant.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }
        public void bind(LineaDocumentoPedido LineaDocumentoPedido) {
            IdLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoId()));
            CantLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoCant());
            ArticuloLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoArticulo());
            NombreLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoNombre());
            TipoPlatoLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoTipoPlato());
            NombrePlatoLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoNombre_plato());
            ObsLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoObs());
            SwFacturaLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoSwFactura()));
            SwPedidoLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoSwPedido()));
            IndividualLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoIndividual()));

            AddCantLineaDocumentoPedido.setText(ActividadPrincipal.getPalabras("Sumar"));
            MinCantLineaDocumentoPedido.setText(ActividadPrincipal.getPalabras("Restar"));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnAdd:
                        mListenerLineaDocumentoPedido.onAdd(
                                (Button) v,
                                String.valueOf(this.IdLineaDocumentoPedido.getText()),
                                String.valueOf(this.SwFacturaLineaDocumentoPedido.getText()),
                                String.valueOf(this.IndividualLineaDocumentoPedido.getText())
                        );
                        break;
                    case R.id.btnMinus:
                        mListenerLineaDocumentoPedido.onMinus(
                                (Button) v,
                                String.valueOf(this.IdLineaDocumentoPedido.getText()),
                                String.valueOf(this.CantLineaDocumentoPedido.getText()),
                                String.valueOf(this.SwFacturaLineaDocumentoPedido.getText()),
                                String.valueOf(this.IndividualLineaDocumentoPedido.getText())
                        );
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro",v.getClass().getName().toString());
                mListenerLineaDocumentoPedido.onPotato(
                        v,
                        this.iconLineaDocumentoPedido,
                        String.valueOf(this.IdLineaDocumentoPedido.getText()),
                        String.valueOf(this.NombreLineaDocumentoPedido.getText()),
                        String.valueOf(this.ArticuloLineaDocumentoPedido.getText()),
                        String.valueOf(this.iconLineaDocumentoPedido.getTag().toString()),
                        String.valueOf(this.CantLineaDocumentoPedido.getText()),
                        String.valueOf(this.ObsLineaDocumentoPedido.getText()),
                        String.valueOf(this.SwFacturaLineaDocumentoPedido.getText()),
                        String.valueOf(this.IndividualLineaDocumentoPedido.getText()),
                        String.valueOf(this.TipoPlatoLineaDocumentoPedido.getText()),
                        String.valueOf(this.NombrePlatoLineaDocumentoPedido.getText())
                );
                //           mListener.onPotato(v);
            }
        }

        @Override
        public void onPotato(View caller,
                             ImageView imageLineaDocumentoPedido,
                             String idLineaDocumentoPedido,
                             String nombreLineaDocumentoPedido,
                             String articuloLineaDocumentoPedido,
                             String iconLineaDocumentoPedido,
                             String cantLineaDocumentoPedido,
                             String obsLineaDocumentoPedido,
                             String swfacturaLineaDocumentoPedido,
                             String individualLineaDocumentoPedido,
                             String tipoplatoLineaDocumentoPedido,
                             String nombreplatoLineaDocumentoPedido) {

        }

        @Override
        public void onAdd(Button callerButton,
                          String idLineaDocumentoPedido,
                          String swfacturaLineaDocumentoPedido,
                          String individualLineaDocumentoPedido) {

        }

        @Override
        public void onMinus(Button callerButton,
                            String idLineaDocumentoPedido,
                            String cantLineaDocumentoPedido,
                            String swfacturaLineaDocumentoPedido,
                            String individualLineaDocumentoPedido) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerLineaDocumentoPedido;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerLineaDocumentoPedido = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerLineaDocumentoPedidoHeader {
        void onDeleteLineaDocumentoPedidoSelected(int id, int individual, String estado);
        void onUpdateLineaDocumentoPedidoSelected(int id, String observa, String articulo,String estado );
        void onArticulosLineaDocumentoPedidoSelected(ImageView imagelinea, String articulo, String cNombre);
        void onAddCantLineaDocumentoPedidoSelected(int id, int individual, String estado);
        void onMinusCantLineaDocumentoPedidoSelected(int id, int individual, String estado);

    }
    /*para filtro*/
    public void setFilter(List<LineaDocumentoPedido> LineaDocumentoPedidos) {
        mLineaDocumentoPedido = new ArrayList<>();
        mLineaDocumentoPedido.addAll(LineaDocumentoPedidos);
        notifyDataSetChanged();
    }
    class Delay extends UnitTransformation {
        private final int sleepTime;
        public Delay(int sleepTime) { this.sleepTime = sleepTime; }
        @Override public Resource transform(Resource resource, int outWidth, int outHeight) {
            try { Thread.sleep(sleepTime); } catch (InterruptedException ex) {}
            return super.transform(resource, outWidth, outHeight);
        }
    }


}
