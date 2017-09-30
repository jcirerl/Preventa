package tpv.cirer.com.marivent.modelo;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * Created by JUAN on 31/01/2017.
 */

public class Articulos {
    private String name;
    private String code;
    private Drawable imagen;
    private CheckBox checkbox;
    private int cant;
    private Button add;
    private Button minus;
    private Spinner cmbtoolbar;
    private boolean checked;
    private ArrayAdapter<String> adapter_plato;
    private int posicionplato;
    private String urlimagen;
    private float preu;
    private float importe;
    private int id;
    private String serie;
    private String factura;

    public Articulos(String name, String code, CheckBox checkbox, int cant, float preu, float importe, Button add, Button minus, String urlimagen){
        this.name = name;
        this.code = code;
//        this.imagen = imagen;
        this.checkbox = checkbox;
        this.cant = cant;
        this.preu = preu;
        this.importe = importe;
        this.add = add;
        this.minus = minus;
        this.urlimagen = urlimagen;
    }
    public Articulos(String name, String code, CheckBox checkbox,  float preu, int id, String serie, String factura ){
        this.name = name;
        this.code = code;
        this.checkbox = checkbox;
        this.preu = preu;
        this.id = id;
        this.serie = serie;
        this.factura = factura;
    }
    public Articulos(String name, String code, CheckBox checkbox, Spinner cmbtoolbar, ArrayAdapter<String> adapter_plato, int posicionplato, String urlimagen ){
        this.name = name;
        this.code = code;
//        this.imagen = imagen;
        this.checkbox = checkbox;
        this.cmbtoolbar = cmbtoolbar;
        this.adapter_plato = adapter_plato;
        this.posicionplato = posicionplato;
        this.urlimagen = urlimagen;
    }
    public Articulos(String name, String code, String urlimagen){
        this.name = name;
        this.code = code;
//        this.imagen = imagen;
        this.urlimagen = urlimagen;
    }
    public String getName() {
        return name;
    }
    public String getUrlimagen() {
        return urlimagen;
    }
    public Drawable getImagen() {
        return imagen;
    }
    public String getCode() {
        return code;
    }
    public int getCant() {
        return cant;
    }
    public void setCant(int cant) {
        this.cant = cant;
    }
    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public ArrayAdapter<String> getAdapter_plato() {
        return adapter_plato;
    }
    public int getPosicionplato() {
        return posicionplato;
    }

    public float getPreu() {
        return preu;
    }
    public void setPreu(float preu) {
        this.preu = preu;
    }
    public float getImporte() {
        return importe;
    }
    public void setImporte(float importe) {
        this.importe = importe;
    }

    public int getId() {
        return id;
    }
    public String getSerie() {
        return serie;
    }
    public String getFactura() {
        return factura;
    }


}

