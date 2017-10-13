package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 25/10/2016.
 */

public class Moneda {
    private String MonedaMoneda;
    private String MonedaNombre;
    private String MonedaSimbolo;

    public Moneda(){}

    public Moneda(String moneda, String nombre){
        this.MonedaMoneda = moneda;
        this.MonedaNombre = nombre;
    }

    public void setMonedaMoneda(String monedaMoneda ){
        this.MonedaMoneda = monedaMoneda;
    }

    public void setMonedaNombre(String monedaoNombre){
        this.MonedaNombre = monedaoNombre;
    }

    public String getMonedaMoneda(){
        return this.MonedaMoneda;
    }
    public void setMonedaSimbolo(String monedaSimbolo ){
        this.MonedaSimbolo = monedaSimbolo;
    }

    public String getMonedaSimbolo(){
        return this.MonedaSimbolo;
    }
    public String getMonedaNombre(){
        return this.MonedaNombre;
    }

}
