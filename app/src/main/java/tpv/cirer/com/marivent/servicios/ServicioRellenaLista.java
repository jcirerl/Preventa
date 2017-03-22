package tpv.cirer.com.marivent.servicios;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import tpv.cirer.com.marivent.conexion_http_post.Post;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.ui.FragmentoCategoria;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JUAN on 07/10/2016.
 */

public class ServicioRellenaLista {

    final Handler handle = new Handler();
    private JSONArray datos;
    private String xWhere = "";
    private String cSql = "";
    public void miThread(final String tipo){
        Thread t = new Thread(){
            @Override
            public void run(){
                Post post = null;
                try{
                    cSql = "";
                    xWhere = "are.GRUPO='" + Filtro.getGrupo() + "'";
                    xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
                    xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
                    xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
                    xWhere += " AND are.TIPO_ARE='" + Filtro.getTipo_are() + "'";
                    cSql += xWhere;
                    if(cSql.equals("")) {
                        cSql="Todos";
                    }
                    Log.i("Sql Lista",cSql);
                    ArrayList<String> parametros = new ArrayList<String>();
                    parametros.add("filtro");
                    parametros.add(cSql);
                    //Llamada a Servidor Web PHP
                    post = new Post();
                    String urlServidor = Filtro.getUrl()+"/RellenaListaArticulos1.php";
                    datos = post.getServerData(parametros,urlServidor);
                } catch (Exception e) {
                    Toast.makeText(
                            FragmentoCategoria.getInstance().getActivity(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                handle.post(proceso);
            }
        };
        t.start();
    }

    final Runnable proceso = new Runnable(){
        @Override
        public void run(){
            try {
                if(datos!=null && datos.length()>0){
                    for(int i = 0 ; i <datos.length() ; i++){
                        JSONObject json_data = datos.getJSONObject(i);
                        String imagen = json_data.getString("IMAGEN");
                        String nombre_are = json_data.getString("NOMBRE_ARE");
                        String tipo_are = json_data.getString("TIPO_ARE");
                        double preu = json_data.getDouble("PREU_VTA1");
       // /                FragmentoCategoria.getInstance().anadeComida(imagen,nombre_are,tipo_are,preu);
                    }
       	///			FragmentoCategoria.getInstance().rellenaAdapter();
                }else{
                    Toast.makeText(
                            FragmentoCategoria.getInstance().getActivity(),
                            "NO HAY REGISTROS", Toast.LENGTH_SHORT).show();

                }
            }catch (Exception e) {}
        }};
}
