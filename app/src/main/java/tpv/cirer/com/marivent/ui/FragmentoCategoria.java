package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.Map;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.SerialExecutor;
import tpv.cirer.com.marivent.herramientas.UpdateableFragment;
import tpv.cirer.com.marivent.modelo.Categoria;
import tpv.cirer.com.marivent.modelo.Comida;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.comidas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lcategoria;
import static tpv.cirer.com.marivent.ui.FragmentoCategorias.adaptadorcategorias;

/**
 * Created by JUAN on 21/09/2016.
 */

public class FragmentoCategoria extends Fragment implements  UpdateableFragment {
    //   private boolean listUpdated = false; // init the update checking value

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    private static JSONParserNew jsonParserNew = null;
    private String url = Filtro.getUrl() + "/RellenaListaArticulos.php";

    private static String url_rellenaarticulos;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARE = "articulos";

    public static ArrayList<Comida> lcomida = null;
    private MySerialExecutor mSerialExecutor;

    public static AdaptadorCategorias adaptador;

    public static AdaptadorCategorias adaptador0;
    public static AdaptadorCategorias adaptador1;
    public static AdaptadorCategorias adaptador2;


    private static FragmentoCategoria fragmentcategoria = null;

    public static RecyclerView recViewcomida;
    View rootViewcomida;
    private static final String INDICE_SECCION
            = "com.marivent.FragmentoCategoriasTab.extra.INDICE_SECCION";
    public static int indiceSeccion;
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    //    private AdaptadorCategorias adaptador;
    String cEstado;
    FloatingActionButton btnFab;

    public FragmentoCategoria() {
    }

    public static FragmentoCategoria newInstance(int indiceseccion, String estado) {
        FragmentoCategoria fragmentcategoria = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceseccion);
        args.putString("ESTADO", estado);
        fragmentcategoria.setArguments(args);
        return fragmentcategoria;
    }

    public static FragmentoCategoria getInstance() {
        if (fragmentcategoria == null) {
            fragmentcategoria = new FragmentoCategoria();
        }
        return fragmentcategoria;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lcomida = new ArrayList<Comida>();

        url_rellenaarticulos = Filtro.getUrl() + "/RellenaListaArticulos.php";

        indiceSeccion = getArguments().getInt(INDICE_SECCION);
        cEstado = getArguments().getString("ESTADO", "");

        Categoria categoria = lcategoria.get(indiceSeccion);
        Filtro.setTipo_are(categoria.getCategoriaTipo_are());
        Log.i("are: ", Integer.toString(indiceSeccion) + " " + categoria.getCategoriaTipo_are() + " _ " + lcomida.size());
        //      new AsyncHttpTaskCategorias().execute(url);
////        mSerialExecutor = new MySerialExecutor(getActivity());
////        mSerialExecutor.execute(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewcomida == null) {
/*             setHasOptionsMenu(true);*/
            rootViewcomida = inflater.inflate(R.layout.fragmento_grupo_items, container, false);
            recViewcomida = (RecyclerView) rootViewcomida.findViewById(R.id.reciclador);
            //     recViewlineafacturatickets.setHasFixedSize(true);

            // 2. set layoutManger
//            recViewcomida.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
            layoutManager = new GridLayoutManager(getActivity(), Filtro.getOpgrid()); // Filtro Opgrid dimension del grid.
            recViewcomida.setLayoutManager(layoutManager);

            // 3. create an adapter
            indiceSeccion = getArguments().getInt(INDICE_SECCION);
            Log.i("lcomida: ", Integer.toString(indiceSeccion) + " " + lcomida.size() + " _ " + cEstado);

            adaptador = new AdaptadorCategorias(getActivity(), comidas.get(indiceSeccion), indiceSeccion, cEstado);

            adaptadorcategorias.add(adaptador);

            // 4. set adapter
            recViewcomida.setAdapter(adaptador);
 ////**           recViewcomida.setAdapter(adaptadorcategorias.get(indiceSeccion));

            // 5. set item animator to DefaultAnimator
            recViewcomida.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recViewcomida.addItemDecoration(itemDecoration);

/*            btnFab = (FloatingActionButton)rootViewcomida.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    adaptador.notifyItemRangeChanged(indiceSeccion, lcomida.size());
                    adaptador.notifyDataSetChanged();          // load data here

                }
            });
*/
        }

        return rootViewcomida;
