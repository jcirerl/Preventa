package tpv.cirer.com.marivent.ui;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tpv.cirer.com.marivent.BuildConfig;
import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParser;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.DatePickerFragment;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.modelo.Seccion;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;

//import static com.google.android.gms.internal.zzir.runOnUiThread;

public class EditOpenSeccionFragment extends Fragment implements View.OnKeyListener {
	static final int DATE_DIALOG_ID = 0;
    private int mYear,mMonth,mDay;
    private StringBuilder nowYYYYMMDD;
    private StringBuilder nowDDMMYYYY;
    private SimpleDateFormat dateformatYYYYMMDD;
    private SimpleDateFormat dateformatDDMMYYYY;
    public static List<Seccion> seccionlist;
    public boolean ok_delete_lectura = false;
    public static final String TAG = "Modifica Seccion";

    LinearLayout rootView;

    CheckBox chkApertura;
    EditText txtMaximoCaja;
    TextView txtSeccion;
    EditText txtFecha_apertura;
    TextView lblMaximoCaja;
    TextView lblSeccion;
    TextView lblFecha;

    ToggleButton btnGuardar1;
  //  Button btnGuardar;
    Button btnFecha;
    Button btnSalir;
    private String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    JSONParserNew jsonParserNew = new JSONParserNew();

    // single Seccion url
    private static final String url_Seccion_details = Filtro.getUrl()+"/lee_seccion_detalle_1.php";
 
    // url to update Seccion
    private static final String url_update_Seccion = Filtro.getUrl()+"/modifica_seccion_1.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    private static final String TAG_SECCION = "seccion";
    private static final String TAG_FECHAAPERTURA = "fechaapertura";
    private static final String TAG_MAXCAJA = "maxcaja";
    private static final String TAG_APERTURA = "apertura";

    private static EditOpenSeccionFragment EditSeccion = null;

    public static EditOpenSeccionFragment newInstance(String id) {
        EditOpenSeccionFragment EditSeccion = new EditOpenSeccionFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        EditSeccion.setArguments(args);
        return EditSeccion;
    }

    public EditOpenSeccionFragment() {
        // Required empty public constructor
    }
    public static EditOpenSeccionFragment getInstance(){
        if(EditSeccion == null){
            EditSeccion = new EditOpenSeccionFragment();
        }
        return EditSeccion;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        pid = getArguments() != null ? getArguments().getString("id") : "0";
        seccionlist = new ArrayList<Seccion>();
        Filtro.setTag_fragment("EditOpenSeccionFragment");

        /// Poner Titulo CABECERA
        ((ActividadPrincipal) getActivity()).setTitle(((ActividadPrincipal)getActivity()).getPalabras("Abrir")+" "+((ActividadPrincipal)getActivity()).getPalabras("Seccion"));

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //   View rootView = inflater.inflate(R.layout.add_lectura, container, false);
        rootView = (LinearLayout) inflater.inflate(R.layout.edit_seccion,container, false);

        // Edit Text

        lblMaximoCaja = (TextView) rootView.findViewById(R.id.lblMaximoCaja);
        lblMaximoCaja.setText(ValorCampo(R.id.lblMaximoCaja, lblMaximoCaja.getClass().getName(),0));
        txtMaximoCaja = (EditText) rootView.findViewById(R.id.MaximoCaja);

        lblFecha = (TextView) rootView.findViewById(R.id.lblFecha);
        lblFecha.setText(ValorCampo(R.id.lblFecha, lblFecha.getClass().getName(),0));
        txtFecha_apertura = (EditText) rootView.findViewById(R.id.Fecha_apertura);
        txtFecha_apertura.setEnabled(false);

        chkApertura = (CheckBox) rootView.findViewById(R.id.chkApertura);
        chkApertura.setText(((ActividadPrincipal)getActivity()).getPalabras(getResources().getString(R.string.ON_apertura)));

        lblSeccion = (TextView) rootView.findViewById(R.id.lblSeccion);
        lblSeccion.setText(ValorCampo(R.id.lblSeccion, lblSeccion.getClass().getName(),0));
        txtSeccion = (TextView) rootView.findViewById(R.id.Seccion);

        txtMaximoCaja.setOnKeyListener(this);

        btnFecha = (Button)rootView.findViewById(R.id.date);
        btnFecha.setText(ValorCampo(R.id.date, btnFecha.getClass().getName(),0));
        // button click event
        btnSalir = (Button)rootView.findViewById(R.id.BtnSalir);
        btnSalir.setText(ValorCampo(R.id.BtnSalir, btnSalir.getClass().getName(),0));
        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
//                getActivity().getSupportFragmentManager().popBackStack();
//                getActivity().getSupportFragmentManager().executePendingTransactions();
            }
        });

        // Create button
