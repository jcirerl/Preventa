package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 16/10/2016.
 */

public class Caja {
    private String CajaCaja;
    private String CajaNombre_Caja;
    private int CajaApertura;
    private String CajaFecha_Apertura;
    private String CajaMax_Caja;
    private String CajaUrlimagen;
    private int CajaId;
    public Caja(){}

    public Caja(String Caja, String nombre){
        this.CajaCaja = Caja;
        this.CajaNombre_Caja = nombre;
    }
    public int getCajaId() {
        return CajaId;
    }

    public void setCajaId(int cajaId) {
        this.CajaId = cajaId;
    }

    public int getCajaApertura() {
        return CajaApertura;
    }

    public void setCajaApertura(int cajaApertura) {
        this.CajaApertura = cajaApertura;
    }

    public String getCajaNombre_Caja() {
        return CajaNombre_Caja;
    }

    public void setCajaNombre_Caja(String cajaNombre_Caja) {
        this.CajaNombre_Caja = cajaNombre_Caja;
    }

    public String getCajaCaja() {
        return CajaCaja;
    }

    public void setCajaCaja(String CajaCaja) {
        this.CajaCaja = CajaCaja;
    }
    public void setCajaUrlimagenopen(String cajaurlimagenopen) {
        this.CajaUrlimagen = cajaurlimagenopen;
    }
    public String getCajaUrlimagenopen() {
        return CajaUrlimagen;
    }
    public String getCajaUrlimagenclose() {
        return CajaUrlimagen;
    }

    public void setCajaUrlimagenclose(String cajaurlimagenclose) {
        this.CajaUrlimagen = cajaurlimagenclose;
    }
    public String getCajaFecha_Apertura() {
        return CajaFecha_Apertura;
    }

    public void setCajaFecha_Apertura(String CajaFecha_Apertura) {
        this.CajaFecha_Apertura = CajaFecha_Apertura;
    }
    public String getCajaMax_Caja() {
        return CajaMax_Caja;
    }

    public void setCajaMax_Caja(String CajaMax_Caja) {
        this.CajaMax_Caja = CajaMax_Caja;
    }

}
