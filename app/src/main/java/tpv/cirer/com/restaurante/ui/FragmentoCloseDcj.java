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
import tpv.cirer.com.restaurante.modelo.Dcj;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 22/11/2016.
 */

public class FragmentoCloseDcj extends Fragment {
    private String title;
    private int page;
    public static final String TAG = "Lista Dcjs";
    //   public List<Dcj> dcjs = new ArrayList<Dcj>();
    public static List<Dcj> ldcj;
    private String url = Filtro.getUrl()+"/RellenaListaDcj.php";
    ProgressDialog pDialog;
    Context cont;

    //public FragmentManager fragment;
    private static String url_create_dcj;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    public static RecyclerView recViewdcj;
    public static AdaptadorDcjHeader adaptadordcj;
    public static AdaptadorDcjHeader_ochopulgadas adaptadordcjochopulgadas;

    View rootViewdcj;
    FloatingActionButton btnFab;
    private static FragmentoCloseDcj CloseDcj = null;

    public static FragmentoCloseDcj newInstance(int page, String title) {
        FragmentoCloseDcj CloseDcj = new FragmentoCloseDcj();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        CloseDcj.setArguments(args);
        return CloseDcj;
    }

    public static FragmentoCloseDcj getInstance(){
        if(CloseDcj == null){
            CloseDcj = new FragmentoCloseDcj();
        }
        return CloseDcj;
    }

    public FragmentoCloseDcj() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ldcj = new ArrayList<Dcj>();
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        //Floating Action Button
        cont = getActivity(); // lee contexto actividad
        Filtro.setTag_fragment("FragmentoCloseDcj");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewdcj == null) {
/*             setHasOptionsMenu(true);*/
            rootViewdcj = inflater.inflate(R.layout.lista, container, false);
            recViewdcj = (RecyclerView) rootViewdcj.findViewById(R.id.RecView);
            //     recViewdcjs.setHasFixedSize(true);

            // 2. set layoutManger
            recViewdcj.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
//            adaptadordcj = new AdaptadorDcjHeader(getActivity(),ldcj);
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadordcj = new AdaptadorDcjHeader(getActivity(),ldcj);
                    break;
                case 1:
                    adaptadordcj = new AdaptadorDcjHeader(getActivity(),ldcj);
                    break;
                case 2:
                    adaptadordcjochopulgadas = new AdaptadorDcjHeader_ochopulgadas(getActivity(),ldcj);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewdcj.setAdapter(adaptadordcj);
                    break;
                case 1:
                    recViewdcj.setAdapter(adaptadordcj);
                    break;
                case 2:
                    recViewdcj.setAdapter(adaptadordcjochopulgadas);
                    break;
            }

            // 4. set adapter
//            recViewdcj.setAdapter(adaptadordcj);

            // 5. set item animator to DefaultAnimator
            recViewdcj.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());

            recViewdcj.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewdcj.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No se puede crear")+" "+ActividadPrincipal.getPalabras("Diario Caja"), Snackbar.LENGTH_SHORT).show();
                }
            });

        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        Log.i("Page",Integer.toString(page)+" "+title);
//        Filtro.setTag_fragment(title);

        return rootViewdcj;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("FragmentoCloseDcj", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("FragmentoCloseDcj", "Yes visible anymore.");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new FragmentoCloseDcj.AsyncHttpTaskDcj(cont).execute(url);
    }
    public class AsyncHttpTaskDcj extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskDcj(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if(mContext!=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Diario Caja")+"..");
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
                    xWhere += " WHERE dcj.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND dcj.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE dcj.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND dcj.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE dcj.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND dcj.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE dcj.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND dcj.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE dcj.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND dcj.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_TURNO().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE dcj.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND dcj.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getFecha().equals(""))) {
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE dcj.FECHA_APERTURA=CONVERT(DATETIME, '" +Filtro.getFecha().substring(0,10)+" 00:00:00" + "', 120)";
                    } else {
                        xWhere += " AND dcj.FECHA_APERTURA=CONVERT(DATETIME, '" + Filtro.getFecha().substring(0,10)+" 00:00:00" + "', 120)";
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE dcj.FECHA_APERTURA='" + Filtro.getFecha() + "'";
                    } else {
                        xWhere += " AND dcj.FECHA_APERTURA='" + Filtro.getFecha() + "'";
                    }
                }
            }
            xWhere += " AND dcj.APERTURA="+lparam.get(0).getDEFAULT_VALOR_OFF_APERTURA();

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
                    Log.i("Longitud Antes: ",Integer.toString(ldcj.size()));
/*                    for (int i = 0; i < ldcj.size(); i++) {
                        ldcj.remove(ldcj.get(i));
                    }
*/
                    for (Iterator<Dcj> it = ldcj.iterator(); it.hasNext();){
                        Dcj dcj = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(ldcj.size()));

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
            if(mContext!=null) {
                pDialog.dismiss();
            }
            if (result == 1) {
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcj.getItemCount()));
                        adaptadordcj.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcj.getItemCount()));
                        adaptadordcj.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcjochopulgadas.getItemCount()));
                        adaptadordcjochopulgadas.notifyDataSetChanged();
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

                Dcj dcjItem = new Dcj();
                dcjItem.setDcjId(post.optInt("ID"));
                dcjItem.setDcjIvaIncluido(post.optInt("IVAINCLUIDO"));
                dcjItem.setDcjFecha_open(post.optString("FECHA_OPEN"));
                dcjItem.setDcjFecha_close(post.optString("FECHA_CLOSE"));
                dcjItem.setDcjCod_turno(post.optString("TURNO"));
                dcjItem.setDcjApertura(post.optInt("APERTURA"));
                dcjItem.setDcjFecha_Apertura(post.optString("FECHA_APERTURA"));
                dcjItem.setDcjSaldo_inicio(post.optString("SALDO_INICIO"));
                dcjItem.setDcjSaldo_final(post.optString("SALDO_FINAL"));
                dcjItem.setDcjUrlimagenopen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_CLOSE").trim());
                ldcj.add(dcjItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
