package tpv.cirer.com.restaurante.modelo;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * Created by JUAN on 06/09/2017.
 */

public class ArticulosNew {
    private String codigo;
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
    private int dependientetipoplatomaestro;
    private int sw_tipo_are;
    private int sw_suma_precio_individual;

    public ArticulosNew() {
    }

    public ArticulosNew(String name, String code, CheckBox checkbox, int cant, float preu, float importe, Button add, Button minus, String urlimagen){
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
    public ArticulosNew(String codigo, 
                        String name, 
                        String code, 
                        CheckBox checkbox, 
                        Spinner cmbtoolbar, 
                        ArrayAdapter<String> adapter_plato, 
                        int posicionplato, 
                        String urlimagen, 
                        int dependietetipoplatomaestro, 
                        float preu, 
                        int sw_tipo_are,
                        int sw_suma_precio_individual  ){
        this.codigo = codigo;
        this.name = name;
        this.code = code;
//        this.imagen = imagen;
        this.checkbox = checkbox;
        this.cmbtoolbar = cmbtoolbar;
        this.adapter_plato = adapter_plato;
        this.posicionplato = posicionplato;
        this.urlimagen = urlimagen;
        this.dependientetipoplatomaestro=dependietetipoplatomaestro;
        this.preu = preu;
        this.sw_tipo_are = sw_tipo_are;
        this.sw_suma_precio_individual = sw_suma_precio_individual;
    }
    public ArticulosNew(String name, String code, String urlimagen){
        this.name = name;
        this.code = code;
//        this.imagen = imagen;
        this.urlimagen = urlimagen;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrlimagen() {
        return urlimagen;
    }
    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
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
    public void setPosicionplato(int posicionplato) {
        this.posicionplato = posicionplato;
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
    public int getDependientetipoplatomaestro() {
        return dependientetipoplatomaestro;
    }
    public void setDependientetipoplatomaestro(int Dependientetipoplatomaestro) {
        this.dependientetipoplatomaestro = Dependientetipoplatomaestro;
    }
    public int getSw_tipo_are() {
        return sw_tipo_are;
    }
    public void setSw_tipo_are(int idsw_tipo_are) {
        this.sw_tipo_are = idsw_tipo_are;
    }

    public int getSw_suma_precio_individual() {
        return sw_suma_precio_individual;
    }
    public void setSw_suma_precio_individual(int idsw_suma_precio_individual) {
        this.sw_suma_precio_individual = idsw_suma_precio_individual;
    }

}
