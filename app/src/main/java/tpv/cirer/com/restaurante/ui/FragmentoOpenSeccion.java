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
import tpv.cirer.com.restaurante.herramientas.DividerItemDecoration1;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.Seccion;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 14/09/2016.
 */
public class FragmentoOpenSeccion  extends Fragment    {
    private String title;
    private int page;
    public static final String TAG = "Lista Secciones";
    //   public List<Seccion> seccions = new ArrayList<Seccion>();
    public static List<Seccion> lseccion;
    private String url = Filtro.getUrl()+"/RellenaListaSeccion.php";
    ProgressDialog pDialog;
    //public FragmentManager fragment;

    public static RecyclerView recViewseccion;
    public static AdaptadorSeccionHeader adaptadorseccion;
    public static AdaptadorSeccionHeader_ochopulgadas adaptadorseccionochopulgadas;

    View rootViewseccion;
    FloatingActionButton btnFab;
    Context cont;
    private static FragmentoOpenSeccion OpenSeccion = null;

    public static FragmentoOpenSeccion newInstance(int page, String title) {
        FragmentoOpenSeccion OpenSeccion = new FragmentoOpenSeccion();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        OpenSeccion.setArguments(args);
        return OpenSeccion;
    }

    public static FragmentoOpenSeccion getInstance(){
        if(OpenSeccion == null){
            OpenSeccion = new FragmentoOpenSeccion();
        }
        return OpenSeccion;
    }

    public FragmentoOpenSeccion() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lseccion = new ArrayList<Seccion>();
        cont = getActivity(); // lee contexto actividad

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        //Floating Action Button
        Filtro.setTag_fragment("FragmentoOpenSeccion");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (rootViewseccion == null) {
/*             setHasOptionsMenu(true);*/
            rootViewseccion = inflater.inflate(R.layout.lista, container, false);
            recViewseccion = (RecyclerView) rootViewseccion.findViewById(R.id.RecView);
            //     recViewseccions.setHasFixedSize(true);

            // 2. set layoutManger
            recViewseccion.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
//            adaptadorseccion = new AdaptadorSeccionHeader(getActivity(),lseccion);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadorseccion = new AdaptadorSeccionHeader(getActivity(),lseccion);
                    break;
                case 1:
                    adaptadorseccion = new AdaptadorSeccionHeader(getActivity(),lseccion);
                    break;
                case 2:
                    adaptadorseccionochopulgadas = new AdaptadorSeccionHeader_ochopulgadas(getActivity(),lseccion);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewseccion.setAdapter(adaptadorseccion);
                    break;
                case 1:
                    recViewseccion.setAdapter(adaptadorseccion);
                    break;
                case 2:
                    recViewseccion.setAdapter(adaptadorseccionochopulgadas);
                    break;
            }

//            recViewseccion.setAdapter(adaptadorseccion);

            // 5. set item animator to DefaultAnimator
            recViewseccion.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());

            recViewseccion.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewseccion.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, ActividadPrincipal.getPalabras("No se puede crear")+" "+ActividadPrincipal.getPalabras("Seccion"), Snackbar.LENGTH_SHORT).show();
                }
            });

        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        Log.i("Page",Integer.toString(page)+" "+title);
//        Filtro.setTag_fragment(title);

        return rootViewseccion;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("FragmentoOpenSeccion", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("FragmentoOpenSeccion", "Yes visible anymore.");
                onResume();
            }
        }
    }
      @Override
    public void onResume() {
        super.onResume();
        new AsyncHttpTaskSeccion(cont).execute(url);
    }

     public class AsyncHttpTaskSeccion extends AsyncTask<String, Void, Integer> {
         private Context mContext;
         public AsyncHttpTaskSeccion(Context context){
             this.mContext=context;
         }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if(mContext!=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Secciones")+"..");
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
                    xWhere += " WHERE sec.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND sec.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND sec.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND sec.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE sec.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND sec.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            xWhere += " AND sec.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
            xWhere += " AND sec.APERTURA="+lparam.get(0).getDEFAULT_VALOR_ON_APERTURA();
            xWhere += " AND sec.SECCION<>'"+ lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()+"'"; // SECCION 00 NO DEBE TENERLA EN CUENTA

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
                    Log.i("Longitud Antes: ",Integer.toString(lseccion.size()));
/*                    for (int i = 0; i < lseccion.size(); i++) {
                        lseccion.remove(lseccion.get(i));
                    }
*/
                    for (Iterator<Seccion> it = lseccion.iterator(); it.hasNext();){
                        Seccion seccion = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lseccion.size()));

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
///                adaptadorseccion.notifyDataSetChanged();
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        ActividadPrincipal.itemseccion.setText(Integer.toString(adaptadorseccion.getItemCount()-1));
                        Log.i("ADAPTADOR SECCION", Integer.toString(adaptadorseccion.getItemCount()));
                        adaptadorseccion.notifyDataSetChanged();
                        break;
                    case 1:
                        ActividadPrincipal.itemseccion.setText(Integer.toString(adaptadorseccion.getItemCount()-1));
                        Log.i("ADAPTADOR SECCION", Integer.toString(adaptadorseccion.getItemCount()));
                        adaptadorseccion.notifyDataSetChanged();
                        break;
                    case 2:
                        ActividadPrincipal.itemseccion.setText(Integer.toString(adaptadorseccionochopulgadas.getItemCount()-1));
                        Log.i("ADAPTADOR SECCION", Integer.toString(adaptadorseccionochopulgadas.getItemCount()));
                        adaptadorseccionochopulgadas.notifyDataSetChanged();
                        break;
                }

                if(mContext!=null) {
                    if (Integer.parseInt(ActividadPrincipal.itemseccion.getText().toString()) == 0) {
                        ActividadPrincipal.itemseccion.setTextColor(Filtro.getColorItemZero());
                    } else {
                        ActividadPrincipal.itemseccion.setTextColor(Filtro.getColorItem());
                    }
                }
//                adaptadorseccion.notifyDataSetChanged();
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

/*
        for (NameValuePair pair : params1)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
*/
        return result.toString();
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            //Initialize array if null
/*            if (null == seccion) {
                seccion = new ArrayList<Seccion>();
                Log.i("Inicializar seccion: ",Integer.toString(seccion.size()));
            }
*/             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Seccion seccionItem = new Seccion();
                seccionItem.setSeccionId(post.optInt("ID"));
                seccionItem.setSeccionNombre_Sec(post.optString("NOMBRE"));
                seccionItem.setSeccionSeccion(post.optString("SECCION"));
                seccionItem.setSeccionIvaIncluido(post.optInt("IVAINCLUIDO"));
                seccionItem.setSeccionApertura(post.optInt("APERTURA"));
                seccionItem.setSeccionFecha_Apertura(post.optString("FECHA_APERTURA"));
                seccionItem.setSeccionUrlimagenopen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_OPEN").trim());
                lseccion.add(seccionItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
