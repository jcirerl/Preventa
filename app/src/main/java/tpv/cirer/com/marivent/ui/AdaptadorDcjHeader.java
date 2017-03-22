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
import android.widget.Toast;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.IMyDcjViewHolderClicks;
import tpv.cirer.com.marivent.modelo.Dcj;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JUAN on 21/11/2016.
 */

public class AdaptadorDcjHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<Dcj> mDcj;
    private Context mContextDcj;
    private OnHeadlineSelectedListenerDcjHeader mCallbackDcj;

    /*   public AdaptadorDcjHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorDcjHeader(Context context, List<Dcj> Dcj) {
        this.mDcj = Dcj;
        this.mContextDcj = context;
        try {
            this.mCallbackDcj = ((OnHeadlineSelectedListenerDcjHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_dcj, parent, false);
            return new VHItem(v, new IMyDcjViewHolderClicks() {
                public void onPotato(View caller,
                              String campoDcj,
                              String iconDcj,
                              String idDcj,
                              String turnoDcj,
                              String fechaaperturaDcj,
                              String fechaopenDcj,
                              String fechacloseDcj,
                              String saldoinicioDcj,
                              String saldofinalDcj,
                              String aperturaDcj) {
                    final String campoDCJ = campoDcj;
                    final int idDCJ = Integer.parseInt(idDcj);
                    final String turnoDCJ = turnoDcj;
                    final String fechaaperturaDCJ = fechaaperturaDcj;
                    final String fechaopenDCJ = fechaopenDcj;
                    final String fechacloseDCJ = fechacloseDcj;
                    final String saldoinicioDCJ = saldoinicioDcj;
                    final String saldofinalDCJ = saldofinalDcj;
                    final int aperturaDCJ = Integer.parseInt(aperturaDcj);


                    final int apertura = Integer.parseInt(iconDcj);


                    String sOpcion= "";
                    if (Filtro.getTag_fragment().equals("OPEN")) {sOpcion = "Abrir";}
                    if (Filtro.getTag_fragment().equals("CLOSE")) {sOpcion = "Cerrar";}

                    Log.d("Poh-tah-tos", idDCJ + " " + turnoDCJ + " " + iconDcj );
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext()/*,R.style.MyAlertDialogStyle*/);
                    dialog.setTitle(ActividadPrincipal.getPalabras("Turno")+": "+turnoDCJ);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+fechaaperturaDCJ +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idDCJ +
                            "\n"+ActividadPrincipal.getPalabras("ABIERTO")+": "+ (0 != apertura ? "SI" : "NO") +
                            "\n"+ActividadPrincipal.getPalabras("Fecha Apertura")+"..: " + fechaopenDCJ +
                            "\n"+ActividadPrincipal.getPalabras("Fecha Cierre")+".: " + fechacloseDCJ +
                            "\n"+ActividadPrincipal.getPalabras("Saldo Inicio")+": " + saldoinicioDCJ +
                            "\n"+ActividadPrincipal.getPalabras("Saldo Final")+".: " + saldofinalDCJ);
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Modificar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                switch (campoDCJ) {
                                    case "Saldo_inicio":
                                        mCallbackDcj.onUpdateDcjSelected(idDCJ, saldoinicioDCJ, campoDCJ);
                                        break;
                                }
                            } catch (ClassCastException exception) {
                                // do something
                            }
