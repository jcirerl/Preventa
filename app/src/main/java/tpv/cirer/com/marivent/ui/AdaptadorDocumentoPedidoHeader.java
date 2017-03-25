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

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.AutoResizeTextView;
import tpv.cirer.com.marivent.herramientas.IMyDocumentoPedidoViewHolderClicks;
import tpv.cirer.com.marivent.modelo.DocumentoPedido;

/**
 * Created by JUAN on 29/10/2016.
 */

public class AdaptadorDocumentoPedidoHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<DocumentoPedido> mDocumentoPedido;
    private Context mContextDocumentoPedido;
    private OnHeadlineSelectedListenerDocumentoPedidoHeader mCallbackDocumentoPedido;

    /*   public AdaptadorDocumentoPedidoHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorDocumentoPedidoHeader(Context context, List<DocumentoPedido> DocumentoPedido) {
        this.mDocumentoPedido = DocumentoPedido;
        this.mContextDocumentoPedido = context;
        try {
            this.mCallbackDocumentoPedido = ((OnHeadlineSelectedListenerDocumentoPedidoHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_documentopedido_sony, parent, false);
            return new VHItem(v, new IMyDocumentoPedidoViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller,
                                     final String campoDocumentoPedido,
                                     String iconDocumentoPedido,
                                     String idDocumentoPedido,
                                     String pedidoDocumentoPedido,
                                     String comensalesDocumentoPedido,
                                     String mesaDocumentoPedido,
                                     String estadoDocumentoPedido,
                                     String empleadoDocumentoPedido,
                                     String cajaDocumentoPedido,
                                     String turnoDocumentoPedido,
                                     String obsDocumentoPedido,
                                     String lineasDocumentoPedido) {
                    final String campoPDD = campoDocumentoPedido;
                    final int idPDD = Integer.parseInt(idDocumentoPedido);
                    final int pedidoPDD = Integer.parseInt(pedidoDocumentoPedido);
                    final String comensalesPDD = comensalesDocumentoPedido;
                    final String mesaPDD = mesaDocumentoPedido;
                    final String estadoPDD = estadoDocumentoPedido;
                    final String empleadoPDD = empleadoDocumentoPedido;
                    final String cajaPDD = cajaDocumentoPedido;
                    final String turnoPDD = turnoDocumentoPedido;
                    final String obsPDD = obsDocumentoPedido;
                    lineasDocumentoPedido = lineasDocumentoPedido.replace(Html.fromHtml("&nbsp;"), "");
                    final int lineasPDD = Integer.parseInt(lineasDocumentoPedido);

                    Log.d("Poh-tah-tos", pedidoPDD + " " + mesaPDD + " " + iconDocumentoPedido);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext());
                    dialog.setTitle(ActividadPrincipal.getPalabras("Modificar")+ " "+campoPDD+" "+ActividadPrincipal.getPalabras("Pedido")+": "+pedidoPDD);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Datos")+" "+ActividadPrincipal.getPalabras("Pedido")+": "+
                                    "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Comensales")+".: " + comensalesPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Mesa")+".: " + mesaPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Estado")+": " + estadoPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Empleado")+".: " + empleadoPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Caja")+": " + cajaPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Turno")+": " + turnoPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Observaciones")+": " + obsPDD +
                                    "\n"+ActividadPrincipal.getPalabras("Lineas")+": " + lineasPDD
                    );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if (!estadoPDD.contains("CLOSE")) {
                                    switch (campoPDD) {
                                        case "Comensales":
                                            mCallbackDocumentoPedido.onUpdateDocumentoPedidoSelected(idPDD, comensalesPDD, campoPDD);
                                            break;
                                        case "Mesa":
                                            mCallbackDocumentoPedido.onUpdateDocumentoPedidoSelected(idPDD, mesaPDD, campoPDD);
                                            break;
                                        case "Empleado":
                                            mCallbackDocumentoPedido.onUpdateDocumentoPedidoSelected(idPDD, empleadoPDD, campoPDD);
                                            break;
                                        case "Obs":
                                            mCallbackDocumentoPedido.onUpdateDocumentoPedidoSelected(idPDD, obsPDD, campoPDD);
                                            break;
                                    }
                                }
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackDocumentoPedido.onDeleteDocumentoPedidoSelected(idPDD, estadoPDD, pedidoPDD);
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onDelete(Button callerButton,
                                     String idDocumentoPedido,
                                     String estadoDocumentoPedido,
                                     String pedidoDocumentoPedido ) {
                    Log.d("DELETE BUTTON", pedidoDocumentoPedido+" "+estadoDocumentoPedido);
                    int idPDD = Integer.parseInt(idDocumentoPedido);
                    String estadoPDD = estadoDocumentoPedido;
                    int pedidoPDD = Integer.parseInt(pedidoDocumentoPedido);

                    try {
                        mCallbackDocumentoPedido.onDeleteDocumentoPedidoSelected(idPDD, estadoPDD, pedidoPDD);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onUpdate(Button callerButton,
                                     String idDocumentoPedido,
                                     String estadoDocumentoPedido,
                                     String mesaDocumentoPedido,
                                     String pedidoDocumentoPedido ) {
                    Log.d("UPDATE BUTTON", " + "+estadoDocumentoPedido);
                    int idPDD = Integer.parseInt(idDocumentoPedido);
                    String estadoPDD = estadoDocumentoPedido;
                    final String mesaPDD = mesaDocumentoPedido;
                    String pedidoPDD = pedidoDocumentoPedido;

                    try {
                        mCallbackDocumentoPedido.onUpdateLineasDocumentoPedidoSelected(idPDD, estadoPDD, mesaPDD, pedidoPDD);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
                public void onInvoice(Button callerButton,
                                      String pedidoDocumentoPedido,
                                      String estadoDocumentoPedido ) {
                    Log.d("UPDATE BUTTON", "+");
                    final String estadoPDD = estadoDocumentoPedido.toString();
                    final String pedidoPDD = pedidoDocumentoPedido.toString();

                    try {
                        mCallbackDocumentoPedido.onInvoiceDocumentoPedidoSelected(Integer.parseInt(pedidoPDD),estadoPDD);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_pedidos, parent, false);
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
        String newText;
        String space01 = new String(new char[01]).replace('\0', ' ');
        String space05 = new String(new char[05]).replace('\0', ' ');
        String space10 = new String(new char[10]).replace('\0', ' ');
        String space50 = new String(new char[50]).replace('\0', ' ');
        if (holder instanceof VHItem) {

            Log.i("recview LPD i", Integer.toString(position));

//        if (DocumentoPedidoRowHolder == vhitem) {
            Log.i("recview LPD vhitem", Integer.toString(mDocumentoPedido.size()));
            try {
            /*para filtro*/
                DocumentoPedido model = mDocumentoPedido.get(position-1);
                Log.i("Imagen: ", model.getDocumentoPedidoUrlimagen());

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                DocumentoPedido DocumentoPedido = mDocumentoPedido.get(position-1);
                Picasso.with(mContextDocumentoPedido)
                        .load(model.getDocumentoPedidoUrlimagen())
                        .resize(60, 60)
                        .centerCrop()
                        .into(((VHItem) holder).iconDocumentoPedido);

