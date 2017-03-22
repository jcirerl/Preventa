package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 21/11/2016.
 */

public class Dcj {
    private String DcjGrupo;
    private String DcjEmpresa;
    private String DcjLocal;
    private String DcjSeccion;
    private String DcjCaja;
    private String DcjCod_turno;
    private String DcjNombre_Dcj;
    private String DcjSaldo_final;
    private String DcjFecha_open;
    private String DcjFecha_close;
    private String DcjFecha_Apertura;
    private String DcjSaldo_inicio;
    private String DcjUrlimagen;
    private int DcjApertura;
    private int DcjId;
    private int DcjIvaIncluido;

    public Dcj(){}

    public Dcj(String fecha_apertura, String nombre){
        this.DcjFecha_Apertura = fecha_apertura;
        this.DcjNombre_Dcj = nombre;
    }
    public String getDcjGrupo() {
        return DcjGrupo;
    }
    public void setDcjGrupo(String dcjGrupo) {
        this.DcjGrupo = dcjGrupo;
    }

    public String getDcjEmpresa() {
        return DcjEmpresa;
    }
    public void setDcjEmpresa(String dcjEmpresa) {
        this.DcjEmpresa = dcjEmpresa;
    }

    public String getDcjSeccion() {
        return DcjSeccion;
    }
    public void setDcjSeccion(String dcjSeccion) {
        this.DcjSeccion = dcjSeccion;
    }

    public String getDcjLocal() {
        return DcjLocal;
    }
    public void setDcjLocal(String dcjLocal) {
        this.DcjLocal = dcjLocal;
    }

    public String getDcjCaja() {
        return DcjCaja;
    }
    public void setDcjCaja(String dcjCaja) {
        this.DcjCaja = dcjCaja;
    }


    public String getDcjCod_turno() {
        return DcjCod_turno;
    }

    public void setDcjCod_turno(String dcjCod_turno) {
        this.DcjCod_turno = dcjCod_turno;
    }


    public int getDcjId() {
        return DcjId;
    }

    public void setDcjId(int dcjId) {
        this.DcjId = dcjId;
    }
    public int getDcjApertura() {
        return DcjApertura;
    }

    public void setDcjApertura(int dcjApertura) {
        this.DcjApertura = dcjApertura;
    }

 
    public String getDcjNombre_Dcj() {
        return DcjNombre_Dcj;
    }

    public void setDcjNombre_Dcj(String dcjNombre_Dcj) {
        this.DcjNombre_Dcj = dcjNombre_Dcj;
    }

    public void setDcjUrlimagenopen(String dcjurlimagenopen) {
        this.DcjUrlimagen = dcjurlimagenopen;
    }
    public String getDcjUrlimagenopen() {
        return DcjUrlimagen;
    }
    public String getDcjUrlimagenclose() {
        return DcjUrlimagen;
    }

    public void setDcjUrlimagenclose(String dcjurlimagenclose) {
        this.DcjUrlimagen = dcjurlimagenclose;
    }
    public String getDcjFecha_Apertura() {
        return DcjFecha_Apertura;
    }

    public void setDcjFecha_Apertura(String DcjFecha_Apertura) {
        this.DcjFecha_Apertura = DcjFecha_Apertura;
    }
    public String getDcjFecha_open() {
        return DcjFecha_open;
    }

    public void setDcjFecha_open(String dcjFecha_open) {
        this.DcjFecha_open = dcjFecha_open;
    }
    public String getDcjFecha_close() {
        return DcjFecha_close;
    }

    public void setDcjFecha_close(String dcjFecha_close) {
        this.DcjFecha_close = dcjFecha_close;
    }

    public int getDcjIvaIncluido() {
        return DcjIvaIncluido;
    }

    public void setDcjIvaIncluido(int dcjIvaIncluido) {
        this.DcjIvaIncluido = dcjIvaIncluido;
    }

    public String getDcjSaldo_inicio() {
        return DcjSaldo_inicio;
    }

    public void setDcjSaldo_inicio(String dcjSaldo_inicio) {
        this.DcjSaldo_inicio = dcjSaldo_inicio;
    }
    public String getDcjSaldo_final() {
        return DcjSaldo_final;
    }

    public void setDcjSaldo_final(String dcjSaldo_final) {
        this.DcjSaldo_final = dcjSaldo_final;
    }

}
