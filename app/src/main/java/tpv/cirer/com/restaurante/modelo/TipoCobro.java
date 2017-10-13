package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 10/11/2016.
 */

public class TipoCobro {
    private String TipoCobroT_fra;
    private String TipoCobroNombre_tft;
    private int TipoCobroCopia_print;
    private float TipoCobroPrecio;


    public TipoCobro(){}

    public TipoCobro(String t_fra, String nombre, int copia_print, float precio){
        this.TipoCobroT_fra = t_fra;
        this.TipoCobroNombre_tft = nombre;
        this.TipoCobroCopia_print = copia_print;
        this.TipoCobroPrecio=precio;
    }
    public void setTipoCobroCopia_print(int tipocobrocopia_print){
        this.TipoCobroCopia_print = tipocobrocopia_print;
    }

    public int getTipoCobroCopia_print(){
        return this.TipoCobroCopia_print;
    }

    public void setTipoCobroT_fra(String tipocobrot_fra ){
        this.TipoCobroT_fra = tipocobrot_fra;
    }

    public void setTipoCobroNombre_tft(String tipocobronombre_tft){
        this.TipoCobroNombre_tft = tipocobronombre_tft;
    }

    public String getTipoCobroT_fra(){
        return this.TipoCobroT_fra;
    }

    public String getTipoCobroNombre_tft(){
        return this.TipoCobroNombre_tft;
    }
    public float getTipoCobroPrecio() {
        return TipoCobroPrecio;
    }
    public void setTipoCobroPrecio(float tipocobroPrecio) {
        this.TipoCobroPrecio = tipocobroPrecio;
    }

}
