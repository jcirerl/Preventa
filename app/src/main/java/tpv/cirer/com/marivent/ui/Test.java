package tpv.cirer.com.marivent.ui;

/**
 * Created by JUAN on 07/02/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.BadgeView;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.SerialExecutor;
import tpv.cirer.com.marivent.modelo.DocumentoPedido;
import tpv.cirer.com.marivent.modelo.Mesa;
import com.squareup.picasso.Picasso;

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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;

public class Test extends Activity implements OnTouchListener {

    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;
    private ImageView view, fin;
    private  Bitmap bmap;
    private static JSONParserNew jsonParserNew = null;
    private static String url_updatemesacoordenate;
    private static String url_update_cabecera;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PEDIDO = "pedido";
    private static final String TAG_FACTURA = "factura";
    private static final String TAG_ID = "id";

    private static final String TAG = "MESAS PDD";
    private MySerialExecutor mSerialExecutor;

    boolean actionMove;

    int result;
    int countPdd;
    int countFtp;
    String valormesa;
    String valoraction;
    String valortabla;
    String pid;
    ProgressDialog pDialog;
    private static String url_create_pdd;
    private static String url_create_ftp;
    private static String url_updatemesa_mesa;
    private static String url_count;
    private static String url_pedido_factura;
    private static String url_pedido_a_factura_id;

    // JSON Node names

    String TAG_MESA="MESA: ";
    private ArrayList<Mesa> mesaplanningList;
    private static String URL_MESASPLANNING;
    private ArrayList<Integer> mSelectedItems;

    public static List<DocumentoPedido> ldocumentopedido;
    Bitmap bimage;
    RelativeLayout layout;
    ImageView image;
    Bitmap innerBitmap;
    TextView texto;
    BadgeView badge;
    View target;
    Mesa model;
    public static float xCoordenate, yCoordenate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = (ImageView) findViewById(R.id.imageView);
        fin = (ImageView) findViewById(R.id.imageView1);
   //     view.setOnTouchListener(this);

        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get MESA, ACTION
        valormesa = intent.getStringExtra("Mesa");
        valoraction = intent.getStringExtra("Action");
        valortabla = intent.getStringExtra("Tabla");

        url_create_pdd = Filtro.getUrl()+"/crea_pdd.php";
        url_create_ftp = Filtro.getUrl()+"/crea_ftp.php";
        url_updatemesa_mesa = Filtro.getUrl()+"/updatemesa_mesa.php";
        url_pedido_factura = Filtro.getUrl()+"/RellenaListaPDD.php";
        url_pedido_a_factura_id = Filtro.getUrl()+"/invoice_pdd_ftp_multiple.php";
        url_update_cabecera = Filtro.getUrl()+"/update_cabecera.php";


        ////       agregarToolbar();
        // Rellenar string toolbar_mesas
        mesaplanningList = new ArrayList<Mesa>();
        URL_MESASPLANNING = Filtro.getUrl() + "/get_mesasplanning.php";
        new GetMesas().execute(URL_MESASPLANNING);
        Log.i("Mesas","Create Planning");
        url_updatemesacoordenate = Filtro.getUrl()+"/updatemesa_coordenadas.php";

        mSerialExecutor = new MySerialExecutor(getApplicationContext());


    }
    private void populatePlanningMesas() {
        layout = (RelativeLayout)findViewById(R.id.rlt);

        for (int i = 0; i < mesaplanningList.size(); i++) {
            model = mesaplanningList.get(i);
            view = new ImageView(this);
      //      view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            view.setVisibility(View.VISIBLE);
            view.setTag(model.getMesaMesa());
            view.setX(model.getMesaXCoordenate());
            view.setY(model.getMesaYCoordenate());
//            if (model.getMesaXCoordenate()!=0.00000){image.setX(model.getMesaXCoordenate());}
//            if (model.getMesaYCoordenate()!=0.00000){image.setY(model.getMesaYCoordenate());}
            if (model.getMesaApertura() == 0) {
                view.setAlpha(0.3f); // COLOR APAGADO MESA CERRADA
            } else {
                view.setAlpha(1.0f); // COLOR BRILLANTE MESA ABIERTA
            }
            Picasso.with(getApplicationContext())
                    .load(model.getMesaUrlimagen())
                    .resize(90, 90)
                    .centerCrop()
                    .into(view);
            Log.i("instance MESA",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(view.getY())+" x "+Float.toString(view.getX()));

                int drawableId = 0;
            String uri = "@drawable/house_kitchen_table";
            int icon = getResources().getIdentifier(uri, "drawable", getPackageName());
             // Adds the view to the layout
            layout.addView(view);
            view.setOnTouchListener(this);
            Log.i("URLIMAGEN TAG: ", view.getTag() + " iconId " + Integer.toString(icon) + " x: " + Float.toString(model.getMesaXCoordenate()) + " y: " + Float.toString(model.getMesaYCoordenate()) + " IMAGEN: " + model.getMesaUrlimagen() + " MESA: " + model.getMesaMesa() + " APERTURA: " + Integer.toString(model.getMesaApertura()));
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
            for (int i = 0; i < mesaplanningList.size(); i++) {
                model = mesaplanningList.get(i);
                Filtro.setMesa(model.getMesaMesa());
                xCoordenate = model.getMesaXCoordenate();
                yCoordenate = model.getMesaYCoordenate();
                mSerialExecutor.execute(null);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    private void populate_dialog() {
        String[] pedidos = new String[ldocumentopedido.size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        for (int i = 0; i < ldocumentopedido.size(); i++) {
            //////////////////////////////////////////////////////////
            //pedido,fecha,
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
            String xfecha = "";
            try {
                Date datehora = sdf1.parse(String.valueOf(ldocumentopedido.get(i).getDocumentoPedidoFecha()));
                xfecha = sdf2.format(datehora);

            } catch (Exception e) {
                e.getMessage();
            }
            String myTextTotal = String.format("%1$,.2f", Float.parseFloat(ldocumentopedido.get(i).getDocumentoPedidoImp_total()));
            myTextTotal = myTextTotal.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
            myTextTotal = myTextTotal.replaceAll("\\s+$", ""); // Quitamos espacios derecha
            String newTextTotal="";
            for (int ii = 0; ii < (8-myTextTotal.length()); ii++) {
                newTextTotal+=space01;
            }
            newTextTotal +=myTextTotal;

            pedidos[i] =  String.format("%-48s", String.format("%07d", Integer.parseInt(String.valueOf(ldocumentopedido.get(i).getDocumentoPedidoPedido())))
                    + " " + xfecha + " " + Html.fromHtml(newTextTotal.replace(" ", "&nbsp;&nbsp;")).toString() + " " + Filtro.getSimbolo());

        }
        mSelectedItems = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(Test.this);
        view = (ImageView)layout.findViewWithTag(Filtro.getMesa());
        builder.setIcon(view.getDrawable());
        builder.setTitle(ActividadPrincipal.getPalabras("Pedidos")+" "+ActividadPrincipal.getPalabras("Facturar")+" "+ActividadPrincipal.getPalabras("Mesa")+ " " + Filtro.getMesa() + " " + (getMesas(Filtro.getMesa(), 0) ? ActividadPrincipal.getPalabras("CERRADO") : ActividadPrincipal.getPalabras("ABIERTO")))
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(pedidos, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        if (mSelectedItems.size()>0) {
                            String[] idpedidos = new String[mSelectedItems.size()];
                            pid = "";
                            for (int i = 0; i < mSelectedItems.size(); i++) {
                                idpedidos[i] = Integer.toString(ldocumentopedido.get(mSelectedItems.get(i)).getDocumentoPedidoId());
                                Log.i("Id Pedidos", idpedidos[i] + " " + pid);
                                pid += idpedidos[i] + ",";
                            }
                            pid = pid.substring(0, pid.length() - 1);
                            Log.i("Id Pedidos Final", pid);
                            new TraspasoPedidoFactura().execute(pid);
                        }
                    }
                })
                .setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog1 = builder.create();
        dialog1.show();

    }
    private boolean getMesas (String search, int value){
        boolean success = false;
        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                if (d.getMesaApertura()==value){
                    success = true;
                }else{
                    success = false;
                }
                break;
            }
        }
        return success;
    }
    private void setMesas (String search, int value){
        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                d.setMesaApertura(value);
                break;
            }
        }
    }
    private void setMesaCoordenadas (String search, float x, float y){
        Log.i("instance Mesa Valor: ",search+" "+Float.toString(x)+" "+Float.toString(y));
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                d.setMesaXCoordenate(x);
                d.setMesaYCoordenate(y);
                break;
            }
        }
    }
    public class GetMesas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getRango().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                } else {
                    xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                }
            }

            xWhere += " AND mesas.MESA<>'00'"; // MESA 00 NO DEBE TENERLA EN CUENTA
            xWhere += " AND mesas.ACTIVO=1";

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

                    for (Iterator<Mesa> it = mesaplanningList.iterator(); it.hasNext();){
                        Mesa mesa = it.next();
                        it.remove();
                    }

                    parseResultMesas(response.toString());
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
            /* Download complete. Lets update UI */
            if (result == 1) {
                Log.e(TAG_MESA, "OK PLANNING MESA");
                populatePlanningMesas();
            } else {
                Log.e(TAG_MESA, "Failed to fetch data!");
            }
        }
    }
    private void parseResultMesas(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                String imagen = post.optString("IMAGEN");
                String nombre_mesas = post.optString("NOMBRE");
                String mesa = post.optString("MESA");
                int apertura = post.optInt("APERTURA");

                Mesa mesaItem = new Mesa();
                mesaItem.setMesaNombre_Mesas(nombre_mesas);
                mesaItem.setMesaMesa(mesa);
                mesaItem.setMesaApertura(apertura);
                mesaItem.setMesaUrlimagen(Filtro.getUrl() + "/image/" + imagen.trim() );
                mesaItem.setMesaXCoordenate(BigDecimal.valueOf(post.getDouble("XCOORDENATE")).floatValue());
                mesaItem.setMesaYCoordenate(BigDecimal.valueOf(post.getDouble("YCOORDENATE")).floatValue());
                mesaplanningList.add(mesaItem);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
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
    private static class MySerialExecutor extends SerialExecutor {
        private final Context mContext;

        public MySerialExecutor(Context context){
            super();
            this.mContext = context;
        }

        @Override
        public void execute(TaskParams params) {
            MySerialExecutor.MyParams myParams = (MySerialExecutor.MyParams) params;
            // do something...
            // Check for success tag
            int success;

            String filtro = "mesas.GRUPO='" + Filtro.getGrupo() + "'";
            filtro += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
            filtro += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
            filtro += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
            filtro += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
            filtro += " AND mesas.RANGO='" + Filtro.getRango() + "'";
            filtro += " AND mesas.MESA='" + Filtro.getMesa() + "'";
            try {
                ContentValues values = new ContentValues();
                values.put("filtro", filtro);
                values.put("xcoordenate", Float.toString(xCoordenate));
                values.put("ycoordenate", Float.toString(yCoordenate));

                jsonParserNew = new JSONParserNew();
                JSONObject json = jsonParserNew.makeHttpRequest(
                        url_updatemesacoordenate, "POST", values);


                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.i("SERIALIZABLE: ","ok "+Filtro.getMesa());

                } else {
                    Log.i("SERIALIZABLE: ","NOT ok "+Filtro.getMesa());
                    // LineaDocumentoFactura with pid not found
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public static class MyParams extends TaskParams {
            // ... params definition

            public MyParams(int param) {
                // ... params init
            }
        }
    }
    class UpdateMesaApertura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Apertura")+" "+ActividadPrincipal.getPalabras("Mesa")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            final String valueapertura = args[0];
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        String cSql = "";
                        String xWhere = "";

                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND mesas.GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(Filtro.getRango().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += " WHERE mesas.RANGO='" + Filtro.getRango() + "'";
                            } else {
                                xWhere += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                            }
                        }

                        xWhere += " AND mesas.MESA='"+Filtro.getMesa()+"'";

                        cSql += xWhere;
                        if(cSql.equals("")) {
                            cSql="Todos";
                        }
                        Log.i("Sql Lista",cSql);

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",cSql);
                        values.put("apertura", valueapertura);
                        values.put("updated", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        jsonParserNew = new JSONParserNew();
                        JSONObject json = jsonParserNew.makeHttpRequest(url_updatemesa_mesa,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            view = (ImageView)layout.findViewWithTag(Filtro.getMesa());
//                            image.findViewWithTag(Filtro.getMesa());
                            if (valueapertura.equals("1")) {
                                setMesas(Filtro.getMesa(),1);
                                view.setAlpha(1.0f); // abro mesa
                                Snackbar.make(layout, ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("ABIERTA"), Snackbar.LENGTH_LONG).show();

                            }else{
                                setMesas(Filtro.getMesa(),0);
                                view.setAlpha(0.3f); // cierro mesa
                                Snackbar.make(layout, ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR NO MESA MESA ", Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

    class CreaDocumentoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Factura")+"..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
            values.put("estado", "01");
            values.put("fecha", Filtro.getFechaapertura());
            values.put("empleado", Filtro.getEmpleado());
            values.put("t_fra", "CO");
            values.put("tabla", "ftp");
            values.put("obs", "");
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            jsonParserNew = new JSONParserNew();
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_ftp,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            int success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
                Filtro.setFactura(json.getInt(TAG_FACTURA));
                Filtro.setId(json.getInt(TAG_ID));

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
            pDialog.dismiss();

            if (success == 1) {
                Snackbar.make(layout, ActividadPrincipal.getPalabras("Factura")+" "+ActividadPrincipal.getPalabras("Generada")+" "+Filtro.getSerie()+" "+Integer.toString(Filtro.getFactura()), Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Creado Factura ", Toast.LENGTH_SHORT).show();
                // SALIR ACTIVITY
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Mesa",Filtro.getMesa());
                returnIntent.putExtra("Action","ADD");
                returnIntent.putExtra("Tabla","ftp");
                setResult(RESULT_OK,returnIntent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "ERROR NO "+ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }

    class CreaDocumentoPedido extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Crear")+" "+ActividadPrincipal.getPalabras("Pedido")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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
            values.put("estado", "01");
            values.put("fecha", Filtro.getFechaapertura());
            values.put("empleado", Filtro.getEmpleado());
            values.put("tabla", "pdd");
            values.put("obs", "");
            values.put("updated", dateNow);
            values.put("creado", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            // getting JSON Object
            // Note that create product url accepts POST method
            jsonParserNew = new JSONParserNew();
            JSONObject json = jsonParserNew.makeHttpRequest(url_create_pdd,
                    "POST", values);

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
            Integer success = 0;

            try {
                success = json.getInt(TAG_SUCCESS);
                Filtro.setPedido(json.getInt(TAG_PEDIDO));
                Filtro.setId(json.getInt(TAG_ID));

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
            pDialog.dismiss();

            if (success == 1) {
                Snackbar.make(layout, "Documento Pedido Generado "+Integer.toString(Filtro.getPedido()), Snackbar.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Creado Pedido ", Toast.LENGTH_SHORT).show();
                // SALIR ACTIVITY
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Mesa",Filtro.getMesa());
                returnIntent.putExtra("Action","ADD");
                returnIntent.putExtra("Tabla","pdd");
                setResult(RESULT_OK,returnIntent);
                finish();
                // find your fragment
            } else {
                Toast.makeText(getApplicationContext(), "ERROR NO Creado Pedido ", Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }
    public class CountOpenPdd extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Contar")+" "+ActividadPrincipal.getPalabras("Pedidos")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND pdd.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND pdd.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND pdd.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND pdd.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getMesa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.MESA='" + Filtro.getMesa() + "'";
                } else {
                    xWhere += " AND pdd.MESA='" + Filtro.getMesa() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                } else {
                    xWhere += " AND pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE pdd.ESTADO<'13'";
            } else {
                xWhere += " AND pdd.ESTADO<'13'";
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Count Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

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

                    parseResultCount(response.toString(),params[1]);
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
            pDialog.dismiss();

            if (result == 1) {
                Log.i("COUNT", "OK");
                if (countPdd==0) {
                    url_count = Filtro.getUrl() + "/CountFtpOpen.php";
                    new CountOpenFtp().execute(url_count, "ftp");
                }else{
                    Toast.makeText(getApplicationContext(), "No puede cerrar Mesa Pedidos Abiertos "+Integer.toString(countPdd), Toast.LENGTH_SHORT).show();
                }

            } else {
                Log.e("COUNT", "Failed to fetch data!");
            }
        }
    }
    public class CountOpenFtp extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Contar")+" "+ActividadPrincipal.getPalabras("Facturas")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND ftp.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND ftp.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND ftp.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND ftp.SECCION='" + Filtro.getSeccion() + "'";
                }
            }

            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND ftp.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND ftp.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getMesa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.MESA='" + Filtro.getMesa() + "'";
                } else {
                    xWhere += " AND ftp.MESA='" + Filtro.getMesa() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                } else {
                    xWhere += " AND ftp.FECHA='" + Filtro.getFechaapertura() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE ftp.ESTADO<'13'";
            } else {
                xWhere += " AND ftp.ESTADO<'13'";
            }

            cSql += xWhere;
            if(cSql.equals("")) {
                cSql="Todos";
            }
            Log.i("Sql Count Lista",cSql);
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                // forming th java.net.URL object
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

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

                    parseResultCount(response.toString(),params[1]);
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
            pDialog.dismiss();

            if (result == 1) {
                Log.i("COUNT", "OK");
                if (countFtp==0) {
                    new UpdateMesaApertura().execute("0");
                }else{
                    Toast.makeText(getApplicationContext(), "No puede cerrar Mesa Facturas Abiertas "+Integer.toString(countFtp), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("COUNT", "Failed to fetch data!");
            }
        }
    }
    private void parseResultCount(String result, String table) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                switch (table){
                    case "pdd":
                        countPdd = post.optInt("COUNT");
                        break;
                    case "ftp":
                        countFtp = post.optInt("COUNT");
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class GetDocumentoPedidos extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Pedidos")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND pdd.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND pdd.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND pdd.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND pdd.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if(!(Filtro.getCaja().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.CAJA='" + Filtro.getCaja() + "'";
                } else {
                    xWhere += " AND pdd.CAJA='" + Filtro.getCaja() + "'";
                }
            }
            if(!(Filtro.getTurno().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                } else {
                    xWhere += " AND pdd.COD_TURNO='" + Filtro.getTurno() + "'";
                }
            }
            if(!(Filtro.getMesa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.MESA='" + Filtro.getMesa() + "'";
                } else {
                    xWhere += " AND pdd.MESA='" + Filtro.getMesa() + "'";
                }
            }
            if(!(Filtro.getFechaapertura().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                } else {
                    xWhere += " AND pdd.FECHA='" + Filtro.getFechaapertura() + "'";
                }
            }

            if (xWhere.equals("")) {
                xWhere += " WHERE pdd.ESTADO<'13'";
            } else {
                xWhere += " AND pdd.ESTADO<'13'";
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
                    for (Iterator<DocumentoPedido> it = ldocumentopedido.iterator(); it.hasNext();){
                        DocumentoPedido documentopedido = it.next();
                        it.remove();
                    }

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
            pDialog.dismiss();
            if (result == 1) {
                Log.i("PDD", "OK");
                populate_dialog();
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
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
                documentopedidoItem.setDocumentoPedidoImp_total(post.optString("IMP_TOTAL"));
                documentopedidoItem.setDocumentoPedidoUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());

                ldocumentopedido.add(documentopedidoItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class TraspasoPedidoFactura extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Test.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Facturar")+" "+ActividadPrincipal.getPalabras("Pedidos")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            pid = args[0];
//            oksucces = 0;
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Check for success tag
                    try {
                        int success;
                        String filtro = "";
                        String xWhere = "";
                        if(!(Filtro.getGrupo().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        filtro+=xWhere;

                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        long maxDate = currentDate.getTime().getTime(); // Twice!

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);
                        values.put("filtro",filtro);
                        values.put("estado","01");
                        values.put("serie",Filtro.getSerie());
                        values.put("factura",Long.toString(maxDate));
                        values.put("t_fra","CO");
                        values.put("updated", dateNow);
                        values.put("creado", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        jsonParserNew = new JSONParserNew();
                        JSONObject json = jsonParserNew.makeHttpRequest(url_pedido_a_factura_id,
                                "POST", values);


                        success = json.getInt(TAG_SUCCESS);
                        result = success;
                        if (success == 1) {
                            Filtro.setId(json.getInt(TAG_ID));
                            Filtro.setFactura(json.getInt(TAG_FACTURA));
                            Snackbar.make(layout, ActividadPrincipal.getPalabras("Factura")+" "+ActividadPrincipal.getPalabras("Generada")+" "+Filtro.getSerie()+" "+Integer.toString(Filtro.getFactura()), Snackbar.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR NO "+ActividadPrincipal.getPalabras("Traspaso")+" "+ActividadPrincipal.getPalabras("Pedido")+" "+ActividadPrincipal.getPalabras("Factura"), Toast.LENGTH_SHORT).show();
                            // failed to create product
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return result;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(Integer success) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (result==1) {
//                new CalculaCabecera().execute();
                // SALIR ACTIVITY
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Mesa", Filtro.getMesa());
                returnIntent.putExtra("Action", "ADD");
                returnIntent.putExtra("Tabla", "ftp");
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }

    }

    public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
        view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null && event.getPointerCount() == 2 || event.getPointerCount() == 3) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);

        bmap= Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmap);
        view.draw(canvas);

        //fin.setImageBitmap(bmap);
        return true;
    }


    public void ButtonClick(View v){

        fin.setImageBitmap(bmap);
    }
    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float s=x * x + y * y;
        return (float)Math.sqrt(s);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}

