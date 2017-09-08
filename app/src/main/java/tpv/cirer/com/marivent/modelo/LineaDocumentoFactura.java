package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 20/09/2016.
 */

public class LineaDocumentoFactura {
    private String LineaDocumentoFacturaGrupo;
    private String LineaDocumentoFacturaEmpresa;
    private String LineaDocumentoFacturaLocal;
    private String LineaDocumentoFacturaSeccion;
    private String LineaDocumentoFacturaCaja;
    private String LineaDocumentoFacturaSerie;
    private String LineaDocumentoFacturaArticulo;
    private String LineaDocumentoFacturaNombre;

    private String LineaDocumentoFacturaCant;
    private String LineaDocumentoFacturaCantRestar;
    private String LineaDocumentoFacturaPreu;
    private String LineaDocumentoFacturaImporte;

    private String LineaDocumentoFacturaUrlimagen;
    private String LineaDocumentoFacturaTipo_iva;

    private int LineaDocumentoFacturaFactura;
    private int LineaDocumentoFacturaTiva_id;
    private int LineaDocumentoFacturaId;
    
    public LineaDocumentoFactura(){}

 /*   public LineaDocumentoFactura(String LineaDocumentoFactura, String nombre){
        this.LineaDocumentoFacturaLineaDocumentoFactura = LineaDocumentoFactura;
        this.LineaDocumentoFacturaNombre_Sec = nombre;
    }
*/
    public String getLineaDocumentoFacturaTipo_iva() {
        return LineaDocumentoFacturaTipo_iva;
    }
    public void setLineaDocumentoFacturaTipo_iva(String lineaDocumentoFacturaTipo_iva) {
        this.LineaDocumentoFacturaTipo_iva = lineaDocumentoFacturaTipo_iva;
    }
    public String getLineaDocumentoFacturaGrupo() {
        return LineaDocumentoFacturaGrupo;
    }
    public void setLineaDocumentoFacturaGrupo(String lineadocumentofacturaGrupo) {
        this.LineaDocumentoFacturaGrupo = lineadocumentofacturaGrupo;
    }

    public String getLineaDocumentoFacturaEmpresa() {
        return LineaDocumentoFacturaEmpresa;
    }
    public void setLineaDocumentoFacturaEmpresa(String lineadocumentofacturaEmpresa) {
        this.LineaDocumentoFacturaEmpresa = lineadocumentofacturaEmpresa;
    }

    public String getLineaDocumentoFacturaSeccion() {
        return LineaDocumentoFacturaSeccion;
    }
    public void setLineaDocumentoFacturaSeccion(String lineadocumentofacturaSeccion) {
        this.LineaDocumentoFacturaSeccion = lineadocumentofacturaSeccion;
    }

    public String getLineaDocumentoFacturaLocal() {
        return LineaDocumentoFacturaLocal;
    }
    public void setLineaDocumentoFacturaLocal(String lineadocumentofacturaLocal) {
        this.LineaDocumentoFacturaLocal = lineadocumentofacturaLocal;
    }

    public String getLineaDocumentoFacturaCaja() {
        return LineaDocumentoFacturaCaja;
    }
    public void setLineaDocumentoFacturaCaja(String lineadocumentofacturaCaja) {
        this.LineaDocumentoFacturaCaja = lineadocumentofacturaCaja;
    }

    public String getLineaDocumentoFacturaSerie() {
        return LineaDocumentoFacturaSerie;
    }
    public void setLineaDocumentoFacturaSerie(String lineaFacturafacturaSerie) {
        this.LineaDocumentoFacturaSerie = lineaFacturafacturaSerie;
    }

    public String getLineaDocumentoFacturaArticulo() {
        return LineaDocumentoFacturaArticulo;
    }
    public void setLineaDocumentoFacturaArticulo(String lineadocumentofacturaArticulo) {
        this.LineaDocumentoFacturaArticulo = lineadocumentofacturaArticulo;
    }

    public String getLineaDocumentoFacturaNombre() {
        return LineaDocumentoFacturaNombre;
    }
    public void setLineaDocumentoFacturaNombre(String lineadocumentofacturaNombre) {
        this.LineaDocumentoFacturaNombre = lineadocumentofacturaNombre;
    }

    public String getLineaDocumentoFacturaCantRestar() {
        return LineaDocumentoFacturaCantRestar;
    }
    public void setLineaDocumentoFacturaCantRestar(String lineadocumentofacturaCantRestar) {
        this.LineaDocumentoFacturaCantRestar = lineadocumentofacturaCantRestar;
    }

    public String getLineaDocumentoFacturaCant() {
        return LineaDocumentoFacturaCant;
    }
    public void setLineaDocumentoFacturaCant(String lineadocumentofacturaCant) {
        this.LineaDocumentoFacturaCant = lineadocumentofacturaCant;
    }
    public String getLineaDocumentoFacturaPreu() {
        return LineaDocumentoFacturaPreu;
    }
    public void setLineaDocumentoFacturaPreu(String lineadocumentofacturaPreu) {
        this.LineaDocumentoFacturaPreu = lineadocumentofacturaPreu;
    }

    public String getLineaDocumentoFacturaImporte() {
        return LineaDocumentoFacturaImporte;
    }
    public void setLineaDocumentoFacturaImporte(String lineadocumentofacturaImporte) {
        this.LineaDocumentoFacturaImporte = lineadocumentofacturaImporte;
    }

    public int getLineaDocumentoFacturaId() {
        return LineaDocumentoFacturaId;
    }
    public void setLineaDocumentoFacturaId(int lineadocumentofacturaId) {
        this.LineaDocumentoFacturaId = lineadocumentofacturaId;
    }

    public int getLineaDocumentoFacturaTiva_id() {
        return LineaDocumentoFacturaTiva_id;
    }
    public void setLineaDocumentoFacturaTiva_id(int lineadocumentofacturaTiva_id) {
        this.LineaDocumentoFacturaTiva_id = lineadocumentofacturaTiva_id;
    }

    public int getLineaDocumentoFacturaFactura() {
        return LineaDocumentoFacturaFactura;
    }
    public void setLineaDocumentoFacturaFactura(int lineadocumentofacturaFactura) {
        this.LineaDocumentoFacturaFactura = lineadocumentofacturaFactura;
    }


    public String getLineaDocumentoFacturaUrlimagen() {
        return LineaDocumentoFacturaUrlimagen;
    }

    public void setLineaDocumentoFacturaUrlimagen(String lineadocumentofacturaurlimagen) {
        this.LineaDocumentoFacturaUrlimagen = lineadocumentofacturaurlimagen;
    }


}
