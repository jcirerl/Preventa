package tpv.cirer.com.restaurante.ui;

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
import tpv.cirer.com.restaurante.herramientas.IMyTurnoViewHolderClicks;
import tpv.cirer.com.restaurante.modelo.Turno;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JUAN on 08/11/2016.
 */

public class AdaptadorTurnoHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<Turno> mTurno;
    private Context mContextTurno;
    private OnHeadlineSelectedListenerTurnoHeader mCallbackTurno;

    /*   public AdaptadorTurnoHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorTurnoHeader(Context context, List<Turno> Turno) {
        this.mTurno = Turno;
        this.mContextTurno = context;
        try {
            this.mCallbackTurno = ((OnHeadlineSelectedListenerTurnoHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_turno, parent, false);
            return new VHItem(v, new IMyTurnoViewHolderClicks() {
                public void onPotato(View caller,
                                     final String idTurno,
                                     String nombreTurno,
                                     String codigoTurno,
                                     String Icon) {
                    final String xTurno = nombreTurno.toString();

                    final String turno = codigoTurno.toString();

                    final int apertura = Integer.parseInt(Icon);


                    String sOpcion= "";
                    if (Filtro.getTag_fragment().equals("OPEN")) {sOpcion = "Abrir";}
                    if (Filtro.getTag_fragment().equals("CLOSE")) {sOpcion = "Cerrar";}

                    Log.d("Poh-tah-tos", xTurno + " " + turno + " " + Icon );
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext()/*,R.style.MyAlertDialogStyle*/);
                    dialog.setTitle(ActividadPrincipal.getPalabras("Turno")+": "+codigoTurno);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+nombreTurno +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idTurno +
                            "\n"+ActividadPrincipal.getPalabras("ABIERTO")+": "+ (0 != apertura ? "SI" : "NO") );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Consultar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackTurno.onTurnoSelected(Integer.parseInt(idTurno));
                            } catch (ClassCastException exception) {
                                // do something
                            }
