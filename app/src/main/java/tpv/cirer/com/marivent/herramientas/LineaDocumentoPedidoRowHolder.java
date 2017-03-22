package tpv.cirer.com.marivent.herramientas;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.LineaDocumentoPedido;

/**
 * Created by JUAN on 05/10/2016.
 */

public class LineaDocumentoPedidoRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public TextView headerLineaDocumentoPedido;

    public ImageView iconLineaDocumentoPedido;

    public Button AddCantLineaDocumentoPedido;
    public Button MinCantLineaDocumentoPedido;
    public TextView ArticuloLineaDocumentoPedido;
    public TextView NombreLineaDocumentoPedido;
    public TextView CantLineaDocumentoPedido;
    public TextView ObsLineaDocumentoPedido;
    public TextView IdLineaDocumentoPedido;


    public IMyLineaDocumentoPedidoViewHolderClicks mListenerLineaDocumentoPedido;

    public LineaDocumentoPedidoRowHolder(View view, int type_item, IMyLineaDocumentoPedidoViewHolderClicks listener) {
        super(view);
        mListenerLineaDocumentoPedido = listener;
//        if (type_item == TYPE_ITEM) {
            this.IdLineaDocumentoPedido = (TextView) view.findViewById(R.id.pid);
            this.iconLineaDocumentoPedido = (ImageView) view.findViewById(R.id.icon);
            this.CantLineaDocumentoPedido = (TextView) view.findViewById(R.id.cant);
            this.ArticuloLineaDocumentoPedido = (TextView) view.findViewById(R.id.articulo);
            this.NombreLineaDocumentoPedido = (TextView) view.findViewById(R.id.nombre);
            this.ObsLineaDocumentoPedido = (TextView) view.findViewById(R.id.obs);


            this.AddCantLineaDocumentoPedido = (Button) view.findViewById(R.id.btnAdd);
            this.MinCantLineaDocumentoPedido = (Button) view.findViewById(R.id.btnMinus);

            this.AddCantLineaDocumentoPedido.setOnClickListener(this);
            this.MinCantLineaDocumentoPedido.setOnClickListener(this);

            //       this.MinCant.setOnClickListener(this);

            view.setOnClickListener(this);

/*        } else if (type_item == TYPE_HEADER) {
            this.headerLineaDocumentoPedido = (TextView) view.findViewById(R.id.header_texto);

        }
*/    }

    public void bind(LineaDocumentoPedido LineaDocumentoPedido) {
        IdLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoId()));
        CantLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoCant());
        ArticuloLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoArticulo());
        NombreLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoNombre());
        ObsLineaDocumentoPedido.setText(LineaDocumentoPedido.getLineaDocumentoPedidoObs());
        IdLineaDocumentoPedido.setText(Integer.toString(LineaDocumentoPedido.getLineaDocumentoPedidoId()));
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
                            String.valueOf(this.IdLineaDocumentoPedido.getText())
                    );
                    break;
                case R.id.btnMinus:
                    mListenerLineaDocumentoPedido.onMinus(
                            (Button) v,
                            String.valueOf(this.IdLineaDocumentoPedido.getText()),
                            String.valueOf(this.CantLineaDocumentoPedido.getText())
                    );
                    break;
            }
//            mListener.onTomato((ImageView)v);

        } else {
            Log.i("instance v dentro",v.getClass().getName().toString());
            mListenerLineaDocumentoPedido.onPotato(
                    v,
                    String.valueOf(this.IdLineaDocumentoPedido.getText()),
                    String.valueOf(this.NombreLineaDocumentoPedido.getText()),
                    String.valueOf(this.ArticuloLineaDocumentoPedido.getText()),
                    String.valueOf(this.iconLineaDocumentoPedido.getTag().toString()),
                    String.valueOf(this.CantLineaDocumentoPedido.getText()),
                    String.valueOf(this.ObsLineaDocumentoPedido.getText())
            );
            //           mListener.onPotato(v);
        }
    }


    public static interface IMyLineaDocumentoPedidoViewHolderClicks {
        void onPotato(View caller,
                      String idLineaDocumentoPedido,
                      String nombreLineaDocumentoPedido,
                      String articuloLineaDocumentoPedido,
                      String iconLineaDocumentoPedido,
                      String cantLineaDocumentoPedido,
                      String obsLineaDocumentoPedido);
        void onAdd(Button callerButton,
                   String idLineaDocumentoPedido);
        void onMinus(Button callerButton,
                     String idLineaDocumentoPedido,
                     String cantLineaDocumentoPedido);

        void onTomato(ImageView callerImage);
        //    public void onPotato(View caller);

    }
}