package tpv.cirer.com.restaurante.ui;

/**
 * Created by JUAN on 07/11/2016.
 */

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
import android.widget.Toast;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.IMyCajaViewHolderClicks;
import tpv.cirer.com.restaurante.modelo.Caja;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JUAN on 06/11/2016.
 */

public class AdaptadorCajaHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<Caja> mCaja;
    private Context mContextCaja;
    private AdaptadorCajaHeader.OnHeadlineSelectedListenerCajaHeader mCallbackCaja;

    /*   public AdaptadorCajaHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorCajaHeader(Context context, List<Caja> Caja) {
        this.mCaja = Caja;
        this.mContextCaja = context;
        try {
            this.mCallbackCaja = ((AdaptadorCajaHeader.OnHeadlineSelectedListenerCajaHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_caja, parent, false);
            return new AdaptadorCajaHeader.VHItem(v, new IMyCajaViewHolderClicks() {
                public void onPotato(View caller,
                                     final String idCaja,
                                     String nombreCaja,
                                     String codigoCaja,
                                     String Icon) {
                    final String xCaja = nombreCaja.toString();

                    final String caja = codigoCaja.toString();

                    final int apertura = Integer.parseInt(Icon);


                    String sOpcion= "";
                    if (Filtro.getTag_fragment().equals("OPEN")) {sOpcion = "Abrir";}
                    if (Filtro.getTag_fragment().equals("CLOSE")) {sOpcion = "Cerrar";}

                    Log.d("Poh-tah-tos", xCaja + " " + caja + " " + Icon );
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext()/*,R.style.MyAlertDialogStyle*/);
                    dialog.setTitle(ActividadPrincipal.getPalabras("Caja")+": "+codigoCaja);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+nombreCaja +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idCaja +
                            "\n"+ActividadPrincipal.getPalabras("ABIERTO")+": "+ (0 != apertura ? "SI" : "NO") );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Consultar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackCaja.onCajaSelected(Integer.parseInt(idCaja));
                            } catch (ClassCastException exception) {
                                // do something
                            }