/*                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.placeholder)
                        .resize(imgWidth, imgHeight)
                        .centerCrop()
                        .into(image);
*/
                ((VHItem) holder).iconDocumentoPedido.setTag("0");
/*                Glide.with(mContextDocumentoPedido)
                        .load(new DownloadImageTask(((VHItem) holder).iconDocumentoPedido)
                                .execute(model.getDocumentoPedidoUrlimagen()))
                        .override(60,60)
                        .into(((VHItem) holder).iconDocumentoPedido);
*/
                /////////////////////////////////////////////////////////////////

                /////////////////////////////////////////////////////////////////

                ((VHItem) holder).IdDocumentoPedido.setText(Html.fromHtml(Integer.toString(DocumentoPedido.getDocumentoPedidoId())));
                ((VHItem) holder).MesaDocumentoPedido.setText(Html.fromHtml(DocumentoPedido.getDocumentoPedidoMesa()));

                ((VHItem) holder).PedidoDocumentoPedido.setText(Html.fromHtml(String.format("%08d", DocumentoPedido.getDocumentoPedidoPedido())));
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = model.getDocumentoPedidoFecha();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")+"&nbsp;&nbsp;").toString());

                } catch (Exception e) {
                    e.getMessage();
                }

                ((VHItem) holder).NombreMesaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoNombre_Mesa().trim()+fixString);
                ((VHItem) holder).EstadoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoEstado().trim()+fixString);
                ((VHItem) holder).EmpleadoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoEmpleado().trim()+fixString);
                ((VHItem) holder).CajaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoCaja().trim()+fixString);
                ((VHItem) holder).TurnoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoCod_turno().trim()+fixString);
