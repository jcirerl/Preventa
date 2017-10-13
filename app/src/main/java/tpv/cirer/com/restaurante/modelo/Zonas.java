package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 27/09/2017.
 */

public class Zonas {
    private String ZonasZona;
    private String ZonasNombre_Zona;
    private String ZonasUrlimagen;
    private int ZonasId;
    public Zonas(){}

    public Zonas(String zona, String nombre, String imagen){
        this.ZonasZona = zona;
        this.ZonasNombre_Zona = nombre;
        this.ZonasUrlimagen = imagen;
    }
    public int getZonasId() {
        return ZonasId;
    }

    public void setZonasId(int zonasId) {
        this.ZonasId = zonasId;
    }

    public String getZonasNombre_Zona() {
        return ZonasNombre_Zona;
    }

    public void setZonasNombre_Zona(String zonasNombre_Zona) {
        this.ZonasNombre_Zona = zonasNombre_Zona;
    }

    public String getZonasZona() {
        return ZonasZona;
    }

    public void setZonasZona(String ZonasZona) {
        this.ZonasZona = ZonasZona;
    }
    public void setZonasUrlimagen(String zonasurlimagen) {
        this.ZonasUrlimagen = zonasurlimagen;
    }
    public String getZonasUrlimagen() {
        return ZonasUrlimagen;
    }


}
