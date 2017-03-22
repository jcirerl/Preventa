package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 25/10/2016.
 */

public class Simbolo {
    private String MonedaSimbolo;

    public Simbolo(){}

    public Simbolo(String simbolo){
        this.MonedaSimbolo = simbolo;
    }

    public void setMonedaSimbolo(String monedaSimbolo ){
        this.MonedaSimbolo = monedaSimbolo;
    }

    public String getMonedaSimbolo(){
        return this.MonedaSimbolo;
    }


}
