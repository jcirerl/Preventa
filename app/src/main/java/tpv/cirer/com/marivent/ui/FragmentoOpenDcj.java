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
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration1;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.Dcj;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 21/11/2016.
 */



public class FragmentoOpenDcj  extends Fragment {
    private String title;
    private int page;
    public static final String TAG = "Lista Dcjs";
    //   public List<Dcj> dcjs = new ArrayList<Dcj>();
    public static List<Dcj> ldcj;
    private String url = Filtro.getUrl()+"/RellenaListaDcj.php";
    //    ProgressDialog pDialog;
    //public FragmentManager fragment;
    ProgressDialog pDialogOpen,pDialogcreadcj;
    private static String url_create_dcj;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    public static RecyclerView recViewdcj;
    public static AdaptadorDcjHeader adaptadordcj;
    public static AdaptadorDcjHeader_ochopulgadas adaptadordcjochopulgadas;

    View rootViewdcj;
    FloatingActionButton btnFab;
    Context cont;
    private static FragmentoOpenDcj OpenDcj = null;

    public static FragmentoOpenDcj newInstance(int page, String title) {
        FragmentoOpenDcj OpenDcj = new FragmentoOpenDcj();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        OpenDcj.setArguments(args);
        return OpenDcj;
    }

    public static FragmentoOpenDcj getInstance(){
        if(OpenDcj == null){
            OpenDcj = new FragmentoOpenDcj();
        }
        return OpenDcj;
    }

    public FragmentoOpenDcj() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        url_create_dcj = Filtro.getUrl()+"/crea_dcj.php";
        ldcj = new ArrayList<Dcj>();
        cont = getActivity(); // lee contexto actividad
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        //Floating Action Button
        Filtro.setTag_fragment("FragmentoOpenDcj");

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
                    if(!(ActividadPrincipal.itemturno.getText().equals("0"))) {
                        if (!((ActividadPrincipal)getActivity()).getCruge("action_dcj_create")){
                            Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                        }else {
                            new CreaDcj().execute();
                            Snackbar.make(view, ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Diario Caja"), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No Hay")+" "+ActividadPrincipal.getPalabras("Turno")+" "+ActividadPrincipal.getPalabras("Abierto"), Snackbar.LENGTH_SHORT).show();
                    }
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
                Log.d("FragmentoOpenDcj", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("FragmentoOpenDcj", "Yes visible anymore.");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new AsyncHttpTaskDcj(cont).execute(url);
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
                pDialogOpen = new ProgressDialog(mContext);
                pDialogOpen.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Diario Caja")+"..");
                pDialogOpen.setIndeterminate(false);
                pDialogOpen.setCancelable(true);
                pDialogOpen.show();
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
            if(!(Filtro.getFechaapertura().equals(""))) {
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE dcj.FECHA_APERTURA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                    } else {
                        xWhere += " AND dcj.FECHA_APERTURA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE dcj.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                    } else {
                        xWhere += " AND dcj.FECHA_APERTURA='" + Filtro.getFechaapertura() + "'";
                    }
                }
            }
            xWhere += " AND dcj.APERTURA="+lparam.get(0).getDEFAULT_VALOR_ON_APERTURA();

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
                pDialogOpen.dismiss();
            }
            if (result == 1) {
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcj.getItemCount()));
                        ActividadPrincipal.itemdcj.setText(Integer.toString(adaptadordcj.getItemCount()-1));
                        adaptadordcj.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcj.getItemCount()));
                        ActividadPrincipal.itemdcj.setText(Integer.toString(adaptadordcj.getItemCount()-1));
                        adaptadordcj.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR DCJ", Integer.toString(adaptadordcjochopulgadas.getItemCount()));
                        ActividadPrincipal.itemdcj.setText(Integer.toString(adaptadordcjochopulgadas.getItemCount()-1));
                        adaptadordcjochopulgadas.notifyDataSetChanged();
                        break;
                }
                
                if(mContext!=null) {
                    if (Integer.parseInt(ActividadPrincipal.itemdcj.getText().toString()) == 0) {
                        ActividadPrincipal.itemdcj.setTextColor(Filtro.getColorItemZero());
                    } else {
                        ActividadPrincipal.itemdcj.setTextColor(Filtro.getColorItem());
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
                dcjItem.setDcjUrlimagenopen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN_OPEN").trim());
                ldcj.add(dcjItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Background Async Task to Create new product
     * */
    class CreaDcj extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogcreadcj = new ProgressDialog(getActivity());
            pDialogcreadcj.setMessage(ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Diario Caja")+"..");
            pDialogcreadcj.setIndeterminate(false);
            pDialogcreadcj.setCancelable(true);
            pDialogcreadcj.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());

            long maxDate = currentDate.getTime().getTime(); // Twice!

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put("grupo", Filtro.getGrupo());
            values.put("empresa", Filtro.getEmpresa());
            values.put("local", Filtro.getLocal());
            values.put("seccion", Filtro.getSeccion());
            values.put("caja", Filtro.getCaja());
            values.put("cod_turno", Filtro.getTurno());
            values.put("apertura", 1);
            values.put("fecha_apertura", Filtro.getFechaapertura());
            values.put("fecha_open", Filtro.getFechaapertura());
            values.put("fecha_close", Filtro.getFechaapertura());
            values.put("ivaincluido", Filtro.getIvaIncluido());
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_dcj,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            int success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return success;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialogcreadcj.dismiss();

            if (success == 1) {
//                Toast.makeText(ActividadPrincipal.this, "Creada linea ", Toast.LENGTH_SHORT).show();
                // find your fragment
                new AsyncHttpTaskDcj(cont).execute(url);

            } else {
                Toast.makeText(getActivity(), "ERROR NO "+ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Diario Caja"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

}
