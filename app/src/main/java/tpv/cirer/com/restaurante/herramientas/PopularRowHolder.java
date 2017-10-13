package tpv.cirer.com.restaurante.herramientas;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.modelo.Popular;

/**
 * Created by JUAN on 18/11/2016.
 */

public class PopularRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView articulo;
    public TextView tiva_id;
    public TextView nombre;
    public TextView precio;
    public ImageView imagen;

    public IMyViewHolderClicks mListener;

    public PopularRowHolder(View view, IMyViewHolderClicks listener) {
        super(view);
        mListener = listener;
        this.articulo = (TextView) view.findViewById(R.id.articulo);
        this.tiva_id = (TextView) view.findViewById(R.id.tiva_id);
        this.nombre = (TextView) view.findViewById(R.id.nombre_comida);
        this.precio = (TextView) view.findViewById(R.id.precio_comida);
        this.imagen = (ImageView) view.findViewById(R.id.miniatura_comida);

        this.imagen.setOnClickListener(this);
        view.setOnClickListener(this);

    }
    public void bind(Popular Popular) {
        String myText;
        myText =String.format("%1$-50s",Popular.getNombre());
        nombre.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
        precio.setText(Html.fromHtml(String.format("%1$,.2f", Double.valueOf(Popular.getPrecio()))+" "+Filtro.getSimbolo()));
        articulo.setText(Popular.getArticulo());
        tiva_id.setText(Integer.toString(Popular.getTiva_id()));
//        precio.setText(Float.toString(Popular.getPrecio()));
//        nombre.setText(Popular.getNombre());
    }
    @Override
    public void onClick(View v) {
        mListener.onPotato(
                v,
                String.valueOf(this.articulo.getText()),
                String.valueOf(this.nombre.getText()),
                String.valueOf(this.precio.getText()),
                String.valueOf(this.tiva_id.getText())
        );
    }

    public static interface IMyViewHolderClicks {
        void onPotato(View caller, String articuloPopular, String nombrePopular, String precioPopular, String tivaPopular);
        void onTomato(ImageView callerImage);
    }

}