//        btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);

        // Getting complete Seccion details in background thread
    //    new GetSeccionDetails().execute();
        btnGuardar1 = (ToggleButton)rootView.findViewById(R.id.BtnGuardar1);
        btnGuardar1.setText(ValorCampo(R.id.BtnGuardar1, btnGuardar1.getClass().getName(),0));
        btnGuardar1.setText(ValorCampo(R.id.BtnGuardar1, btnGuardar1.getClass().getName(),1));
        btnGuardar1.setEnabled(false);
        btnGuardar1.setChecked(false);
        btnGuardar1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                String cMaximo = txtMaximoCaja.getText().toString();
                cMaximo = cMaximo.replace(".","");
                cMaximo = cMaximo.replace(",",".");
                txtMaximoCaja.setText(cMaximo);

                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

                for (int i = 0; i < seccionlist.size(); i++) {
                    seccionlist.get(i).setSeccionMax_Caja(txtMaximoCaja.getText().toString());
                    seccionlist.get(i).setSeccionApertura((chkApertura.isChecked()?1:0));
                    seccionlist.get(i).setSeccionFecha_Apertura(txtFecha_apertura.getText().toString());

                }
                // starting background task to update Seccion
                new SaveSeccionDetails().execute();
/*                String Mensaje;
                if(btnGuardar1.isChecked())
                    Mensaje = "Botón Toggle: ON";
                else
                    Mensaje = "Botón Toggle: OFF";
                Toast.makeText(
                        getActivity(),
                        Mensaje,
                        Toast.LENGTH_LONG).show();*/
            }
        });
        // save button click event