////                ((VHItem) holder).ObsDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoObs().trim()+fixString);

                ((VHItem) holder).ComensalesDocumentoPedido.setText(Html.fromHtml(String.format("%02d",DocumentoPedido.getDocumentoPedidoComensales())+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
                ((VHItem) holder).LineasDocumentoPedido.setText(Html.fromHtml(String.format("%06d",DocumentoPedido.getDocumentoPedidoLineas())+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));

                myText =String.format("%1$-50s",DocumentoPedido.getDocumentoPedidoObs());
                ((VHItem) holder).ObsDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());


                ((VHItem) holder).PedidoDocumentoPedido.setTextColor(Color.BLACK);
                ((VHItem) holder).NombreMesaDocumentoPedido.setTextColor(Color.MAGENTA);
                ((VHItem) holder).EmpleadoDocumentoPedido.setTextColor(Color.MAGENTA);
                ((VHItem) holder).EstadoDocumentoPedido.setTextColor(Color.BLUE);
                ((VHItem) holder).ObsDocumentoPedido.setTextColor(Color.MAGENTA);
                ((VHItem) holder).ComensalesDocumentoPedido.setTextColor(Color.MAGENTA);

                ((VHItem) holder).ObsDocumentoPedido.setBackgroundColor(Color.TRANSPARENT);
                if (DocumentoPedido.getDocumentoPedidoObs().trim().length()==0) {
                    if(DocumentoPedido.getDocumentoPedidoEstado().contains("OPEN")) {
                        ((VHItem) holder).ObsDocumentoPedido.setBackgroundColor(Color.MAGENTA);
                    }
                }

                //        DocumentoPedidoRowHolder.NombreDocumentoPedido.setTextSize(16);
                //        DocumentoPedidoRowHolder.ArticuloDocumentoPedido.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview PDD dentro", Integer.toString(mDocumentoPedido.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {
            /// SONY
            myText = " "+
                    ActividadPrincipal.getPalabras("Pedido")+StringUtils.repeat(space01, 5)+
                    ActividadPrincipal.getPalabras("Fecha")+StringUtils.repeat(space01, 6)+
                    ActividadPrincipal.getPalabras("Np")+ StringUtils.repeat(space01, 2)+
                    ActividadPrincipal.getPalabras("Mesa")+StringUtils.repeat(space01, 12)+
                    ActividadPrincipal.getPalabras("Estado")+StringUtils.repeat(space01, 10)+
                    ActividadPrincipal.getPalabras("Usuario")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Caja")+StringUtils.repeat(space01, 12)+
                    ActividadPrincipal.getPalabras("Turno")+StringUtils.repeat(space01, 10)+
                    ActividadPrincipal.getPalabras("Lineas")+StringUtils.repeat(space01, 2)+
                    ActividadPrincipal.getPalabras("Observacion");
 /*
            // MARIVENT
            myText = StringUtils.repeat(space01, 6)+
                    ActividadPrincipal.getPalabras("Pedido")+ StringUtils.repeat(space01, 6)+
                    ActividadPrincipal.getPalabras("Fecha")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Np")+StringUtils.repeat(space01, 2)+
                    ActividadPrincipal.getPalabras("Mesa")+StringUtils.repeat(space01, 6)+
                    ActividadPrincipal.getPalabras("Estado")+StringUtils.repeat(space01, 22)+
                    ActividadPrincipal.getPalabras("Usuario")+StringUtils.repeat(space01, 22)+
                    ActividadPrincipal.getPalabras("Caja")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Turno")+StringUtils.repeat(space01, 9)+
                    ActividadPrincipal.getPalabras("Lineas")+StringUtils.repeat(space01, 3)+
                    ActividadPrincipal.getPalabras("Observacion");
*/
            //cast holder to VHHeader and set data for header.
            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_pedidos.
            ((VHHeader) holder).headerDocumentoPedido.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerDocumentoPedido.setTextColor(Color.WHITE);

        }
    }
    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */



    @Override
    public int getItemCount() {
        return (null != mDocumentoPedido ? mDocumentoPedido.size()+1 : 1);
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
            IMyDocumentoPedidoViewHolderClicks {
        //   TextView description;

        public ImageView iconDocumentoPedido;
        public Button UpdateDocumentoPedido;
        public Button DeleteDocumentoPedido;
        public Button SendDocumentoPedido;
        public Button InvoiceDocumentoPedido;
/*
//      MARIVENT
        public AutoResizeTextView PedidoDocumentoPedido;
        public AutoResizeTextView FechaDocumentoPedido;
        public AutoResizeTextView NombreMesaDocumentoPedido;
        public TextView MesaDocumentoPedido;
        public AutoResizeTextView ComensalesDocumentoPedido;
        public AutoResizeTextView EstadoDocumentoPedido;
        public AutoResizeTextView EmpleadoDocumentoPedido;
        public AutoResizeTextView CajaDocumentoPedido;
        public AutoResizeTextView TurnoDocumentoPedido;
        public AutoResizeTextView ObsDocumentoPedido;
        public TextView IdDocumentoPedido;
        public AutoResizeTextView LineasDocumentoPedido;
*/
//      sony
        public TextView PedidoDocumentoPedido;
        public TextView FechaDocumentoPedido;
        public AutoResizeTextView NombreMesaDocumentoPedido;
        public TextView MesaDocumentoPedido;
        public AutoResizeTextView ComensalesDocumentoPedido;
        public AutoResizeTextView EstadoDocumentoPedido;
        public AutoResizeTextView EmpleadoDocumentoPedido;
        public AutoResizeTextView CajaDocumentoPedido;
        public AutoResizeTextView TurnoDocumentoPedido;
        public TextView ObsDocumentoPedido;
        public TextView IdDocumentoPedido;
        public TextView LineasDocumentoPedido;

        public IMyDocumentoPedidoViewHolderClicks mListenerDocumentoPedido;

        public VHItem(View itemView,IMyDocumentoPedidoViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerDocumentoPedido = listener;

            //       this.MinCant.setOnClickListener(this);
/*
//          MARIVENT
            this.IdDocumentoPedido = (TextView) itemView.findViewById(R.id.pid);
            this.iconDocumentoPedido = (ImageView) itemView.findViewById(R.id.icon);
            this.EstadoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.estado);
            this.PedidoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.pedido);
            this.FechaDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.fecha);
            this.MesaDocumentoPedido = (TextView) itemView.findViewById(R.id.mesa);
            this.ComensalesDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.comensales);
            this.NombreMesaDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.nombremesa);
            this.EmpleadoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.empleado);
            this.CajaDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.caja);
            this.TurnoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.turno);
            this.ObsDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.obs);
            this.LineasDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.lineas);
*/
//          SONY
            this.IdDocumentoPedido = (TextView) itemView.findViewById(R.id.pid);
            this.iconDocumentoPedido = (ImageView) itemView.findViewById(R.id.icon);
            this.EstadoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.estado);
            this.PedidoDocumentoPedido = (TextView) itemView.findViewById(R.id.pedido);
            this.FechaDocumentoPedido = (TextView) itemView.findViewById(R.id.fecha);
            this.MesaDocumentoPedido = (TextView) itemView.findViewById(R.id.mesa);
            this.ComensalesDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.comensales);
            this.NombreMesaDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.nombremesa);
            this.EmpleadoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.empleado);
            this.CajaDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.caja);
            this.TurnoDocumentoPedido = (AutoResizeTextView) itemView.findViewById(R.id.turno);
            this.ObsDocumentoPedido = (TextView) itemView.findViewById(R.id.obs);
            this.LineasDocumentoPedido = (TextView) itemView.findViewById(R.id.lineas);

            this.UpdateDocumentoPedido = (Button) itemView.findViewById(R.id.btnUpdate);
            this.DeleteDocumentoPedido = (Button) itemView.findViewById(R.id.btnDelete);
            this.SendDocumentoPedido = (Button) itemView.findViewById(R.id.btnSend);
            this.InvoiceDocumentoPedido = (Button) itemView.findViewById(R.id.btnInvoice);

            this.UpdateDocumentoPedido.setOnClickListener(this);
            this.DeleteDocumentoPedido.setOnClickListener(this);
            this.SendDocumentoPedido.setOnClickListener(this);
            this.InvoiceDocumentoPedido.setOnClickListener(this);

            this.ObsDocumentoPedido.setOnClickListener(this);
            this.NombreMesaDocumentoPedido.setOnClickListener(this);
            this.EmpleadoDocumentoPedido.setOnClickListener(this);
            this.ComensalesDocumentoPedido.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }
        public void bind(DocumentoPedido DocumentoPedido) {
            IdDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoId()));
            EstadoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoEstado().trim());
            PedidoDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoPedido()));
            FechaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoFecha());
            MesaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoMesa());
            ComensalesDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoComensales()));
            NombreMesaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoNombre_Mesa());
            EmpleadoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoEmpleado());
            CajaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoCaja());
            TurnoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoCod_turno());
            ObsDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoObs());
            LineasDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoLineas()));

        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnUpdate:
                        mListenerDocumentoPedido.onUpdate(
                                (Button) v,
                                String.valueOf(this.IdDocumentoPedido.getText()),
                                String.valueOf(this.EstadoDocumentoPedido.getText()),
                                String.valueOf(this.MesaDocumentoPedido.getText()),
                                String.valueOf(this.PedidoDocumentoPedido.getText())
                        );
                        break;
                    case R.id.btnDelete:
                        mListenerDocumentoPedido.onDelete(
                                (Button) v,
                                String.valueOf(this.IdDocumentoPedido.getText()),
                                String.valueOf(this.EstadoDocumentoPedido.getText()),
                                String.valueOf(this.PedidoDocumentoPedido.getText())
                        );
                        break;
                    case R.id.btnSend:
/*                    mListener.onMinus(
                            (Button) v,
                            String.valueOf(this.IdLineaDocumentoPedido.getText()),
                            String.valueOf(this.CantLineaDocumentoPedido.getText())
                    );
*/                    break;
                    case R.id.btnInvoice:
                        mListenerDocumentoPedido.onInvoice(
                                (Button) v,
                                String.valueOf(this.IdDocumentoPedido.getText()),
                                String.valueOf(this.EstadoDocumentoPedido.getText())

                        );
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro", v.getClass().getName().toString());
                if (v instanceof TextView) {
                    Log.i("instance v dentro", v.getClass().getName().toString());
                    switch (v.getId()) {
                        case R.id.comensales:
                            mListenerDocumentoPedido.onPotato(
                                    v,
                                    "Comensales",
                                    String.valueOf(this.iconDocumentoPedido.getTag().toString()),
                                    String.valueOf(this.IdDocumentoPedido.getText()),
                                    String.valueOf(this.PedidoDocumentoPedido.getText()),
                                    String.valueOf(this.ComensalesDocumentoPedido.getText()),
                                    String.valueOf(this.MesaDocumentoPedido.getText()),
                                    String.valueOf(this.EstadoDocumentoPedido.getText()),
                                    String.valueOf(this.EmpleadoDocumentoPedido.getText()),
                                    String.valueOf(this.CajaDocumentoPedido.getText()),
                                    String.valueOf(this.TurnoDocumentoPedido.getText()),
                                    String.valueOf(this.ObsDocumentoPedido.getText()),
                                    String.valueOf(this.LineasDocumentoPedido.getText())
                            );
                            break;
                        case R.id.nombremesa:
                            mListenerDocumentoPedido.onPotato(
                                    v,
                                    "Mesa",
                                    String.valueOf(this.iconDocumentoPedido.getTag().toString()),
                                    String.valueOf(this.IdDocumentoPedido.getText()),
                                    String.valueOf(this.PedidoDocumentoPedido.getText()),
                                    String.valueOf(this.ComensalesDocumentoPedido.getText()),
                                    String.valueOf(this.MesaDocumentoPedido.getText()),
                                    String.valueOf(this.EstadoDocumentoPedido.getText()),
                                    String.valueOf(this.EmpleadoDocumentoPedido.getText()),
                                    String.valueOf(this.CajaDocumentoPedido.getText()),
                                    String.valueOf(this.TurnoDocumentoPedido.getText()),
                                    String.valueOf(this.ObsDocumentoPedido.getText()),
                                    String.valueOf(this.LineasDocumentoPedido.getText())
                            );
                            break;
                        case R.id.empleado:
                            mListenerDocumentoPedido.onPotato(
                                    v,
                                    "Empleado",
                                    String.valueOf(this.iconDocumentoPedido.getTag().toString()),
                                    String.valueOf(this.IdDocumentoPedido.getText()),
                                    String.valueOf(this.PedidoDocumentoPedido.getText()),
                                    String.valueOf(this.ComensalesDocumentoPedido.getText()),
                                    String.valueOf(this.MesaDocumentoPedido.getText()),
                                    String.valueOf(this.EstadoDocumentoPedido.getText()),
                                    String.valueOf(this.EmpleadoDocumentoPedido.getText()),
                                    String.valueOf(this.CajaDocumentoPedido.getText()),
                                    String.valueOf(this.TurnoDocumentoPedido.getText()),
                                    String.valueOf(this.ObsDocumentoPedido.getText()),
                                    String.valueOf(this.LineasDocumentoPedido.getText())
                            );
                            break;
                        case R.id.obs:
                            mListenerDocumentoPedido.onPotato(
                                    v,
                                    "Obs",
                                    String.valueOf(this.iconDocumentoPedido.getTag().toString()),
                                    String.valueOf(this.IdDocumentoPedido.getText()),
                                    String.valueOf(this.PedidoDocumentoPedido.getText()),
                                    String.valueOf(this.ComensalesDocumentoPedido.getText()),
                                    String.valueOf(this.MesaDocumentoPedido.getText()),
                                    String.valueOf(this.EstadoDocumentoPedido.getText()),
                                    String.valueOf(this.EmpleadoDocumentoPedido.getText()),
                                    String.valueOf(this.CajaDocumentoPedido.getText()),
                                    String.valueOf(this.TurnoDocumentoPedido.getText()),
                                    String.valueOf(this.ObsDocumentoPedido.getText()),
                                    String.valueOf(this.LineasDocumentoPedido.getText())
                            );
                            break;
                    }

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             String campoDocumentoPedido,
                             String iconDocumentoPedido,
                             String idDocumentoPedido,
                             String pedidoDocumentoPedido,
                             String comensalesDocumentoPedido,
                             String mesaDocumentoPedido,
                             String estadoDocumentoPedido,
                             String empleadoDocumentoPedido,
                             String cajaDocumentoPedido,
                             String turnoDocumentoPedido,
                             String obsDocumentoPedido,
                             String lineasDocumentoPedido) {

        }

        @Override
        public void onUpdate(Button callerButton,
                             String idDocumentoPedido,
                             String estadoDocumentoPedido,
                             String mesaDocumentoPedido,
                             String pedidoDocumentoPedido) {

        }

        @Override
        public void onDelete(Button callerButton,
                             String idDocumentoPedido,
                             String estadoDocumentoPedido,
                             String pedidoDocumentoPedido) {

        }

        @Override
        public void onInvoice(Button callerButton,
                              String idDocumentoPedido,
                              String estadoDocumentoPedido) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerDocumentoPedido;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerDocumentoPedido = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerDocumentoPedidoHeader {
        void onDeleteDocumentoPedidoSelected(int id, String estado, int pedido);
        void onUpdateDocumentoPedidoSelected(int id, String valor, String campo);
        void onUpdateLineasDocumentoPedidoSelected(int id, String estado, String mesa, String pedido);
        void onInvoiceDocumentoPedidoSelected(int id, String estado);
    }
    /*para filtro*/
    public void setFilter(List<DocumentoPedido> DocumentoPedidos) {
        mDocumentoPedido = new ArrayList<>();
        mDocumentoPedido.addAll(DocumentoPedidos);
        notifyDataSetChanged();
    }


}