/*                        Intent intent = new Intent(mContext, AllLecturasActivity.class);
                        intent.putExtra("Turno", Turno);
                        mContext.getApplicationContext().startActivityForResult(intent,100);
*/                        dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras(sOpcion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackTurno.onTurnoSelected(Integer.parseInt(idTurno));
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onOpen(Button callerButton,
                                   String idTurno ) {
                    Log.d("OPEN BUTTON", idTurno);
                    final int idSEC = Integer.parseInt(idTurno);

                    try {
                        mCallbackTurno.onOpenTurnoSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onClose(Button callerButton,
                                    String idTurno ) {
                    Log.d("CLOSE BUTTON", "+");
                    final int idSEC = Integer.parseInt(idTurno);

                    try {
                        mCallbackTurno.onCloseTurnoSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_turno, parent, false);
            return new VHHeader(v);
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

            Log.i("recview TURNO i", Integer.toString(position));

//        if ((VHItem) == vhitem) {
            Log.i("recview TURNO vhitem", Integer.toString(mTurno.size()));
            try {
            /*para filtro*/
                Turno model = mTurno.get(position-1);

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                Turno Turno = mTurno.get(position-1);
                if (model.getTurnoApertura()==1) {
                    Log.i("Imagen: ", model.getTurnoUrlimagenopen()+" "+model.getTurnoCod_turno());
                    Picasso.with(mContextTurno)
                            .load(model.getTurnoUrlimagenopen())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("1");
                    // Boton Open Turno invisible.
                    ((VHItem) holder).OpenTurno.setVisibility(View.GONE);
                    // Boton Close Turno ENABLED=Pedidos y Facturas cerrados sino NOT ENABLED.
                    if (Integer.valueOf(ActividadPrincipal.itemdcj.getText().toString())>0){
                        ((VHItem) holder).CloseTurno.setEnabled(false);
                        Toast.makeText(mContextTurno, "No puede cerrar Turno " +((VHItem) holder).NombreTurno.getText().toString() + " Numero de Diario Cajas Abiertos " + ActividadPrincipal.itemdcj.getText().toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        ((VHItem) holder).CloseTurno.setEnabled(true);
                    }
                }else{
                    Log.i("Imagen: ", model.getTurnoUrlimagenclose()+" "+model.getTurnoCod_turno());
                    Picasso.with(mContextTurno)
                            .load(model.getTurnoUrlimagenclose())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("0");
                    // Boton Close Turno invisible
                    ((VHItem) holder).CloseTurno.setVisibility(View.GONE);
                    // Boton Open Turno Enabled=Caja Abierta sino NOT ENABLED.
                    if (Integer.valueOf(ActividadPrincipal.itemcaja.getText().toString())>0){
                        ((VHItem) holder).OpenTurno.setEnabled(true);
                    }else{
                        Toast.makeText(mContextTurno, "No puede abrir TURNO No Hay CAJA Abierta", Toast.LENGTH_SHORT).show();
                        ((VHItem) holder).OpenTurno.setEnabled(false);
                    }

                }
                ((VHItem) holder).IdTurno.setText(Html.fromHtml(Integer.toString(model.getTurnoId())));

                myText =String.format("%1$-20s", model.getTurnoCod_turno());
                ((VHItem) holder).CodigoTurno.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText =String.format("%1$-32s", model.getTurnoNombre_Turno());
                ((VHItem) holder).NombreTurno.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = model.getTurnoFecha_Apertura();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaaperturaTurno.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }

                ((VHItem) holder).NombreTurno.setTextColor(Color.BLUE);
                ((VHItem) holder).CodigoTurno.setTextColor(Color.BLACK);

            } catch (Exception e) {
                Log.i("recview TURNO dentro", Integer.toString(mTurno.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            String space05 = new String(new char[05]).replace('\0', ' ');
            String space10 = new String(new char[10]).replace('\0', ' ');
            String space50 = new String(new char[50]).replace('\0', ' ');
            myText = ActividadPrincipal.getPalabras("Turno")+"         "+
                     ActividadPrincipal.getPalabras("Nombre")+space10+space10+space05+
                     ActividadPrincipal.getPalabras("Fecha Apertura");

            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_pedidos.
            ((VHHeader) holder).headerTurno.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerTurno.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mTurno ? mTurno.size()+1 : 1);
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
            IMyTurnoViewHolderClicks {
        //   TextView description;
        public Button OpenTurno;
        public Button CloseTurno;
        public ImageView icon;
        public TextView NombreTurno;
        public TextView CodigoTurno;
        public TextView FechaaperturaTurno;
        public TextView IdTurno;

        public IMyTurnoViewHolderClicks mListenerTurno;

        public VHItem(View itemView,IMyTurnoViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerTurno = listener;
            this.IdTurno = (TextView) itemView.findViewById(R.id.pid);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.NombreTurno = (TextView) itemView.findViewById(R.id.nombreturno);
            this.CodigoTurno = (TextView) itemView.findViewById(R.id.turno);
            this.FechaaperturaTurno = (TextView) itemView.findViewById(R.id.fechaapertura);
            this.OpenTurno = (Button) itemView.findViewById(R.id.btnOpen);
            this.CloseTurno = (Button) itemView.findViewById(R.id.btnClose);

            this.OpenTurno.setOnClickListener(this);
            this.CloseTurno.setOnClickListener(this);

            this.icon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(Turno Turno) {
            IdTurno.setText(Integer.toString(Turno.getTurnoId()));
            NombreTurno.setText(Turno.getTurnoNombre_Turno());
            CodigoTurno.setText(Turno.getTurnoCod_turno());
            FechaaperturaTurno.setText(Turno.getTurnoFecha_Apertura());
            OpenTurno.setText(ActividadPrincipal.getPalabras("ABIERTO"));
            CloseTurno.setText(ActividadPrincipal.getPalabras("CERRADO"));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnOpen:
                        mListenerTurno.onOpen(
                                (Button) v,
                                String.valueOf(this.IdTurno.getText())
                        );
                        break;
                    case R.id.btnClose:
                        mListenerTurno.onClose(
                                (Button) v,
                                String.valueOf(this.IdTurno.getText())
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
                            mListenerTurno.onPotato(
                                    v,
                                    String.valueOf(this.IdTurno.getText()),
                                    String.valueOf(this.NombreTurno.getText()),
                                    String.valueOf(this.CodigoTurno.getText()),
                                    String.valueOf(this.icon.getTag().toString())
                            );
                    }

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             String idTurno,
                             String nombreTurno,
                             String codigoTurno,
                             String Icon){
        }

        @Override
        public void onOpen(Button callerButton, String idTurno) {

        }

        @Override
        public void onClose(Button callerButton, String idTurno) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerTurno;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerTurno = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerTurnoHeader {
        void onTurnoSelected(int id);
        void onOpenTurnoSelected(int id);
        void onCloseTurnoSelected(int id);

    }
    /*para filtro*/
    public void setFilter(List<Turno> Turnos) {
        mTurno = new ArrayList<>();
        mTurno.addAll(Turnos);
        notifyDataSetChanged();
    }


}