/*                        Intent intent = new Intent(mContext, AllLecturasActivity.class);
                        intent.putExtra("Dcj", Dcj);
                        mContext.getApplicationContext().startActivityForResult(intent,100);
*/                        dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras(sOpcion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackDcj.onDcjSelected(idDCJ);
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onOpen(Button callerButton,
                                   String idDcj ) {
                    Log.d("OPEN BUTTON", idDcj);
                    final int idSEC = Integer.parseInt(idDcj);

                    try {
                        mCallbackDcj.onOpenDcjSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onClose(Button callerButton,
                                    String idDcj ) {
                    Log.d("CLOSE BUTTON", "+");
                    final int idSEC = Integer.parseInt(idDcj);

                    try {
                        mCallbackDcj.onCloseDcjSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onPrint(Button callerButton,
                                    String idDcj,
                                    String aperturaDcj) {
                    Log.d("PRINT BUTTON", "+");
                    final int idDCJ = Integer.parseInt(idDcj);
                    final int aperturaDCJ = Integer.parseInt(aperturaDcj);

                    try {
                        mCallbackDcj.onPrintDcjSelected(idDCJ,aperturaDCJ);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_dcj, parent, false);
            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String myText;
        //cast holder to VHHeader and set data for header.
        String space05 = new String(new char[05]).replace('\0', ' ');
        String space10 = new String(new char[10]).replace('\0', ' ');
        String space50 = new String(new char[50]).replace('\0', ' ');
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

        if (holder instanceof VHItem) {
//            String dataItem = getItem(position);
            //cast holder to VHItem and set data
//            ((VHItem) holder).description.setText(dataItem);

            Log.i("recview DCJ i", Integer.toString(position));

//        if ((VHItem) == vhitem) {
            Log.i("recview DCJ vhitem", Integer.toString(mDcj.size()));
            try {
            /*para filtro*/
                Dcj model = mDcj.get(position-1);

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                Dcj Dcj = mDcj.get(position-1);
                if (model.getDcjApertura()==1) {
                    Log.i("Imagen: ", model.getDcjUrlimagenopen()+" "+model.getDcjCod_turno());
                    Picasso.with(mContextDcj)
                            .load(model.getDcjUrlimagenopen())
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("1");
                    // Boton Open Dcj invisible.
                    ((VHItem) holder).OpenDcj.setVisibility(View.GONE);
                    // Boton Close Dcj ENABLED=Pedidos y Facturas cerrados sino NOT ENABLED.
                    if ((Integer.valueOf(ActividadPrincipal.itempedido.getText().toString())>0) ||
                            (Integer.valueOf(ActividadPrincipal.itemfactura.getText().toString())>0)){
                        if (Integer.valueOf(ActividadPrincipal.itempedido.getText().toString())>0)
                        {
                            try{
                                String StringRecogido = model.getDcjFecha_Apertura();
                                Date datehora = sdf1.parse(StringRecogido);
                                myText = sdf2.format(datehora);
                                ((VHItem) holder).FechaaperturaDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                            } catch (Exception e) {
                                e.getMessage();
                            }
                            Toast.makeText(mContextDcj, "No puede cerrar Diario Caja " + ((VHItem) holder).FechaaperturaDcj.getText().toString() + " Numero de Pedidos Abiertos " + ActividadPrincipal.itempedido.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        if (Integer.valueOf(ActividadPrincipal.itemfactura.getText().toString())>0)
                        {
                            try{
                                String StringRecogido = model.getDcjFecha_Apertura();
                                Date datehora = sdf1.parse(StringRecogido);
                                myText = sdf2.format(datehora);
                                ((VHItem) holder).FechaaperturaDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                            } catch (Exception e) {
                                e.getMessage();
                            }
                            Toast.makeText(mContextDcj, "No puede cerrar Diario Caja " + ((VHItem) holder).FechaaperturaDcj.getText().toString() + " Numero de Facturas Abiertas " + ActividadPrincipal.itemfactura.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        ((VHItem) holder).CloseDcj.setEnabled(false);
                    }else{
                        ((VHItem) holder).CloseDcj.setEnabled(true);
                    }
                }else{
                    Log.i("Imagen: ", model.getDcjUrlimagenclose()+" "+model.getDcjCod_turno());
                    Picasso.with(mContextDcj)
                            .load(model.getDcjUrlimagenclose())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("0");
                    // Boton Close Dcj invisible
                    ((VHItem) holder).CloseDcj.setVisibility(View.GONE);
                    ((VHItem) holder).OpenDcj.setVisibility(View.GONE); // LA APERTURA DE LOS DIARIOS DE CAJA SE HACE A TRAVES DEL FLOATING BUTTON

                    // Boton Open Dcj Enabled=Caja Abierta sino NOT ENABLED.
/*                    if (Integer.valueOf(ActividadPrincipal.itemturno.getText().toString())>0){
                        ((VHItem) holder).OpenDcj.setEnabled(true);
                    }else{
                        Toast.makeText(mContextDcj, "No puede abrir DIARIO CAJA No Hay TURNO Abierto", Toast.LENGTH_SHORT).show();
                        ((VHItem) holder).OpenDcj.setEnabled(false);
                    }
*/
                }
                ((VHItem) holder).IdDcj.setText(Html.fromHtml(Integer.toString(model.getDcjId())));
                ((VHItem) holder).AperturaDcj.setText(Html.fromHtml(Integer.toString(model.getDcjApertura())));

                myText =String.format("%1$-20s", model.getDcjCod_turno());
                ((VHItem) holder).TurnoDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                try{
                    String StringRecogido = model.getDcjFecha_Apertura();
                    Date datehora = sdf1.parse(StringRecogido);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaaperturaDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }
                try{
                    String StringRecogido = model.getDcjFecha_open();
                    Date datehora = sdf1.parse(StringRecogido);
                    myText = space10+sdf2.format(datehora);
                    ((VHItem) holder).FechaopenDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }
                try{
                    String StringRecogido = model.getDcjFecha_close();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = space10+space10+sdf2.format(datehora);
                    ((VHItem) holder).FechacloseDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }
                myText = space10+space10+String.format("%1$,.2f", Float.parseFloat(Dcj.getDcjSaldo_inicio()))+" "+ Filtro.getSimbolo();
                ((VHItem) holder).SaldoinicioDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText = space10+space10+space10+String.format("%1$,.2f", Float.parseFloat(Dcj.getDcjSaldo_final()))+" "+ Filtro.getSimbolo();
                ((VHItem) holder).SaldofinalDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                if (model.getDcjIvaIncluido()==1) {
                    Picasso.with(mContextDcj)
                            .load(R.drawable.ok)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).ivaincluido);

                    ((VHItem) holder).ivaincluido.setTag("1");

                }else{
                    Picasso.with(mContextDcj)
                            .load(R.drawable.no)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).ivaincluido);

                    ((VHItem) holder).icon.setTag("0");

                }

                ((VHItem) holder).FechaaperturaDcj.setTextColor(Color.BLUE);
                ((VHItem) holder).SaldoinicioDcj.setTextColor(Color.MAGENTA);

            } catch (Exception e) {
                Log.i("recview DCJ dentro", Integer.toString(mDcj.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {
            myText = ActividadPrincipal.getPalabras("Turno")+space05+space10+
                     ActividadPrincipal.getPalabras("Apertura")+space10+
                     ActividadPrincipal.getPalabras("Saldo Inicio")+space05+
                     ActividadPrincipal.getPalabras("Saldo Final")+space05+
                     ActividadPrincipal.getPalabras("Fecha Apertura")+space05+
                     ActividadPrincipal.getPalabras("Fecha Cierre")+space10+space10+space10+
                     ActividadPrincipal.getPalabras("Iva Incluido");

            ((VHHeader) holder).headerDcj.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerDcj.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mDcj ? mDcj.size()+1 : 1);
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
            IMyDcjViewHolderClicks {
        //   TextView description;
        public Button OpenDcj;
        public Button CloseDcj;
        public Button PrintDcj;
        public ImageView icon;
        public TextView TurnoDcj;
        public TextView SaldoinicioDcj;
        public TextView SaldofinalDcj;
        public TextView FechaopenDcj;
        public TextView FechacloseDcj;
        public TextView FechaaperturaDcj;
        public TextView IdDcj;
        public TextView AperturaDcj;
        public ImageView ivaincluido;

        public IMyDcjViewHolderClicks mListenerDcj;

        public VHItem(View itemView,IMyDcjViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerDcj = listener;
            this.IdDcj = (TextView) itemView.findViewById(R.id.pid);
            this.AperturaDcj = (TextView) itemView.findViewById(R.id.apertura);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.FechaopenDcj = (TextView) itemView.findViewById(R.id.fechaopen);
            this.FechacloseDcj = (TextView) itemView.findViewById(R.id.fechaclose);
            this.SaldofinalDcj = (TextView) itemView.findViewById(R.id.saldofinal);
            this.SaldoinicioDcj = (TextView) itemView.findViewById(R.id.saldoinicio);
            this.ivaincluido = (ImageView) itemView.findViewById(R.id.ivaincluido);
            this.TurnoDcj = (TextView) itemView.findViewById(R.id.turno);
            this.FechaaperturaDcj = (TextView) itemView.findViewById(R.id.fechaapertura);
            this.OpenDcj = (Button) itemView.findViewById(R.id.btnOpen);
            this.CloseDcj = (Button) itemView.findViewById(R.id.btnClose);
            this.PrintDcj = (Button) itemView.findViewById(R.id.btnPrint);

            this.OpenDcj.setOnClickListener(this);
            this.CloseDcj.setOnClickListener(this);
            this.PrintDcj.setOnClickListener(this);

            this.SaldoinicioDcj.setOnClickListener(this);

            this.icon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(Dcj Dcj) {
            IdDcj.setText(Integer.toString(Dcj.getDcjId()));
            AperturaDcj.setText(Integer.toString(Dcj.getDcjApertura()));
            FechaopenDcj.setText(Dcj.getDcjFecha_open());
            FechacloseDcj.setText(Dcj.getDcjFecha_close());
            SaldoinicioDcj.setText(Dcj.getDcjSaldo_inicio());
            SaldofinalDcj.setText(Dcj.getDcjSaldo_final());
            TurnoDcj.setText(Dcj.getDcjCod_turno());
            FechaaperturaDcj.setText(Dcj.getDcjFecha_Apertura());
            OpenDcj.setText(ActividadPrincipal.getPalabras("ABIERTO"));
            CloseDcj.setText(ActividadPrincipal.getPalabras("CERRADO"));
            PrintDcj.setText(ActividadPrincipal.getPalabras("Imprimir"));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnOpen:
                        mListenerDcj.onOpen(
                                (Button) v,
                                String.valueOf(this.IdDcj.getText())
                        );
                        break;
                    case R.id.btnClose:
                        mListenerDcj.onClose(
                                (Button) v,
                                String.valueOf(this.IdDcj.getText())
                        );
                        break;
                    case R.id.btnPrint:
                        mListenerDcj.onPrint(
                                (Button) v,
                                String.valueOf(this.IdDcj.getText()),
                                String.valueOf(this.AperturaDcj.getText())
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
                            mListenerDcj.onPotato(
                                    v,
                                    "",
                                    String.valueOf(this.icon.getTag().toString()),
                                    String.valueOf(this.IdDcj.getText()),
                                    String.valueOf(this.TurnoDcj.getText()),
                                    String.valueOf(this.FechaaperturaDcj.getText()),
                                    String.valueOf(this.FechaopenDcj.getText()),
                                    String.valueOf(this.FechacloseDcj.getText()),
                                    String.valueOf(this.SaldoinicioDcj.getText()),
                                    String.valueOf(this.SaldofinalDcj.getText()),
                                    String.valueOf(this.AperturaDcj.getText())
                            );
                    }

                    //           mListener.onPotato(v);
                }else{
                    if (v instanceof TextView) {
                        Log.i("instance v dentro", v.getClass().getName().toString());
                        switch (v.getId()) {
                            case R.id.saldoinicio:
                                mListenerDcj.onPotato(
                                        v,
                                        "Saldo_inicio",
                                        String.valueOf(this.icon.getTag().toString()),
                                        String.valueOf(this.IdDcj.getText()),
                                        String.valueOf(this.TurnoDcj.getText()),
                                        String.valueOf(this.FechaaperturaDcj.getText()),
                                        String.valueOf(this.FechaopenDcj.getText()),
                                        String.valueOf(this.FechacloseDcj.getText()),
                                        String.valueOf(this.SaldoinicioDcj.getText()),
                                        String.valueOf(this.SaldofinalDcj.getText()),
                                        String.valueOf(this.AperturaDcj.getText())
                                );
                                break;
                        }

                        //           mListener.onPotato(v);
                    }

                }
            }
        }
        @Override
        public void onPotato(View caller,
                      String campoDcj,
                      String iconDcj,
                      String idDcj,
                      String turnoDcj,
                      String fechaaperturaDcj,
                      String fechaopenDcj,
                      String fechacloseDcj,
                      String saldoinicioDcj,
                      String saldofinalDcj,
                      String aperturaDcj){
        }

        @Override
        public void onOpen(Button callerButton, String idDcj) {

        }

        @Override
        public void onClose(Button callerButton, String idDcj) {

        }

        @Override
        public void onPrint(Button callerButton, String idDcj, String aperturaDcj) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerDcj;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerDcj = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerDcjHeader {
        void onDcjSelected(int id);
        void onOpenDcjSelected(int id);
        void onCloseDcjSelected(int id);
        void onPrintDcjSelected(int id, int apertura);
        void onUpdateDcjSelected(int id, String valor, String campo);

    }
    /*para filtro*/
    public void setFilter(List<Dcj> Dcjs) {
        mDcj = new ArrayList<>();
        mDcj.addAll(Dcjs);
        notifyDataSetChanged();
    }


}


