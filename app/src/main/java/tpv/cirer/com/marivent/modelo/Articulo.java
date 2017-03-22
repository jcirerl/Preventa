package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 06/10/2016.
 */

public class Articulo {
    private int ArticuloId;
    private int ArticuloTiva_id;
    private String ArticuloGrupo;
    private String ArticuloEmpresa;
    private String ArticuloLocal;
    private String ArticuloSeccion;
    private String ArticuloArticulo;
    private String ArticuloNombre;
    private float ArticuloPrecio;
    private String ArticuloCantidad;
    private String ArticuloUrlimagen;
    private String ArticuloTipo_are;
    private String ArticuloTipo_iva;
    private String ArticuloTipoPlato;
    private String ArticuloNombre_Plato;

    public Articulo(int id,
                    String articulo,
                    String nombre,
                    String tipoplato,
                    String nombre_plato,
                    float precio,
                    String cantidad,
                    String urlimagen,
                    String tipo_are,
                    String tipo_iva,
                    int tiva_id) {
        this.ArticuloId = id;
        this.ArticuloArticulo = articulo;
        this.ArticuloNombre = nombre;
        this.ArticuloTipoPlato = tipoplato;
        this.ArticuloNombre_Plato = nombre_plato;
        this.ArticuloPrecio = precio;
        this.ArticuloCantidad = cantidad;
        this.ArticuloUrlimagen = urlimagen;
        this.ArticuloTipo_are = tipo_are;
        this.ArticuloTipo_iva = tipo_iva;
        this.ArticuloTiva_id = tiva_id;
    }
    public Articulo() {
    }
    public int getArticuloId() {
        return ArticuloId;
    }
    public void setArticuloId(int articuloId) {
        this.ArticuloId = getArticuloId();
    }
    
    
    public String getArticuloTipo_iva() {
        return ArticuloTipo_iva;
    }
    public void setArticuloTipo_iva(String articuloTipo_iva) {
        this.ArticuloTipo_iva = articuloTipo_iva;
    }
    public float getArticuloPrecio() {
        return ArticuloPrecio;
    }
    public void setArticuloPrecio(float articuloPrecio) {
        this.ArticuloPrecio = articuloPrecio;
    }

    public String getArticuloGrupo() {
        return ArticuloGrupo;
    }
    public void setArticuloGrupo(String articuloGrupo) {
        this.ArticuloGrupo = articuloGrupo;
    }

    public String getArticuloEmpresa() {
        return ArticuloEmpresa;
    }
    public void setArticuloEmpresa(String articuloEmpresa) {
        this.ArticuloEmpresa = articuloEmpresa;
    }

    public String getArticuloSeccion() {
        return ArticuloSeccion;
    }
    public void setArticuloSeccion(String articuloSeccion) {
        this.ArticuloSeccion = articuloSeccion;
    }

    public String getArticuloLocal() {
        return ArticuloLocal;
    }
    public void setArticuloLocal(String articuloLocal) {
        this.ArticuloLocal = articuloLocal;
    }


    public String getArticuloCantidad() {
        return ArticuloCantidad;
    }
    public void setArticuloCantidad(String articuloCantidad) {
        this.ArticuloCantidad = articuloCantidad;
    }

    public String getArticuloArticulo() {
        return ArticuloArticulo;
    }
    public void setArticuloArticulo(String articuloArticulo) {
        this.ArticuloArticulo = articuloArticulo;
    }
    public String getArticuloTipoPlato() {
        return ArticuloTipoPlato;
    }
    public void setArticuloTipoPlato(String tipoPlato) {
        this.ArticuloTipoPlato = tipoPlato;
    }

    public String getArticuloNombre_Plato() {
        return ArticuloNombre_Plato;
    }
    public void setArticuloNombre_Plato(String nombre_plato) {
        this.ArticuloNombre_Plato = nombre_plato;
    }
    public String getArticuloTipo_are() {
        return ArticuloTipo_are;
    }
    public void setArticuloTipo_are(String lineaDocumentoPedidoTipo_are) {
        this.ArticuloTipo_are = lineaDocumentoPedidoTipo_are;
    }

    public String getArticuloNombre() {
        return ArticuloNombre;
    }
    public void setArticuloNombre(String articuloNombre) {
        this.ArticuloNombre = articuloNombre;
    }

    public String getArticuloUrlimagen() {
        return ArticuloUrlimagen;
    }

    public void setArticuloUrlimagen(String articulourlimagen) {
        this.ArticuloUrlimagen = articulourlimagen;
    }
    public int getArticuloTiva_id() {
        return ArticuloTiva_id;
    }
    public void setArticuloTiva_id(int lineadocumentopedidoTiva_id) {
        this.ArticuloTiva_id = lineadocumentopedidoTiva_id;
    }
    
}
