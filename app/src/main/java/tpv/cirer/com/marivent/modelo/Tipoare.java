package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 05/08/2017.
 */

public class Tipoare {
    private int TipoareId;
    private String TipoareGrupo;
    private String TipoareEmpresa;
    private String TipoareLocal;
    private String TipoareSeccion;
    private String TipoareNombre_tipoare;
    private String TipoareUrlimagen;
    private String TipoareTipo_are;

    public Tipoare(int id,
                    String tipo_are,
                    String nombre,
                    String urlimagen) {
        this.TipoareId = id;
        this.TipoareNombre_tipoare = nombre;
        this.TipoareUrlimagen = urlimagen;
        this.TipoareTipo_are = tipo_are;
    }
    public Tipoare() {
    }
    public int getTipoareId() {
        return TipoareId;
    }
    public void setTipoareId(int tipoareId) {
        this.TipoareId = getTipoareId();
    }



    public String getTipoareGrupo() {
        return TipoareGrupo;
    }
    public void setTipoareGrupo(String tipoareGrupo) {
        this.TipoareGrupo = tipoareGrupo;
    }

    public String getTipoareEmpresa() {
        return TipoareEmpresa;
    }
    public void setTipoareEmpresa(String tipoareEmpresa) {
        this.TipoareEmpresa = tipoareEmpresa;
    }

    public String getTipoareSeccion() {
        return TipoareSeccion;
    }
    public void setTipoareSeccion(String tipoareSeccion) {
        this.TipoareSeccion = tipoareSeccion;
    }

    public String getTipoareLocal() {
        return TipoareLocal;
    }
    public void setTipoareLocal(String tipoareLocal) {
        this.TipoareLocal = tipoareLocal;
    }


    public String getTipoareTipo_are() {
        return TipoareTipo_are;
    }
    public void setTipoareTipo_are(String lineaDocumentoPedidoTipo_are) {
        this.TipoareTipo_are = lineaDocumentoPedidoTipo_are;
    }

    public String getTipoareNombre_tipoare() {
        return TipoareNombre_tipoare;
    }
    public void setTipoareNombre_tipoare(String tipoareNombre) {
        this.TipoareNombre_tipoare = tipoareNombre;
    }

    public String getTipoareUrlimagen() {
        return TipoareUrlimagen;
    }

    public void setTipoareUrlimagen(String tipoareurlimagen) {
        this.TipoareUrlimagen = tipoareurlimagen;
    }

}
