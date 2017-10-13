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

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.AutoResizeTextView;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.IMyDocumentoFacturaViewHolderClicks;
import tpv.cirer.com.restaurante.modelo.DocumentoFactura;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getPalabras;

/**
 * Created by JUAN on 08/07/2017.
 */

public class AdaptadorDocumentoFacturaHeaderAsus extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<DocumentoFactura> mDocumentoFactura;
    private Context mContextDocumentoFactura;
    private OnHeadlineSelectedListenerDocumentoFacturaHeader mCallbackDocumentoFactura;

    /*   public AdaptadorDocumentoFacturaHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorDocumentoFacturaHeaderAsus(Context context, List<DocumentoFactura> DocumentoFactura) {
        this.mDocumentoFactura = DocumentoFactura;
        this.mContextDocumentoFactura = context;
        try {
            this.mCallbackDocumentoFactura = ((OnHeadlineSelectedListenerDocumentoFacturaHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_documentofactura_asus, parent, false);
            return new VHItem(v, new IMyDocumentoFacturaViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller,
                                     ImageView imageDocumentoFactura,
                                     final String campoDocumentoFactura,
                                     String iconDocumentoFactura,
                                     String idDocumentoFactura,
                                     String serieDocumentoFactura,
                                     String facturaDocumentoFactura,
                                     String fechaDocumentoFactura,
                                     String mesaDocumentoFactura,
                                     String tfraDocumentoFactura,
                                     String estadoDocumentoFactura,
                                     String empleadoDocumentoFactura,
                                     String cajaDocumentoFactura,
                                     String turnoDocumentoFactura,
                                     String obsDocumentoFactura,
                                     String impcobroDocumentoFactura,
                                     String lineasDocumentoFactura) {
                    final String campoFTP = campoDocumentoFactura;
                    final String serieFTP = serieDocumentoFactura;
                    final int idFTP = Integer.parseInt(idDocumentoFactura);
                    final int facturaFTP = Integer.parseInt(facturaDocumentoFactura);
                    final String fechaFTP = fechaDocumentoFactura;
                    final String mesaFTP = mesaDocumentoFactura;
                    final String tfraFTP = tfraDocumentoFactura;
                    final String estadoFTP = estadoDocumentoFactura;
                    final String empleadoFTP = empleadoDocumentoFactura;
                    final String cajaFTP = cajaDocumentoFactura;
                    final String turnoFTP = turnoDocumentoFactura;
                    final String impcobroFTP = impcobroDocumentoFactura;
                    final String obsFTP = obsDocumentoFactura;
                    lineasDocumentoFactura = lineasDocumentoFactura.replace(Html.fromHtml("&nbsp;"), "");
                    final int lineasFTP = Integer.parseInt(lineasDocumentoFactura);

                    Log.d("Poh-tah-tos", facturaFTP + " " + mesaFTP + " " + iconDocumentoFactura);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext());
                    dialog.setTitle(ActividadPrincipal.getPalabras("Modificar")+ " "+ActividadPrincipal.getPalabras(campoFTP)+" "+ActividadPrincipal.getPalabras("Factura")+": "+facturaFTP);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Datos")+" "+ActividadPrincipal.getPalabras("Factura")+": "+
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idFTP +
                            "\n"+ActividadPrincipal.getPalabras("Serie")+": "+ serieFTP +
                            "\n"+ActividadPrincipal.getPalabras("Fecha")+".: " + fechaFTP +
                            "\n"+ActividadPrincipal.getPalabras("Mesa")+".: " + mesaFTP +
                            "\n"+ActividadPrincipal.getPalabras("Tipo Cobro")+".: " + tfraFTP +
                            "\n"+ActividadPrincipal.getPalabras("Estado")+": " + estadoFTP +
                            "\n"+ActividadPrincipal.getPalabras("Empleado")+".: " + empleadoFTP +
                            "\n"+ActividadPrincipal.getPalabras("Caja")+": " + cajaFTP +
                            "\n"+ActividadPrincipal.getPalabras("Turno")+": " + turnoFTP +
                            "\n"+ActividadPrincipal.getPalabras("Observaciones")+": " + obsFTP +
                            "\n"+ActividadPrincipal.getPalabras("Lineas")+": " + lineasFTP
                    );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Modificar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                if (!estadoFTP.contains("CLOSE")) {
                                    switch (campoFTP) {
                                        case "Mesa":
                                            mCallbackDocumentoFactura.onUpdateDocumentoFacturaSelected(idFTP, mesaFTP, campoFTP);
                                            break;
                                        case "Empleado":
                                            mCallbackDocumentoFactura.onUpdateDocumentoFacturaSelected(idFTP, empleadoFTP, campoFTP);
                                            break;
                                        case "Obs":
                                            mCallbackDocumentoFactura.onUpdateDocumentoFacturaSelected(idFTP, obsFTP, campoFTP);
                                            break;
                                    }
                                }else{
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                                    try{
                                        String StringRecogido = Filtro.getFechaapertura();
                                        Date datehora = sdf1.parse(StringRecogido);

                                        //System.out.println("Fecha input : "+datehora);
                                        String myText = sdf2.format(datehora);
//                                        Log.i("compara",String.valueOf(fechaFTP.length())+' '+String.valueOf(myText.length()));
//                                        Log.i("compara",fechaFTP.substring(0,10)+' '+myText);
                                        if(fechaFTP.substring(0,10).equals(myText)) {
                                            switch (campoFTP) {
                                                case "Tipo Cobro":
                                                    mCallbackDocumentoFactura.onUpdateCobroDocumentoFacturaSelected(idFTP, serieFTP, String.valueOf(facturaFTP), impcobroFTP);
                                                    break;
                                            }
                                        }else{
                                            Toast.makeText(mContextDocumentoFactura, getPalabras("Fecha")+" "+getPalabras("Factura")+" "+getPalabras("distinta")+" "+getPalabras("Fecha")+" "+getPalabras("Apertura"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.getMessage();
                                    }

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
                                mCallbackDocumentoFactura.onDeleteDocumentoFacturaSelected(idFTP, estadoFTP, serieFTP, facturaFTP);
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onDelete(Button callerButton,
                                     ImageView imageDocumentoFactura,
                                     String idDocumentoFactura,
                                     String estadoDocumentoFactura,
                                     String serieDocumentoFactura,
                                     String facturaDocumentoFactura ) {
                    Log.d("DELETE BUTTON", facturaDocumentoFactura);
                    final String serieFTP = serieDocumentoFactura;
                    final int idFTP = Integer.parseInt(idDocumentoFactura);
                    final int facturaFTP = Integer.parseInt(facturaDocumentoFactura);
                    final String estadoFTP = estadoDocumentoFactura;

                    try {
                        mCallbackDocumentoFactura.onDeleteDocumentoFacturaSelected(idFTP, estadoFTP, serieFTP,facturaFTP);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onUpdate(Button callerButton,
                                     ImageView imageDocumentoFactura,
                                     String idDocumentoFactura,
                                     String estadoDocumentoFactura,
                                     String mesaDocumentoFactura,
                                     String serieDocumentoFactura,
                                     String facturaDocumentoFactura,
                                     String totalDocumentoFactura,
                                     String obsDocumentoFactura ) {
                    Log.d("UPDATE BUTTON", "+");
                    final ImageView imageFTP = imageDocumentoFactura;
                    final int idFTP = Integer.parseInt(idDocumentoFactura);
                    final String mesaFTP = mesaDocumentoFactura;
                    final String serieFTP = serieDocumentoFactura;
                    final String facturaFTP = facturaDocumentoFactura;
                    final String estadoFTP = estadoDocumentoFactura;
                    final String totalFTP = totalDocumentoFactura;
                    final String obsFTP = obsDocumentoFactura;

                    try {
                        mCallbackDocumentoFactura.onUpdateLineasDocumentoFacturaSelected(imageFTP,idFTP,mesaFTP,estadoFTP,serieFTP,facturaFTP,totalFTP, obsFTP);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
                public void onCobro (Button callerButton,
                                     ImageView imageDocumentoFactura,
                                     String idDocumentoFactura,
                                     String estadoDocumentoFactura,
                                     String serieDocumentoFactura,
                                     String facturaDocumentoFactura,
                                     String totalDocumentoFactura,
                                     String obsDocumentoFactura) {
                    Log.d("UPDATE BUTTON", "+");
                    final String idFTP = idDocumentoFactura;
                    final String estadoFTP = estadoDocumentoFactura;
                    final String serieFTP = serieDocumentoFactura;
                    final String facturaFTP = facturaDocumentoFactura;
                    final String totalFTP = totalDocumentoFactura;
                    final String obsFTP = obsDocumentoFactura;

                    try {
                        mCallbackDocumentoFactura.onCobroDocumentoFacturaSelected(Integer.parseInt(idFTP),estadoFTP,serieFTP,facturaFTP,totalFTP,obsFTP);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
                public void onDivision(Button callerButton,
                                       ImageView imageDocumentoFactura,
                                       String idDocumentoFactura,
                                       String estadoDocumentoFactura,
                                       String mesaDocumentoFactura,
                                       String serieDocumentoFactura,
                                       String facturaDocumentoFactura,
                                       String totalDocumentoFactura,
                                       String obsDocumentoFactura ) {
                    Log.d("UPDATE BUTTON", "+");
                    final ImageView imageFTP = imageDocumentoFactura;
                    final int idFTP = Integer.parseInt(idDocumentoFactura);
                    final String mesaFTP = mesaDocumentoFactura;
                    final String serieFTP = serieDocumentoFactura;
                    final String facturaFTP = facturaDocumentoFactura;
                    final String estadoFTP = estadoDocumentoFactura;
                    final String totalFTP = totalDocumentoFactura;
                    final String obsFTP = obsDocumentoFactura;

                    try {
                        mCallbackDocumentoFactura.onUpdateDivisionLineasDocumentoFacturaSelected(imageFTP,idFTP,mesaFTP,estadoFTP,serieFTP,facturaFTP,totalFTP, obsFTP);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onFactura (Button callerButton,
                                       ImageView imageDocumentoFactura,
                                       String idDocumentoFactura,
                                       String estadoDocumentoFactura,
                                       String serieDocumentoFactura,
                                       String facturaDocumentoFactura) {
                    Log.d("UPDATE BUTTON", "+");
                    final String idFTP = idDocumentoFactura;
                    final String estadoFTP = estadoDocumentoFactura;
                    final String serieFTP = serieDocumentoFactura;
                    final String facturaFTP = facturaDocumentoFactura;

                    try {
                        mCallbackDocumentoFactura.onFacturarDocumentoFacturaSelected(Integer.parseInt(idFTP),estadoFTP,serieFTP,facturaFTP);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

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

//        if (DocumentoFacturaRowHolder == vhitem) {
            Log.i("recview LPD vhitem", Integer.toString(mDocumentoFactura.size()));
            try {
            /*para filtro*/
                DocumentoFactura model = mDocumentoFactura.get(position-1);
                Log.i("Imagen: ", model.getDocumentoFacturaUrlimagen());

                ((VHItem) holder).bind(model);
                ////////////////////////////////////////////////////////
                DocumentoFactura DocumentoFactura = mDocumentoFactura.get(position-1);
                Picasso.with(mContextDocumentoFactura)
                        .load(model.getDocumentoFacturaUrlimagen())
                        .resize(60, 60)
                        .centerCrop()
                        .into(((VHItem) holder).iconDocumentoFactura);
                ((VHItem) holder).iconDocumentoFactura.setTag("0");
                /////////////////////////////////////////////////////////////////

                /////////////////////////////////////////////////////////////////

                ((VHItem) holder).IdDocumentoFactura.setText(Html.fromHtml(Integer.toString(DocumentoFactura.getDocumentoFacturaId())));
                ((VHItem) holder).MesaDocumentoFactura.setText(Html.fromHtml(DocumentoFactura.getDocumentoFacturaMesa()));
                ((VHItem) holder).TfraDocumentoFactura.setText(Html.fromHtml(DocumentoFactura.getDocumentoFacturaT_fra()));

                myText =String.format("%1$-1s", DocumentoFactura.getDocumentoFacturaSerie());
                ((VHItem) holder).SerieDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                ((VHItem) holder).FacturaDocumentoFactura.setText(Html.fromHtml(String.format("%08d", DocumentoFactura.getDocumentoFacturaFactura())));

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                try{
                    String StringRecogido = model.getDocumentoFacturaFecha();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).FechaDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")+"&nbsp;&nbsp;").toString());

                } catch (Exception e) {
                    e.getMessage();
                }

                ((VHItem) holder).NombreMesaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaNombre_Mesa().trim()+fixString);
                ((VHItem) holder).EstadoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaEstado().trim()+fixString);
                ((VHItem) holder).EmpleadoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaEmpleado().trim()+fixString);
                ((VHItem) holder).CajaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaCaja().trim()+fixString);
                ((VHItem) holder).TurnoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaCod_turno().trim()+fixString);
                ((VHItem) holder).NombreTftDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaNombre_tft().trim()+fixString);

                ((VHItem) holder).LineasDocumentoFactura.setText(Html.fromHtml(String.format("%06d",DocumentoFactura.getDocumentoFacturaLineas())+"&nbsp;&nbsp;"));

                int lenobs = DocumentoFactura.getDocumentoFacturaObs().trim().length();
                myText =String.format("%1$-25s",DocumentoFactura.getDocumentoFacturaObs().substring(0,(lenobs>25 ? 25 : lenobs)));
                ((VHItem) holder).ObsDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

