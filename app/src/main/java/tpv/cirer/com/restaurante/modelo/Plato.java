package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 24/02/2017.
 */

public class Plato {
    private String PlatoTipoPlato;
    private String PlatoNombre_Plato;
    private int PlatoOrden;
    private int PlatoId;
    public Plato(){}

    public Plato(String tipoplato, String nombre){
        this.PlatoTipoPlato = tipoplato;
        this.PlatoNombre_Plato = nombre;
    }
    public int getPlatoId() {
        return PlatoId;
    }

    public void setPlatoId(int platoId) {
        this.PlatoId = platoId;
    }

    public int getPlatoOrden() {
        return PlatoOrden;
    }

    public void setPlatoOrden(int platoOrden) {
        this.PlatoOrden = platoOrden;
    }

    public String getPlatoNombre_Plato() {
        return PlatoNombre_Plato;
    }

    public void setPlatoNombre_Plato(String platoNombre_Plato) {
        this.PlatoNombre_Plato = platoNombre_Plato;
    }

    public String getPlatoTipoPlato() {
        return PlatoTipoPlato;
    }

    public void setPlatoTipoPlato(String PlatoTipoPlato) {
        this.PlatoTipoPlato = PlatoTipoPlato;
    }

}
