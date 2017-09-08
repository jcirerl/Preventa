package tpv.cirer.com.marivent.herramientas;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import tpv.cirer.com.marivent.BuildConfig;
import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Articulos;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.iconCarrito;

/**
 * Created by JUAN on 31/01/2017.
 */

public class ArticulosListArrayAdapter  extends ArrayAdapter<Articulos> {

    private final List<Articulos> list;
    private final Activity context;

    static class ViewHolder {
        protected TextView name;
        protected ImageView imagen;
        protected CheckBox checkbox;
        protected TextView cant;
        protected Button add;
        protected Button minus;
        protected Spinner cmbtoolbarplato;
        protected TextView importe;
        protected TextView preu;
    }

    public ArticulosListArrayAdapter(Activity context, List<Articulos> list) {
        super(context, R.layout.activity_articuloscode_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_articuloscode_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.imagen = (ImageView) view.findViewById(R.id.imagen);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            viewHolder.cant = (TextView) view.findViewById(R.id.cant);
            viewHolder.add = (Button) view.findViewById(R.id.btnAdd);
            viewHolder.minus = (Button) view.findViewById(R.id.btnMinus);
            viewHolder.cmbtoolbarplato = (Spinner) view.findViewById(R.id.CmbToolbarPlato);
            viewHolder.preu = (TextView) view.findViewById(R.id.preu);
            viewHolder.importe = (TextView) view.findViewById(R.id.importe);


            view.setTag(viewHolder);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("Checkbox", "Click");
                    switch(list.get(position).getCode()) {
                        case "BUFFET":
                            viewHolder.cant.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.add.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.minus.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.importe.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            break;
                    }
                    if(isChecked){
                        list.get(position).setChecked(true);
                        list.get(position).setImporte(list.get(position).getCant()*list.get(position).getPreu());

                        String space01 = new String(new char[01]).replace('\0', ' ');
                        String myText="";
                        myText = String.format("%1$,.2f", list.get(position).getImporte());
                        Log.i("lMytextImp",Integer.toString(myText.length()));
                        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                        String newText="";
                        for (int ii = 0; ii < (8-myText.length()); ii++) {
                            newText+=space01;
                        }
                        newText +=myText;
                        viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                        /// PONEMOS TOTAL EN CARRITO
                        int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                        View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                        float nSaldo=0;
                        for (int i=0 ; i<list.size(); i++){
                            nSaldo+=list.get(i).getImporte();
                        }

                        TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                        if (textSaldo!=null) {
                            textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                        }
                        // Datos en appbar
                        //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                        int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                        TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                        txtSaldo.setText(String.format("%1$,.2f", nSaldo)+" "+Filtro.getSimbolo());
                        txtSaldo.setTextSize(30);

                        Utils.setBadgeCount(context, iconCarrito, 0);
                        ////////////////////////////////////////////////////////////////////////
                    }else{
                        list.get(position).setChecked(false);

                        list.get(position).setImporte(0);
                        list.get(position).setCant(1);

                        String space01 = new String(new char[01]).replace('\0', ' ');
                        String myText="";
                        myText = String.format("%1$,.2f", list.get(position).getImporte());
                        Log.i("lMytextImp",Integer.toString(myText.length()));
                        myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                        myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                        String newText="";
                        for (int ii = 0; ii < (8-myText.length()); ii++) {
                            newText+=space01;
                        }
                        newText +=myText;
                        viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());
                        viewHolder.cant.setText(String.valueOf(list.get(position).getCant()));

                        /// PONEMOS TOTAL EN CARRITO
                        int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                        View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                        float nSaldo=0;
                        for (int i=0 ; i<list.size(); i++){
                            nSaldo+=list.get(i).getImporte();
                        }

                        TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                        if (textSaldo!=null) {
                            textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                        }
                        // Datos en appbar
                        //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                        int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                        TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                        txtSaldo.setText(String.format("%1$,.2f", nSaldo)+" "+Filtro.getSimbolo());
                        txtSaldo.setTextSize(30);

                        Utils.setBadgeCount(context, iconCarrito, 0);
                        ////////////////////////////////////////////////////////////////////////
                    }
                }
            });
            viewHolder.add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    viewHolder.cant.setText(String.valueOf(Integer.parseInt(viewHolder.cant.getText().toString()) + 1));
                    list.get(position).setCant(list.get(position).getCant()+1);

                    list.get(position).setImporte(list.get(position).getCant()*list.get(position).getPreu());

                    String space01 = new String(new char[01]).replace('\0', ' ');
                    String myText="";
                    myText = String.format("%1$,.2f", list.get(position).getImporte());
                    Log.i("lMytextImp",Integer.toString(myText.length()));
                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                    String newText="";
                    for (int ii = 0; ii < (8-myText.length()); ii++) {
                        newText+=space01;
                    }
                    newText +=myText;
                    viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                    /// PONEMOS TOTAL EN CARRITO
                    int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                    View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                    float nSaldo=0;
                    for (int i=0 ; i<list.size(); i++){
                        nSaldo+=list.get(i).getImporte();
                    }

                    TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                    if (textSaldo!=null) {
                        textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                    }
                    // Datos en appbar
                    //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                    int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                    TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                    txtSaldo.setText(String.format("%1$,.2f", nSaldo)+" "+Filtro.getSimbolo());
                    txtSaldo.setTextSize(30);

                    Utils.setBadgeCount(context, iconCarrito, 0);
                    ////////////////////////////////////////////////////////////////////////
                    /// PONEMOS TOTAL EN TOTALBUFFET
                    ////////////////////////////////////////////////////////////////////////

                }
            });
            viewHolder.minus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    viewHolder.cant.setText(String.valueOf(Integer.parseInt(viewHolder.cant.getText().toString()) - 1));
                    list.get(position).setCant(list.get(position).getCant()-1);

                    list.get(position).setImporte(list.get(position).getCant()*list.get(position).getPreu());

                    String space01 = new String(new char[01]).replace('\0', ' ');
                    String myText="";
                    myText = String.format("%1$,.2f", list.get(position).getImporte());
                    Log.i("lMytextImp",Integer.toString(myText.length()));
                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                    String newText="";
                    for (int ii = 0; ii < (8-myText.length()); ii++) {
                        newText+=space01;
                    }
                    newText +=myText;
                    viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                    /// PONEMOS TOTAL EN CARRITO
                    int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                    View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                    float nSaldo=0;
                    for (int i=0 ; i<list.size(); i++){
                        nSaldo+=list.get(i).getImporte();
                    }

                    TextView textSaldo = (TextView) rootView.findViewById(textViewID);

                    if (textSaldo!=null) {
                        textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                    }
                    // Datos en appbar
                    //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                    int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                    TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                    txtSaldo.setText(String.format("%1$,.2f", nSaldo)+" "+Filtro.getSimbolo());
                    txtSaldo.setTextSize(30);

                    Utils.setBadgeCount(context, iconCarrito, 0);
                    ////////////////////////////////////////////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////
                    /// PONEMOS TOTAL EN TOTALBUFFET
                    ////////////////////////////////////////////////////////////////////////

                }
            });

