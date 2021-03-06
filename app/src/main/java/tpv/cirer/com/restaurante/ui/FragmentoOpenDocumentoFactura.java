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

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.conexion_http_post.JSONParserNew;
import tpv.cirer.com.restaurante.herramientas.DividerItemDecoration1;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.restaurante.modelo.DocumentoFactura;

import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.CountTable;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.empleadosList;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.mSerialExecutorActivity;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.position_usuario;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.url_count;
import static tpv.cirer.com.restaurante.ui.ActividadPrincipal.usuario_identificado;

/**
 * Created by JUAN on 08/11/2016.
 */

public class FragmentoOpenDocumentoFactura extends Fragment {
    private String title;
    private int page;

    public static final String TAG = "Lista FTP";
    //   public List<DocumentoFactura> documentofacturas = new ArrayList<DocumentoFactura>();
    public static List<DocumentoFactura> ldocumentofactura;
    private String url = Filtro.getUrl()+"/RellenaListaFTP.php";
    //    ProgressDialog pDialog;
    //public FragmentManager fragment;

    public static RecyclerView recViewopendocumentofactura;

    public static AdaptadorDocumentoFacturaHeaderAsus adaptadordocumentofacturaasus;
    public static AdaptadorDocumentoFacturaHeaderSony adaptadordocumentofacturasony;
    public static AdaptadorDocumentoFacturaHeaderOchoPulgadas adaptadordocumentofacturaochopulgadas;
    View rootViewopendocumentofactura;
    FloatingActionButton btnFab;
    Context cont;

    ProgressDialog pDialog,pDialogcreaftp;
    private static String url_create_ftp;
    JSONParserNew jsonParserNew = new JSONParserNew();
    // JSON Node names
    String TAG_SUCCESS = "success";

    private static FragmentoOpenDocumentoFactura OpenDocumentoFactura = null;

    public static FragmentoOpenDocumentoFactura newInstance(int page, String title, String estado) {
        FragmentoOpenDocumentoFactura OpenDocumentoFactura = new FragmentoOpenDocumentoFactura();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("estado", estado);
        OpenDocumentoFactura.setArguments(args);
        return OpenDocumentoFactura;
    }

    public static FragmentoOpenDocumentoFactura getInstance(){
        if(OpenDocumentoFactura == null){
            OpenDocumentoFactura = new FragmentoOpenDocumentoFactura();
        }
        return OpenDocumentoFactura;
    }

    public FragmentoOpenDocumentoFactura() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ldocumentofactura = new ArrayList<DocumentoFactura>();
        cont = getActivity(); // lee contexto actividad
        url_create_ftp = Filtro.getUrl()+"/crea_ftp.php";
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        Filtro.setEstado(getArguments().getString("estado"));

        Filtro.setTag_fragment("FragmentoOpenDocumentoFactura");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewopendocumentofactura == null) {
/*             setHasOptionsMenu(true);*/
            rootViewopendocumentofactura = inflater.inflate(R.layout.lista, container, false);
            recViewopendocumentofactura = (RecyclerView) rootViewopendocumentofactura.findViewById(R.id.RecView);
            //     recViewopendocumentofacturas.setHasFixedSize(true);

            // 2. set layoutManger
            recViewopendocumentofactura.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
/*
            // 3. create an adapter
            adaptadordocumentofactura = new AdaptadorDocumentoFacturaHeader(getActivity(),ldocumentofactura);
            // 4. set adapter
            recViewopendocumentofactura.setAdapter(adaptadordocumentofactura);
*/  
            // 3. create an adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    adaptadordocumentofacturaasus = new AdaptadorDocumentoFacturaHeaderAsus(getActivity(),ldocumentofactura);
                    break;
                case 1:
                    adaptadordocumentofacturasony = new AdaptadorDocumentoFacturaHeaderSony(getActivity(),ldocumentofactura);
                    break;
                case 2:
                    adaptadordocumentofacturaochopulgadas = new AdaptadorDocumentoFacturaHeaderOchoPulgadas(getActivity(),ldocumentofactura);
                    break;
            }
            // 4. set adapter
            switch (Filtro.getOptipotablet()) {
                case 0:
                    recViewopendocumentofactura.setAdapter(adaptadordocumentofacturaasus);
                    break;
                case 1:
                    recViewopendocumentofactura.setAdapter(adaptadordocumentofacturasony);
                    break;
                case 2:
                    recViewopendocumentofactura.setAdapter(adaptadordocumentofacturaochopulgadas);
                    break;
            }

            // 5. set item animator to DefaultAnimator
            recViewopendocumentofactura.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
