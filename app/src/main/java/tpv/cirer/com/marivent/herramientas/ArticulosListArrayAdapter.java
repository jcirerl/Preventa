package tpv.cirer.com.marivent.herramientas;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import tpv.cirer.com.marivent.BuildConfig;
import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Articulos;
import tpv.cirer.com.marivent.ui.FirmaActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.iconCarrito;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.larticulobuffet;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.tftList;

/**
 * Created by JUAN on 31/01/2017.
 */

public class ArticulosListArrayAdapter  extends ArrayAdapter<Articulos> {

    private final List<Articulos> list;
    private final Activity context;
    private OnHeadlineSelectedListenerArticulos mCallback;

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
        try {
            this.mCallback = ((OnHeadlineSelectedListenerArticulos) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    public boolean onKey(View view, int keyCode, KeyEvent event) {

        //TextView responseText = (TextView) findViewById(R.id.responseText);
        EditText myEditText = (EditText) view;
        String value = myEditText.getText().toString();
        int pos = 0;
        /////////////////////////////////////////
        if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                int id = view.getId();
                switch (view.getId()) {
                    case R.id.TotalCobro:
                }
                return true;
            }
        }
        return false; // pass on to other listeners.

    }
    /**
     * To hide a keypad.
     *
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        if ((context == null) || (view == null)) {
            return;
        }
        InputMethodManager mgr =
                (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void showKeyboard(Context context, View view) {
        if ((context == null) || (view == null)) {
            return;
        }
        InputMethodManager mgr = (InputMethodManager) context.
                                getSystemService(INPUT_METHOD_SERVICE);
        mgr.showSoftInputFromInputMethod(view.getWindowToken(), 0);
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
            viewHolder.preu = (EditText) view.findViewById(R.id.preu);
            viewHolder.importe = (TextView) view.findViewById(R.id.importe);

            view.setTag(viewHolder);
            view.setFocusable(true);

            //Fill EditText with the value you have in data source
            viewHolder.preu.setText(String.valueOf(list.get(position).getPreu()));
            viewHolder.preu.setId(position);
            viewHolder.preu.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            viewHolder.preu.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    EditText myEditText = (EditText) v;
                    String value = myEditText.getText().toString();
                    int pos = 0;

                    Log.i("Edittext","setOnKeyListener");
                    if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                            keyCode == EditorInfo.IME_ACTION_DONE ||
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        value = value.replace(Html.fromHtml("&nbsp;"), "");
                        value = value.replace(Filtro.getSimbolo(), "");
                        value = value.replace(".", "");
                        value = value.replace(",", ".");
                        if(value.trim().length()==0){
                            value="0.00";
                        }
                        myEditText.setText(String.format("%1$,.2f", Double.valueOf(value)));

                        // Ponerse al final del edittext
                        pos = myEditText.getText().length();
                        myEditText.setSelection(pos);
                        myEditText.setTextColor(Color.RED);
                        myEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        KeyListener keyListener = DigitsKeyListener.getInstance("-,1234567890");
                        myEditText.setKeyListener(keyListener);

                        float nSaldo = 0;
//                        String value = Caption.getText().toString();
                        value = value.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                        value = value.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                        if (value.matches("")){// || !value.matches("-?\\d+(\\,\\d+)?")) {
                            Toast.makeText(context, getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Precio"), Toast.LENGTH_SHORT).show();
                            //            this.btnGuardar.setEnabled(false);
                            myEditText.setText("0");
                            String cValor = myEditText.getText().toString();
                            cValor = cValor.replace(Html.fromHtml("&nbsp;"), "");
                            cValor = cValor.replace(".", "");
                            cValor = cValor.replace(",", ".");

                            list.get(position).setPreu(Float.parseFloat(cValor));
                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText = "";
                            myText = String.format("%1$,.2f", list.get(position).getPreu());
                            Log.i("lMytextImp", Integer.toString(myText.length()));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            String newText = "";
                            for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                newText += space01;
                            }
                            newText += myText;
                            viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());
                            nSaldo = 0;
                            hideKeyboard(context,myEditText);

                        } else {
                            String cValor = myEditText.getText().toString();
                            cValor = cValor.replace(Html.fromHtml("&nbsp;"), "");
                            cValor = cValor.replace(".", "");
                            cValor = cValor.replace(",", ".");
                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText = "";
                            String newText = "";

                            switch(list.get(position).getCode()) {
                                case "BUFFET":

                                    list.get(position).setPreu(Float.parseFloat(cValor));
                                    list.get(position).setImporte(list.get(position).getCant() * list.get(position).getPreu());

                                    larticulobuffet.get(position).setArticuloPrecio(Float.parseFloat(cValor));

                                    myText = String.format("%1$,.2f", list.get(position).getPreu());
                                    Log.i("lMytextImp", Integer.toString(myText.length()));
                                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                    newText = "";
                                    for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                        newText += space01;
                                    }
                                    newText += myText;
                                    viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                                    myText = String.format("%1$,.2f", list.get(position).getImporte());
                                    Log.i("lMytextImp", Integer.toString(myText.length()));
                                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                    newText = "";
                                    for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                        newText += space01;
                                    }
                                    newText += myText;
                                    viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString() + " " + Filtro.getSimbolo());

                                    /// PONEMOS TOTAL EN CARRITO
                                    int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                                    View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                                    nSaldo = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        nSaldo += list.get(i).getImporte();
                                    }

                                    TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                                    if (textSaldo != null) {
                                        textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                                    }
                                    // Datos en appbar
                                    //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                                    int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                                    TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                                    txtSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                                    txtSaldo.setTextSize(30);

                                    Utils.setBadgeCount(context, iconCarrito, 0);
                                    ////////////////////////////////////////////////////////////////////////
                                    /// PONEMOS TOTAL EN TOTALBUFFET
                                    ////////////////////////////////////////////////////////////////////////
                                    try {
                                        mCallback.onArticulosBuffetNewChecked(String.valueOf(nSaldo));
                                    } catch (ClassCastException exception) {
                                        // do something
                                    }
                                    ////////////////////////////////////////////////////////////////////////
                                    break;
                                case "COBRO":

                                    list.get(position).setPreu(Float.parseFloat(cValor));
                                    list.get(position).setImporte(list.get(position).getPreu());

                                    tftList.get(position).setTipoCobroPrecio(Float.parseFloat(cValor));

                                    myText = String.format("%1$,.2f", list.get(position).getPreu());
                                    Log.i("lMytextImp", Integer.toString(myText.length()));
                                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                    newText = "";
                                    for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                        newText += space01;
                                    }
                                    newText += myText;
                                    viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                                    nSaldo = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        nSaldo += list.get(i).getImporte();
                                    }
                                    ////////////////////////////////////////////////////////////////////////
                                    /// PONEMOS TOTAL EN TOTALCOBRO
                                    ////////////////////////////////////////////////////////////////////////
                                    try {
                                        mCallback.onArticulosCobroNewChecked(String.valueOf(nSaldo));
                                    } catch (ClassCastException exception) {
                                        // do something
                                    }
                                    ////////////////////////////////////////////////////////////////////////

                                    break;
                            }
                            // the user is done typing.
                            hideKeyboard(context,myEditText);
                        }

                       return true;
                    }else {
                        return false;
                    }
                }
            });
/*            viewHolder.preu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final EditText Caption = (EditText) view;
                    Log.i("Edittext","setOnCLickListener");
                    String valor = Caption.getText().toString();
                    int pos = 0;
                    valor = valor.replace(Filtro.getSimbolo(), ""); //Quitamos simbolo moneda
                    valor = valor.replace(Html.fromHtml("&nbsp;"), ""); //Quitamos espacion
                    valor = valor.replace(".", "");
                    valor = valor.replace(",", ".");

                    double xValor = Double.valueOf(valor);
                    Caption.setText(String.format("%1$,.2f", xValor));

                    // Ponerse al final del edittext
                    pos = Caption.getText().length();
                    Caption.setSelection(pos);

                    Caption.setTextColor(Color.RED);
                    Caption.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    KeyListener keyListener = DigitsKeyListener.getInstance("-,1234567890");
                    Caption.setKeyListener(keyListener);

                }
            });
*/            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("Checkbox", "Click");
                    switch(list.get(position).getCode()) {
                        case "BUFFET":
                            viewHolder.preu.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.cant.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.add.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.minus.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            viewHolder.importe.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            if(isChecked){

                                list.get(position).setChecked(true);
                                list.get(position).setImporte(list.get(position).getCant()*list.get(position).getPreu());

                                String space01 = new String(new char[01]).replace('\0', ' ');
                                String myText="";
                                myText = String.format("%1$,.2f", list.get(position).getPreu());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                String newText="";
                                for (int ii = 0; ii < (8-myText.length()); ii++) {
                                    newText+=space01;
                                }
                                newText +=myText;
                                viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                                myText = String.format("%1$,.2f", list.get(position).getImporte());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                newText="";
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
                                try {
                                    mCallback.onArticulosBuffetNewChecked( String.valueOf(nSaldo));
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                                ////////////////////////////////////////////////////////////////////////
                            }else{
                                list.get(position).setChecked(false);

                                list.get(position).setImporte(0);
                                list.get(position).setCant(1);

                                String space01 = new String(new char[01]).replace('\0', ' ');
                                String myText="";
                                myText = String.format("%1$,.2f", list.get(position).getPreu());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                String newText="";
                                for (int ii = 0; ii < (8-myText.length()); ii++) {
                                    newText+=space01;
                                }
                                newText +=myText;
                                viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                                myText = String.format("%1$,.2f", list.get(position).getImporte());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                newText="";
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
                                ////////////////////////////////////////////////////////////////////////
                                /// PONEMOS TOTAL EN TOTALBUFFET
                                ////////////////////////////////////////////////////////////////////////
                                try {
                                    mCallback.onArticulosBuffetNewChecked( String.valueOf(nSaldo));
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                            }
                            break;
                        case "COBRO":
                            viewHolder.preu.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                            if(isChecked){
                                if (list.get(position).getName().trim().equals("CREDITO")){
                                    viewHolder.add.setVisibility(View.VISIBLE);
                                    viewHolder.add.setCompoundDrawablesWithIntrinsicBounds(R.drawable.signature, 0, 0, 0);
//                                    viewHolder.add.setBackgroundResource(R.drawable.signature);
                                }else{
                                    viewHolder.add.setVisibility(View.GONE);
                                }
                                list.get(position).setChecked(true);
//                                list.get(position).setImporte(list.get(position).getPreu());
                                list.get(position).setImporte(Filtro.getTotalcobro());

                                String space01 = new String(new char[01]).replace('\0', ' ');
                                String myText="";
                                String newText="";

                                myText = String.format("%1$,.2f", list.get(position).getImporte());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                newText="";
                                for (int ii = 0; ii < (8-myText.length()); ii++) {
                                    newText+=space01;
                                }
                                newText +=myText;
                                viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                                float nSaldo=0;
                                for (int i=0 ; i<list.size(); i++){
                                    nSaldo+=list.get(i).getImporte();
                                }

                                ////////////////////////////////////////////////////////////////////////
                                /// PONEMOS TOTAL EN TOTALCOBRO
                                ////////////////////////////////////////////////////////////////////////
                                try {
                                    mCallback.onArticulosCobroNewChecked( String.valueOf(nSaldo));
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                                ////////////////////////////////////////////////////////////////////////
                            }else{
                                viewHolder.add.setVisibility(View.GONE);

                                list.get(position).setChecked(false);

                                list.get(position).setImporte(0);

                                String space01 = new String(new char[01]).replace('\0', ' ');
                                String myText="";
                                String newText="";

                                myText = String.format("%1$,.2f", list.get(position).getImporte());
                                Log.i("lMytextImp",Integer.toString(myText.length()));
                                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                newText="";
                                for (int ii = 0; ii < (8-myText.length()); ii++) {
                                    newText+=space01;
                                }
                                newText +=myText;
                                viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo());

                                float nSaldo=0;
                                for (int i=0 ; i<list.size(); i++){
                                    nSaldo+=list.get(i).getImporte();
                                }

                                ////////////////////////////////////////////////////////////////////////
                                /// PONEMOS TOTAL EN TOTALBUFFET
                                ////////////////////////////////////////////////////////////////////////
                                try {
                                    mCallback.onArticulosCobroNewChecked( String.valueOf(nSaldo));
                                } catch (ClassCastException exception) {
                                    // do something
                                }
                            }
                            break;
                    }

                }
            });
            viewHolder.add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    switch(list.get(position).getCode()) {
                        case "BUFFET":

                            viewHolder.cant.setText(String.valueOf(Integer.parseInt(viewHolder.cant.getText().toString()) + 1));
                            list.get(position).setCant(list.get(position).getCant() + 1);

                            list.get(position).setImporte(list.get(position).getCant() * list.get(position).getPreu());

                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText = "";
                            myText = String.format("%1$,.2f", list.get(position).getPreu());
                            Log.i("lMytextImp", Integer.toString(myText.length()));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            String newText = "";
                            for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                newText += space01;
                            }
                            newText += myText;
                            viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                            myText = String.format("%1$,.2f", list.get(position).getImporte());
                            Log.i("lMytextImp", Integer.toString(myText.length()));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            newText = "";
                            for (int ii = 0; ii < (8 - myText.length()); ii++) {
                                newText += space01;
                            }
                            newText += myText;
                            viewHolder.importe.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString() + " " + Filtro.getSimbolo());

                            /// PONEMOS TOTAL EN CARRITO
                            int textViewID = context.getResources().getIdentifier("texto_total_carrito", "id", BuildConfig.APPLICATION_ID);
                            View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
                            float nSaldo = 0;
                            for (int i = 0; i < list.size(); i++) {
                                nSaldo += list.get(i).getImporte();
                            }

                            TextView textSaldo = (TextView) rootView.findViewById(textViewID);
                            if (textSaldo != null) {
                                textSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                            }
                            // Datos en appbar
                            //                         int layoutID = getResources().getIdentifier("action_view_total", "layout", getPackageName());
                            int txtViewID = context.getResources().getIdentifier("total_carrito", "id", BuildConfig.APPLICATION_ID);
                            TextView txtSaldo = (TextView) rootView.findViewById(txtViewID);
                            txtSaldo.setText(String.format("%1$,.2f", nSaldo) + " " + Filtro.getSimbolo());
                            txtSaldo.setTextSize(30);

                            Utils.setBadgeCount(context, iconCarrito, 0);
                            ////////////////////////////////////////////////////////////////////////
                            /// PONEMOS TOTAL EN TOTALBUFFET
                            ////////////////////////////////////////////////////////////////////////
                            try {
                                mCallback.onArticulosBuffetNewChecked(String.valueOf(nSaldo));
                            } catch (ClassCastException exception) {
                                // do something
                            }
                            break;
                        case "COBRO":
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                            dialog.setTitle(getPalabras("Factura")+": ");
                            dialog.setMessage(getPalabras("Tipo Cobro")+": " + list.get(position).getName() +
                                    "\n"+getPalabras("Total Cobro")+": " + list.get(position).getPreu()
                            );
                            dialog.setIcon(R.drawable.mark_as_read);
                            dialog.setPositiveButton(getPalabras("Registra Firma"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        // 1. create an intent pass class name or intnet action name
                                        Intent intent = new Intent(context,FirmaActivity.class);

                                        // 2. put ID, SERIE, FACTURA in intent
                                        intent.putExtra("Id",  String.valueOf(list.get(position).getId()));
                                        intent.putExtra("Serie",list.get(position).getSerie());
                                        intent.putExtra("Factura",list.get(position).getFactura());

                                        // 3. start the activity
                                        context.startActivityForResult(intent, 1);
                                    } catch (ClassCastException exception) {
                                        // do something
                                    }
                                    dialog.cancel();
                                }
                            });
                            dialog.setNegativeButton(getPalabras("Salir"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                    break;
                    }

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
                    myText = String.format("%1$,.2f", list.get(position).getPreu());
                    Log.i("lMytextImp",Integer.toString(myText.length()));
                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                    String newText="";
                    for (int ii = 0; ii < (8-myText.length()); ii++) {
                        newText+=space01;
                    }
                    newText +=myText;
                    viewHolder.preu.setText(Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString());

                    myText = String.format("%1$,.2f", list.get(position).getImporte());
                    Log.i("lMytextImp",Integer.toString(myText.length()));
                    myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                    myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                    newText="";
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
                    try {
                        mCallback.onArticulosBuffetNewChecked( String.valueOf(nSaldo));
                    } catch (ClassCastException exception) {
                        // do something
                    }

                }
            });



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

                holder.preu.setTextColor(Color.RED);
                holder.preu.setTextSize(30);

                holder.importe.setTextColor(Color.RED);
                holder.importe.setTextSize(30);

                if (holder.checkbox.isChecked()) {
                    holder.cant.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.minus.setVisibility(View.VISIBLE);
                    holder.importe.setVisibility(View.VISIBLE);
                    holder.preu.setVisibility(View.VISIBLE);
                } else{
                    holder.cant.setVisibility(View.GONE);
                    holder.add.setVisibility(View.GONE);
                    holder.minus.setVisibility(View.GONE);
                    holder.importe.setVisibility(View.GONE);
                    holder.preu.setVisibility(View.GONE);
                }
                holder.name.setText(list.get(position).getName());
