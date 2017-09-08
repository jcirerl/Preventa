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
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration1;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.Message;

        import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;

/**
 * Created by JUAN on 08/11/2016.
 */

public class FragmentoCloseMessage extends Fragment {
    private String title;
    private int page;

    public static final String TAG = "Lista Message";
    public static List<Message> lmessage;
    private String url = Filtro.getUrl()+"/RellenaListaMESSAGE.php";
 
    public static RecyclerView recViewopenmessage;
    public static AdaptadorMessageHeader adaptadormessage;
    View rootViewopenmessage;
    FloatingActionButton btnFab;
    Context cont;

    ProgressDialog pDialog;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    private static FragmentoCloseMessage CloseMessage = null;

    public static FragmentoCloseMessage newInstance(int page, String title) {
        FragmentoCloseMessage CloseMessage = new FragmentoCloseMessage();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        CloseMessage.setArguments(args);
        return CloseMessage;
    }

    public static FragmentoCloseMessage getInstance(){
        if(CloseMessage == null){
            CloseMessage = new FragmentoCloseMessage();
        }
        return CloseMessage;
    }

    public FragmentoCloseMessage() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lmessage = new ArrayList<Message>();
        cont = getActivity(); // lee contexto actividad
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

        Filtro.setTag_fragment("FragmentoCloseMessage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewopenmessage == null) {
/*             setHasOptionsMenu(true);*/
            rootViewopenmessage = inflater.inflate(R.layout.lista, container, false);
            recViewopenmessage = (RecyclerView) rootViewopenmessage.findViewById(R.id.RecView);
            //     recViewopenmessages.setHasFixedSize(true);

            // 2. set layoutManger
            recViewopenmessage.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            adaptadormessage = new AdaptadorMessageHeader(getActivity(),lmessage);
            // 4. set adapter
            recViewopenmessage.setAdapter(adaptadormessage);

            // 5. set item animator to DefaultAnimator
            recViewopenmessage.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*                        RecyclerView.ItemDecoration itemDecoration =
                                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/                        RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());
            recViewopenmessage.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewopenmessage.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, ActividadPrincipal.getPalabras("No se puede crear")+" "+ActividadPrincipal.getPalabras("Mensajes"), Snackbar.LENGTH_LONG).show();
                }
            });
            setUserVisibleHint(true);
        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        return rootViewopenmessage;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("CloseMessage", "Not visible anymore.");
                // TODO stop audio playback
            }else{
                Log.d("CloseMessage", "Yes visible anymore.");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new AsyncHttpTaskMessage(cont).execute(url);
    }

    public class AsyncHttpTaskMessage extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskMessage(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if (mContext !=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Mensajes")+"..");
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
                    xWhere += " WHERE message.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND message.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND message.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND message.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND message.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE message.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND message.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                } else {
                    xWhere += " AND DATE(message.creado)='" + Filtro.getFechaapertura() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE message.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
            } else {
                xWhere += " AND message.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_OFF_ACTIVO();
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
                    Log.i("Longitud Antes: ",Integer.toString(lmessage.size()));

                    for (Iterator<Message> it = lmessage.iterator(); it.hasNext();){
                        Message message = it.next();
                        it.remove();
                    }


                    Log.i("Longitud Despues: ",Integer.toString(lmessage.size()));

                    parseResult(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //    setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (mContext!=null) {
                pDialog.dismiss();
            }
            if (result == 1) {
                Log.i("ADAPTADOR Message", Integer.toString(adaptadormessage.getItemCount()));
                adaptadormessage.notifyDataSetChanged();
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

                Message messageItem = new Message();
                messageItem.setMessageId(post.optInt("ID"));
                messageItem.setMessageCreado(post.optString("creado"));
                messageItem.setMessageMesa(post.optString("MESA"));
                messageItem.setMessageActivo(post.optInt("ACTIVO"));
                messageItem.setMessageCaja(post.optString("CAJA"));
                messageItem.setMessageComensales(post.optInt("COMENSALES"));
                messageItem.setMessageUpdated(post.optString("updated"));
                messageItem.setMessageUsuario(post.optString("USER"));

                lmessage.add(messageItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
 
}

