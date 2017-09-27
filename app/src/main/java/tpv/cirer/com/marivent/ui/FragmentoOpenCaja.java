package tpv.cirer.com.marivent.ui;

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

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration1;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.Caja;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 07/11/2016.
 */

public class FragmentoOpenCaja  extends Fragment {
    private String title;
    private int page;
    public static final String TAG = "Lista Cajas";
    //   public List<Caja> cajas = new ArrayList<Caja>();
    public static List<Caja> lcaja;
    private String url = Filtro.getUrl()+"/RellenaListaCaja.php";
    //    ProgressDialog pDialog;
    //public FragmentManager fragment;
    ProgressDialog pDialog;

    public static RecyclerView recViewcaja;
    public static AdaptadorCajaHeader adaptadorcaja;
    View rootViewcaja;
    FloatingActionButton btnFab;
    Context cont;
    private static FragmentoOpenCaja OpenCaja = null;

    public static FragmentoOpenCaja newInstance(int page, String title) {
        FragmentoOpenCaja OpenCaja = new FragmentoOpenCaja();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        OpenCaja.setArguments(args);
        return OpenCaja;
    }

    public static FragmentoOpenCaja getInstance(){
        if(OpenCaja == null){
            OpenCaja = new FragmentoOpenCaja();
        }
        return OpenCaja;
    }

    public FragmentoOpenCaja() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lcaja = new ArrayList<Caja>();
        cont = getActivity(); // lee contexto actividad

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        //Floating Action Button
        Filtro.setTag_fragment("FragmentoOpenCaja");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewcaja == null) {
/*             setHasOptionsMenu(true);*/
            rootViewcaja = inflater.inflate(R.layout.lista, container, false);
            recViewcaja = (RecyclerView) rootViewcaja.findViewById(R.id.RecView);
            //     recViewcajas.setHasFixedSize(true);

            // 2. set layoutManger
            recViewcaja.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            adaptadorcaja = new AdaptadorCajaHeader(getActivity(),lcaja);

            // 4. set adapter
            recViewcaja.setAdapter(adaptadorcaja);

            // 5. set item animator to DefaultAnimator
            recViewcaja.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());

            recViewcaja.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewcaja.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, ActividadPrincipal.getPalabras("No se puede crear")+" "+ActividadPrincipal.getPalabras("Caja"), Snackbar.LENGTH_SHORT).show();
                }
            });

        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        Log.i("Page",Integer.toString(page)+" "+title);
//        Filtro.setTag_fragment(title);

        return rootViewcaja;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("FragmentoOpenCaja", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("FragmentoOpenCaja", "Yes visible anymore.");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new FragmentoOpenCaja.AsyncHttpTaskCaja(cont).execute(url);
    }

    public class AsyncHttpTaskCaja extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskCaja(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if(mContext!=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Cajas")+"..");
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
                    xWhere += " WHERE caja.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND caja.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND caja.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND caja.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND caja.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE caja.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND caja.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            xWhere += " AND caja.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
            xWhere += " AND caja.APERTURA="+lparam.get(0).getDEFAULT_VALOR_ON_APERTURA();
            xWhere += " AND caja.CAJA<>'"+lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim() +"'"; // CAJA 00 NO DEBE TENERLA EN CUENTA

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
                    Log.i("Longitud Antes: ",Integer.toString(lcaja.size()));
/*                    for (int i = 0; i < lcaja.size(); i++) {
                        lcaja.remove(lcaja.get(i));
                    }
*/
                    for (Iterator<Caja> it = lcaja.iterator(); it.hasNext();){
                        Caja caja = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lcaja.size()));

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
                Log.i("ADAPTADOR SECCION", Integer.toString(adaptadorcaja.getItemCount()));
                ActividadPrincipal.itemcaja.setText(Integer.toString(adaptadorcaja.getItemCount()-1));
                adaptadorcaja.notifyDataSetChanged();
                if(mContext!=null) {
                    if (Integer.parseInt(ActividadPrincipal.itemcaja.getText().toString()) == 0) {
                        ActividadPrincipal.itemcaja.setTextColor(Filtro.getColorItemZero());
                    } else {
                        ActividadPrincipal.itemcaja.setTextColor(Filtro.getColorItem());
                    }
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
/*            if (null == caja) {
                caja = new ArrayList<Caja>();
                Log.i("Inicializar caja: ",Integer.toString(caja.size()));
            }
*/             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Caja cajaItem = new Caja();
                cajaItem.setCajaId(post.optInt("ID"));
                cajaItem.setCajaNombre_Caja(post.optString("NOMBRE"));
                cajaItem.setCajaCaja(post.optString("CAJA"));
                cajaItem.setCajaApertura(post.optInt("APERTURA"));
                cajaItem.setCajaFecha_Apertura(post.optString("FECHA_APERTURA"));
                cajaItem.setCajaUrlimagenopen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_OPEN").trim());
                lcaja.add(cajaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
