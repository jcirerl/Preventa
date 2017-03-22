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
import tpv.cirer.com.marivent.herramientas.IMySeccionViewHolderClicks;
import tpv.cirer.com.marivent.modelo.Seccion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JUAN on 06/11/2016.
 */

public class AdaptadorSeccionHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<Seccion> mSeccion;
    private Context mContextSeccion;
    private OnHeadlineSelectedListenerSeccionHeader mCallbackSeccion;

    /*   public AdaptadorSeccionHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorSeccionHeader(Context context, List<Seccion> Seccion) {
        this.mSeccion = Seccion;
        this.mContextSeccion = context;
        try {
            this.mCallbackSeccion = ((OnHeadlineSelectedListenerSeccionHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_seccion, parent, false);
            return new AdaptadorSeccionHeader.VHItem(v, new IMySeccionViewHolderClicks() {
                public void onPotato(View caller,
                                     final String idSeccion,
                                     String nombreSeccion,
                                     String codigoSeccion,
                                     String Icon,
                                     String Ivaincluido) {
                    final String xSeccion = nombreSeccion.toString();

                    final String seccion = codigoSeccion.toString();

                    final int apertura = Integer.parseInt(Icon);

                    final int ivaincluido = Integer.parseInt(Ivaincluido);

                    String sOpcion= "";
                    if (Filtro.getTag_fragment().equals("OPEN")) {sOpcion = "Abrir";}
                    if (Filtro.getTag_fragment().equals("CLOSE")) {sOpcion = "Cerrar";}

                    Log.d("Poh-tah-tos", xSeccion + " " + seccion + " " + Icon + " " + Ivaincluido);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext()/*,R.style.MyAlertDialogStyle*/);
                    dialog.setTitle(ActividadPrincipal.getPalabras("Seccion")+": "+codigoSeccion);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Nombre")+": "+nombreSeccion +
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idSeccion +
                            "\n"+ActividadPrincipal.getPalabras("Iva Incluido")+": "+ (0 != ivaincluido ? "SI" : "NO")+
                            "\n"+ActividadPrincipal.getPalabras("ABIERTO")+": "+ (0 != apertura ? "SI" : "NO") );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Consultar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackSeccion.onSeccionSelected(Integer.parseInt(idSeccion));
                            } catch (ClassCastException exception) {
                                // do something
                            }
