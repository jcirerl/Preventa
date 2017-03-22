package tpv.cirer.com.marivent.modelo;

/**
 * Created by JUAN on 20/10/2016.
 */

public class Grupo {

    private String GrupoGrupo;
    private String GrupoNombre;

    public Grupo(){}

    public Grupo(String grupo, String nombre){
        this.GrupoGrupo = grupo;
        this.GrupoNombre = nombre;
    }

    public void setGrupoGrupo(String grupoGrupo ){
        this.GrupoGrupo = grupoGrupo;
    }

    public void setGrupoNombre(String grupoNombre){
        this.GrupoNombre = grupoNombre;
    }

    public String getGrupoGrupo(){
        return this.GrupoGrupo;
    }

    public String getGrupoNombre(){
        return this.GrupoNombre;
    }


}
