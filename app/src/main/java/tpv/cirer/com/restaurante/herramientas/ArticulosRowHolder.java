package tpv.cirer.com.restaurante.herramientas;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.modelo.ArticulosNew;

/**
 * Created by JUAN on 06/09/2017.
 */

public class ArticulosRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                                                                           AdapterView.OnItemSelectedListener {
    public TextView name;
    public ImageView imagen;
    public CheckBox checkbox;
    public TextView cant;
    public Button add;
    public Button minus;
    public Spinner cmbtoolbarplato;
    public TextView importe;
    public TextView preu;
    public TextView codigo;
    public Button swtipoare;

    public ArticulosRowHolder.IMyArticulosViewHolderClicks mListener;

    public ArticulosRowHolder(View view, ArticulosRowHolder.IMyArticulosViewHolderClicks listener) {
        super(view);
        mListener = listener;
        this.name = (TextView) view.findViewById(R.id.name);
        this.imagen = (ImageView) view.findViewById(R.id.imagen);
        this.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        this.cant = (TextView) view.findViewById(R.id.cant);
        this.add = (Button) view.findViewById(R.id.btnAdd);
        this.minus = (Button) view.findViewById(R.id.btnMinus);
        this.cmbtoolbarplato = (Spinner) view.findViewById(R.id.CmbToolbarPlato);
        this.preu = (TextView) view.findViewById(R.id.preu);
        this.importe = (TextView) view.findViewById(R.id.importe);
        this.codigo = (TextView) view.findViewById(R.id.codigo);
        this.swtipoare = (Button) view.findViewById(R.id.btnswtipoare);

        this.checkbox.setOnClickListener(this);

        this.cmbtoolbarplato.setOnItemSelectedListener(this);
        this.imagen.setOnClickListener(this);
        this.swtipoare.setOnClickListener(this);
        view.setOnClickListener(this);

    }
    public void bind(ArticulosNew ArticulosNew) {
        this.codigo.setText(ArticulosNew.getCodigo());
        switch(ArticulosNew.getCode()){
            case "BUFFET":
                cant.setText(String.valueOf(ArticulosNew.getCant()));
                cant.setTextColor(Color.RED);
                cant.setTextSize(30);

                importe.setTextColor(Color.RED);
                importe.setTextSize(30);

                String space01 = new String(new char[01]).replace('\0', ' ');
                String myText="";
                myText = String.format("%1$,.2f", ArticulosNew.getImporte());
                Log.i("lMytextImp",Integer.toString(myText.length()));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newText="";
                for (int ii = 0; ii < (8-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                if (checkbox.isChecked()) {
                    cant.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);
                    importe.setVisibility(View.VISIBLE);
                } else{
                    cant.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                    minus.setVisibility(View.GONE);
                    importe.setVisibility(View.GONE);
                }
                name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                checkbox.setText(ArticulosNew.getName());
                name.setVisibility(View.GONE);
                cmbtoolbarplato.setVisibility(View.GONE);
                preu.setVisibility(View.GONE);
                break;
            case "ARTICULO":
                name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                checkbox.setVisibility(View.GONE);
                cant.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                minus.setVisibility(View.GONE);
                cmbtoolbarplato.setVisibility(View.GONE);
                preu.setVisibility(View.GONE);
                importe.setVisibility(View.GONE);
                break;
            case "GRUPO":
                checkbox.setChecked(true);
                ArticulosNew.setChecked(true);
                name.setText(ArticulosNew.getName());
///                imagen.setImageDrawable(ArticulosNew.getImagen());
                cant.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                minus.setVisibility(View.GONE);
                preu.setVisibility(View.GONE);
                importe.setVisibility(View.GONE);
                if(ArticulosNew.getDependientetipoplatomaestro()==0) {
                    cmbtoolbarplato.setVisibility(View.VISIBLE);
                    cmbtoolbarplato.setAdapter(ArticulosNew.getAdapter_plato());
                    cmbtoolbarplato.setSelection(ArticulosNew.getPosicionplato());
                }else{
                    cmbtoolbarplato.setVisibility(View.GONE);
                }
                if(ArticulosNew.getSw_tipo_are()==0) {
                    swtipoare.setVisibility(View.GONE);
                }else{
                    swtipoare.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        Log.i("Item Click instance v",v.getClass().getName().toString());
        if (v instanceof CheckBox){
            if (this.checkbox.isChecked()){
                mListener.onPotato(
                        v,
                        this.imagen,
                        "CHECKBOX",
                        String.valueOf(this.codigo.getText().toString()),
                        String.valueOf("1"),
                        String.valueOf(getAdapterPosition())
                );
            }else{
                mListener.onPotato(
                        v,
                        this.imagen,
                        "CHECKBOX",
                        String.valueOf(this.codigo.getText().toString()),
                        String.valueOf("0"),
                        String.valueOf(getAdapterPosition())
                );
            }
        }
        if (v instanceof ImageView) {
            mListener.onPotato(
                    v,
                    this.imagen,
                    "IMAGEN",
                    String.valueOf(this.codigo.getText().toString()),
                    String.valueOf(this.name.getText()),
                    String.valueOf(getAdapterPosition())
            );
        }
        if (v instanceof Button) {
            mListener.onPotato(
                    v,
                    this.imagen,
                    "BUTTON",
                    String.valueOf(this.codigo.getText().toString()),
                    String.valueOf(this.name.getText()),
                    String.valueOf(getAdapterPosition())
            );
        }

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Spinner spinner = (Spinner) parent;
//        if (spinner.getId() == R.id.CmbToolbarPlato) {
            Log.i("Item selected view",spinner.getClass().getName().toString());
        mListener.onPotato(
                view,
                this.imagen,
                "SPINNER",
                String.valueOf(this.codigo.getText().toString()),
                String.valueOf(position),
                String.valueOf(getAdapterPosition())
        );

//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public static interface IMyArticulosViewHolderClicks {
        void onPotato(View caller, ImageView imageArticulo, String action, String codigoArticulo ,String nombreArticulo, String postionArticulo);
    }
}