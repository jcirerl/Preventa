package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 26/09/2016.
 */

public class LineaDocumentoPedido {
    private String LineaDocumentoPedidoGrupo;
    private String LineaDocumentoPedidoEmpresa;
    private String LineaDocumentoPedidoLocal;
    private String LineaDocumentoPedidoSeccion;
    private String LineaDocumentoPedidoCaja;
    private String LineaDocumentoPedidoCant;
    private String LineaDocumentoPedidoArticulo;
    private String LineaDocumentoPedidoNombre;
    private String LineaDocumentoPedidoTipo_are;
    private String LineaDocumentoPedidoNombre_tipoare;
    private String LineaDocumentoPedidoTipoPlato;
    private String LineaDocumentoPedidoNombre_plato;

    private String LineaDocumentoPedidoObs;
    private String LineaDocumentoPedidoPreu;
    private String LineaDocumentoPedidoImporte;

    private String LineaDocumentoPedidoUrlimagen;
    private String LineaDocumentoPedidoTipo_iva;
    private String LineaDocumentoPedidoTerminal;

    private int LineaDocumentoPedidoPedido;
    private int LineaDocumentoPedidoTiva_id;
    private int LineaDocumentoPedidoSwFactura;
    private int LineaDocumentoPedidoSwPedido;
    private int LineaDocumentoPedidoId;
    private int LineaDocumentoPedidoIndividual;
    private int LineaDocumentoPedidoIdRelacion;
    private int LineaDocumentoPedidoPrincipal;

    private int idDrawable;

    public LineaDocumentoPedido(){}

    public int getIdDrawable() {
        return idDrawable;
    }
    public int getLineaDocumentoPedidoPrincipal() {
        return LineaDocumentoPedidoPrincipal;
    }
    public void setLineaDocumentoPedidoPrincipal(int lineaDocumentoPedidoPrincipal) {
        this.LineaDocumentoPedidoPrincipal = lineaDocumentoPedidoPrincipal;
    }
    public int getLineaDocumentoPedidoIndividual() {
        return LineaDocumentoPedidoIndividual;
    }
    public void setLineaDocumentoPedidoIndividual(int lineadocumentopedidoIndividual) {
        this.LineaDocumentoPedidoIndividual = lineadocumentopedidoIndividual;
    }
    public int getLineaDocumentoPedidoSwPedido() {
        return LineaDocumentoPedidoSwPedido;
    }
    public void setLineaDocumentoPedidoSwPedido(int lineadocumentopedidoSwPedido) {
        this.LineaDocumentoPedidoSwPedido = lineadocumentopedidoSwPedido;
    }
    public int getLineaDocumentoPedidoSwFactura() {
        return LineaDocumentoPedidoSwFactura;
    }
    public void setLineaDocumentoPedidoSwFactura(int lineadocumentopedidoSwFactura) {
        this.LineaDocumentoPedidoSwFactura = lineadocumentopedidoSwFactura;
    }

    public int getLineaDocumentoPedidoTiva_id() {
        return LineaDocumentoPedidoTiva_id;
    }
    public void setLineaDocumentoPedidoTiva_id(int lineadocumentopedidoTiva_id) {
        this.LineaDocumentoPedidoTiva_id = lineadocumentopedidoTiva_id;
    }
    public String getLineaDocumentoPedidoPreu() {
        return LineaDocumentoPedidoPreu;
    }
    public void setLineaDocumentoPedidoPreu(String lineadocumentopedidoPreu) {
        this.LineaDocumentoPedidoPreu = lineadocumentopedidoPreu;
    }

    public String getLineaDocumentoPedidoImporte() {
        return LineaDocumentoPedidoImporte;
    }
    public void setLineaDocumentoPedidoImporte(String lineadocumentopedidoImporte) {
        this.LineaDocumentoPedidoImporte = lineadocumentopedidoImporte;
    }

    public String getLineaDocumentoPedidoTipo_iva() {
        return LineaDocumentoPedidoTipo_iva;
    }
    public void setLineaDocumentoPedidoTipo_iva(String lineaDocumentoPedidoTipo_iva) {
        this.LineaDocumentoPedidoTipo_iva = lineaDocumentoPedidoTipo_iva;
    }
     public String getLineaDocumentoPedidoGrupo() {
        return LineaDocumentoPedidoGrupo;
    }
    public void setLineaDocumentoPedidoGrupo(String lineadocumentopedidoGrupo) {
        this.LineaDocumentoPedidoGrupo = lineadocumentopedidoGrupo;
    }

    public String getLineaDocumentoPedidoEmpresa() {
        return LineaDocumentoPedidoEmpresa;
    }
    public void setLineaDocumentoPedidoEmpresa(String lineadocumentopedidoEmpresa) {
        this.LineaDocumentoPedidoEmpresa = lineadocumentopedidoEmpresa;
    }

