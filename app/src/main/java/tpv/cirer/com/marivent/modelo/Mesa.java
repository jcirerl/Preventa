package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 18/10/2016.
 */

public class Mesa {
    private String MesaMesa;
    private String MesaNombre_Mesas;
    private int MesaApertura;
    private int MesaId;
    private String MesaUrlimagen;
    private float MesaXCoordenate;
    private float MesaYCoordenate;
    private int MesaModificado;
    private int MesaPedidos;
    private int MesaFacturas;
    private int MesaComensales;
    private String MesaT_mesa;
    private String MesaMensaje;

    public Mesa(){}

    public Mesa(String Mesa, String nombre, String t_mesa){
        this.MesaMesa = Mesa;
        this.MesaNombre_Mesas = nombre;
        this.MesaT_mesa = t_mesa;
     }
    public Mesa(String Mesa, String nombre, int comensales){
        this.MesaMesa = Mesa;
        this.MesaNombre_Mesas = nombre;
        this.MesaComensales = comensales;
    }
    public Mesa(String Mesa, String nombre, int comensales, String mensaje){
        this.MesaMesa = Mesa;
        this.MesaNombre_Mesas = nombre;
        this.MesaComensales = comensales;
        this.MesaMensaje = mensaje;
    }

    public float getMesaXCoordenate() {
        return MesaXCoordenate;
    }

    public void setMesaXCoordenate(float mesaxcoordenate) {
        this.MesaXCoordenate = mesaxcoordenate;
    }
    public float getMesaYCoordenate() {
        return MesaYCoordenate;
    }

    public void setMesaYCoordenate(float mesaycoordenate) {
        this.MesaYCoordenate = mesaycoordenate;
    }
    public int getMesaFacturas() {
        return MesaFacturas;
    }

    public void setMesaFacturas(int mesaFacturas) {
        this.MesaFacturas = mesaFacturas;
    }
    public int getMesaPedidos() {
        return MesaPedidos;
    }

    public void setMesaPedidos(int mesaPedidos) {
        this.MesaPedidos = mesaPedidos;
    }

    public int getMesaId() {
        return MesaId;
    }

    public void setMesaId(int mesaId) {
        this.MesaId = mesaId;
    }
    public int getMesaModificado() {
        return MesaModificado;
    }

    public void setMesaModificado(int mesaModificado) {
        this.MesaModificado = mesaModificado;
    }

    public int getMesaApertura() {
        return MesaApertura;
    }

    public void setMesaApertura(int mesaApertura) {
        this.MesaApertura = mesaApertura;
    }
    public String getMesaMesa() {
        return MesaMesa;
    }

    public void setMesaMesa(String MesaMesa) {
        this.MesaMesa = MesaMesa;
    }

    public String getMesaNombre_Mesas() {
        return MesaNombre_Mesas;
    }

    public void setMesaNombre_Mesas(String mesaNombre_Mesas) {
        this.MesaNombre_Mesas = mesaNombre_Mesas;
    }
    public String getMesaT_mesa() {
        return MesaT_mesa;
    }

    public void setMesaT_mesa(String mesaT_mesa) {
        this.MesaT_mesa = mesaT_mesa;
    }

    public String getMesaMensaje() {
        return MesaMensaje;
    }

    public void setMesaMensaje(String MesaMensaje) {
        this.MesaMensaje = MesaMensaje;
    }
    public String getMesaUrlimagen() {
        return MesaUrlimagen;
    }

    public void setMesaUrlimagen(String mesaurlimagen) {
        this.MesaUrlimagen = mesaurlimagen;
    }
    public int getMesaComensales() {
        return MesaComensales;
    }
    public void setMesaComensales(int mesaComensales) {
        this.MesaComensales = mesaComensales;
    }

}
