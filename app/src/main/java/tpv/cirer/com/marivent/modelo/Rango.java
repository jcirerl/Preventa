package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 02/11/2016.
 */

public class Rango {
    private String RangoRango;
    private String RangoNombre_Rangos;
    private int RangoApertura;
    private int RangoId;
    public Rango(){}

    public Rango(String Rango, String nombre){
        this.RangoRango = Rango;
        this.RangoNombre_Rangos = nombre;
    }
    public int getRangoId() {
        return RangoId;
    }

    public void setRangoId(int rangoId) {
        this.RangoId = rangoId;
    }

     public String getRangoNombre_Rangos() {
        return RangoNombre_Rangos;
    }

    public void setRangoNombre_Rangos(String mesaNombre_Rangos) {
        this.RangoNombre_Rangos = mesaNombre_Rangos;
    }

    public String getRangoRango() {
        return RangoRango;
    }

    public void setRangoRango(String RangoRango) {
        this.RangoRango = RangoRango;
    }

}