    public String getLineaDocumentoPedidoSeccion() {
        return LineaDocumentoPedidoSeccion;
    }
    public void setLineaDocumentoPedidoSeccion(String lineadocumentopedidoSeccion) {
        this.LineaDocumentoPedidoSeccion = lineadocumentopedidoSeccion;
    }

    public String getLineaDocumentoPedidoLocal() {
        return LineaDocumentoPedidoLocal;
    }
    public void setLineaDocumentoPedidoLocal(String lineadocumentopedidoLocal) {
        this.LineaDocumentoPedidoLocal = lineadocumentopedidoLocal;
    }

    public String getLineaDocumentoPedidoCaja() {
        return LineaDocumentoPedidoCaja;
    }
    public void setLineaDocumentoPedidoCaja(String lineadocumentopedidoCaja) {
        this.LineaDocumentoPedidoCaja = lineadocumentopedidoCaja;
    }

    public String getLineaDocumentoPedidoCant() {
        return LineaDocumentoPedidoCant;
    }
    public void setLineaDocumentoPedidoCant(String lineaPedidopedidoCant) {
        this.LineaDocumentoPedidoCant = lineaPedidopedidoCant;
    }

    public String getLineaDocumentoPedidoArticulo() {
        return LineaDocumentoPedidoArticulo;
    }
    public void setLineaDocumentoPedidoArticulo(String lineadocumentopedidoArticulo) {
        this.LineaDocumentoPedidoArticulo = lineadocumentopedidoArticulo;
    }
    public String getLineaDocumentoPedidoTipo_are() {
        return LineaDocumentoPedidoTipo_are;
    }
    public void setLineaDocumentoPedidoTipo_are(String lineaDocumentoPedidoTipo_are) {
        this.LineaDocumentoPedidoTipo_are = lineaDocumentoPedidoTipo_are;
    }
    public String getLineaDocumentoPedidoNombre_tipoare() {
        return LineaDocumentoPedidoNombre_tipoare;
    }
    public void setLineaDocumentoPedidoNombre_tipoare(String lineaDocumentoPedidoNombre_tipoare) {
        this.LineaDocumentoPedidoNombre_tipoare = lineaDocumentoPedidoNombre_tipoare;
    }

    public String getLineaDocumentoPedidoNombre() {
        return LineaDocumentoPedidoNombre;
    }
    public void setLineaDocumentoPedidoNombre(String lineadocumentopedidoNombre) {
        this.LineaDocumentoPedidoNombre = lineadocumentopedidoNombre;
    }

    public String getLineaDocumentoPedidoObs() {
        return LineaDocumentoPedidoObs;
    }
    public void setLineaDocumentoPedidoObs(String lineadocumentopedidoObs) {
        this.LineaDocumentoPedidoObs = lineadocumentopedidoObs;
    }

 
    public int getLineaDocumentoPedidoId() {
        return LineaDocumentoPedidoId;
    }
    public void setLineaDocumentoPedidoId(int lineadocumentopedidoId) {
        this.LineaDocumentoPedidoId = lineadocumentopedidoId;
    }
    public int getLineaDocumentoPedidoIdRelacion() {
        return LineaDocumentoPedidoIdRelacion;
    }
    public void setLineaDocumentoPedidoIdRelacion(int lineaDocumentoPedidoIdRelacion) {
        this.LineaDocumentoPedidoIdRelacion = lineaDocumentoPedidoIdRelacion;
    }

 
    public int getLineaDocumentoPedidoPedido() {
        return LineaDocumentoPedidoPedido;
    }
    public void setLineaDocumentoPedidoPedido(int lineadocumentopedidoPedido) {
        this.LineaDocumentoPedidoPedido = lineadocumentopedidoPedido;
    }


    public String getLineaDocumentoPedidoUrlimagen() {
        return LineaDocumentoPedidoUrlimagen;
    }

    public void setLineaDocumentoPedidoUrlimagen(String lineadocumentopedidourlimagen) {
        this.LineaDocumentoPedidoUrlimagen = lineadocumentopedidourlimagen;
    }
    public String getLineaDocumentoPedidoTipoPlato() {
        return LineaDocumentoPedidoTipoPlato;
    }
    public void setLineaDocumentoPedidoTipoPlato(String lineaDocumentoPedidoTipoPlato) {
        this.LineaDocumentoPedidoTipoPlato = lineaDocumentoPedidoTipoPlato;
    }
    public String getLineaDocumentoPedidoNombre_plato() {
        return LineaDocumentoPedidoNombre_plato;
    }
    public void setLineaDocumentoPedidoNombre_plato(String lineaDocumentoPedidoNombre_plato) {
        this.LineaDocumentoPedidoNombre_plato = lineaDocumentoPedidoNombre_plato;
    }
    public String getLineaDocumentoPedidoTerminal() {
        return LineaDocumentoPedidoTerminal;
    }

    public void setLineaDocumentoPedidoTerminal(String lineadocumentopedidoterminal) {
        this.LineaDocumentoPedidoTerminal = lineadocumentopedidoterminal;
    }


}