/*            viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.i("Checkbox","Click");
                    list.get(position).setCant(1);
                }
            });
*/
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
/*        Picasso.with(context)
                .load(list.get(position).getUrlimagen())
                .resize(60, 60)
                .centerCrop()
                .into(holder.imagen);
*/        Glide.with(context)
                .load(list.get(position).getUrlimagen())
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .override(60,60)
                .into(holder.imagen);

        switch(list.get(position).getCode()){
            case "BUFFET":
                holder.cant.setText(String.valueOf(list.get(position).getCant()));
                holder.cant.setTextColor(Color.RED);
                holder.cant.setTextSize(30);

                holder.importe.setTextColor(Color.RED);
                holder.importe.setTextSize(30);

                String space01 = new String(new char[01]).replace('\0', ' ');
                String myText="";
                myText = String.format("%1$,.2f", list.get(position).getImporte());
                Log.i("lMytextImp",Integer.toString(myText.length()));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newText="";
                for (int ii = 0; ii < (8-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                holder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                if (holder.checkbox.isChecked()) {
                    holder.cant.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.importe.setVisibility(View.VISIBLE);
                } else{
                    holder.cant.setVisibility(View.GONE);
                    holder.add.setVisibility(View.GONE);
                    holder.minus.setVisibility(View.GONE);
                    holder.importe.setVisibility(View.GONE);
                }
                holder.name.setText(list.get(position).getName());
///                holder.imagen.setImageDrawable(list.get(position).getImagen());
                holder.checkbox.setText(list.get(position).getName());
                holder.name.setVisibility(View.GONE);
                holder.cmbtoolbarplato.setVisibility(View.GONE);
                holder.preu.setVisibility(View.GONE);
                break;
            case "ARTICULO":
                holder.name.setText(list.get(position).getName());
///                holder.imagen.setImageDrawable(list.get(position).getImagen());
                holder.checkbox.setVisibility(View.GONE);
                holder.cant.setVisibility(View.GONE);
                holder.add.setVisibility(View.GONE);
                holder.minus.setVisibility(View.GONE);
                holder.cmbtoolbarplato.setVisibility(View.GONE);
                holder.preu.setVisibility(View.GONE);
                holder.importe.setVisibility(View.GONE);
                break;
            case "GRUPO":
                holder.checkbox.setChecked(true);
                list.get(position).setChecked(true);
                holder.name.setText(list.get(position).getName());
///                holder.imagen.setImageDrawable(list.get(position).getImagen());
                holder.cant.setVisibility(View.GONE);
                holder.add.setVisibility(View.GONE);
                holder.minus.setVisibility(View.GONE);
                holder.preu.setVisibility(View.GONE);
                holder.importe.setVisibility(View.GONE);

                holder.cmbtoolbarplato.setAdapter(list.get(position).getAdapter_plato());
                holder.cmbtoolbarplato.setSelection(list.get(position).getPosicionplato());

                break;
        }

        return view;
    }
    private void findAllEdittexts(ViewGroup viewGroup) {

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            Log.i("Buffet:",view.getTag()+" "+String.valueOf(view.getId()));
            if (view instanceof ViewGroup)
                findAllEdittexts((ViewGroup) view);
            else if (view instanceof TextView) {
 //               Log.i("Buffet:",String.valueOf(view.getId()));
            }
        }

    }
}
