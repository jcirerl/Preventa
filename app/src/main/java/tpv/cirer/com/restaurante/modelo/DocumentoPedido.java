package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 28/09/2016.
 */
public class DocumentoPedido {
    private String DocumentoPedidoGrupo;
    private String DocumentoPedidoEmpresa;
    private String DocumentoPedidoLocal;
    private String DocumentoPedidoSeccion;
    private String DocumentoPedidoCaja;
    private String DocumentoPedidoMesa;
    private int DocumentoPedidoComensales;
    private String DocumentoPedidoNombre_Mesa;
    private String DocumentoPedidoEmpleado;
    private String DocumentoPedidoCod_turno;
    private String DocumentoPedidoFecha;
    private String DocumentoPedidoEstado;
    private String DocumentoPedidoTabla;
    private String DocumentoPedidoImp_base;
    private String DocumentoPedidoImp_iva;
    private String DocumentoPedidoImp_total;

    private String DocumentoPedidoObs;

    private String DocumentoPedidoUrlimagen;

    private int DocumentoPedidoPedido;
    private int DocumentoPedidoId;
    private int DocumentoPedidoLineas;

    private int idDrawable;

    public DocumentoPedido(){}

    public int getIdDrawable() {
        return idDrawable;
    }

    public String getDocumentoPedidoGrupo() {
        return DocumentoPedidoGrupo;
    }
    public void setDocumentoPedidoGrupo(String documentopedidoGrupo) {
        this.DocumentoPedidoGrupo = documentopedidoGrupo;
    }

    public String getDocumentoPedidoEmpresa() {
        return DocumentoPedidoEmpresa;
    }
    public void setDocumentoPedidoEmpresa(String documentopedidoEmpresa) {
        this.DocumentoPedidoEmpresa = documentopedidoEmpresa;
    }

    public String getDocumentoPedidoSeccion() {
        return DocumentoPedidoSeccion;
    }
    public void setDocumentoPedidoSeccion(String documentopedidoSeccion) {
        this.DocumentoPedidoSeccion = documentopedidoSeccion;
    }

    public String getDocumentoPedidoLocal() {
        return DocumentoPedidoLocal;
    }
    public void setDocumentoPedidoLocal(String documentopedidoLocal) {
        this.DocumentoPedidoLocal = documentopedidoLocal;
    }

    public String getDocumentoPedidoCaja() {
        return DocumentoPedidoCaja;
    }
    public void setDocumentoPedidoCaja(String documentopedidoCaja) {
        this.DocumentoPedidoCaja = documentopedidoCaja;
    }

    public String getDocumentoPedidoNombre_Mesa() {
        return DocumentoPedidoNombre_Mesa;
    }
    public void setDocumentoPedidoNombre_Mesa(String documentopedidonombre_mesa) {
        this.DocumentoPedidoNombre_Mesa = documentopedidonombre_mesa;
    }
    public String getDocumentoPedidoMesa() {
        return DocumentoPedidoMesa;
    }
    public void setDocumentoPedidoMesa(String documentopedidomesa) {
        this.DocumentoPedidoMesa = documentopedidomesa;
    }

    public String getDocumentoPedidoEmpleado() {
        return DocumentoPedidoEmpleado;
    }
    public void setDocumentoPedidoEmpleado(String documentopedidoEmpleado) {
        this.DocumentoPedidoEmpleado = documentopedidoEmpleado;
    }

    public String getDocumentoPedidoCod_turno() {
        return DocumentoPedidoCod_turno;
    }
    public void setDocumentoPedidoCod_turno(String documentopedidoCod_turno) {
        this.DocumentoPedidoCod_turno = documentopedidoCod_turno;
    }
    public String getDocumentoPedidoFecha() {
        return DocumentoPedidoFecha;
    }
    public void setDocumentoPedidoFecha(String lineaPedidopedidoFecha) {
        this.DocumentoPedidoFecha = lineaPedidopedidoFecha;
    }

    public String getDocumentoPedidoEstado() {
        return DocumentoPedidoEstado;
    }
    public void setDocumentoPedidoEstado(String documentopedidoEstado) {
        this.DocumentoPedidoEstado = documentopedidoEstado;
    }

    public String getDocumentoPedidoTabla() {
        return DocumentoPedidoTabla;
    }
    public void setDocumentoPedidoTabla(String documentopedidoTabla) {
        this.DocumentoPedidoTabla = documentopedidoTabla;
    }

    public String getDocumentoPedidoObs() {
        return DocumentoPedidoObs;
    }
    public void setDocumentoPedidoObs(String documentopedidoObs) {
        this.DocumentoPedidoObs = documentopedidoObs;
    }


    public int getDocumentoPedidoId() {
        return DocumentoPedidoId;
    }
    public void setDocumentoPedidoId(int documentopedidoId) {
        this.DocumentoPedidoId = documentopedidoId;
    }


    public int getDocumentoPedidoPedido() {
        return DocumentoPedidoPedido;
    }
    public void setDocumentoPedidoPedido(int documentopedidoPedido) {
        this.DocumentoPedidoPedido = documentopedidoPedido;
    }

    public int getDocumentoPedidoLineas() {
        return DocumentoPedidoLineas;
    }
    public void setDocumentoPedidoLineas(int documentopedidoLineas) {
        this.DocumentoPedidoLineas = documentopedidoLineas;
    }

    public String getDocumentoPedidoUrlimagen() {
        return DocumentoPedidoUrlimagen;
    }

    public void setDocumentoPedidoUrlimagen(String documentopedidourlimagen) {
        this.DocumentoPedidoUrlimagen = documentopedidourlimagen;
    }
    public String getDocumentoPedidoImp_base() {
        return DocumentoPedidoImp_base;
    }
    public void setDocumentoPedidoImp_base(String documentopedidoimp_base) {
        this.DocumentoPedidoImp_base = documentopedidoimp_base;
    }

    public String getDocumentoPedidoImp_iva() {
        return DocumentoPedidoImp_iva;
    }
    public void setDocumentoPedidoImp_iva(String documentopedidoimp_iva) {
        this.DocumentoPedidoImp_iva = documentopedidoimp_iva;
    }

    public String getDocumentoPedidoImp_total() {
        return DocumentoPedidoImp_total;
    }
    public void setDocumentoPedidoImp_total(String documentopedidoimp_total) {
        this.DocumentoPedidoImp_total = documentopedidoimp_total;
    }
    public int getDocumentoPedidoComensales() {
        return DocumentoPedidoComensales;
    }
    public void setDocumentoPedidoComensales(int cabeceraComensales) {
        this.DocumentoPedidoComensales = cabeceraComensales;
    }

}
