package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 18/11/2016.
 */

public class Popular {
    private String articulo;
    private float precio;
    private String nombre;
    private int idDrawable;
    private String urlimagen;
    private String tipo_are;
    private int tiva_id;

    public Popular(float precio, String nombre, int idDrawable, String urlimagen) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.urlimagen = urlimagen;
    }
    public Popular() {
    }

    public float getPrecio() {
        return precio;
    }
    public int getTiva_id() {
        return tiva_id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
    public void setNombre(String popularnombre) {
        this.nombre = popularnombre;
    }
    public void setPrecio(float popularprecio) {
        this.precio = popularprecio;
    }
    public void setTiva_id(int populartiva_id) {
        this.tiva_id = populartiva_id;
    }

    public String getUrlimagen() {
        return urlimagen;
    }
    public void setUrlimagen(String popularurlimagen) {
        this.urlimagen = popularurlimagen;
    }
    public String getTipo_are() {
        return tipo_are;
    }
    public void setTipo_are(String populartipo_are) {
        this.tipo_are = populartipo_are;
    }
    public String getArticulo() {
        return articulo;
    }
    public void setArticulo(String populararticulo) {
        this.articulo = populararticulo;
    }



}
