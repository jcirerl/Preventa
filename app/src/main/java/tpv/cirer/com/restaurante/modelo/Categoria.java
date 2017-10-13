package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 06/10/2016.
 */

public class Categoria {
    private String CategoriaNombre_tipoare;
    private String CategoriaTipo_are;
    private int CategoriaOrden;
    private String CategoriaUrlimagen;

    public Categoria(){}

    public String getCategoriaTipo_are() {
        return CategoriaTipo_are;
    }
    public void setCategoriaTipo_are(String categoriatipo_are) {
        this.CategoriaTipo_are = categoriatipo_are;
    }

    public String getCategoriaNombre_tipoare() {
        return CategoriaNombre_tipoare;
    }
    public void setCategoriaNombre_tipoare(String categorianombre_tipoare) {
        this.CategoriaNombre_tipoare = categorianombre_tipoare;
    }
    public int getCategoriaOrden() {
        return CategoriaOrden;
    }
    public void setCategoriaOrden(int categoriaorden) {
        this.CategoriaOrden = categoriaorden;
    }
    public String getCategoriaUrlimagen() {
        return CategoriaUrlimagen;
    }
    public void setCategoriaUrlimagen(String categoriaurlimagen) {
        this.CategoriaUrlimagen = categoriaurlimagen;
    }

}
