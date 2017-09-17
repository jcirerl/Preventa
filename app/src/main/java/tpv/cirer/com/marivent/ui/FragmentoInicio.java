package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import tpv.cirer.com.marivent.herramientas.CargaFragment;
import tpv.cirer.com.marivent.herramientas.DividerItemDecoration;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.WrapContentLinearLayoutManager;
import tpv.cirer.com.marivent.modelo.Popular;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lpopular;


/**
 * Fragmento para la sección de "Inicio"
 */

/*public class FragmentoInicio extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptadorpopulares;

    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

        adaptadorpopulares = new AdaptadorInicio();
        reciclador.setAdapter(adaptadorpopulares);
        return view;
    }

}*/
/**
 * Created by JUAN on 21/09/2016.
 */

public class FragmentoInicio extends Fragment {
    //   private boolean listUpdated = false; // init the update checking value

    // Progress Dialog
    private ProgressDialog pDialogpopular;

    // JSON parser class

    private static String url_rellenaponpulares;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_POPULAR = "posts";

    private static ArrayList<Popular> lpopular1 = null;

    public static AdaptadorPopulares adaptadorpopulares;
    public static AdaptadorPopulares adaptador;



    private static FragmentoInicio fragmentinicio = null;

    public static RecyclerView recViewpopular;
    View rootViewpopular;
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
//    private AdaptadorPopulares adaptadorpopulares;

    TextView textView;

    FloatingActionButton btnTicket,btnPedido,btnMesas;

