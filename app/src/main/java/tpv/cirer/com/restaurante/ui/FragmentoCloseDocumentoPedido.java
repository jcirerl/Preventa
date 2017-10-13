package tpv.cirer.com.restaurante.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.conexion_http_post.JSONParserNew;
import tpv.cirer.com.restaurante.herramientas.DividerItemDecoration1;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.DocumentoPedido;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.empleadosList;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.position_usuario;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.usuario_identificado;

/**
 * Created by JUAN on 16/11/2016.
 */

public class FragmentoCloseDocumentoPedido extends Fragment {
    private String title;
    private int page;

    public static final String TAG = "Lista PDD";
    //   public List<DocumentoPedido> documentopedidos = new ArrayList<DocumentoPedido>();
    public static List<DocumentoPedido> ldocumentopedido;
    private String url = Filtro.getUrl()+"/RellenaListaPDD.php";

    public static RecyclerView recViewopendocumentopedido;
    public static AdaptadorDocumentoPedidoHeaderAsus adaptadordocumentopedidoasus;
    public static AdaptadorDocumentoPedidoHeaderSony adaptadordocumentopedidosony;
    public static AdaptadorDocumentoPedidoHeaderOchoPulgadas adaptadorDocumentopedidoochopulgadas;

    View rootViewopendocumentopedido;
    FloatingActionButton btnFab;
    Context cont;

    ProgressDialog pDialog;
    private static String url_create_pdd;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    private static FragmentoCloseDocumentoPedido CloseDocumentoPedido = null;

    public static FragmentoCloseDocumentoPedido newInstance(int page, String title, String estado) {
        FragmentoCloseDocumentoPedido CloseDocumentoPedido = new FragmentoCloseDocumentoPedido();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("estado", estado);
        CloseDocumentoPedido.setArguments(args);
        return CloseDocumentoPedido;
    }

    public static FragmentoCloseDocumentoPedido getInstance(){
        if(CloseDocumentoPedido == null){
            CloseDocumentoPedido = new FragmentoCloseDocumentoPedido();
        }
        return CloseDocumentoPedido;
    }

    public FragmentoCloseDocumentoPedido() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ldocumentopedido = new ArrayList<DocumentoPedido>();
        //Floating Action Button
        cont = getActivity(); // lee contexto actividad
        Filtro.setTag_fragment("FragmentoCloseDocumentoPedido");

