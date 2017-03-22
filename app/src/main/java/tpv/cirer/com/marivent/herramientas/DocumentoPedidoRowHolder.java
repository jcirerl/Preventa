package tpv.cirer.com.marivent.herramientas;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.DocumentoPedido;

/**
 * Created by JUAN on 26/09/2016.
 */

public class DocumentoPedidoRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public TextView headerDocumentoPedido;

    public ImageView iconDocumentoPedido;
    public Button UpdateDocumentoPedido;
    public Button DeleteDocumentoPedido;
    public Button SendDocumentoPedido;
    public Button InvoiceDocumentoPedido;

    public TextView PedidoDocumentoPedido;
    public TextView MesaDocumentoPedido;
    public TextView EstadoDocumentoPedido;
    public TextView EmpleadoDocumentoPedido;
    public TextView CajaDocumentoPedido;
    public TextView TurnoDocumentoPedido;
    public TextView ObsDocumentoPedido;
    public TextView IdDocumentoPedido;
    public TextView LineasDocumentoPedido;


    public IMyDocumentoPedidoViewHolderClicks mListenerDocumentoPedido;

    public DocumentoPedidoRowHolder(View view, int type_item, IMyDocumentoPedidoViewHolderClicks listener) {
        super(view);
        mListenerDocumentoPedido = listener;
        if (type_item == TYPE_ITEM) {

            this.IdDocumentoPedido = (TextView) view.findViewById(R.id.pid);
            this.iconDocumentoPedido = (ImageView) view.findViewById(R.id.icon);
            this.EstadoDocumentoPedido = (TextView) view.findViewById(R.id.estado);
            this.PedidoDocumentoPedido = (TextView) view.findViewById(R.id.pedido);
            this.MesaDocumentoPedido = (TextView) view.findViewById(R.id.mesa);
            this.EmpleadoDocumentoPedido = (TextView) view.findViewById(R.id.empleado);
            this.CajaDocumentoPedido = (TextView) view.findViewById(R.id.caja);
            this.TurnoDocumentoPedido = (TextView) view.findViewById(R.id.turno);
            this.ObsDocumentoPedido = (TextView) view.findViewById(R.id.obs);
            this.LineasDocumentoPedido = (TextView) view.findViewById(R.id.lineas);

            this.UpdateDocumentoPedido = (Button) view.findViewById(R.id.btnUpdate);
            this.DeleteDocumentoPedido = (Button) view.findViewById(R.id.btnDelete);
            this.SendDocumentoPedido = (Button) view.findViewById(R.id.btnSend);
            this.InvoiceDocumentoPedido = (Button) view.findViewById(R.id.btnInvoice);

            this.UpdateDocumentoPedido.setOnClickListener(this);
            this.DeleteDocumentoPedido.setOnClickListener(this);
            this.SendDocumentoPedido.setOnClickListener(this);
            this.InvoiceDocumentoPedido.setOnClickListener(this);

            //       this.MinCant.setOnClickListener(this);

            view.setOnClickListener(this);

        } else if (type_item == TYPE_HEADER) {
            this.headerDocumentoPedido = (TextView) view.findViewById(R.id.header_texto);

        }
    }
    public void bind(DocumentoPedido DocumentoPedido) {
        IdDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoId()));
        EstadoDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoEstado());
        PedidoDocumentoPedido.setText(Integer.toString(DocumentoPedido.getDocumentoPedidoPedido()));
        MesaDocumentoPedido.setText(DocumentoPedido.getDocumentoPedidoMesa());
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
                            String.valueOf(this.PedidoDocumentoPedido.getText())
                            );
                    break;
                case R.id.btnDelete:
                    mListenerDocumentoPedido.onDelete(
                            (Button) v,
                            String.valueOf(this.IdDocumentoPedido.getText()),
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
                            String.valueOf(this.IdDocumentoPedido.getText())
                    );
                    break;
            }
//            mListener.onTomato((ImageView)v);

        } else {
            Log.i("instance v dentro",v.getClass().getName().toString());
            mListenerDocumentoPedido.onPotato(
                    v,
                    String.valueOf(this.iconDocumentoPedido.getTag().toString()),
                    String.valueOf(this.IdDocumentoPedido.getText()),
                    String.valueOf(this.PedidoDocumentoPedido.getText()),
                    String.valueOf(this.MesaDocumentoPedido.getText()),
                    String.valueOf(this.EstadoDocumentoPedido.getText()),
                    String.valueOf(this.EmpleadoDocumentoPedido.getText()),
                    String.valueOf(this.CajaDocumentoPedido.getText()),
                    String.valueOf(this.TurnoDocumentoPedido.getText()),
                    String.valueOf(this.ObsDocumentoPedido.getText()),
                    String.valueOf(this.LineasDocumentoPedido.getText())
            );
            //           mListener.onPotato(v);
        }
    }


    public static interface IMyDocumentoPedidoViewHolderClicks {
        public void onPotato(View caller,
                             String iconDocumentoPedido,
                             String idDocumentoPedido,
                             String pedidoDocumentoPedido,
                             String mesaDocumentoPedido,
                             String estadoDocumentoPedido,
                             String empleadoDocumentoPedido,
                             String cajaDocumentoPedido,
                             String turnoDocumentoPedido,
                             String obsDocumentoPedido,
                             String lineasDocumentoPedido);
        public void onUpdate(Button callerButton,
                             String pedidoDocumentoPedido
                             );
        public void onDelete(Button callerButton,
                             String idDocumentoPedido,
                             String pedidoDocumentoPedido
        );
        public void onInvoice(Button callerButton, String idDocumentoPedido);

        public void onTomato(ImageView callerImage);
        //    public void onPotato(View caller);

    }
}