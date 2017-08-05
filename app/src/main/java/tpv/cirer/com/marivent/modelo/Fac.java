package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 31/07/2017.
 */

public class Fac {
    private String FacSerie;
    private int FacFactura;
    private int FacId;
    private String FacNif;
    private String FacRazon;
    private String FacTotal;
    private String FacFecha;

    public Fac(){}

    public Fac(int id, String serie, int factura, String nif, String razon, String total, String fecha){
        this.FacId = id;
        this.FacSerie = serie;
        this.FacFactura = factura;
        this.FacNif = nif;
        this.FacRazon = razon;
        this.FacTotal = total;
        this.FacFecha = fecha;
    }
    public int getFacId() {
        return FacId;
    }

    public void setFacId(int fraid) {
        this.FacId = fraid;
    }

    public int getFacFactura() {
        return FacFactura;
    }

    public void setFacFactura(int facfactura) {
        this.FacFactura = facfactura;
    }


    public String getFacSerie() {
        return FacSerie;
    }

    public void setFacSerie(String fraserie) {
        this.FacSerie = fraserie;
    }
    public String getFacNif() {
        return FacNif;
    }

    public void setFacNif(String facnif) {
        this.FacNif = facnif;
    }
    public String getFacRazon() {
        return FacRazon;
    }

    public void setFacRazon(String facrazon) {
        this.FacRazon = facrazon;
    }
    public String getFacTotal() {
        return FacTotal;
    }

    public void setFacTotal(String factotal) {
        this.FacTotal = factotal;
    }
    public String getFacFecha() {
        return FacFecha;
    }

    public void setFacFecha(String facfecha) {
        this.FacFecha = facfecha;
    }


}