    public FragmentoInicio() {
    }
    public static FragmentoInicio newInstance() {
        FragmentoInicio fragmentinicio = new FragmentoInicio();
        return fragmentinicio;
    }
    public static FragmentoInicio getInstance(){
        if(fragmentinicio == null){
            fragmentinicio = new FragmentoInicio();
        }
        return fragmentinicio;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        lpopular1 = new ArrayList<Popular>();

        url_rellenaponpulares = Filtro.getUrl()+"/RellenaListaPopulares.php";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootViewpopular == null) {
/*             setHasOptionsMenu(true);*/
            rootViewpopular = inflater.inflate(R.layout.fragmento_inicio, container, false);
            recViewpopular = (RecyclerView) rootViewpopular.findViewById(R.id.reciclador);
            //     recViewlineafacturatickets.setHasFixedSize(true);

            // 2. set layoutManger
            recViewpopular.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));

            // 3. create an adapter
            adaptadorpopulares = new AdaptadorPopulares(getActivity(), lpopular);

            // 4. set adapter
            recViewpopular.setAdapter(adaptadorpopulares);

            // 5. set item animator to DefaultAnimator
            recViewpopular.setItemAnimator(new DefaultItemAnimator());

            // 6. Añadir separador recyclerView
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
            recViewpopular.addItemDecoration(itemDecoration);

       /*     btnMesas = (FloatingActionButton)rootViewpopular.findViewById(R.id.btnMesas);
            btnMesas.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tables));
            btnMesas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_mesas_admin")){
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                    }else {
                        // 1. create an intent pass class name or intnet action name
                        Intent intent = new Intent(getActivity(),MesasActivity.class);

                        // 2. put MESA, OPERACION in intent
                        intent.putExtra("Mesa",  "");
                        intent.putExtra("Action","");
                        intent.putExtra("Tabla","");

                        // 3. start the activity
                        startActivityForResult(intent, 1);
                    }
                }
            });
*/          textView = (TextView) rootViewpopular.findViewById(R.id.textView);
            textView.setText(ValorCampo(R.id.textView, textView.getClass().getName(),0));

            btnTicket = (FloatingActionButton)rootViewpopular.findViewById(R.id.btnTicket);
            btnTicket.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.invoice32));
            btnTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_ftp_admin")){
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                    }else {
                        Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoFactura.newInstance(0),getFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener((ActividadPrincipal)getActivity());
                        if (cargafragment.getFragment() != null){
                            cargafragment.setTransaction(R.id.contenedor_principal);
                            Snackbar.make(view, "Ir a Ticket", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
            btnPedido = (FloatingActionButton)rootViewpopular.findViewById(R.id.btnPedido);
            btnPedido.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.order32));
            btnPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!((ActividadPrincipal)getActivity()).getCruge("action_pdd_admin")){
                        Snackbar.make(view, ActividadPrincipal.getPalabras("No puede realizar esta accion"), Snackbar.LENGTH_LONG).show();
                    }else {
                        Filtro.setMesa(lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA());
                        CargaFragment cargafragment = null;
                        cargafragment = new CargaFragment(FragmentoPedido.newInstance(),getFragmentManager());
                        cargafragment.getFragmentManager().addOnBackStackChangedListener((ActividadPrincipal)getActivity());
                        if (cargafragment.getFragment() != null){
                            cargafragment.setTransaction(R.id.contenedor_principal);
                            Snackbar.make(view,ActividadPrincipal.getPalabras("Ir")+" "+ActividadPrincipal.getPalabras("Pedido"), Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

        return rootViewpopular;
    }



    public String getNameResource(int id, String view, int num) {
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) rootViewpopular.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("CheckBox")){
            CheckBox text = (CheckBox) rootViewpopular.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("Button")){
            Button text = (Button) rootViewpopular.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("ToggleButton")){
            if (num==0) {
                ToggleButton textOn = (ToggleButton) rootViewpopular.findViewById(id);
                restext = textOn.getText().toString();
                Log.i("get1NameResource",textOn.getText().toString());
            }else{
                ToggleButton textOff = (ToggleButton) rootViewpopular.findViewById(id);
                restext = textOff.getText().toString();
                Log.i("get1NameResource",textOff.getText().toString());
            }
        }
        return restext;
    }

    public String ValorCampo (int ID, String viewclass, int num ){
        String name = getNameResource(ID, viewclass, num);
        if (!name.equals("")){
            String valorcampo =((ActividadPrincipal)getActivity()).getPalabras(name);
            if(!valorcampo.equals("")){
                return valorcampo;
            }else{
                return name;
            }
        }
        return "**";
    }

    @Override
    public void onResume() {
        super.onResume();
            Filtro.setTag_fragment("FragmentoInicio");
            /// Poner Datos CABECERA
        ///    ((ActividadPrincipal) getActivity()).setCabecera("Inicio",0.00,1);
            ((ActividadPrincipal) getActivity()).setTitle(((ActividadPrincipal)getActivity()).getPalabras("Inicio"));
            Spannable text = new SpannableString(((ActividadPrincipal) getActivity()).getTitle());
            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(((ActividadPrincipal) getActivity()), R.color.light_blue_500)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            ((ActividadPrincipal) getActivity()).setTitle(text);

 ////           new AsyncHttpTaskPopulares().execute(url_rellenaponpulares);
////            adaptadorpopulares.notifyDataSetChanged();
    }

    public class AsyncHttpTaskPopulares extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogpopular = new ProgressDialog(getActivity());
            pDialogpopular.setMessage(ActividadPrincipal.getPalabras("Cargando")+" Populares"+". "+ActividadPrincipal.getPalabras("Espere por favor")+"...");
            pDialogpopular.setIndeterminate(false);
            pDialogpopular.setCancelable(true);
            pDialogpopular.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";

            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE popular.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND popular.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE popular.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND popular.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE popular.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND popular.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE popular.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND popular.SECCION='" + Filtro.getSeccion() + "'";
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

                    for (Iterator<Popular> it = lpopular1.iterator(); it.hasNext();){
                        Popular popular = it.next();
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
            pDialogpopular.dismiss();
            /* Download complete. Lets update UI */
            if (result == 1) {
                Log.i("ADAPTADOR POPULARES", Integer.toString(adaptadorpopulares.getItemCount()));
                adaptadorpopulares.notifyDataSetChanged();
           } else {
                Log.e(TAG_POPULAR, "Failed to fetch data!");
            }
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

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray(TAG_POPULAR);

            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);

                Popular popularItem = new Popular();
                popularItem.setPrecio(Float.parseFloat(String.valueOf(post.optDouble("PREU_VTA1"))));
                popularItem.setNombre(post.optString("NOMBRE_ARE"));
                popularItem.setArticulo(post.optString("ARTICULO"));
                popularItem.setTipo_are(post.optString("TIPO_ARE"));
                popularItem.setUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim());
                lpopular1.add(popularItem);
                Log.i("Articulo: ", popularItem.getTipo_are() + " - " + popularItem.getNombre());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