/*                        Intent intent = new Intent(mContext, AllLecturasActivity.class);
                        intent.putExtra("Seccion", Seccion);
                        mContext.getApplicationContext().startActivityForResult(intent,100);
*/                        dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras(sOpcion), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackSeccion.onSeccionSelected(Integer.parseInt(idSeccion));
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onOpen(Button callerButton,
                                   String idSeccion ) {
                    Log.d("OPEN BUTTON", idSeccion);
                    final int idSEC = Integer.parseInt(idSeccion);

                    try {
                        mCallbackSeccion.onOpenSeccionSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onClose(Button callerButton,
                                    String idSeccion ) {
                    Log.d("CLOSE BUTTON", "+");
                    final int idSEC = Integer.parseInt(idSeccion);

                    try {
                        mCallbackSeccion.onCloseSeccionSelected(idSEC);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_seccion, parent, false);
            return new AdaptadorSeccionHeader.VHHeader(v);
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

            Log.i("recview SEC i", Integer.toString(position));

//        if ((AdaptadorSeccionHeader.VHItem) == vhitem) {
            Log.i("recview SEC vhitem", Integer.toString(mSeccion.size()));
            try {
            /*para filtro*/
                Seccion model = mSeccion.get(position-1);

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                Seccion Seccion = mSeccion.get(position-1);
                if (model.getSeccionApertura()==1) {
                    Log.i("Imagen: ", model.getSeccionUrlimagenopen()+" "+model.getSeccionSeccion());
                    Picasso.with(mContextSeccion)
                            .load(model.getSeccionUrlimagenopen())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("1");
                    ((VHItem) holder).OpenSeccion.setVisibility(View.GONE);
                    if (Integer.valueOf(ActividadPrincipal.itemcaja.getText().toString())>0){
                        ((VHItem) holder).CloseSeccion.setEnabled(false);
                        Toast.makeText(mContextSeccion, "No puede cerrar Seccion " +((VHItem) holder).NombreSeccion.getText().toString() + " Numero de Cajas Abiertas " + ActividadPrincipal.itemcaja.getText().toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        ((VHItem) holder).CloseSeccion.setEnabled(true);
                    }
                }else{
                    Log.i("Imagen: ", model.getSeccionUrlimagenclose()+" "+model.getSeccionSeccion());
                    Picasso.with(mContextSeccion)
                            .load(model.getSeccionUrlimagenclose())
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).icon);
                    ((VHItem) holder).icon.setTag("0");

                    ((VHItem) holder).CloseSeccion.setVisibility(View.GONE);

                }

                ((VHItem) holder).IdSeccion.setText(Html.fromHtml(Integer.toString(model.getSeccionId())));

                myText =String.format("%1$-20s", model.getSeccionSeccion());
                ((VHItem) holder).CodigoSeccion.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText =String.format("%1$-32s", model.getSeccionNombre_Sec());
                ((VHItem) holder).NombreSeccion.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = model.getSeccionFecha_Apertura();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaaperturaSeccion.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                } catch (Exception e) {
                    e.getMessage();
                }

                ((VHItem) holder).NombreSeccion.setTextColor(Color.BLUE);
                ((VHItem) holder).CodigoSeccion.setTextColor(Color.BLACK);

                if (model.getSeccionIvaIncluido()==1) {
                    Picasso.with(mContextSeccion)
                            .load(R.drawable.ok)
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).ivaincluido);

                    ((VHItem) holder).ivaincluido.setTag("1");

                }else{
                    Picasso.with(mContextSeccion)
                            .load(R.drawable.no)
                            .error(R.drawable.placeholder)
                            .resize(60, 60)
                            .centerCrop()
                            .into(((VHItem) holder).ivaincluido);

                    ((VHItem) holder).icon.setTag("0");

                }


                //        (AdaptadorSeccionHeader.VHItem).NombreSeccion.setTextSize(16);
                //        (AdaptadorSeccionHeader.VHItem).ArticuloSeccion.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview SEC dentro", Integer.toString(mSeccion.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            String space05 = new String(new char[05]).replace('\0', ' ');
            String space10 = new String(new char[10]).replace('\0', ' ');
            String space50 = new String(new char[50]).replace('\0', ' ');
            myText = ActividadPrincipal.getPalabras("Seccion")+"     "+
                     ActividadPrincipal.getPalabras("Nombre")+space10+space10+space10+
                     ActividadPrincipal.getPalabras("Fecha Apertura")+space50+space50+space10+
                     ActividadPrincipal.getPalabras("Iva Incluido");

            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_pedidos.
            ((VHHeader) holder).headerSeccion.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerSeccion.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mSeccion ? mSeccion.size()+1 : 1);
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
            IMySeccionViewHolderClicks {
        //   TextView description;
        public Button OpenSeccion;
        public Button CloseSeccion;
        public ImageView icon;
        public TextView NombreSeccion;
        public TextView CodigoSeccion;
        public TextView FechaaperturaSeccion;
        public ImageView ivaincluido;
        public TextView IdSeccion;

        public IMySeccionViewHolderClicks mListenerSeccion;

        public VHItem(View itemView,IMySeccionViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerSeccion = listener;
            this.IdSeccion = (TextView) itemView.findViewById(R.id.pid);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
/*            this.NombreSeccion = (TextView) itemView.findViewById(R.id.row_toptext);
            this.DatosSeccion = (TextView) itemView.findViewById(R.id.row_bottomtext);
            this.CodigoSeccion = (TextView) itemView.findViewById(R.id.seccion);
            this.ivaincluido = (ImageView) itemView.findViewById(R.id.ivaincluido);
*/
            this.NombreSeccion = (TextView) itemView.findViewById(R.id.nombreseccion);
            this.CodigoSeccion = (TextView) itemView.findViewById(R.id.seccion);
            this.FechaaperturaSeccion = (TextView) itemView.findViewById(R.id.fechaapertura);
            this.ivaincluido = (ImageView) itemView.findViewById(R.id.ivaincluido);
            this.OpenSeccion = (Button) itemView.findViewById(R.id.btnOpen);
            this.CloseSeccion = (Button) itemView.findViewById(R.id.btnClose);

            this.OpenSeccion.setOnClickListener(this);
            this.CloseSeccion.setOnClickListener(this);

            this.icon.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void bind(Seccion Seccion) {
            IdSeccion.setText(Integer.toString(Seccion.getSeccionId()));
            NombreSeccion.setText(Seccion.getSeccionNombre_Sec());
            CodigoSeccion.setText(Seccion.getSeccionSeccion());
            FechaaperturaSeccion.setText(Seccion.getSeccionFecha_Apertura());
            OpenSeccion.setText(ActividadPrincipal.getPalabras("ABIERTO"));
            CloseSeccion.setText(ActividadPrincipal.getPalabras("CERRADO"));
        }
        @Override
        public void onClick(View v) {
            Log.i("instance v",v.getClass().getName().toString());

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnOpen:
                        mListenerSeccion.onOpen(
                                (Button) v,
                                String.valueOf(this.IdSeccion.getText())
                        );
                        break;
                    case R.id.btnClose:
                        mListenerSeccion.onClose(
                                (Button) v,
                                String.valueOf(this.IdSeccion.getText())
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
                            mListenerSeccion.onPotato(
                                    v,
                                    String.valueOf(this.IdSeccion.getText()),
                                    String.valueOf(this.NombreSeccion.getText()),
                                    String.valueOf(this.CodigoSeccion.getText()),
                                    String.valueOf(this.icon.getTag().toString()),
                                    String.valueOf(this.ivaincluido.getTag().toString())
                            );
                    }

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             String idSeccion,
                             String nombreSeccion,
                             String codigoSeccion,
                             String Icon,
                             String Ivaincluido){
        }

        @Override
        public void onOpen(Button callerButton, String idSeccion) {

        }

        @Override
        public void onClose(Button callerButton, String idSeccion) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerSeccion;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerSeccion = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerSeccionHeader {
        void onSeccionSelected(int id);
        void onOpenSeccionSelected(int id);
        void onCloseSeccionSelected(int id);

    }
    /*para filtro*/
    public void setFilter(List<Seccion> Seccions) {
        mSeccion = new ArrayList<>();
        mSeccion.addAll(Seccions);
        notifyDataSetChanged();
    }


}
