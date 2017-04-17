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
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.IMyMessageViewHolderClicks;
import tpv.cirer.com.marivent.modelo.Message;

/**
 * Created by JUAN on 31/03/2017.
 */

public class AdaptadorMessageHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //    String[] data;
    private List<Message> mMessage;
    private Context mContextMessage;
    private OnHeadlineSelectedListenerMessageHeader mCallbackMessage;

    /*   public AdaptadorMessageHeader(String[] data) {
           this.data = data;
       }
    */
    public AdaptadorMessageHeader(Context context, List<Message> Message) {
        this.mMessage = Message;
        this.mContextMessage = context;
        try {
            this.mCallbackMessage = ((OnHeadlineSelectedListenerMessageHeader) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_message_sony, parent, false);
            return new VHItem(v, new IMyMessageViewHolderClicks() {
                //    public void onPotato(View caller) { Log.d("VEGETABLES", "Poh-tah-tos"); };
                public void onPotato(View caller,
                                     String idMessage,
                                     String mesaMessage,
                                     String comensalesMessage,
                                     String creadoMessage,
                                     String activoMessage,
                                     String cajaMessage) {
                    final int idMESSAGE = Integer.parseInt(idMessage);
                    final int activoMESSAGE = Integer.parseInt(activoMessage);
                    final String creadoMESSAGE = creadoMessage;
                    final String cajaMESSAGE = cajaMessage;
                    final String mesaMESSAGE = mesaMessage;
                    final String comensalesMESSAGE = comensalesMessage;

                    AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext());
                    dialog.setTitle(ActividadPrincipal.getPalabras("Modificar")+" "+ActividadPrincipal.getPalabras("Mensaje")+": "+idMESSAGE);
                    dialog.setMessage(ActividadPrincipal.getPalabras("Datos")+" "+ActividadPrincipal.getPalabras("Mensaje")+": "+
                            "\n"+ActividadPrincipal.getPalabras("Id")+": "+ idMESSAGE +
                            "\n"+ActividadPrincipal.getPalabras("Comensales")+".: " + comensalesMESSAGE +
                            "\n"+ActividadPrincipal.getPalabras("Mesa")+".: " + mesaMESSAGE +
                            "\n"+ActividadPrincipal.getPalabras("Activo")+": " + activoMESSAGE +
                            "\n"+ActividadPrincipal.getPalabras("Caja")+": " + cajaMESSAGE +
                            "\n"+ActividadPrincipal.getPalabras("Fecha")+": " + creadoMESSAGE
                    );
                    dialog.setIcon(R.drawable.mark_as_read);
                    dialog.setPositiveButton(ActividadPrincipal.getPalabras("Consultar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.setNegativeButton(ActividadPrincipal.getPalabras("Borrar"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mCallbackMessage.onDeleteMessageSelected(idMESSAGE, activoMESSAGE);
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                };
                public void onDelete(Button callerButton,
                                     String idMessage,
                                     String activoMessage ) {
                    final int idMESSAGE = Integer.parseInt(idMessage);
                    final int activoMESSAGE = Integer.parseInt(activoMessage);

                    try {
                        mCallbackMessage.onDeleteMessageSelected(idMESSAGE, activoMESSAGE);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }



                public void onUpdate(Button callerButton,
                                     String idMessage,
                                     String activoMessage,
                                     String comensalesMessage,
                                     String mesaMessage ) {
                    Log.d("UPDATE BUTTON", "+");
                    final int idMESSAGE = Integer.parseInt(idMessage);
                    final int activoMESSAGE = Integer.parseInt(activoMessage);
                    final String mesaMESSAGE = mesaMessage;
                    comensalesMessage = comensalesMessage.replace(Html.fromHtml("&nbsp;"), "");
                    final int comensalesMESSAGE = Integer.parseInt(comensalesMessage);

                    try {
                        mCallbackMessage.onUpdateMessageSelected(idMESSAGE,activoMESSAGE,comensalesMESSAGE,mesaMESSAGE);
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }

                public void onTomato(ImageView callerImage) {
                    Log.d("VEGETABLES", "To-m8-tohs");
                }

            });
        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_messages, parent, false);
            return new AdaptadorMessageHeader.VHHeader(v);
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

//        if (MessageRowHolder == vhitem) {
            Log.i("recview LPD vhitem", Integer.toString(mMessage.size()));
            try {
            /*para filtro*/
                Message model = mMessage.get(position-1);

                ((VHItem) holder).IdMessage.setText(Html.fromHtml(Integer.toString(model.getMessageId())));
                ((VHItem) holder).ActivoMessage.setText(Html.fromHtml(Integer.toString(model.getMessageActivo())));
                ((VHItem) holder).MesaMessage.setText(Html.fromHtml(model.getMessageMesa()));
                ((VHItem) holder).UsuarioMessage.setText(Html.fromHtml(model.getMessageUsuario())+StringUtils.repeat(space01, 6));


                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try{
                    String StringRecogido = model.getMessageCreado();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).CreadoMessage.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")+"&nbsp;&nbsp;").toString());

                } catch (Exception e) {
                    e.getMessage();
                }
                try{
                    String StringRecogido = model.getMessageUpdated();
                    Date datehora = sdf1.parse(StringRecogido);

                    //System.out.println("Fecha input : "+datehora);
                    myText = sdf2.format(datehora);
                    ((VHItem) holder).UpdatedMessage.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")+"&nbsp;&nbsp;").toString());

                } catch (Exception e) {
                    e.getMessage();
                }
                ((VHItem) holder).ComensalesMessage.setText(Html.fromHtml(String.format("%02d",model.getMessageComensales())+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));

                ((VHItem) holder).CajaMessage.setText(model.getMessageCaja().trim());
                if (model.getMessageActivo()==0) {
                    ((VHItem) holder).UpdateMessage.setVisibility(View.GONE);
                    ((VHItem) holder).DeleteMessage.setVisibility(View.GONE);
                }else{
                    ((VHItem) holder).UpdateMessage.setVisibility(View.VISIBLE);
                    ((VHItem) holder).DeleteMessage.setVisibility(View.VISIBLE);
                }

                ((VHItem) holder).MesaMessage.setTextColor(Color.BLUE);


                //        MessageRowHolder.NombreMessage.setTextSize(16);
                //        MessageRowHolder.ArticuloMessage.setTextSize(16);

            } catch (Exception e) {
                Log.i("recview MESSAGE dentro", Integer.toString(mMessage.size()));
                // do something
            }

        } else if (holder instanceof VHHeader) {

            //cast holder to VHHeader and set data for header.
            myText = ActividadPrincipal.getPalabras("Fecha")+StringUtils.repeat(space01, 13)+
                     ActividadPrincipal.getPalabras("Modificado")+StringUtils.repeat(space01, 13)+
                     ActividadPrincipal.getPalabras("Usuario")+StringUtils.repeat(space01, 3)+
                     ActividadPrincipal.getPalabras("Np")+StringUtils.repeat(space01, 2)+
                     ActividadPrincipal.getPalabras("Mesa")+StringUtils.repeat(space01, 6);
            //    Html.fromHtml(myText.replace(" ", "&nbsp;")).toString()
            //cast holder to VHHeader and set data for header_facturas.
            ((VHHeader) holder).headerMessage.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
            ((VHHeader) holder).headerMessage.setTextColor(Color.WHITE);

        }
    }

    /*    @Override
        public int getItemCount() {
            return data.length + 1;
        }
    */    @Override
    public int getItemCount() {
        return (null != mMessage ? mMessage.size()+1 : 1);
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
            IMyMessageViewHolderClicks {
        //   TextView description;

        public Button UpdateMessage;
        public Button DeleteMessage;

        public TextView CreadoMessage;
        public TextView UpdatedMessage;
        public TextView UsuarioMessage;
        public TextView ComensalesMessage;
        public TextView MesaMessage;
        public TextView ActivoMessage;
        public TextView CajaMessage;
        public TextView IdMessage;

        public IMyMessageViewHolderClicks mListenerMessage;

        public VHItem(View itemView,IMyMessageViewHolderClicks listener) {
            super(itemView);
            //        description=(TextView)itemView.findViewById(R.id.decription);
            mListenerMessage = listener;

            //       this.MinCant.setOnClickListener(this);

            this.IdMessage = (TextView) itemView.findViewById(R.id.pid);
            this.ActivoMessage = (TextView) itemView.findViewById(R.id.activo);
            this.CreadoMessage = (TextView) itemView.findViewById(R.id.creado);
            this.UpdatedMessage = (TextView) itemView.findViewById(R.id.updated);
            this.UsuarioMessage = (TextView) itemView.findViewById(R.id.usuario);

            this.ComensalesMessage = (TextView) itemView.findViewById(R.id.comensales);
            this.MesaMessage = (TextView) itemView.findViewById(R.id.mesa);
            this.CajaMessage = (TextView) itemView.findViewById(R.id.caja);

            this.UpdateMessage = (Button) itemView.findViewById(R.id.btnUpdate);
            this.DeleteMessage = (Button) itemView.findViewById(R.id.btnDelete);

            this.UpdateMessage.setOnClickListener(this);
            this.DeleteMessage.setOnClickListener(this);


            itemView.setOnClickListener(this);
        }
        public void bind(Message Message) {
            IdMessage.setText(Integer.toString(Message.getMessageId()));
            ActivoMessage.setText(Integer.toOctalString(Message.getMessageActivo()));
            CreadoMessage.setText(Message.getMessageCreado());
            UpdatedMessage.setText(Message.getMessageUpdated());
            UsuarioMessage.setText(Message.getMessageUsuario());

            ComensalesMessage.setText(Integer.toString(Message.getMessageComensales()));
            MesaMessage.setText(Message.getMessageMesa());
            CajaMessage.setText(Message.getMessageCaja());
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
                        mListenerMessage.onUpdate(
                                (Button) v,
                                String.valueOf(this.IdMessage.getText()),
                                String.valueOf(this.ActivoMessage.getText()),
                                String.valueOf(this.ComensalesMessage.getText()),
                                String.valueOf(this.MesaMessage.getText()));
                        break;
                    case R.id.btnDelete:
                        mListenerMessage.onDelete(
                                (Button) v,
                                String.valueOf(this.IdMessage.getText()),
                                String.valueOf(this.ActivoMessage.getText()));
                        break;
                }
//            mListener.onTomato((ImageView)v);

            } else {
                Log.i("instance v dentro", v.getClass().getName().toString());
                if (v instanceof RelativeLayout) {
                    Log.i("instance v dentro", v.getClass().getName().toString());
                        mListenerMessage.onPotato(
                            v,
                            String.valueOf(this.IdMessage.getText()),
                            String.valueOf(this.MesaMessage.getText()),
                            String.valueOf(this.ComensalesMessage.getText()),
                            String.valueOf(this.CreadoMessage.getText()),
                            String.valueOf(this.ActivoMessage.getText()),
                            String.valueOf(this.CajaMessage.getText())
                        );

                    //           mListener.onPotato(v);
                }
            }
        }
        @Override
        public void onPotato(View caller,
                             String idMessage,
                             String mesaMessage,
                             String comensalesMessage,
                             String creadoMessage,
                             String activoMessage,
                             String cajaMessage) {

        }

        @Override
        public void onUpdate(Button callerButton,
                             String idMessage,
                             String activoMessage,
                             String comensalesMessage,
                             String mesaMessage) {

        }

        @Override
        public void onDelete(Button callerButton,
                             String idMessage,
                             String activoMessage) {

        }


        @Override
        public void onTomato(ImageView callerImage) {

        }
        //    public void onPotato(View caller);


    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headerMessage;

        public VHHeader(View itemView) {
            super(itemView);
            this.headerMessage = (TextView) itemView.findViewById(R.id.header_texto);
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerMessageHeader {
        void onDeleteMessageSelected(int id, int activo);
        void onUpdateMessageSelected(int id, int activo, int comensales, String mesa);
    }
    /*para filtro*/
    public void setFilter(List<Message> Messages) {
        mMessage = new ArrayList<>();
        mMessage.addAll(Messages);
        notifyDataSetChanged();
    }


}
