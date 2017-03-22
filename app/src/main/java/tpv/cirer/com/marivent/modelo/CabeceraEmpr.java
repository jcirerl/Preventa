package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 28/11/2016.
 */

public class CabeceraEmpr {
    private String CabeceraRazon;
    private String CabeceraCif;
    private String CabeceraNumero;
    private String CabeceraBloque;
    private String CabeceraEscalera;
    private String CabeceraPiso;
    private String CabeceraPuerta;
    private String CabeceraAmpliacion;
    private String CabeceraNombre_calle;
    private String CabeceraCod_poblacion;
    private String CabeceraNombre_poblacion;
    private String CabeceraNombre_provincia;
    private String CabeceraNombre_pais;

    public CabeceraEmpr(){}

    public CabeceraEmpr(String razon, String cif){
        this.CabeceraRazon = razon;
        this.CabeceraCif = cif;
    }
    public void setCabeceraNombre_calle(String cabeceraNombre_calle ){
        this.CabeceraNombre_calle = cabeceraNombre_calle;
    }
    public void setCabeceraCod_poblacion(String cabeceraCod_poblacion ){
        this.CabeceraCod_poblacion = cabeceraCod_poblacion;
    }
    public void setCabeceraNombre_poblacion(String cabeceraNombre_poblacion ){
        this.CabeceraNombre_poblacion = cabeceraNombre_poblacion;
    }

    public void setCabeceraNombre_provincia(String cabeceraNombre_provincia){
        this.CabeceraNombre_provincia = cabeceraNombre_provincia;
    }

    public void setCabeceraNombre_pais(String cabeceraNombre_pais ){
        this.CabeceraNombre_pais = cabeceraNombre_pais;
    }

    public void setCabeceraEscalera(String cabeceraEscalera ){
        this.CabeceraEscalera = cabeceraEscalera;
    }
    public void setCabeceraPiso(String cabeceraPiso ){
        this.CabeceraPiso = cabeceraPiso;
    }
    public void setCabeceraPuerta(String cabeceraPuerta ){
        this.CabeceraPuerta = cabeceraPuerta;
    }

    public void setCabeceraAmpliacion(String cabeceraAmpliacion){
        this.CabeceraAmpliacion = cabeceraAmpliacion;
    }

    public void setCabeceraNumero(String cabeceraNumero ){
        this.CabeceraNumero = cabeceraNumero;
    }
    public void setCabeceraBloque(String cabeceraBloque ){
        this.CabeceraBloque = cabeceraBloque;
    }
    public void setCabeceraRazon(String cabeceraRazon ){
        this.CabeceraRazon = cabeceraRazon;
    }

    public void setCabeceraCif(String cabeceraCif){
        this.CabeceraCif = cabeceraCif;
    }
    public String getCabeceraNumero(){
        return this.CabeceraNumero;
    }

    public String getCabeceraRazon(){
        return this.CabeceraRazon;
    }

    public String getCabeceraCif(){
        return this.CabeceraCif;
    }
    public String getCabeceraCod_poblacion(){
        return this.CabeceraCod_poblacion;
    }

    public String getCabeceraNombre_calle(){
        return this.CabeceraNombre_calle;
    }

    public String getCabeceraNombre_poblacion(){
        return this.CabeceraNombre_poblacion    ;
    }
    public String getCabeceraNombre_provincia(){
        return this.CabeceraNombre_provincia;
    }

    public String getCabeceraNombre_pais(){
        return this.CabeceraNombre_pais;
    }
    public String getCabeceraBloque(){
        return this.CabeceraBloque;
    }

    public String getCabeceraEscalera(){
        return this.CabeceraEscalera;
    }

    public String getCabeceraPiso(){
        return this.CabeceraPiso    ;
    }
    public String getCabeceraPuerta(){
        return this.CabeceraPuerta;
    }

    public String getCabeceraAmpliacion(){
        return this.CabeceraAmpliacion;
    }


}
