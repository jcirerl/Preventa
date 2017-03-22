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

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration1;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.Turno;

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

/**
 * Created by JUAN on 08/11/2016.
 */

public class FragmentoCloseTurno extends Fragment {
    private String title;
    private int page;
    public static final String TAG = "Lista Turnos";
    //   public List<Turno> turnos = new ArrayList<Turno>();
    public static List<Turno> lturno;
    private String url = Filtro.getUrl()+"/RellenaListaTurno.php";
    ProgressDialog pDialog;
    //public FragmentManager fragment;
    Context cont;
    public static RecyclerView recViewturno;
    public static AdaptadorTurnoHeader adaptadorturno;
    View rootViewturno;
    FloatingActionButton btnFab;
    private static FragmentoCloseTurno CloseTurno = null;

    public static FragmentoCloseTurno newInstance(int page, String title) {
        FragmentoCloseTurno CloseTurno = new FragmentoCloseTurno();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        CloseTurno.setArguments(args);
        return CloseTurno;
    }

    public static FragmentoCloseTurno getInstance(){
        if(CloseTurno == null){
            CloseTurno = new FragmentoCloseTurno();
        }
        return CloseTurno;
    }

    public FragmentoCloseTurno() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lturno = new ArrayList<Turno>();
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        //Floating Action Button
        cont = getActivity();
        Filtro.setTag_fragment("FragmentoCloseTurno");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewturno == null) {
/*             setHasOptionsMenu(true);*/
            rootViewturno = inflater.inflate(R.layout.lista, container, false);
            recViewturno = (RecyclerView) rootViewturno.findViewById(R.id.RecView);
            //     recViewturnos.setHasFixedSize(true);

            // 2. set layoutManger
            recViewturno.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            adaptadorturno = new AdaptadorTurnoHeader(getActivity(),lturno);

            // 4. set adapter
            recViewturno.setAdapter(adaptadorturno);

            // 5. set item animator to DefaultAnimator
            recViewturno.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());

            recViewturno.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewturno.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "No se puede crear TURNO", Snackbar.LENGTH_LONG).show();
                }
            });

        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        Log.i("Page",Integer.toString(page)+" "+title);
//        Filtro.setTag_fragment(title);

        return rootViewturno;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("FragmentoCloseTurno", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("FragmentoCloseTurno", "Yes visible anymore.");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new FragmentoCloseTurno.AsyncHttpTaskTurno(cont).execute(url);
    }

    public class AsyncHttpTaskTurno extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskTurno(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if(mContext!=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Leyendo Turnos..");
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
            if(!(Filtro.getGrupo().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND turno.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND turno.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND turno.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND turno.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND turno.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals("00"))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE turno.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND turno.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            xWhere += " AND turno.ACTIVO=1";
            xWhere += " AND turno.APERTURA=0";
            xWhere += " AND turno.COD_TURNO<>'00'"; // CAJA 00 NO DEBE TENERLA EN CUENTA

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
                    Log.i("Longitud Antes: ",Integer.toString(lturno.size()));
/*                    for (int i = 0; i < lturno.size(); i++) {
                        lturno.remove(lturno.get(i));
                    }
*/
                    for (Iterator<Turno> it = lturno.iterator(); it.hasNext();){
                        Turno turno = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(lturno.size()));

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
                Log.i("ADAPTADOR TURNO", Integer.toString(adaptadorturno.getItemCount()));
                adaptadorturno.notifyDataSetChanged();
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

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));

            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Turno turnoItem = new Turno();
                turnoItem.setTurnoId(post.optInt("ID"));
                turnoItem.setTurnoNombre_Turno(post.optString("NOMBRE"));
                turnoItem.setTurnoCod_turno(post.optString("TURNO"));
                turnoItem.setTurnoApertura(post.optInt("APERTURA"));
                turnoItem.setTurnoFecha_Apertura(post.optString("FECHA_APERTURA"));
                turnoItem.setTurnoUrlimagenclose(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_CLOSE").trim());
                lturno.add(turnoItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
