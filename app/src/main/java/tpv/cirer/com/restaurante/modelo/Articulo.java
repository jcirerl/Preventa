package tpv.cirer.com.restaurante.modelo;

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
    private int ArticuloDependientetipoplatomaestro;
    private int ArticuloExcluyentebuffet;
    private int ArticuloActivobuffet;
    private String ArticuloDescripcion;
    private String ArticuloIngredientes;
    private String ArticuloAutor;
    private int ArticuloSw_tipo_are;
    private int ArticuloSw_suma_precio_individual;
    private int ArticuloPrincipal;

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
                    int tiva_id,
                    int dependientetipoplatomaestro,
                    int excluyentebuffet,
                    int activobuffet,
                    int sw_tipo_are,
                    int sw_suma_precio_indivisual,
                    int principal) {
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
        this.ArticuloDependientetipoplatomaestro = dependientetipoplatomaestro;
        this.ArticuloExcluyentebuffet = excluyentebuffet;
        this.ArticuloActivobuffet = activobuffet;
        this.ArticuloSw_tipo_are = sw_tipo_are;
        this.ArticuloSw_suma_precio_individual = sw_suma_precio_indivisual;
        this.ArticuloPrincipal = principal;
    }
    public Articulo() {
    }
    public int getArticuloId() {
        return ArticuloId;
    }
    public void setArticuloId(int articuloId) {
        this.ArticuloId = articuloId;
    }
    public int getArticuloPrincipal() {
        return ArticuloPrincipal;
    }
    public void setArticuloPrincipal(int articuloPrincipal) {
        this.ArticuloPrincipal = articuloPrincipal;
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
     public int getArticuloDependientetipoplatomaestro() {
        return ArticuloDependientetipoplatomaestro;
    }
    public void setArticuloDependientetipoplatomaestro(int lineadocumentopedidoDependientetipoplatomaestro) {
        this.ArticuloDependientetipoplatomaestro = lineadocumentopedidoDependientetipoplatomaestro;
    }
    public int getArticuloExcluyentebuffet() {
        return ArticuloExcluyentebuffet;
    }
    public void setArticuloExcluyentebuffet(int lineadocumentopedidoExcluyentebuffet) {
        this.ArticuloExcluyentebuffet = lineadocumentopedidoExcluyentebuffet;
    }
    public int getArticuloActivobuffet() {
        return ArticuloActivobuffet;
    }
    public void setArticuloActivobuffet(int lineadocumentopedidoActivobuffet) {
        this.ArticuloActivobuffet = lineadocumentopedidoActivobuffet;
    }
     public String getArticuloDescripcion() {
        return ArticuloDescripcion;
    }
    public void setArticuloDescripcion(String articuloDescripcion) {
        this.ArticuloDescripcion = articuloDescripcion;
    }
    public String getArticuloIngredientes() {
        return ArticuloIngredientes;
    }
    public void setArticuloIngredientes(String articuloIngredientes) {
        this.ArticuloIngredientes = articuloIngredientes;
    }
    public String getArticuloAutor() {
        return ArticuloAutor;
    }
    public void setArticuloAutor(String articuloAutor) {
        this.ArticuloAutor = articuloAutor;
    }
    public int getArticuloSw_tipo_are() {
        return ArticuloSw_tipo_are;
    }
    public void setArticuloSw_tipo_are(int idsw_tipo_are) {
        this.ArticuloSw_tipo_are = idsw_tipo_are;
    }
    public int getArticuloSw_suma_precio_individual() {
        return ArticuloSw_suma_precio_individual;
    }
    public void setArticuloSw_suma_precio_individual(int idsw_suma_precio_individual) {
        this.ArticuloSw_suma_precio_individual = idsw_suma_precio_individual;
    }

}
