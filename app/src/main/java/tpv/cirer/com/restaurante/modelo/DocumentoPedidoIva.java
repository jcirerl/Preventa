package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 30/12/2016.
 */

public class DocumentoPedidoIva {
    private String DocumentoPedidoIvaGrupo;
    private String DocumentoPedidoIvaEmpresa;
    private String DocumentoPedidoIvaLocal;
    private String DocumentoPedidoIvaSeccion;
    private String DocumentoPedidoIvaCaja;

    private String DocumentoPedidoIvaImp_base;
    private String DocumentoPedidoIvaImp_iva;
    private String DocumentoPedidoIvaImp_total;
    private String DocumentoPedidoIvaTipo_iva;

    private int DocumentoPedidoIvaPedido;
    private int DocumentoPedidoIvaId;


    public DocumentoPedidoIva(){}


    public String getDocumentoPedidoIvaGrupo() {
        return DocumentoPedidoIvaGrupo;
    }
    public void setDocumentoPedidoIvaGrupo(String documentofacturaGrupo) {
        this.DocumentoPedidoIvaGrupo = documentofacturaGrupo;
    }

    public String getDocumentoPedidoIvaEmpresa() {
        return DocumentoPedidoIvaEmpresa;
    }
    public void setDocumentoPedidoIvaEmpresa(String documentofacturaEmpresa) {
        this.DocumentoPedidoIvaEmpresa = documentofacturaEmpresa;
    }

    public String getDocumentoPedidoIvaSeccion() {
        return DocumentoPedidoIvaSeccion;
    }
    public void setDocumentoPedidoIvaSeccion(String documentofacturaSeccion) {
        this.DocumentoPedidoIvaSeccion = documentofacturaSeccion;
    }

    public String getDocumentoPedidoIvaLocal() {
        return DocumentoPedidoIvaLocal;
    }
    public void setDocumentoPedidoIvaLocal(String documentofacturaLocal) {
        this.DocumentoPedidoIvaLocal = documentofacturaLocal;
    }

    public String getDocumentoPedidoIvaCaja() {
        return DocumentoPedidoIvaCaja;
    }
    public void setDocumentoPedidoIvaCaja(String documentofacturaCaja) {
        this.DocumentoPedidoIvaCaja = documentofacturaCaja;
    }





    public int getDocumentoPedidoIvaId() {
        return DocumentoPedidoIvaId;
    }
    public void setDocumentoPedidoIvaId(int documentofacturaId) {
        this.DocumentoPedidoIvaId = documentofacturaId;
    }


    public int getDocumentoPedidoIvaPedido() {
        return DocumentoPedidoIvaPedido;
    }
    public void setDocumentoPedidoIvaPedido(int documentofacturaPedido) {
        this.DocumentoPedidoIvaPedido = documentofacturaPedido;
    }

    public String getDocumentoPedidoIvaImp_base() {
        return DocumentoPedidoIvaImp_base;
    }
    public void setDocumentoPedidoIvaImp_base(String documentofacturaimp_base) {
        this.DocumentoPedidoIvaImp_base = documentofacturaimp_base;
    }

    public String getDocumentoPedidoIvaImp_iva() {
        return DocumentoPedidoIvaImp_iva;
    }
    public void setDocumentoPedidoIvaImp_iva(String documentofacturaimp_iva) {
        this.DocumentoPedidoIvaImp_iva = documentofacturaimp_iva;
    }

    public String getDocumentoPedidoIvaImp_total() {
        return DocumentoPedidoIvaImp_total;
    }
    public void setDocumentoPedidoIvaImp_total(String documentofacturaimp_total) {
        this.DocumentoPedidoIvaImp_total = documentofacturaimp_total;
    }
    public String getDocumentoPedidoIvaTipo_iva() {
        return DocumentoPedidoIvaTipo_iva;
    }
    public void setDocumentoPedidoIvaTipo_iva(String documentofacturatipo_iva) {
        this.DocumentoPedidoIvaTipo_iva = documentofacturatipo_iva;
    }


}
