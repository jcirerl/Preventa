package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import tpv.cirer.com.marivent.modelo.DocumentoPedido;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.CountTable;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.mSerialExecutorActivity;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.url_count;

/**
 * Created by JUAN on 19/09/2016.
 */
public class FragmentoOpenDocumentoPedido extends Fragment    {
    private String title;
    private int page;

    public static final String TAG = "Lista PDD";
        //   public List<DocumentoPedido> documentopedidos = new ArrayList<DocumentoPedido>();
        public static List<DocumentoPedido> ldocumentopedido;
        private String url = Filtro.getUrl()+"/RellenaListaPDD.php";
        //    ProgressDialog pDialog;
        //public FragmentManager fragment;

    public static RecyclerView recViewopendocumentopedido;
    public static AdaptadorDocumentoPedidoHeaderAsus adaptadordocumentopedidoasus;
    public static AdaptadorDocumentoPedidoHeaderSony adaptadordocumentopedidosony;
    public static AdaptadorDocumentoPedidoHeaderOchoPulgadas adaptadordocumentopedidoochopulgadas;

    View rootViewopendocumentopedido;
    FloatingActionButton btnFab;
    Context cont;

    ProgressDialog pDialogPdd;
    private static String url_create_pdd;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    private static FragmentoOpenDocumentoPedido OpenDocumentoPedido = null;
 
    public static FragmentoOpenDocumentoPedido newInstance(int page, String title, String estado) {
        FragmentoOpenDocumentoPedido OpenDocumentoPedido = new FragmentoOpenDocumentoPedido();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("estado", estado);
        OpenDocumentoPedido.setArguments(args);
        return OpenDocumentoPedido;
    }

    public static FragmentoOpenDocumentoPedido getInstance(){
        if(OpenDocumentoPedido == null){
            OpenDocumentoPedido = new FragmentoOpenDocumentoPedido();
        }
        return OpenDocumentoPedido;
    }
    
        public FragmentoOpenDocumentoPedido() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            ldocumentopedido = new ArrayList<DocumentoPedido>();
            cont = getActivity(); // lee contexto actividad

            url_create_pdd = Filtro.getUrl()+"/crea_pdd.php";
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
            Filtro.setEstado(getArguments().getString("estado"));

