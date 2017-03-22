package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 30/12/2016.
 */

public class CabeceraPdd {
    private String CabeceraImp_total;
    private String CabeceraGrupo;
    private String CabeceraEmpresa;
    private int CabeceraPedido;
    private String CabeceraFecha;
    private String CabeceraMesa;
    private int CabeceraComensales;
    private String CabeceraNombre_mesa;
    private String CabeceraEstado;
    private String CabeceraNombre_sta;
    private String CabeceraEmpleado;
    private String CabeceraNombre_empleado;
    private String CabeceraCaja;
    private String CabeceraNombre_caja;
    private String CabeceraCod_turno;
    private String CabeceraNombre_turno;
    private String CabeceraSeccion;
    private String CabeceraNombre_seccion;
    private String CabeceraLocal;
    private String CabeceraNombre_local;
    private String CabeceraImagen_local;
    private String CabeceraObs;
    private int CabeceraIvaincluido;

    public CabeceraPdd(){}

    public String getCabeceraImp_total() {
        return CabeceraImp_total;
    }
    public void setCabeceraImp_total(String cabeceraimp_total) {
        this.CabeceraImp_total = cabeceraimp_total;
    }


    public void setCabeceraMesa(String cabeceraMesa ){
        this.CabeceraMesa = cabeceraMesa;
    }
    public void setCabeceraNombre_mesa(String cabeceraNombre_mesa ){
        this.CabeceraNombre_mesa = cabeceraNombre_mesa;
    }
    public void setCabeceraNombre_turno(String cabeceraNombre_turno ){
        this.CabeceraNombre_turno = cabeceraNombre_turno;
    }
    public void setCabeceraEstado(String cabeceraEstado){
        this.CabeceraEstado = cabeceraEstado;
    }

    public void setCabeceraNombre_sta(String cabeceraNombre_sta){
        this.CabeceraNombre_sta = cabeceraNombre_sta;
    }

    public void setCabeceraNombre_caja(String cabeceraNombre_caja ){
        this.CabeceraNombre_caja = cabeceraNombre_caja;
    }
    public void setCabeceraEmpleado(String cabeceraEmpleado ){
        this.CabeceraEmpleado = cabeceraEmpleado;
    }

    public void setCabeceraNombre_empleado(String cabeceraNombre_empleado ){
        this.CabeceraNombre_empleado = cabeceraNombre_empleado;
    }
    public void setCabeceraNombre_local(String cabeceraNombre_local ){
        this.CabeceraNombre_local = cabeceraNombre_local;
    }
    public void setCabeceraFecha(String cabeceraFecha ){
        this.CabeceraFecha = cabeceraFecha;
    }

    public void setCabeceraImagen_local(String cabeceraImagen_local){
        this.CabeceraImagen_local = cabeceraImagen_local;
    }

    public void setCabeceraObs(String cabeceraObs ){
        this.CabeceraObs = cabeceraObs;
    }
    public void setCabeceraNombre_seccion(String cabeceraNombre_seccion ){
        this.CabeceraNombre_seccion = cabeceraNombre_seccion;
    }

    public String getCabeceraObs(){
        return this.CabeceraObs;
    }

    public String getCabeceraMesa(){
        return this.CabeceraMesa;
    }
    public String getCabeceraNombre_mesa(){
        return this.CabeceraNombre_mesa;
    }

    public String getCabeceraNombre_turno(){
        return this.CabeceraNombre_turno    ;
    }
    public String getCabeceraEstado(){
        return this.CabeceraEstado;
    }
    public String getCabeceraNombre_sta(){
        return this.CabeceraNombre_sta;
    }

    public String getCabeceraNombre_caja(){
        return this.CabeceraNombre_caja;
    }
    public String getCabeceraNombre_seccion(){
        return this.CabeceraNombre_seccion;
    }

    public String getCabeceraEmpleado(){
        return this.CabeceraEmpleado;
    }
    public String getCabeceraNombre_empleado(){
        return this.CabeceraNombre_empleado;
    }

    public String getCabeceraNombre_local(){
        return this.CabeceraNombre_local    ;
    }
    public String getCabeceraFecha(){
        return this.CabeceraFecha;
    }

    public String getCabeceraImagen_local(){
        return this.CabeceraImagen_local;
    }
    public int getCabeceraPedido() {
        return CabeceraPedido;
    }
    public void setCabeceraPedido(int cabeceraPedido) {
        this.CabeceraPedido = cabeceraPedido;
    }

    public int getCabeceraIvaincluido() {
        return CabeceraIvaincluido;
    }
    public void setCabeceraIvaincluido(int cabeceraIvaincluido) {
        this.CabeceraIvaincluido = cabeceraIvaincluido;
    }
    public int getCabeceraComensales() {
        return CabeceraComensales;
    }
    public void setCabeceraComensales(int cabeceraComensales) {
        this.CabeceraComensales = cabeceraComensales;
    }


}