/*        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String cMaximo = txtMaximoCaja.getText().toString();
                cMaximo = cMaximo.replace(".","");
                cMaximo = cMaximo.replace(",",".");
                txtMaximoCaja.setText(cMaximo);

                // Eliminar Teclado Virtual
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                for (int i = 0; i < seccionlist.size(); i++) {
                        seccionlist.get(i).setSeccionMax_Caja(txtMaximoCaja.getText().toString());
                        seccionlist.get(i).setSeccionApertura((chkApertura.isChecked()?1:0));
                        seccionlist.get(i).setSeccionFecha_Apertura(txtFecha_apertura.getText().toString());

                }
                    // starting background task to update Seccion
                new SaveSeccionDetails().execute();
            }
        });
*/
        chkApertura.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                if(chkApertura.isChecked()) {
    //                btnGuardar.setEnabled(true);
                    btnGuardar1.setEnabled(true);
                    btnGuardar1.setChecked(true);
                }else {
    //                btnGuardar.setEnabled(false);
                    btnGuardar1.setEnabled(false);
                    btnGuardar1.setChecked(false);
                }
            }
        });

        btnFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        Log.i("Fragment id ", " # " + pid );
        //       new AsyncHttpTask().execute(url);

        return rootView;
    }
    public String getNameResource(int id, String view, int num) {
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        Log.i("get1TypeResource",view);
        String restext="";
        if (view.contains("TextView")){
            TextView text = (TextView) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("CheckBox")){
            CheckBox text = (CheckBox) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("Button")){
            Button text = (Button) rootView.findViewById(id);
            restext = text.getText().toString();
            Log.i("get1NameResource",text.getText().toString());
        }
        if (view.contains("ToggleButton")){
            if (num==0) {
                ToggleButton textOn = (ToggleButton) rootView.findViewById(id);
                restext = textOn.getText().toString();
                Log.i("get1NameResource",textOn.getText().toString());
            }else{
                ToggleButton textOff = (ToggleButton) rootView.findViewById(id);
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
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Bundle args = new Bundle();

        // Poner ultima fecha registrada en la seccion.
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        SimpleDateFormat formatoDelTextoHoy = new SimpleDateFormat("dd-MM-yyyy");

        String strFecha = txtFecha_apertura.getText().toString();
        Date fechainicio = null;
        Date fechafinal = null;
        Date fechahoy = null;
        Log.i("Rol:",Filtro.getRoles()+" FA "+Filtro.getFechaapertura()+" FI "+Filtro.getFechaInicio()+" FF "+Filtro.getFechaFinal());
        try {
            if (Filtro.getRoles().equals("emp")) {
                fechainicio = formatoDelTexto.parse(Filtro.getFechaapertura());
                fechafinal = formatoDelTexto.parse(Filtro.getFechaapertura());
            }else{
                fechainicio = formatoDelTexto.parse(Filtro.getFechaInicio());
                fechafinal = formatoDelTexto.parse(Filtro.getFechaFinal());
            }
            fechahoy = formatoDelTextoHoy.parse(strFecha);
        } catch (ParseException ex) {
            Log.i("Rol:","ERROR");
            ex.printStackTrace();

        }
        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
//        System.out.println("Son las: "+formatoHora.format(fechaActual)+" de fecha: "+formatoFecha.format(fechaActual));

        //Fecha inicio desglosada:
        Calendar fecha_inicio = Calendar.getInstance();
        fecha_inicio.setTime(fechainicio);

        /// PONER FECHA MINIMO DATE EN FUNCION DEL USUARIO
        long minDate = fecha_inicio.getTime().getTime(); // Twice!
        args.putLong("minDate",minDate);
        //////////////////////////////////////////////////////7

        Calendar fecha_final = Calendar.getInstance();
        fecha_final.setTime(fechafinal);
////        fecha_cal_max.add(Calendar.DATE,30); //Añadir 1 dia al ultimo dia cerrado

        long maxDate = fecha_final.getTime().getTime(); // Twice!

        args.putLong("maxDate",maxDate);


        Calendar fecha_hoy = Calendar.getInstance();
        fecha_hoy.setTime(fechahoy);
////        fecha_cal_max.add(Calendar.DATE,30); //Añadir 1 dia al ultimo dia cerrado
        int año = fecha_hoy.get(Calendar.YEAR);
        int mes = fecha_hoy.get(Calendar.MONTH);
        int dia = fecha_hoy.get(Calendar.DAY_OF_MONTH);
        int maxhora = fecha_hoy.get(Calendar.HOUR_OF_DAY);
        int maxminuto = fecha_hoy.get(Calendar.MINUTE);
        int maxsegundo = fecha_hoy.get(Calendar.SECOND);


        args.putInt("year", año);
        args.putInt("month", mes);
        args.putInt("day", dia);


        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");

        Log.i("FECHA APERTURA",txtFecha_apertura.getText().toString()+"_"+Filtro.getFechaInicio()+" _ "+Filtro.getFechaFinal()+" _ "+fecha_hoy.getTime());

    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;
            String numMes = mMonth < 10 ? "0" + mMonth : "" + mMonth;
            String numDia = mDay < 10 ? "0" + mDay : "" + mDay;
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try{
                txtFecha_apertura.setText(new StringBuilder().append(numDia).append("-").append(numMes).append("-").append(mYear));
                String StringRecogido = String.valueOf(txtFecha_apertura);
                Date fecha = sdf1.parse(StringRecogido);
                dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
                nowYYYYMMDD = new StringBuilder(dateformatYYYYMMDD.format(fecha));
                dateformatDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
                nowDDMMYYYY = new StringBuilder(dateformatDDMMYYYY.format(fecha));
                txtFecha_apertura.setText(nowDDMMYYYY);
                seccionlist.get(0).setSeccionFecha_Apertura(txtFecha_apertura.getText().toString());
            }
            catch(Exception e){
                e.getMessage();
            }
            Filtro.setFecha(String.valueOf(year) + "-" + numMes
                    + "-" + numDia);
            Log.i("Fecha: ",Filtro.getFecha());


            Toast.makeText(
                    getActivity(),
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth)+" / "+Filtro.getFecha(),
                    Toast.LENGTH_LONG).show();

        }
    };


    /**
     * Background Async Task to Get complete Seccion details
     * */
    class GetSeccionDetails extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(ActividadPrincipal.getPalabras("Cargando")+" Detalles Seccion. "+ActividadPrincipal.getPalabras("Espere por favor")+"...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Getting Seccion details in background thread
         * */
        @Override
		protected String doInBackground(String... params) {
 
            // updating UI from Background Thread
            ((ActividadPrincipal) getActivity()).runOnUiThread(new Runnable() {
                @Override
				public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
//                        List<NameValuePair> params = new ArrayList<NameValuePair>();
//                        params.add(new BasicNameValuePair("pid", pid));
                        ContentValues values = new ContentValues();
                        values.put("pid", pid);

                        // getting Seccion details by making HTTP request
                        // Note that Seccion details url will use GET request
//                        JSONObject json = jsonParser.makeHttpRequest(
//                                url_Seccion_details, "GET", values);
                        JSONObject json = jsonParserNew.makeHttpRequest(
                                url_Seccion_details, "GET", values);

                        // check your log for json response
///                        Log.d("Single Seccion Details", json.toString());
 
                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received Seccion details
                            JSONArray SeccionObj = json
                                    .getJSONArray(TAG_SECCION); // JSON Array
 
                            // get first Seccion object from JSON Array
                            JSONObject Seccion = SeccionObj.getJSONObject(0);
 
                            // Seccion with this pid found
                            // Edit Text
/*                            txtSeccionActual = (EditText) findViewById(R.id.SeccionActual);
                            txtFecha_apertura = (EditText) findViewById(R.id.Fecha_apertura);
                            txtSeccionAnterior = (TextView) findViewById(R.id.SeccionAnterior);
                            txtConsumo = (TextView) findViewById(R.id.Consumo);
                            txtSeccion = (TextView) findViewById(R.id.Seccion);
                            
*/                          String xfecha="";  
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                            try{
                                String StringRecogido = Seccion.getString(TAG_FECHAAPERTURA);
                                Date datehora = sdf1.parse(StringRecogido);

                                Calendar c = Calendar.getInstance();
                                c.setTime(datehora);
                                c.add(Calendar.DATE, 1);  // number of days to add
                                xfecha = sdf2.format(c.getTime());  // dt is now the new date

                                //System.out.println("Fecha input : "+datehora);
                  ///*              xfecha = sdf2.format(datehora);

                            } catch (Exception e) {
                                e.getMessage();
                            }


                            // display Seccion data in EditText
                            txtFecha_apertura.setText(xfecha);
                            txtSeccion.setText(Seccion.getString(TAG_SECCION));
                            chkApertura.setChecked((0 != Integer.parseInt(Seccion.getString(TAG_APERTURA)) ? true : false));

//                            txtMaximoCaja.setText(Seccion.getString(TAG_MAXCAJA));
                            double xMaximo = Double.valueOf(Seccion.getString(TAG_MAXCAJA));
                            txtMaximoCaja.setText(String.format("%1$,.2f", xMaximo));

                            Seccion seccionItem = new Seccion();
                            seccionItem.setSeccionFecha_Apertura(Seccion.getString(TAG_FECHAAPERTURA));
                            seccionItem.setSeccionMax_Caja(Seccion.getString(TAG_MAXCAJA));
                            seccionItem.setSeccionSeccion(Seccion.getString(TAG_SECCION));
                            seccionItem.setSeccionApertura(Integer.parseInt(Seccion.getString(TAG_APERTURA)));

                            seccionlist.add(seccionItem);


                        }else{
                            // Seccion with pid not found
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
		protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }


    /**
     * Background Async Task to  Save Seccion Details
     * */
    class SaveSeccionDetails extends AsyncTask<String, String, Integer> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Guardando Seccion ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Saving Seccion
         * */
        @Override
		protected Integer doInBackground(String... args) {
                // getting updated data from EditTexts
            String seccion = "";
            String xfecha="";
            String StringRecogido="";
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

            String apertura = "";
            String maxcaja = "";
            Integer result = 0;
            for (int i = 0; i < seccionlist.size(); i++) {
                try{
                    StringRecogido = String.valueOf(seccionlist.get(i).getSeccionFecha_Apertura());
                    Date datehora = sdf1.parse(StringRecogido);
                    //System.out.println("Fecha input : "+datehora);
                    xfecha = sdf2.format(datehora);

                }
                catch(Exception e){
                    e.getMessage();
                }
                apertura = String.valueOf(seccionlist.get(i).getSeccionApertura());
                maxcaja = String.valueOf(seccionlist.get(i).getSeccionMax_Caja());
                seccion = String.valueOf(seccionlist.get(i).getSeccionSeccion());
            }

            Calendar currentDate = Calendar.getInstance(); //Get the current date
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
            String dateNow = formatter.format(currentDate.getTime());
            //           System.out.println("Now the date is :=>  " + dateNow);

            // Building Parameters
            ContentValues values = new ContentValues();
            values.put(TAG_PID, pid);
            values.put(TAG_SECCION,seccion);
            values.put(TAG_FECHAAPERTURA,xfecha);
            values.put(TAG_MAXCAJA,maxcaja);
            values.put(TAG_APERTURA,apertura);
            values.put("updated", dateNow);
            values.put("usuario", Filtro.getUsuario());
            values.put("ip",getLocalIpAddress());

            Log.i("fecha", xfecha + " ... " + StringRecogido + " ..." + pid + " - " + seccion + " - " + maxcaja + " - " + dateNow + " - "+Filtro.getUsuario());


            // sending modified data through http request
            // Notice that update Seccion url accepts POST method
            JSONObject json = jsonParserNew.makeHttpRequest(url_update_Seccion,
                    "POST", values);
 
            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    result = 1;
                    // PONEMOS EN FILTRO FECHA APERTURA LA FECHA ABIERTA CON FORMATO YYYY-MM-DD
                    Filtro.setFechaapertura(xfecha);

                } else {
                    result = 0;
                    // failed to update Seccion
                    Toast.makeText(getActivity(), ActividadPrincipal.getPalabras("No actualizada seccion"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return result;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
		protected void onPostExecute(Integer result) {
            // dismiss the dialog once Seccion uupdated
            pDialog.dismiss();
            if (result == 1) {
                populatefecha();
            }
        }
    }
    public void populatefecha(){
        int textViewID = getResources().getIdentifier("Fecha_apertura", "id", BuildConfig.APPLICATION_ID);
        View rootView = ((ActividadPrincipal)getActivity()).getWindow().getDecorView().findViewById(android.R.id.content);

        TextView textFecha = (TextView) rootView.findViewById(textViewID);

        String xfecha="";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        try{
            Date datehora = sdf1.parse(Filtro.getFechaapertura());

            Calendar c = Calendar.getInstance();
            c.setTime(datehora);
            xfecha = sdf2.format(c.getTime());  // dt is now the new date

        } catch (Exception e) {
            e.getMessage();
        }
        // display Seccion data in EditText
        textFecha.setText(xfecha);getActivity().onBackPressed();}
 

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
      
     //TextView responseText = (TextView) findViewById(R.id.responseText);
     EditText myEditText = (EditText) view;
     // Ponerse al final del edittext
     int pos = myEditText.getText().length();
     myEditText.setSelection(pos);
     /////////////////////////////////////////
     if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
      keyCode == EditorInfo.IME_ACTION_DONE ||
      event.getAction() == KeyEvent.ACTION_DOWN &&
      event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
       
      if (!event.isShiftPressed()) {
         Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
         int id = view.getId();
         if (id == R.id.MaximoCaja) {
              String cMaximo = myEditText.getText().toString();
              if (cMaximo.matches("")) {
                 Toast.makeText(getActivity(), "Valor Vacio", Toast.LENGTH_SHORT).show();
      //            this.btnGuardar.setEnabled(false);
                  this.btnGuardar1.setEnabled(false);
                  this.btnGuardar1.setChecked(false);
                  return false;
             }

             cMaximo = cMaximo.replace(".","");
             cMaximo = cMaximo.replace(",",".");

             double xMaximo = Double.valueOf(cMaximo);
   	         txtMaximoCaja.setText(String.format("%1$,.2f", xMaximo));
         }else if (id == R.id.chkApertura) {
         }
         return true;
      }               
     }
     return false; // pass on to other listeners.
    
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState !=null){
            //    new AsyncHttpTask().execute(url);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        new GetSeccionDetails().execute();
    }

}