/*
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategoriasOld(Comida.PLATILLOS);
                break;
            case 1:
                adaptador = new AdaptadorCategoriasOld(Comida.BEBIDAS);
                break;
            case 2:
                adaptador = new AdaptadorCategoriasOld(Comida.POSTRES);
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
*/
    }

    @Override
    public void update(int xyzData) {
        // this method will be called for every fragment in viewpager
        // so check if update is for this fragment
/*        if(forMe(xyzData)) {
            // do whatever you want to update your UI
        }
*/
        Log.i("UPDATE-->", Integer.toString(xyzData));

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (recViewcomida != null) {
       ////         adaptador.removeComidas();
       ////         adaptador.addComidas(lcomida);
                Log.i("lcomida adaptador ", Integer.toString(indiceSeccion));
            }
        }else{
            // fragment is no longer visible
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ONRESUME: ","NO");
        if (Filtro.getSearch()) {
            Log.i("ONRESUME: ","SI");
//            new AsyncHttpTaskCategorias().execute(url);

        }
    }

    @Override
    public void onDestroy() {
        if (mSerialExecutor != null) {
            mSerialExecutor.stop();
        }
        super.onDestroy();
    }
    public void onTrigger(int param) {
        mSerialExecutor.queue(getContext(), new MySerialExecutor.MyParams(param));
    }


    private static class MySerialExecutor extends SerialExecutor {
        private final Context mContext;

        public MySerialExecutor(Context context){
            super();
            this.mContext = context;
        }

        @Override
        public void execute(TaskParams params) {
            MyParams myParams = (MyParams) params;

            for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                Comida comida = it.next();
            }
            Log.i("SERIAL COMIDAS SIZE: ", Integer.toString(ActividadPrincipal.comidas.size()));
            for (ArrayList<Comida> list : ActividadPrincipal.comidas) { // iterate -list by list
                Log.i("SERIAL COMIDAS ENTRO: ", Integer.toString(indiceSeccion)+ " "+ lcategoria.get(indiceSeccion).getCategoriaTipo_are());
                for (Comida comida : list) { //iterate element by element in a list
                    if (comida.getTipo_are().equals(lcategoria.get(indiceSeccion).getCategoriaTipo_are()))  {
                        lcomida.add(comida);
                        Log.i("SERIAL COMIDA: ", comida.getNombre());
                        Log.i("ImagenUrl",comida.getTipo_are()+" "+comida.getNombre()+" "+comida.getUrlimagen());
                    }
                }
             }

            // do something...
            // Check for success tag
/*            int success;

            String filtro = "are.GRUPO='" + Filtro.getGrupo() + "'";
            filtro += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
            filtro += " AND are.LOCAL='" + Filtro.getLocal() + "'";
            filtro += " AND are.SECCION='" + Filtro.getSeccion() + "'";
            filtro += " AND are.TIPO_ARE='" + Filtro.getTipo_are() + "'";
            try {
                ContentValues values = new ContentValues();
                values.put("filtro", filtro);
                values.put("tipoare", Filtro.getTipo_are());

                jsonParserNew = new JSONParserNew();
                url_rellenaarticulos = Filtro.getUrl() + "/RellenaListaArticulos.php";
                JSONObject json = jsonParserNew.makeHttpRequest(
                        url_rellenaarticulos, "POST", values);


                // json success tag
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                    // successfully received LineaDocumentoFactura details
                        JSONArray posts = json
                                .getJSONArray(TAG_ARE); // JSON Array

/*                        for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                            Comida comida = it.next();
                            it.remove();
                        }
*/
/*                        Log.i("Longitud Datos: ", Integer.toString(posts.length()));
                        Log.i("SERIALIZABLE: ", Integer.toString(indiceSeccion));

                        for (int ii = 0; ii < posts.length(); ii++) {
                            JSONObject post = posts.optJSONObject(ii);

                            String imagen = post.optString("IMAGEN");
                            String nombre_are = post.optString("NOMBRE_ARE");
                            String tipo_are = post.optString("TIPO_ARE");
                            double preu = post.optDouble("PREU_VTA1");
                            int tiva_id = post.optInt("TIVA_ID");
                            String articulo = post.optString("ARTICULO");
                            Log.i("Articulo: ", tipo_are + " - " + nombre_are);

                            Comida comidaItem = new Comida();
                            comidaItem.setPrecio(Float.parseFloat(String.valueOf(preu)));
                            comidaItem.setNombre(nombre_are);
                            comidaItem.setArticulo(articulo);
                            comidaItem.setTipo_are(tipo_are);
                            comidaItem.setTiva_id(tiva_id);
                            comidaItem.setUrlimagen(Filtro.getUrl() + "/image/" + imagen.trim() );
                            lcomida.add(comidaItem);

                        }

                        for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                            Comida comida = it.next();
                            Log.i("SERIALIZABLE ARTICULO: ", comida.getNombre());

                        }

                        // AÑADIMOS LA CATEGORIA DE COMIDA AL ARRAY DE COMIDAS
                        comidas.add(indiceSeccion,lcomida);
                        Log.i("SERIALIZABLE: ",Integer.toString(indiceSeccion)+" comidas size: "+comidas.get(indiceSeccion).size()+" lcomidas.size: "+lcomida.size());

                    } else {
                    // LineaDocumentoFactura with pid not found
                   }

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
        }
        public static class MyParams extends TaskParams {
            // ... params definition

            public MyParams(int param) {
                // ... params init
            }
        }
    }
    public class AsyncHttpTaskCategorias extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "are.GRUPO='" + Filtro.getGrupo() + "'";
            xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
            xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
            xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
            xWhere += " AND are.TIPO_ARE='" + Filtro.getTipo_are() + "'";
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

                ContentValues values = new ContentValues();
                values.put("filtro", cSql);
                values.put("tipoare", Filtro.getTipo_are());

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

                        for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                            Comida comida = it.next();
                            it.remove();
                        }

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

            /* Download complete. Lets update UI */
            if (result == 1) {
                Log.i("ADAPTADOR COMIDAS", Integer.toString(adaptador.getItemCount()));
                adaptador.notifyDataSetChanged();
            } else {
                Log.e(TAG_ARE, "Failed to fetch data!");
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
            JSONArray posts = response.optJSONArray(TAG_ARE);

            //Initialize array if null
/*            if (null == seccion) {
                seccion = new ArrayList<Seccion>();
                Log.i("Inicializar seccion: ",Integer.toString(seccion.size()));
            }
*/             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                String imagen = post.optString("IMAGEN");
                String nombre_are = post.optString("NOMBRE_ARE");
                String tipo_are = post.optString("TIPO_ARE");
                double preu = post.optDouble("PREU_VTA1");
                String articulo = post.optString("ARTICULO");
                Log.i("Articulo: ", tipo_are + " - " + nombre_are);

                Comida comidaItem = new Comida();
                comidaItem.setPrecio(Float.parseFloat(String.valueOf(preu)));
                comidaItem.setNombre(nombre_are);
                comidaItem.setArticulo(articulo);
                comidaItem.setTipo_are(tipo_are);
                comidaItem.setUrlimagen(Filtro.getUrl() + "/image/" + imagen.trim());
                lcomida.add(comidaItem);

            }
            for (Iterator<Comida> it = lcomida.iterator(); it.hasNext();){
                Comida comida = it.next();
                Log.i("SERIALIZABLE ARTICULO: ", comida.getNombre());

            }

            // AÑADIMOS LA CATEGORIA DE COMIDA AL ARRAY DE COMIDAS
            comidas.add(indiceSeccion,lcomida);
            Log.i("SERIALIZABLE: ",Integer.toString(indiceSeccion)+" comidas size: "+comidas.get(indiceSeccion).size()+" lcomidas.size: "+lcomida.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