            Filtro.setTag_fragment("FragmentoOpenDocumentoPedido");

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
                                adaptadordocumentopedidoochopulgadas = new AdaptadorDocumentoPedidoHeaderOchoPulgadas(getActivity(),ldocumentopedido);
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
                                recViewopendocumentopedido.setAdapter(adaptadordocumentopedidoochopulgadas);
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
                                    if(!(ActividadPrincipal.itemdcj.getText().equals("0"))) {
                                        if (!((ActividadPrincipal)getActivity()).getCruge("action_pdd_create")){
                                            Snackbar.make(view, getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                                        }else {
                                            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                            final EditText input = new EditText(getActivity());
                                            input.setText(String.format("%02d", 0));

                                            // Ponerse al final del edittext
                                            int pos = input.getText().length();
                                            input.setSelection(pos);

                                            input.setTextColor(Color.RED);
                                            input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                            KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                                            input.setKeyListener(keyListener);
                                            alert.setTitle(getPalabras("Insertar")+" "+getPalabras("Comensales"));
                                            alert.setView(input);
                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    String value = input.getText().toString();
                                                    if (value.matches("")) {
                                                        Toast.makeText(getActivity(), getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                                        //            this.btnGuardar.setEnabled(false);
                                                    } else {

                                                        value = value.replace(".", "");
                                                        value = value.replace(",", "");

                                                        input.setText(String.format("%2d", Integer.valueOf(value)));

                                                        new CreaDocumentoPedido().execute(value);

            //                                               Snackbar.make(view, "Creando Documento Pedido", Snackbar.LENGTH_LONG).show();
                                                        Toast.makeText(getActivity(),  ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Documento")+" "+ActividadPrincipal.getPalabras("Pedido").toString(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                            alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    dialog.cancel();
                                                }
                                            });
                                            alert.show();
                                        }
                                    } else {
                                        Snackbar.make(view, ActividadPrincipal.getPalabras("Diario Caja No Existe o No Abierto"), Snackbar.LENGTH_LONG).show();
                                    }
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
                Log.d("OpenDocumentoPedido", "Not visible anymore.");
                Filtro.setTag_fragment("FragmentoCloseDocumentoPedido");
                // TODO stop audio playback
            }else{
                Log.d("OpenDocumentoPedido", "Yes visible anymore.");
                Filtro.setTag_fragment("FragmentoOpenDocumentoPedido");
                onResume();
            }
        }
    }
        @Override
        public void onResume() {
                super.onResume();
               // TITULO CABECERA
//               ((ActividadPrincipal) getActivity()).setTitle("Pedidos");
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
                        pDialogPdd = new ProgressDialog(mContext);
                        pDialogPdd.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Pedidos")+"..");
                        pDialogPdd.setIndeterminate(false);
                        pDialogPdd.setCancelable(true);
                        pDialogPdd.show();
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
                    if(!(Filtro.getMesa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA().trim()))) {
                        if (xWhere.equals("")) {
                            xWhere += " WHERE pdd.MESA='" + Filtro.getMesa() + "'";
                        } else {
                            xWhere += " AND pdd.MESA='" + Filtro.getMesa() + "'";
                        }
                    }
                    if(!(Filtro.getFechaapertura().equals(""))) {
                        if(Filtro.getUrl().contains("sqlsrv")) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE pdd.FECHA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                            } else {
                                xWhere += " AND pdd.FECHA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
                            }
                        }else{
                            if (xWhere.equals("")) {
                                xWhere += " WHERE pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                            } else {
                                xWhere += " AND pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                            }
                        }
                    }

                    if (xWhere.equals("")) {
                        xWhere += " WHERE pdd.ESTADO<'13'";
                    } else {
                        xWhere += " AND pdd.ESTADO<'13'";
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
                    if (mContext!=null) {
                        pDialogPdd.dismiss();
                    }

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
                                Log.i("ADAPTADOR PDD", Integer.toString(adaptadordocumentopedidoochopulgadas.getItemCount()));
                                adaptadordocumentopedidoochopulgadas.notifyDataSetChanged();
                                break;
                        }
                        if (mContext!=null) {
                            //Calcular Items
                            mSerialExecutorActivity = new ActividadPrincipal.MySerialExecutor(getActivity());

                            CountTable="pdd";
                            url_count = Filtro.getUrl()+"/CountPddOpen.php";
                            mSerialExecutorActivity.execute(null);
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
/*            if (null == documentopedido) {
                documentopedido = new ArrayList<DocumentoPedido>();
                Log.i("Inicializar documentopedido: ",Integer.toString(documentopedido.size()));
            }
*/             Log.i("Longitud Datos: ",Integer.toString(posts.length()));
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
    /**
     * Background Async Task to Create new product
     * */
    class CreaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogPdd = new ProgressDialog(cont);
            pDialogPdd.setMessage(ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Pedido")+"..");
            pDialogPdd.setIndeterminate(false);
            pDialogPdd.setCancelable(true);
            pDialogPdd.show();
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
            values.put("pedido", Long.toString(maxDate));
            values.put("mesa", Filtro.getMesa());
            values.put("comensales", args[0]);
            values.put("estado", lparam.get(0).getDEFAULT_ESTADO_OPEN_PEDIDO());
            values.put("fecha", Filtro.getFechaapertura());
            values.put("empleado", Filtro.getEmpleado());
            values.put("tabla", lparam.get(0).getDEFAULT_TABLA_OPEN_PEDIDO());
            values.put("obs", "");
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_pdd,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            Integer success = 0;

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
            pDialogPdd.dismiss();

            if (success == 1) {
//                Toast.makeText(ActividadPrincipal.this, "Creada linea ", Toast.LENGTH_SHORT).show();
                // find your fragment
                new AsyncHttpTaskDocumentoPedido(cont).execute(url);
            } else {
                Toast.makeText(getActivity(), "ERROR NO "+ActividadPrincipal.getPalabras("Creado")+" "+ActividadPrincipal.getPalabras("Pedido"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

}
