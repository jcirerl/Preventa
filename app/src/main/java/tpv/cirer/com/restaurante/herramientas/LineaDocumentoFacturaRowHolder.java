package tpv.cirer.com.restaurante.herramientas;

/**
 * Created by JUAN on 20/09/2016.
 */


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.modelo.LineaDocumentoFactura;

//import com.cirer.aguas.principal.R;

public class LineaDocumentoFacturaRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView iconLineaDocumentoFactura;
    public Button AddCant;
    public Button MinCant;
    public TextView ArticuloLineaDocumentoFactura;
    public TextView NombreLineaDocumentoFactura;
    public TextView CantLineaDocumentoFactura;
    public TextView PreuLineaDocumentoFactura;
    public TextView ImporteLineaDocumentoFactura;
    public TextView TivaLineaDocumentoFactura;
    public TextView IdLineaDocumentoFactura;


    public IMyViewHolderClicks mListener;



    public LineaDocumentoFacturaRowHolder(View view, IMyViewHolderClicks listener) {
        super(view);
        mListener = listener;
        this.IdLineaDocumentoFactura = (TextView) view.findViewById(R.id.pid);
        this.iconLineaDocumentoFactura = (ImageView) view.findViewById(R.id.icon);
        this.CantLineaDocumentoFactura = (TextView) view.findViewById(R.id.cant);
        this.ArticuloLineaDocumentoFactura = (TextView) view.findViewById(R.id.articulo);
        this.NombreLineaDocumentoFactura = (TextView) view.findViewById(R.id.nombre);
        this.PreuLineaDocumentoFactura = (TextView) view.findViewById(R.id.preu);
        this.ImporteLineaDocumentoFactura = (TextView) view.findViewById(R.id.importe);
        this.TivaLineaDocumentoFactura = (TextView) view.findViewById(R.id.tipoiva);
        this.AddCant = (Button) view.findViewById(R.id.btnAdd);
        this.MinCant = (Button) view.findViewById(R.id.btnMinus);

        this.AddCant.setOnClickListener(this);
        this.MinCant.setOnClickListener(this);

        view.setOnClickListener(this);

    }
    public void bind(LineaDocumentoFactura LineaDocumentoFactura) {
        IdLineaDocumentoFactura.setText(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaId()));
        CantLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaCant());
        ArticuloLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaArticulo());
        NombreLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaNombre());
        PreuLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaPreu());
        ImporteLineaDocumentoFactura.setText(LineaDocumentoFactura.getLineaDocumentoFacturaImporte());
        TivaLineaDocumentoFactura.setText(Integer.toString(LineaDocumentoFactura.getLineaDocumentoFacturaTiva_id()));
    }
    @Override
    public void onClick(View v) {
        Log.i("instance v",v.getClass().getName().toString());

        if (v instanceof Button){
            Log.i("instance v dentro",v.getClass().getName().toString());
            switch (v.getId()) {
                case R.id.btnAdd:
                    mListener.onAdd(
                            (Button) v,
                            String.valueOf(this.IdLineaDocumentoFactura.getText())
                    );
                    break;
                case R.id.btnMinus:
                    mListener.onMinus(
                             (Button) v,
                             String.valueOf(this.IdLineaDocumentoFactura.getText()),
                             String.valueOf(this.CantLineaDocumentoFactura.getText())
                    );
                    break;
            }
//            mListener.onTomato((ImageView)v);

        } else {
            Log.i("instance v dentro",v.getClass().getName().toString());
            mListener.onPotato(
                    v,
                    String.valueOf(this.IdLineaDocumentoFactura.getText()),
                    String.valueOf(this.NombreLineaDocumentoFactura.getText()),
                    String.valueOf(this.ArticuloLineaDocumentoFactura.getText()),
                    String.valueOf(this.iconLineaDocumentoFactura.getTag().toString()),
                    String.valueOf(this.CantLineaDocumentoFactura.getText()),
                    String.valueOf(this.PreuLineaDocumentoFactura.getText()),
                    String.valueOf(this.ImporteLineaDocumentoFactura.getText()),
                    String.valueOf(this.TivaLineaDocumentoFactura.getText())
            );
 //           mListener.onPotato(v);
        }
    }


    public static interface IMyViewHolderClicks {
        public void onPotato(View caller, String idLineaDocumentoFactura, String nombreLineaDocumentoFactura, String articuloLineaDocumentoFactura, String iconLineaDocumentoFactura, String cantLineaDocumentoFactura, String preuLineaDocumentoFactura, String importeLineaDocumentoFactura, String tiva_idLineaDocumentoFactura);
        public void onAdd(Button callerButton, String idLineaDocumentoFactura);
        public void onMinus(Button callerButton, String idLineaDocumentoFactura,String cantLineaDocumentoFactura);

        public void onTomato(ImageView callerImage);
    //    public void onPotato(View caller);

    }
 }