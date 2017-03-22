package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 28/11/2016.
 */

public class DocumentoFacturaIva {
    private String DocumentoFacturaIvaGrupo;
    private String DocumentoFacturaIvaEmpresa;
    private String DocumentoFacturaIvaLocal;
    private String DocumentoFacturaIvaSeccion;
    private String DocumentoFacturaIvaCaja;

    private String DocumentoFacturaIvaSerie;
    private String DocumentoFacturaIvaImp_base;
    private String DocumentoFacturaIvaImp_iva;
    private String DocumentoFacturaIvaImp_total;
    private String DocumentoFacturaIvaTipo_iva;

    private int DocumentoFacturaIvaFactura;
    private int DocumentoFacturaIvaId;


    public DocumentoFacturaIva(){}


    public String getDocumentoFacturaIvaGrupo() {
        return DocumentoFacturaIvaGrupo;
    }
    public void setDocumentoFacturaIvaGrupo(String documentofacturaGrupo) {
        this.DocumentoFacturaIvaGrupo = documentofacturaGrupo;
    }

    public String getDocumentoFacturaIvaEmpresa() {
        return DocumentoFacturaIvaEmpresa;
    }
    public void setDocumentoFacturaIvaEmpresa(String documentofacturaEmpresa) {
        this.DocumentoFacturaIvaEmpresa = documentofacturaEmpresa;
    }

    public String getDocumentoFacturaIvaSeccion() {
        return DocumentoFacturaIvaSeccion;
    }
    public void setDocumentoFacturaIvaSeccion(String documentofacturaSeccion) {
        this.DocumentoFacturaIvaSeccion = documentofacturaSeccion;
    }

    public String getDocumentoFacturaIvaLocal() {
        return DocumentoFacturaIvaLocal;
    }
    public void setDocumentoFacturaIvaLocal(String documentofacturaLocal) {
        this.DocumentoFacturaIvaLocal = documentofacturaLocal;
    }

    public String getDocumentoFacturaIvaCaja() {
        return DocumentoFacturaIvaCaja;
    }
    public void setDocumentoFacturaIvaCaja(String documentofacturaCaja) {
        this.DocumentoFacturaIvaCaja = documentofacturaCaja;
    }





    public int getDocumentoFacturaIvaId() {
        return DocumentoFacturaIvaId;
    }
    public void setDocumentoFacturaIvaId(int documentofacturaId) {
        this.DocumentoFacturaIvaId = documentofacturaId;
    }


    public int getDocumentoFacturaIvaFactura() {
        return DocumentoFacturaIvaFactura;
    }
    public void setDocumentoFacturaIvaFactura(int documentofacturaFactura) {
        this.DocumentoFacturaIvaFactura = documentofacturaFactura;
    }

    public String getDocumentoFacturaIvaSerie() {
        return DocumentoFacturaIvaSerie;
    }
    public void setDocumentoFacturaIvaSerie(String documentofacturaserie) {
        this.DocumentoFacturaIvaSerie = documentofacturaserie;
    }

    public String getDocumentoFacturaIvaImp_base() {
        return DocumentoFacturaIvaImp_base;
    }
    public void setDocumentoFacturaIvaImp_base(String documentofacturaimp_base) {
        this.DocumentoFacturaIvaImp_base = documentofacturaimp_base;
    }

    public String getDocumentoFacturaIvaImp_iva() {
        return DocumentoFacturaIvaImp_iva;
    }
    public void setDocumentoFacturaIvaImp_iva(String documentofacturaimp_iva) {
        this.DocumentoFacturaIvaImp_iva = documentofacturaimp_iva;
    }

    public String getDocumentoFacturaIvaImp_total() {
        return DocumentoFacturaIvaImp_total;
    }
    public void setDocumentoFacturaIvaImp_total(String documentofacturaimp_total) {
        this.DocumentoFacturaIvaImp_total = documentofacturaimp_total;
    }
    public String getDocumentoFacturaIvaTipo_iva() {
        return DocumentoFacturaIvaTipo_iva;
    }
    public void setDocumentoFacturaIvaTipo_iva(String documentofacturatipo_iva) {
        this.DocumentoFacturaIvaTipo_iva = documentofacturatipo_iva;
    }

}