/*                myText =String.format("%1$-30s",DocumentoFactura.getDocumentoFacturaObs().trim());
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                ((VHItem) holder).ObsDocumentoFactura.setText(myText);
*/
                myText = String.format("%1$,.2f", Float.parseFloat(DocumentoFactura.getDocumentoFacturaImp_base()))+" "+ Filtro.getSimbolo();
                ((VHItem) holder).ImpbaseDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

                myText = String.format("%1$,.2f", Float.parseFloat(DocumentoFactura.getDocumentoFacturaImp_iva()))+" "+ Filtro.getSimbolo();
                ((VHItem) holder).ImpivaDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());

/*                myText = String.format("%1$,.2f", Float.parseFloat(DocumentoFactura.getDocumentoFacturaImp_total()))+" "+ Filtro.getSimbolo();
                myText = String.format("%1$15s",myText);

                ((VHItem) holder).ImptotalDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")+"&nbsp;").toString());
*/
                myText = String.format("%1$,.2f", Float.parseFloat(DocumentoFactura.getDocumentoFacturaImp_total()));
                Log.i("lMytextImp",Integer.toString(myText.length()));
//                myText = String.format("%1$6s",myText);
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                newText="";
                for (int ii = 0; ii < (10-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                ((VHItem) holder).ImptotalDocumentoFactura.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                ((VHItem) holder).ImpcobroDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaImp_cobro());

                ((VHItem) holder).FacturaDocumentoFactura.setTextColor(Color.BLACK);
                ((VHItem) holder).NombreMesaDocumentoFactura.setTextColor(Color.MAGENTA);
                ((VHItem) holder).EmpleadoDocumentoFactura.setTextColor(Color.MAGENTA);
                ((VHItem) holder).EstadoDocumentoFactura.setTextColor(Color.BLUE);
                ((VHItem) holder).ObsDocumentoFactura.setTextColor(Color.MAGENTA);

                if(DocumentoFactura.getDocumentoFacturaEstado().contains("CLOSE")) {
                    ((VHItem) holder).NombreTftDocumentoFactura.setTextColor(Color.MAGENTA);
                }else{
                    ((VHItem) holder).NombreTftDocumentoFactura.setTextColor(Color.BLACK);
                }

                ((VHItem) holder).ObsDocumentoFactura.setBackgroundColor(Color.TRANSPARENT);
                if (DocumentoFactura.getDocumentoFacturaObs().trim().length()==0) {
                    if(DocumentoFactura.getDocumentoFacturaEstado().contains("OPEN")) {
                        ((VHItem) holder).ObsDocumentoFactura.setBackgroundColor(Color.MAGENTA);
                    }
                }

                //        DocumentoFacturaRowHolder.NombreDocumentoFactura.setTextSize(16);
                //        DocumentoFacturaRowHolder.ArticuloDocumentoFactura.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview FTP dentro", Integer.toString(mDocumentoFactura.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.

            // MARIVENT
            myText = StringUtils.repeat(space01, 5)+
                    "S"+StringUtils.repeat(space01, 2)+
                    ActividadPrincipal.getPalabras("Factura")+ StringUtils.repeat(space01, 5)+
                    ActividadPrincipal.getPalabras("Fecha")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Mesa")+StringUtils.repeat(space01, 6)+
                    ActividadPrincipal.getPalabras("Estado")+StringUtils.repeat(space01, 22)+
                    ActividadPrincipal.getPalabras("Usuario")+StringUtils.repeat(space01, 22)+
                    ActividadPrincipal.getPalabras("Caja")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Turno")+StringUtils.repeat(space01, 12)+
                    ActividadPrincipal.getPalabras("Importe")+StringUtils.repeat(space01, 9)+
                    ActividadPrincipal.getPalabras("Tipo")+StringUtils.repeat(space01, 11)+
                    ActividadPrincipal.getPalabras("Lineas")+StringUtils.repeat(space01, 3)+
                    ActividadPrincipal.getPalabras("Observacion");

            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_facturas.
            ((VHHeader) holder).headerDocumentoFactura.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerDocumentoFactura.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mDocumentoFactura ? mDocumentoFactura.size()+1 : 1);
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
            IMyDocumentoFacturaViewHolderClicks {
        //   TextView description;

        public ImageView iconDocumentoFactura;
        public Button UpdateDocumentoFactura;
        public Button DeleteDocumentoFactura;
        public Button CobroDocumentoFactura;
        public Button DivisionDocumentoFactura;
        public Button FacturarDocumentoFactura;

        //      MARIVENT
        public AutoResizeTextView SerieDocumentoFactura;
        public AutoResizeTextView FacturaDocumentoFactura;
        public AutoResizeTextView FechaDocumentoFactura;
        public AutoResizeTextView NombreMesaDocumentoFactura;
        public TextView MesaDocumentoFactura;
        public TextView TfraDocumentoFactura;
        public AutoResizeTextView EstadoDocumentoFactura;
        public AutoResizeTextView EmpleadoDocumentoFactura;
        public AutoResizeTextView CajaDocumentoFactura;
        public AutoResizeTextView TurnoDocumentoFactura;
        public AutoResizeTextView ObsDocumentoFactura;
        public TextView IdDocumentoFactura;
        public AutoResizeTextView LineasDocumentoFactura;
        public TextView ImpbaseDocumentoFactura;
        public TextView ImpivaDocumentoFactura;
        public AutoResizeTextView ImptotalDocumentoFactura;
        public AutoResizeTextView NombreTftDocumentoFactura;
        public TextView ImpcobroDocumentoFactura;

        public IMyDocumentoFacturaViewHolderClicks mListenerDocumentoFactura;

        public VHItem(View itemView,IMyDocumentoFacturaViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerDocumentoFactura = listener;

            //       this.MinCant.setOnClickListener(this);

//         MARIVENT
            this.IdDocumentoFactura = (TextView) itemView.findViewById(R.id.pid);
            this.iconDocumentoFactura = (ImageView) itemView.findViewById(R.id.icon);
            this.EstadoDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.estado);
            this.SerieDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.serie);
            this.FacturaDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.factura);
            this.FechaDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.fecha);
            this.MesaDocumentoFactura = (TextView) itemView.findViewById(R.id.mesa);
            this.TfraDocumentoFactura = (TextView) itemView.findViewById(R.id.tfra);
            this.NombreMesaDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.nombremesa);
            this.EmpleadoDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.empleado);
            this.CajaDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.caja);
            this.TurnoDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.turno);
            this.ObsDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.obs);
            this.LineasDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.lineas);
            this.ImpbaseDocumentoFactura = (TextView) itemView.findViewById(R.id.impbase);
            this.ImpivaDocumentoFactura = (TextView) itemView.findViewById(R.id.impiva);
            this.ImptotalDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.imptotal);
            this.ImpcobroDocumentoFactura = (TextView) itemView.findViewById(R.id.impcobro);
            this.NombreTftDocumentoFactura = (AutoResizeTextView) itemView.findViewById(R.id.nombretft);

            this.UpdateDocumentoFactura = (Button) itemView.findViewById(R.id.btnUpdate);
            this.DeleteDocumentoFactura = (Button) itemView.findViewById(R.id.btnDelete);
            this.CobroDocumentoFactura = (Button) itemView.findViewById(R.id.btnCobro);
            this.DivisionDocumentoFactura = (Button) itemView.findViewById(R.id.btnDivision);
            this.FacturarDocumentoFactura = (Button) itemView.findViewById(R.id.btnFactura);

            this.UpdateDocumentoFactura.setOnClickListener(this);
            this.DeleteDocumentoFactura.setOnClickListener(this);
            this.CobroDocumentoFactura.setOnClickListener(this);
            this.DivisionDocumentoFactura.setOnClickListener(this);
            this.FacturarDocumentoFactura.setOnClickListener(this);

            this.ObsDocumentoFactura.setOnClickListener(this);
            this.NombreMesaDocumentoFactura.setOnClickListener(this);
            this.EmpleadoDocumentoFactura.setOnClickListener(this);
            this.NombreTftDocumentoFactura.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }
        public void bind(DocumentoFactura DocumentoFactura) {
            IdDocumentoFactura.setText(Integer.toString(DocumentoFactura.getDocumentoFacturaId()));
            SerieDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaSerie());
            EstadoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaEstado().trim());
            FacturaDocumentoFactura.setText(Integer.toString(DocumentoFactura.getDocumentoFacturaFactura()));
            FechaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaFecha());
            MesaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaMesa());
            TfraDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaT_fra());
            NombreMesaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaNombre_Mesa());
            EmpleadoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaEmpleado());
            CajaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaCaja());
            TurnoDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaCod_turno());
            ObsDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaObs());
            LineasDocumentoFactura.setText(Integer.toString(DocumentoFactura.getDocumentoFacturaLineas()));
            ImpbaseDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaImp_base());
            ImpivaDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaImp_iva());
            ImptotalDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaImp_total());
            ImpcobroDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaImp_cobro());
            NombreTftDocumentoFactura.setText(DocumentoFactura.getDocumentoFacturaNombre_tft());

            UpdateDocumentoFactura.setText(ActividadPrincipal.getPalabras("Modificar"));
            DeleteDocumentoFactura.setText(ActividadPrincipal.getPalabras("Borrar"));
            CobroDocumentoFactura.setText(ActividadPrincipal.getPalabras("Cobro"));
            DivisionDocumentoFactura.setText(getPalabras("Dividir"));
            FacturarDocumentoFactura.setText(ActividadPrincipal.getPalabras("Facturar"));
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

            if (v instanceof Button){
                Log.i("instance v dentro",v.getClass().getName().toString());
                switch (v.getId()) {
                    case R.id.btnUpdate:
                        mListenerDocumentoFactura.onUpdate(
                                (Button) v,
                                this.iconDocumentoFactura,
                                String.valueOf(this.IdDocumentoFactura.getText()),
                                String.valueOf(this.EstadoDocumentoFactura.getText()),
                                String.valueOf(this.MesaDocumentoFactura.getText()),
                                String.valueOf(this.SerieDocumentoFactura.getText()),
                                String.valueOf(this.FacturaDocumentoFactura.getText()),
                                String.valueOf(this.ImptotalDocumentoFactura.getText()),
                                String.valueOf(this.ObsDocumentoFactura.getText())
                        );
                        break;
                    case R.id.btnDelete:
                        mListenerDocumentoFactura.onDelete(
                                (Button) v,
                                this.iconDocumentoFactura,
                                String.valueOf(this.IdDocumentoFactura.getText()),
                                String.valueOf(this.EstadoDocumentoFactura.getText()),
                                String.valueOf(this.SerieDocumentoFactura.getText()),
                                String.valueOf(this.FacturaDocumentoFactura.getText())
                        );
                        break;
                    case R.id.btnCobro:
                        mListenerDocumentoFactura.onCobro(
                                (Button) v,
                                this.iconDocumentoFactura,
                                String.valueOf(this.IdDocumentoFactura.getText()),
                                String.valueOf(this.EstadoDocumentoFactura.getText()),
                                String.valueOf(this.SerieDocumentoFactura.getText()),
                                String.valueOf(this.FacturaDocumentoFactura.getText()),
                                String.valueOf(this.ImptotalDocumentoFactura.getText()),
                                String.valueOf(this.ObsDocumentoFactura.getText())
                        );
                        break;
                    case R.id.btnDivision:
                        mListenerDocumentoFactura.onDivision(
                                (Button) v,
                                this.iconDocumentoFactura,
                                String.valueOf(this.IdDocumentoFactura.getText()),
                                String.valueOf(this.EstadoDocumentoFactura.getText()),
                                String.valueOf(this.MesaDocumentoFactura.getText()),
                                String.valueOf(this.SerieDocumentoFactura.getText()),
                                String.valueOf(this.FacturaDocumentoFactura.getText()),
                                String.valueOf(this.ImptotalDocumentoFactura.getText()),
                                String.valueOf(this.ObsDocumentoFactura.getText())
                        );
                        break;
                    case R.id.btnFactura:
                        mListenerDocumentoFactura.onFactura(
                                (Button) v,
                                this.iconDocumentoFactura,
                                String.valueOf(this.IdDocumentoFactura.getText()),
                                String.valueOf(this.EstadoDocumentoFactura.getText()),
                                String.valueOf(this.SerieDocumentoFactura.getText()),
                                String.valueOf(this.FacturaDocumentoFactura.getText())
                        );
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro", v.getClass().getName().toString());
                if (v instanceof TextView) {
                    Log.i("instance v dentro", v.getClass().getName().toString());
                    switch (v.getId()) {
                        case R.id.nombremesa:
                            mListenerDocumentoFactura.onPotato(
                                    v,
                                    this.iconDocumentoFactura,
                                    "Mesa",
                                    String.valueOf(this.iconDocumentoFactura.getTag().toString()),
                                    String.valueOf(this.IdDocumentoFactura.getText()),
                                    String.valueOf(this.SerieDocumentoFactura.getText()),
                                    String.valueOf(this.FacturaDocumentoFactura.getText()),
                                    String.valueOf(this.FechaDocumentoFactura.getText()),
                                    String.valueOf(this.MesaDocumentoFactura.getText()),
                                    String.valueOf(this.TfraDocumentoFactura.getText()),
                                    String.valueOf(this.EstadoDocumentoFactura.getText()),
                                    String.valueOf(this.EmpleadoDocumentoFactura.getText()),
                                    String.valueOf(this.CajaDocumentoFactura.getText()),
                                    String.valueOf(this.TurnoDocumentoFactura.getText()),
                                    String.valueOf(this.ObsDocumentoFactura.getText()),
                                    String.valueOf(this.ImpcobroDocumentoFactura.getText()),
                                    String.valueOf(this.LineasDocumentoFactura.getText())
                            );
                            break;
                        case R.id.nombretft:
                            if (String.valueOf(this.EstadoDocumentoFactura.getText()).contains("CLOSE")) {
                                mListenerDocumentoFactura.onPotato(
                                        v,
                                        this.iconDocumentoFactura,
                                        "Tipo Cobro",
                                        String.valueOf(this.iconDocumentoFactura.getTag().toString()),
                                        String.valueOf(this.IdDocumentoFactura.getText()),
                                        String.valueOf(this.SerieDocumentoFactura.getText()),
                                        String.valueOf(this.FacturaDocumentoFactura.getText()),
                                        String.valueOf(this.FechaDocumentoFactura.getText()),
                                        String.valueOf(this.MesaDocumentoFactura.getText()),
                                        String.valueOf(this.TfraDocumentoFactura.getText()),
                                        String.valueOf(this.EstadoDocumentoFactura.getText()),
                                        String.valueOf(this.EmpleadoDocumentoFactura.getText()),
                                        String.valueOf(this.CajaDocumentoFactura.getText()),
                                        String.valueOf(this.TurnoDocumentoFactura.getText()),
                                        String.valueOf(this.ObsDocumentoFactura.getText()),
                                        String.valueOf(this.ImpcobroDocumentoFactura.getText()),
                                        String.valueOf(this.LineasDocumentoFactura.getText())
                                );
                            }
                            break;
                        case R.id.empleado:
                            mListenerDocumentoFactura.onPotato(
                                    v,
                                    this.iconDocumentoFactura,
                                    "Empleado",
                                    String.valueOf(this.iconDocumentoFactura.getTag().toString()),
                                    String.valueOf(this.IdDocumentoFactura.getText()),
                                    String.valueOf(this.SerieDocumentoFactura.getText()),
                                    String.valueOf(this.FacturaDocumentoFactura.getText()),
                                    String.valueOf(this.FechaDocumentoFactura.getText()),
                                    String.valueOf(this.MesaDocumentoFactura.getText()),
                                    String.valueOf(this.TfraDocumentoFactura.getText()),
                                    String.valueOf(this.EstadoDocumentoFactura.getText()),
                                    String.valueOf(this.EmpleadoDocumentoFactura.getText()),
                                    String.valueOf(this.CajaDocumentoFactura.getText()),
                                    String.valueOf(this.TurnoDocumentoFactura.getText()),
                                    String.valueOf(this.ObsDocumentoFactura.getText()),
                                    String.valueOf(this.ImpcobroDocumentoFactura.getText()),
                                    String.valueOf(this.LineasDocumentoFactura.getText())
                            );
                            break;
                        case R.id.obs:
                            mListenerDocumentoFactura.onPotato(
                                    v,
                                    this.iconDocumentoFactura,
                                    "Obs",
                                    String.valueOf(this.iconDocumentoFactura.getTag().toString()),
                                    String.valueOf(this.IdDocumentoFactura.getText()),
                                    String.valueOf(this.SerieDocumentoFactura.getText()),
                                    String.valueOf(this.FacturaDocumentoFactura.getText()),
                                    String.valueOf(this.FechaDocumentoFactura.getText()),
                                    String.valueOf(this.MesaDocumentoFactura.getText()),
                                    String.valueOf(this.TfraDocumentoFactura.getText()),
                                    String.valueOf(this.EstadoDocumentoFactura.getText()),
                                    String.valueOf(this.EmpleadoDocumentoFactura.getText()),
                                    String.valueOf(this.CajaDocumentoFactura.getText()),
                                    String.valueOf(this.TurnoDocumentoFactura.getText()),
                                    String.valueOf(this.ObsDocumentoFactura.getText()),
                                    String.valueOf(this.ImpcobroDocumentoFactura.getText()),
                                    String.valueOf(this.LineasDocumentoFactura.getText())
                            );
                            break;
                    }

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             ImageView imageDocumentoFactura,
                             String campoDocumentoFactura,
                             String iconDocumentoFactura,
                             String idDocumentoFactura,
                             String serieDocumentoFactura,
                             String facturaDocumentoFactura,
                             String fechaDocumentoFactura,
                             String mesaDocumentoFactura,
                             String tfraDocumentoFactura,
                             String estadoDocumentoFactura,
                             String empleadoDocumentoFactura,
                             String cajaDocumentoFactura,
                             String turnoDocumentoFactura,
                             String obsDocumentoFactura,
                             String impcobroDocumentoFactura,
                             String lineasDocumentoFactura) {

        }

        @Override
        public void onUpdate(Button callerButton,
                             ImageView imageDocumentoFactura,
                             String idDocumentoFactura,
                             String estadoDocumentoFactura,
                             String mesaDocumentoFactura,
                             String serieDocumentoFactura,
                             String facturaDocumentoFactura,
                             String totalDocumentoFactura,
                             String obsDocumentoFactura) {

        }

        @Override
        public void onDelete(Button callerButton,
                             ImageView imageDocumentoFactura,
                             String idDocumentoFactura,
                             String estadoDocumentoFactura,
                             String serieDocumentoFactura,
                             String facturaDocumentoFactura) {

        }

        @Override
        public void onCobro (Button callerButton,
                             ImageView imageDocumentoFactura,
                             String idDocumentoFactura,
                             String estadoDocumentoFactura,
                             String serieDocumentoFactura,
                             String facturaDocumentoFactura,
                             String totalDocumentoFactura,
                             String obsDocumentoFactura) {

        }
        @Override
        public void onDivision(Button callerButton,
                               ImageView imageDocumentoFactura,
                               String idDocumentoFactura,
                               String estadoDocumentoFactura,
                               String mesaDocumentoFactura,
                               String serieDocumentoFactura,
                               String facturaDocumentoFactura,
                               String totalDocumentoFactura,
                               String obsDocumentoFactura) {

        }
        @Override
        public void onFactura (Button callerButton,
                               ImageView imageDocumentoFactura,
                               String idDocumentoFactura,
                               String estadoDocumentoFactura,
                               String serieDocumentoFactura,
                               String facturaDocumentoFactura) {

        }

        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerDocumentoFactura;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerDocumentoFactura = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerDocumentoFacturaHeader {
        void onDeleteDocumentoFacturaSelected(int id, String estado, String serie, int factura);
        void onUpdateDocumentoFacturaSelected(int id, String valor, String campo);
        void onUpdateCobroDocumentoFacturaSelected(int id, String serie, String factura, String impcobro);
        void onUpdateLineasDocumentoFacturaSelected(ImageView image, int id, String mesa, String estado, String serie, String factura, String total, String obs);
        void onUpdateDivisionLineasDocumentoFacturaSelected(ImageView image, int id, String mesa, String estado, String serie, String factura, String total, String obs);
        void onCobroDocumentoFacturaSelected(int id, String estado, String serie, String factura, String total, String obs);
        void onFacturarDocumentoFacturaSelected(int id, String estado, String serie, String factura);
    }
    /*para filtro*/
    public void setFilter(List<DocumentoFactura> DocumentoFacturas) {
        mDocumentoFactura = new ArrayList<>();
        mDocumentoFactura.addAll(DocumentoFacturas);
        notifyDataSetChanged();
    }


}