        url_create_pdd = Filtro.getUrl()+"/crea_pdd.php";
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        Filtro.setEstado(getArguments().getString("estado"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewopendocumentopedido == null) {
/*             setHasOptionsMenu(true);*/
            rootViewopendocumentopedido = inflater.inflate(R.layout.lista, container, false);
            recViewopendocumentopedido = (RecyclerView) rootViewopendocumentopedido.findViewById(R.id.RecView);
            //     recViewopendocumentopedidos.setHasFixedSize(true);

            // 2. set layoutManger
            recViewopendocumentopedido.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadordocumentopedidoasus = new AdaptadorDocumentoPedidoHeaderAsus(getActivity(),ldocumentopedido);
                    break;
                case 1:
                    adaptadordocumentopedidosony = new AdaptadorDocumentoPedidoHeaderSony(getActivity(),ldocumentopedido);
                    break;
                case 2:
                    adaptadorDocumentopedidoochopulgadas = new AdaptadorDocumentoPedidoHeaderOchoPulgadas(getActivity(),ldocumentopedido);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewopendocumentopedido.setAdapter(adaptadordocumentopedidoasus);
                    break;
                case 1:
                    recViewopendocumentopedido.setAdapter(adaptadordocumentopedidosony);
                    break;
                case 2:
                    recViewopendocumentopedido.setAdapter(adaptadorDocumentopedidoochopulgadas);
                    break;
            }

            // 5. set item animator to DefaultAnimator
            recViewopendocumentopedido.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*                        RecyclerView.ItemDecoration itemDecoration =
                                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/                        RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());
            recViewopendocumentopedido.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewopendocumentopedido.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    new CreaDocumentoPedido().execute();
                    Snackbar.make(view, ActividadPrincipal.getPalabras("No se puede crear")+" "+ActividadPrincipal.getPalabras("Documento")+" "+ActividadPrincipal.getPalabras("Pedido"), Snackbar.LENGTH_SHORT).show();
                }
            });
            setUserVisibleHint(true);
        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        return rootViewopendocumentopedido;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("CloseDocumentoPedido", "Not visible anymore.");
                Filtro.setTag_fragment("FragmentoOpenDocumentoPedido");
                // TODO stop audio playback
            }else{
                Log.d("CloseDocumentoPedido", "Yes visible anymore.");
                Filtro.setTag_fragment("FragmentoCloseDocumentoPedido");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new AsyncHttpTaskDocumentoPedido(cont).execute(url);
    }

    public class AsyncHttpTaskDocumentoPedido extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskDocumentoPedido(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if(mContext!=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Pedidos")+"..");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_GRUPO().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND pdd.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND pdd.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND pdd.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND pdd.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_TURNO().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getEmpleado().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPLEADO().trim()))) {
                if(usuario_identificado) {
                    if (!(ActividadPrincipal.InUsuarios.equals(""))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                        } else {
                            xWhere += " AND pdd.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                        }
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.EMPLEADO IN ('" + empleadosList.get(position_usuario).getEmpleadoEmpleado() + "')";
                    } else {
                        xWhere += " AND pdd.EMPLEADO IN ('" +empleadosList.get(position_usuario).getEmpleadoEmpleado() + "')";
                    }
                }
/*
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                } else {
                    xWhere += " AND pdd.EMPLEADO='" + Filtro.getEmpleado() + "'";
                }
 */
            }
            if(!(Filtro.getMesa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.MESA='" + Filtro.getMesa() + "'";
                } else {
                    xWhere += " AND pdd.MESA='" + Filtro.getMesa() + "'";
                }
            }
            if(!(Filtro.getFecha().equals(""))) {
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.FECHA=CONVERT(DATETIME, '" +Filtro.getFecha().substring(0,10)+" 00:00:00" + "', 120)";
                    } else {
                        xWhere += " AND pdd.FECHA=CONVERT(DATETIME, '" + Filtro.getFecha().substring(0,10)+" 00:00:00" + "', 120)";
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.FECHA='" + Filtro.getFecha() + "'";
                    } else {
                        xWhere += " AND pdd.FECHA='" + Filtro.getFecha() + "'";
                    }
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE pdd.ESTADO>='13'";
            } else {
                xWhere += " AND pdd.ESTADO>='13'";
            }
            if(!(ActividadPrincipal.InUsuarios.equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                } else {
                    xWhere += " AND pdd.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                }
            }
            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                // for Get request
                ///           urlConnection.setRequestMethod("GET");

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
//                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//                params1.add(new BasicNameValuePair("filtro", cSql));

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(getQuery(params1));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                Log.i("STATUS CODE: ", Integer.toString(urlConnection.getResponseCode()) + " - " + urlConnection.getResponseMessage());
                // 200 represents HTTP OK
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    Log.i("JSON-->", response.toString());
                    Log.i("Longitud Antes: ",Integer.toString(ldocumentopedido.size()));
/*                    for (int i = 0; i < ldocumentopedido.size(); i++) {
                        ldocumentopedido.remove(ldocumentopedido.get(i));
                    }
*/
                    for (Iterator<DocumentoPedido> it = ldocumentopedido.iterator(); it.hasNext();){
                        DocumentoPedido documentopedido = it.next();
//                        if (student.equals(studentToCompare)){
                        it.remove();
//                            return true;
//                        }
                    }

/*                Iterator<DocumentoPedido> itr = documentopedido.iterator();

// remove all even numbers
                    while (itr.hasNext()) {
                        DocumentoPedido number = itr.next();
                           itr.remove();
                    }
*/
                    Log.i("Longitud Despues: ",Integer.toString(ldocumentopedido.size()));

                    parseResult(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

//                Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //    setProgressBarIndeterminateVisibility(false);
            if(mContext!=null) {
                pDialog.dismiss();
            }

            /* Download complete. Lets update UI */
            if (result == 1) {
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR PDD", Integer.toString(adaptadordocumentopedidoasus.getItemCount()));
                        adaptadordocumentopedidoasus.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR PDD", Integer.toString(adaptadordocumentopedidosony.getItemCount()));
                        adaptadordocumentopedidosony.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR PDD", Integer.toString(adaptadorDocumentopedidoochopulgadas.getItemCount()));
                        adaptadorDocumentopedidoochopulgadas.notifyDataSetChanged();
                        break;
                }
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }
    //   private String getQuery(List<NameValuePair> params1) throws UnsupportedEncodingException
    private String getQuery(ContentValues values) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : values.valueSet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }
        Log.i("Result QUERY", result.toString());

        return result.toString();
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                DocumentoPedido documentopedidoItem = new DocumentoPedido();
                documentopedidoItem.setDocumentoPedidoId(post.optInt("ID"));
                documentopedidoItem.setDocumentoPedidoPedido(post.optInt("PEDIDO"));
                documentopedidoItem.setDocumentoPedidoFecha(post.optString("FECHA"));
                documentopedidoItem.setDocumentoPedidoMesa(post.optString("MESA"));
                documentopedidoItem.setDocumentoPedidoNombre_Mesa(post.optString("NOMBRE_MESA"));
                documentopedidoItem.setDocumentoPedidoEstado(post.optString("ESTADO"));
                documentopedidoItem.setDocumentoPedidoEmpleado(post.optString("EMPLEADO"));
                documentopedidoItem.setDocumentoPedidoCaja(post.optString("CAJA"));
                documentopedidoItem.setDocumentoPedidoCod_turno(post.optString("COD_TURNO"));
                documentopedidoItem.setDocumentoPedidoObs(post.optString("OBS"));
                documentopedidoItem.setDocumentoPedidoLineas(post.optInt("LINEAS"));
                documentopedidoItem.setDocumentoPedidoUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                documentopedidoItem.setDocumentoPedidoComensales(post.optInt("COMENSALES"));

                ldocumentopedido.add(documentopedidoItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
