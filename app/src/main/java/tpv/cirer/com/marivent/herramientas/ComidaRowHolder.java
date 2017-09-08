package tpv.cirer.com.marivent.herramientas;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Comida;

/**
 * Created by JUAN on 21/09/2016.
 */

public class ComidaRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView articulo;
    public TextView tiva_id;
    public TextView individual;
    public TextView nombre;
    public TextView precio;
    public ImageView imagen;
    public TextView urlimagen;

    public ComidaRowHolder.IMyViewHolderClicks mListener;

    public ComidaRowHolder(View view, ComidaRowHolder.IMyViewHolderClicks listener) {
        super(view);
        mListener = listener;
        this.articulo = (TextView) view.findViewById(R.id.articulo);
        this.urlimagen = (TextView) view.findViewById(R.id.urlimagen);
        this.tiva_id = (TextView) view.findViewById(R.id.tiva_id);
        this.nombre = (TextView) view.findViewById(R.id.nombre_comida);
        this.precio = (TextView) view.findViewById(R.id.precio_comida);
        this.individual = (TextView) view.findViewById(R.id.individual);
        this.imagen = (ImageView) view.findViewById(R.id.miniatura_comida);

        this.imagen.setOnClickListener(this);
        view.setOnClickListener(this);

    }
    public void bind(Comida Comida) {
        String myText;
        myText =String.format("%1$-50s",Comida.getNombre());
        nombre.setText(Html.fromHtml(myText.replace(" ", "&nbsp;")).toString());
        precio.setText(Html.fromHtml(String.format("%1$,.2f", Double.valueOf(Comida.getPrecio()))+" "+Filtro.getSimbolo()));
        articulo.setText(Comida.getArticulo());
        urlimagen.setText(Comida.getUrlimagen());
        tiva_id.setText(Integer.toString(Comida.getTiva_id()));
        individual.setText(Integer.toString(Comida.getIndividual()));

//        precio.setText(Float.toString(Comida.getPrecio()));
//        nombre.setText(Comida.getNombre());
    }
    @Override
    public void onClick(View v) {
        mListener.onPotato(
                v,
                this.imagen,
                String.valueOf(this.articulo.getText()),
                String.valueOf(this.nombre.getText()),
                String.valueOf(this.precio.getText()),
                String.valueOf(this.tiva_id.getText()),
                String.valueOf(this.individual.getText()),
                String.valueOf(this.urlimagen.getText())
        );
    }

    public static interface IMyViewHolderClicks {
        void onPotato(View caller, ImageView imagenComida, String articuloComida, String nombreComida, String precioComida, String tivaComida, String individualComida, String urlimagen);
        void onTomato(ImageView callerImage);
    }
}