/*                        RecyclerView.ItemDecoration itemDecoration =
                                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
*/                        RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration1(getContext());
            recViewopendocumentofactura.addItemDecoration(itemDecoration);

            btnFab = (FloatingActionButton)rootViewopendocumentofactura.findViewById(R.id.btnFab);
            btnFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(ActividadPrincipal.itemdcj.getText().equals("0"))) {
                        if (!((ActividadPrincipal)getActivity()).getCruge("action_ftp_create")){
                            Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_SHORT).show();
                        }else {
                            new CreaDocumentoFactura().execute();
                            Snackbar.make(view, ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Documento")+" "+ActividadPrincipal.getPalabras("Factura"), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(view, ActividadPrincipal.getPalabras("Diario Caja No Existe o No Abierto"), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
            setUserVisibleHint(true);
        }

//         new AsyncHttpTask().execute(url);
//        Log.i("Fragment", " #" + mNum);

        return rootViewopendocumentofactura;

//        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                Log.d("OpenDocumentoFactura", "Not visible anymore.");
                Filtro.setTag_fragment("FragmentoCloseDocumentoFactura");
                // TODO stop audio playback
            }else{
                Log.d("OpenDocumentoFactura", "Yes visible anymore.");
                Filtro.setTag_fragment("FragmentoOpenDocumentoFactura");
                onResume();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        new AsyncHttpTaskDocumentoFactura(cont).execute(url);
    }

    public class AsyncHttpTaskDocumentoFactura extends AsyncTask<String, Void, Integer> {
        private Context mContext;
        public AsyncHttpTaskDocumentoFactura(Context context){
            this.mContext=context;
        }

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            if (mContext !=null) {
                super.onPreExecute();
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage(ActividadPrincipal.getPalabras("Leyendo")+" "+ActividadPrincipal.getPalabras("Facturas")+"..");
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
                    xWhere += " WHERE ftp.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND ftp.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_EMPRESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_LOCAL().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND ftp.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_SECCION().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND ftp.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_CAJA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_TURNO().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getMesa().equals(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA().trim()))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.MESA='" + Filtro.getMesa() + "'";
                } else {
                    xWhere += " AND ftp.MESA='" + Filtro.getMesa() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.FECHA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                    } else {
                        xWhere += " AND ftp.FECHA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() +  "', 120)";
                    }
                }else{
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                    } else {
                        xWhere += " AND ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                    }
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE ftp.ESTADO<'13'";
            } else {
                xWhere += " AND ftp.ESTADO<'13'";
            }
            if(usuario_identificado) {
                if (!(ActividadPrincipal.InUsuarios.equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                    } else {
                        xWhere += " AND ftp.EMPLEADO IN (" + ActividadPrincipal.InUsuarios + ")";
                    }
                }
            }else{
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPLEADO IN ('" + empleadosList.get(position_usuario).getEmpleadoEmpleado() + "')";
                } else {
                    xWhere += " AND ftp.EMPLEADO IN ('" +empleadosList.get(position_usuario).getEmpleadoEmpleado() + "')";
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
                    Log.i("Longitud Antes: ",Integer.toString(ldocumentofactura.size()));
/*                    for (int i = 0; i < ldocumentofactura.size(); i++) {
                        ldocumentofactura.remove(ldocumentofactura.get(i));
                    }
*/
                    for (Iterator<DocumentoFactura> it = ldocumentofactura.iterator(); it.hasNext();){
                        DocumentoFactura documentofactura = it.next();
                        it.remove();
                    }


                    Log.i("Longitud Despues: ",Integer.toString(ldocumentofactura.size()));

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
            if (mContext!=null) {
                pDialog.dismiss();
            }
            if (result == 1) {
/*                Log.i("ADAPTADOR FTP", Integer.toString(adaptadordocumentofactura.getItemCount()));
                adaptadordocumentofactura.notifyDataSetChanged();
*/
                switch (Filtro.getOptipotablet()) {
                    case 0:
                        Log.i("ADAPTADOR FTP", Integer.toString(adaptadordocumentofacturaasus.getItemCount()));
                        adaptadordocumentofacturaasus.notifyDataSetChanged();
                        break;
                    case 1:
                        Log.i("ADAPTADOR FTP", Integer.toString(adaptadordocumentofacturasony.getItemCount()));
                        adaptadordocumentofacturasony.notifyDataSetChanged();
                        break;
                    case 2:
                        Log.i("ADAPTADOR FTP", Integer.toString(adaptadordocumentofacturaochopulgadas.getItemCount()));
                        adaptadordocumentofacturaochopulgadas.notifyDataSetChanged();
                        break;
                }
                if (mContext!=null) {
                    //Calcular Items
                    mSerialExecutorActivity = new ActividadPrincipal.MySerialExecutor(getActivity());

                    CountTable = "ftp";
                    url_count = Filtro.getUrl() + "/CountFtpOpen.php";
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

        return result.toString();
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
 
                DocumentoFactura documentofacturaItem = new DocumentoFactura();
                documentofacturaItem.setDocumentoFacturaId(post.optInt("ID"));
                documentofacturaItem.setDocumentoFacturaSerie(post.optString("SERIE"));
                documentofacturaItem.setDocumentoFacturaFactura(post.optInt("FACTURA"));
                documentofacturaItem.setDocumentoFacturaFecha(post.optString("FECHA"));
                documentofacturaItem.setDocumentoFacturaMesa(post.optString("MESA"));
                documentofacturaItem.setDocumentoFacturaNombre_Mesa(post.optString("NOMBRE_MESA"));
                documentofacturaItem.setDocumentoFacturaEstado(post.optString("ESTADO"));
                documentofacturaItem.setDocumentoFacturaEmpleado(post.optString("EMPLEADO"));
                documentofacturaItem.setDocumentoFacturaCaja(post.optString("CAJA"));
                documentofacturaItem.setDocumentoFacturaCod_turno(post.optString("COD_TURNO"));
                documentofacturaItem.setDocumentoFacturaObs(post.optString("OBS"));
                documentofacturaItem.setDocumentoFacturaNombre_tft(post.optString("NOMBRE_TFT"));
                documentofacturaItem.setDocumentoFacturaT_fra(post.optString("T_FRA"));
                documentofacturaItem.setDocumentoFacturaImp_base(post.optString("IMP_BASE"));
                documentofacturaItem.setDocumentoFacturaImp_iva(post.optString("IMP_IVA"));
                documentofacturaItem.setDocumentoFacturaImp_total(post.optString("IMP_TOTAL"));
                documentofacturaItem.setDocumentoFacturaImp_cobro(post.optString("IMP_COBRO"));
                documentofacturaItem.setDocumentoFacturaLineas(post.optInt("LINEAS"));
                documentofacturaItem.setDocumentoFacturaUrlimagen(Filtro.getUrl() + "/image/" +post.optString("IMAGEN").trim());

                ldocumentofactura.add(documentofacturaItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * Background Async Task to Create new product
     * */
    class CreaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogcreaftp = new ProgressDialog(getActivity());
            pDialogcreaftp.setMessage(ActividadPrincipal.getPalabras("Creando")+" "+ActividadPrincipal.getPalabras("Factura")+"..");
            pDialogcreaftp.setIndeterminate(false);
            pDialogcreaftp.setCancelable(true);
            pDialogcreaftp.show();
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
            values.put("serie", Filtro.getSerie());
            values.put("factura", Long.toString(maxDate));
//            values.put("factura", Integer.toString(Filtro.getFactura()));
            values.put("mesa", Filtro.getMesa());
            values.put("estado", lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA());
            values.put("fecha", Filtro.getFechaapertura());
            values.put("empleado", Filtro.getEmpleado());
            values.put("t_fra", lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA());
            values.put("tabla", lparam.get(0).getDEFAULT_TABLA_OPEN_FACTURA());
            values.put("obs", "");
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_ftp,
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
            pDialogcreaftp.dismiss();

            if (success == 1) {
//                Toast.makeText(ActividadPrincipal.this, "Creada linea ", Toast.LENGTH_SHORT).show();
                // find your fragment
                new AsyncHttpTaskDocumentoFactura(cont).execute(url);
//                FragmentoOpenDocumentoFactura.getInstance().onResume();
            } else {
                Toast.makeText(getActivity(), "ERROR NO "+ActividadPrincipal.getPalabras("Creado")+" "+ActividadPrincipal.getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

}

