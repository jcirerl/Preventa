package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 03/09/2017.
 */

public class Observacion {
    private String articulo;
    private String nombre_obs;
    private String urlimagen;
    private String codigo_obs;

    public Observacion(String articulo, String codigo_obs, String nombre_obs, String urlimagen) {
        this.articulo = articulo;
        this.codigo_obs = codigo_obs;
        this.nombre_obs = nombre_obs;
        this.urlimagen = urlimagen;
    }
    public Observacion() {
    }


    public String getNombre_obs() {
        return nombre_obs;
    }

    public void setNombre_obs(String nombre_obs) {
        this.nombre_obs = nombre_obs;
    }

    public String getUrlimagen() {
        return urlimagen;
    }
    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }
    public String getCodigo_obs() {
        return codigo_obs;
    }
    public void setCodigo_obs(String codigo_obs) {
        this.codigo_obs = codigo_obs;
    }
    public String getArticulo() {
        return articulo;
    }
    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