/*                        Intent intent = new Intent(mContext, AllLecturasActivity.class);
                        intent.putExtra("Caja", Caja);
                        mContext.getApplicationContext().startActivityForResult(intent,100);
*/                        dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras(sOpcion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackCaja.onCajaSelected(Integer.parseInt(idCaja));
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onOpen(Button callerButton,
                                   String idCaja ) {
                    Log.d("OPEN BUTTON", idCaja);
                    final int idSEC = Integer.parseInt(idCaja);

                    try {
                        mCallbackCaja.onOpenCajaSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onClose(Button callerButton,
                                    String idCaja ) {
                    Log.d("CLOSE BUTTON", "+");
                    final int idSEC = Integer.parseInt(idCaja);

                    try {
                        mCallbackCaja.onCloseCajaSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_caja, parent, false);
            return new AdaptadorCajaHeader.VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String myText;
        if (holder instanceof VHItem) {
//            String dataItem = getItem(position);
            //cast holder to VHItem and set data
//            ((VHItem) holder).description.setText(dataItem);

            Log.i("recview CAJA i", Integer.toString(position));

//        if ((AdaptadorCajaHeader.VHItem) == vhitem) {
            Log.i("recview CAJA vhitem", Integer.toString(mCaja.size()));
            try {
            /*para filtro*/
                Caja model = mCaja.get(position-1);

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                Caja Caja = mCaja.get(position-1);
                if (model.getCajaApertura()==1) {
                    Log.i("Imagen: ", model.getCajaUrlimagenopen()+" "+model.getCajaCaja());
                    Picasso.with(mContextCaja)
                            .load(model.getCajaUrlimagenopen())
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("1");
                    // Boton Open Caja invisible.
                    ((VHItem) holder).OpenCaja.setVisibility(View.GONE);
                    // Boton Close Caja ENABLED=Turnos cerrados sino NOT ENABLED.
                    if (Integer.valueOf(ActividadPrincipal.itemturno.getText().toString())>0){
                        ((VHItem) holder).CloseCaja.setEnabled(false);
                        Toast.makeText(mContextCaja, "No puede cerrar Caja " +((VHItem) holder).NombreCaja.getText().toString() + " Numero de Turnos Abiertos " + ActividadPrincipal.itemturno.getText().toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        ((VHItem) holder).CloseCaja.setEnabled(true);
                    }
                }else{
                    Log.i("Imagen: ", model.getCajaUrlimagenclose()+" "+model.getCajaCaja());
                    Picasso.with(mContextCaja)
                            .load(model.getCajaUrlimagenclose())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("0");
                    // Boton Close Caja invisible
                    ((VHItem) holder).CloseCaja.setVisibility(View.GONE);
                    // Boton Open Caja Enabled=Seccion Abierta sino NOT ENABLED.
                    if (Integer.valueOf(ActividadPrincipal.itemseccion.getText().toString())>0){
                        ((VHItem) holder).OpenCaja.setEnabled(true);
                    }else{
                        Toast.makeText(mContextCaja, "No puede abrir CAJA No Hay SECCION Abierta", Toast.LENGTH_SHORT).show();
                        ((VHItem) holder).OpenCaja.setEnabled(false);
                    }

                }
                ((VHItem) holder).IdCaja.setText(Html.fromHtml(Integer.toString(model.getCajaId())));

                myText =String.format("%1$-20s", model.getCajaCaja());
                ((VHItem) holder).CodigoCaja.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText =String.format("%1$-32s", model.getCajaNombre_Caja());
                ((VHItem) holder).NombreCaja.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = model.getCajaFecha_Apertura();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaaperturaCaja.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }

                ((VHItem) holder).NombreCaja.setTextColor(Color.BLUE);
                ((VHItem) holder).CodigoCaja.setTextColor(Color.BLACK);

            } catch (Exception e) {
                Log.i("recview CAJA dentro", Integer.toString(mCaja.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            String space05 = new String(new char[05]).replace('\0', ' ');
            String space10 = new String(new char[10]).replace('\0', ' ');
            String space50 = new String(new char[50]).replace('\0', ' ');
            myText = ActividadPrincipal.getPalabras("Caja")+"         "+
                     ActividadPrincipal.getPalabras("Nombre")+space10+space10+space05+
                     ActividadPrincipal.getPalabras("Fecha Apertura");

            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_pedidos.
            ((VHHeader) holder).headerCaja.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerCaja.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mCaja ? mCaja.size()+1 : 1);
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
            IMyCajaViewHolderClicks {
        //   TextView description;
        public Button OpenCaja;
        public Button CloseCaja;
        public ImageView icon;
        public TextView NombreCaja;
        public TextView CodigoCaja;
        public TextView FechaaperturaCaja;
        public TextView IdCaja;

        public IMyCajaViewHolderClicks mListenerCaja;

        public VHItem(View itemView,IMyCajaViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerCaja = listener;
            this.IdCaja = (TextView) itemView.findViewById(R.id.pid);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.NombreCaja = (TextView) itemView.findViewById(R.id.nombrecaja);
            this.CodigoCaja = (TextView) itemView.findViewById(R.id.caja);
            this.FechaaperturaCaja = (TextView) itemView.findViewById(R.id.fechaapertura);
            this.OpenCaja = (Button) itemView.findViewById(R.id.btnOpen);
            this.CloseCaja = (Button) itemView.findViewById(R.id.btnClose);

            this.OpenCaja.setOnClickListener(this);
            this.CloseCaja.setOnClickListener(this);

            this.icon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(Caja Caja) {
            IdCaja.setText(Integer.toString(Caja.getCajaId()));
            NombreCaja.setText(Caja.getCajaNombre_Caja());
            CodigoCaja.setText(Caja.getCajaCaja());
            FechaaperturaCaja.setText(Caja.getCajaFecha_Apertura());
            OpenCaja.setText(ActividadPrincipal.getPalabras("ABIERTO"));
            CloseCaja.setText(ActividadPrincipal.getPalabras("CERRADO"));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnOpen:
                        mListenerCaja.onOpen(
                                (Button) v,
                                String.valueOf(this.IdCaja.getText())
                        );
                        break;
                    case R.id.btnClose:
                        mListenerCaja.onClose(
                                (Button) v,
                                String.valueOf(this.IdCaja.getText())
                        );
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro", v.getClass().getName().toString());
                if (v instanceof ImageView) {
                    Log.i("instance v dentro", v.getClass().getName().toString());
                    switch (v.getId()) {
                        case R.id.icon:
                            mListenerCaja.onPotato(
                                    v,
                                    String.valueOf(this.IdCaja.getText()),
                                    String.valueOf(this.NombreCaja.getText()),
                                    String.valueOf(this.CodigoCaja.getText()),
                                    String.valueOf(this.icon.getTag().toString())
                            );
                    }

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             String idCaja,
                             String nombreCaja,
                             String codigoCaja,
                             String Icon){
        }

        @Override
        public void onOpen(Button callerButton, String idCaja) {

        }

        @Override
        public void onClose(Button callerButton, String idCaja) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerCaja;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerCaja = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerCajaHeader {
        void onCajaSelected(int id);
        void onOpenCajaSelected(int id);
        void onCloseCajaSelected(int id);

    }
    /*para filtro*/
    public void setFilter(List<Caja> Cajas) {
        mCaja = new ArrayList<>();
        mCaja.addAll(Cajas);
        notifyDataSetChanged();
    }


}
