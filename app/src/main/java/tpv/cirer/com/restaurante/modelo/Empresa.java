package tpv.cirer.com.restaurante.modelo;

/**
 * Created by JUAN on 12/06/2015.
 */
public class Empresa {

    private String EmpresaEmpresa;
    private String EmpresaRazon;

    public Empresa(){}

    public Empresa(String empresa, String nombre){
        this.EmpresaEmpresa = empresa;
        this.EmpresaRazon = nombre;
    }

    public void setEmpresaEmpresa(String empresaEmpresa ){
        this.EmpresaEmpresa = empresaEmpresa;
    }

    public void setEmpresaRazon(String empresaRazon){
        this.EmpresaRazon = empresaRazon;
    }

    public String getEmpresaEmpresa(){
        return this.EmpresaEmpresa;
    }

    public String getEmpresaRazon(){
        return this.EmpresaRazon;
    }

}