///                holder.imagen.setImageDrawable(list.get(position).getImagen());
                holder.checkbox.setText(list.get(position).getName());
                holder.name.setVisibility(View.GONE);
                holder.cmbtoolbarplato.setVisibility(View.GONE);
                break;
            case "COBRO":
                holder.preu.setTextColor(Color.RED);
                holder.preu.setTextSize(30);

                if (holder.checkbox.isChecked()) {
                    holder.preu.setVisibility(View.VISIBLE);
                    if (list.get(position).getName().trim().equals("CREDITO")){
                        holder.add.setVisibility(View.VISIBLE);
                    }else{
                        holder.add.setVisibility(View.GONE);
                    }
                } else{
                    holder.preu.setVisibility(View.GONE);
                    holder.add.setVisibility(View.GONE);
                }
                holder.name.setText(list.get(position).getName());
///                holder.imagen.setImageDrawable(list.get(position).getImagen());
                holder.checkbox.setText(list.get(position).getName());
                holder.name.setVisibility(View.GONE);
                holder.cmbtoolbarplato.setVisibility(View.GONE);
                holder.cant.setVisibility(View.GONE);
                holder.minus.setVisibility(View.GONE);
                holder.importe.setVisibility(View.GONE);
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

    ///////////////////////////////////////////
// La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListenerArticulos {
        void onArticulosBuffetNewChecked(String saldo);
        void onArticulosCobroNewChecked(String saldo);
    }

}
