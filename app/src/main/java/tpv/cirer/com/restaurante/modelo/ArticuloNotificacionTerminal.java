package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 28/10/2017.
 */

public class ArticuloNotificacionTerminal {
    private int ArticuloId;
    private String ArticuloGrupo;
    private String ArticuloEmpresa;
    private String ArticuloLocal;
    private String ArticuloSeccion;
    private String ArticuloArticulo;
    private String ArticuloTerminal;

    public ArticuloNotificacionTerminal(
                    int id,
                    String grupo,
                    String empresa,
                    String local,
                    String seccion,
                    String articulo,
                    String terminal) {
        this.ArticuloId = id;
        this.ArticuloGrupo = grupo;
        this.ArticuloEmpresa = empresa;
        this.ArticuloLocal = local;
        this.ArticuloSeccion = seccion;
        this.ArticuloArticulo = articulo;
        this.ArticuloTerminal = terminal;
    }
    public ArticuloNotificacionTerminal() {
    }
    public int getArticuloId() {
        return ArticuloId;
    }
    public void setArticuloId(int articuloId) {
        this.ArticuloId = articuloId;
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


    public String getArticuloTerminal() {
        return ArticuloTerminal;
    }
    public void setArticuloTerminal(String articuloTerminal) {
        this.ArticuloTerminal = articuloTerminal;
    }

    public String getArticuloArticulo() {
        return ArticuloArticulo;
    }
    public void setArticuloArticulo(String articuloArticulo) {
        this.ArticuloArticulo = articuloArticulo;
    }
}
