package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 16/10/2016.
 */

public class Empleado {
    private String EmpleadoEmpleado;
    private String EmpleadoNombre_Empleado;
    private String EmpleadoUsername;
    private int EmpleadoActivo;
    private int EmpleadoId;
    public Empleado(){}

    public Empleado(String Empleado, String nombre, String username){
        this.EmpleadoEmpleado = Empleado;
        this.EmpleadoNombre_Empleado = nombre;
        this.EmpleadoUsername = username;
    }
    public int getEmpleadoId() {
        return EmpleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.EmpleadoId = empleadoId;
    }

    public int getEmpleadoActivo() {
        return EmpleadoActivo;
    }

    public void setEmpleadoActivo(int empleadoActivo) {
        this.EmpleadoActivo = empleadoActivo;
    }

    public String getEmpleadoNombre_Empleado() {
        return EmpleadoNombre_Empleado;
    }

    public void setEmpleadoNombre_Empleado(String empleadoNombre_Empleado) {
        this.EmpleadoNombre_Empleado = empleadoNombre_Empleado;
    }

    public String getEmpleadoEmpleado() {
        return EmpleadoEmpleado;
    }

    public void setEmpleadoEmpleado(String empleadoEmpleado) {
        this.EmpleadoEmpleado = empleadoEmpleado;
    }
    public String getEmpleadoUsername() {
        return EmpleadoUsername;
    }

    public void setEmpleadoUsername(String empleadoUsername) {
        this.EmpleadoUsername = empleadoUsername;
    }

}
