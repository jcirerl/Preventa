package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 29/12/2016.
 */

public class Fra {
    private String FraSerie;
    private int FraFactura;
    private int FraId;

    public Fra(){}

    public Fra(String serie, int factura){
        this.FraSerie = serie;
        this.FraFactura = factura;
    }
    public int getFraId() {
        return FraId;
    }

    public void setFraId(int fraid) {
        this.FraId = fraid;
    }

    public int getFraFactura() {
        return FraFactura;
    }

    public void setFraFactura(int frafactura) {
        this.FraFactura = frafactura;
    }


    public String getFraSerie() {
        return FraSerie;
    }

    public void setFraSerie(String fraserie) {
        this.FraSerie = fraserie;
    }

}
