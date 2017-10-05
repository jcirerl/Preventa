package tpv.cirer.com.marivent.ui;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.conexion_http_post.JSONParserNew;
import tpv.cirer.com.marivent.herramientas.ArticulosListArrayAdapter;
import tpv.cirer.com.marivent.herramientas.BadgeView;
import tpv.cirer.com.marivent.herramientas.CircleArea;
import tpv.cirer.com.marivent.herramientas.DecoratedTextViewDrawable;
import tpv.cirer.com.marivent.herramientas.Filtro;
import tpv.cirer.com.marivent.herramientas.SerialExecutor;
import tpv.cirer.com.marivent.modelo.Articulo;
import tpv.cirer.com.marivent.modelo.Articulos;
import tpv.cirer.com.marivent.modelo.DocumentoPedido;
import tpv.cirer.com.marivent.modelo.Mesa;
import tpv.cirer.com.marivent.modelo.Zonas;

import static android.view.MotionEvent.INVALID_POINTER_ID;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.CountTable;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.LoadImageFromWebOperations;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.comidas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getLocalIpAddress;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.itemmesas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.larticulobuffet;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lcategoria;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lparam;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lzonas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.mSerialExecutorActivity;

/**
 * Created by JUAN on 04/11/2016.
 */

public class MesasActivity extends AppCompatActivity implements ArticulosListArrayAdapter.OnHeadlineSelectedListenerArticulos{
    // Variables para detect click circle
    private static final String TAG_CIRCLE = "CirclesDrawingView";
    boolean flag = false;
    ////////////////////////////////////////////////////////////////
    private static String URL_ARTICULOS;

    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    private int mActivePointerId = INVALID_POINTER_ID;
    private float xCoOrdinate, yCoOrdinate, mLastTouchX,mLastTouchY, mPosX,mPosY;
    // JSON parser class
    private static JSONParserNew jsonParserNew = null;
    private static String url_updatemesacoordenate;
    private static String url_update_cabecera;
    private static String url_updateare_activobuffet;
    private static String url_updatemesa_mensaje;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PEDIDO = "pedido";
    private static final String TAG_FACTURA = "factura";
    private static final String TAG_ID = "id";

    private static final String TAG = "MESAS PDD";
    private MySerialExecutor mSerialExecutor;

    /** All available circles */
    private static final int CIRCLES_LIMIT = 1;
    private static final HashSet<CircleArea> mCircles = new HashSet<CircleArea>(CIRCLES_LIMIT);
    private static final SparseArray<CircleArea> mCirclePointer = new SparseArray<CircleArea>(CIRCLES_LIMIT);

    boolean actionMove;

    int result;
    int countPdd;
    int countFtp;
    String valormesa;
    String valoraction;
    String valortabla;
    String pid;
    String selectedVal;
    String totalfactura;
    String obsfactura;

    float dx=0,dy=0,x=0,y=0;

    ProgressDialog pDialog,pDialogAre;
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
    ArrayList<View> viewmesaList;
    ArrayList<View> viewzonaList;
    TextView nombrezona;

    ImageView imageview;
    Drawable dpedidos,dfacturas,dMesa;
    Bitmap bimage;
    RelativeLayout layout;
    LinearLayout linearLayout;
    Button image ;
    Button btnzona;

    Bitmap innerBitmap;
    TextView texto;
    BadgeView badge;
    View target;
    Mesa model;
    Zonas modelzona;

    public static float xCoordenate, yCoordenate;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.mesas);
        ((AppCompatActivity)this).getSupportActionBar().setTitle(getPalabras("Mesas"));
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
        url_updateare_activobuffet = Filtro.getUrl()+"/updateare_activobuffet.php";
        url_updatemesa_mensaje = Filtro.getUrl()+"/updatemesa_mensaje.php";


        imageview = new ImageView(this);

 ////       agregarToolbar();
        // Rellenar string toolbar_mesas
        mesaplanningList = new ArrayList<Mesa>();
        URL_MESASPLANNING = Filtro.getUrl() + "/get_mesasplanning.php";
        new GetMesas().execute(URL_MESASPLANNING,lzonas.get(0).getZonasZona().trim());
        Log.i("Mesas","Create Planning");
        url_updatemesacoordenate = Filtro.getUrl()+"/updatemesa_coordenadas.php";

        mSerialExecutor = new MySerialExecutor(getApplicationContext());

    }
    public void onArticulosBuffetNewChecked(String saldo) {
       // txtBoxTotal.setText(String.format("%1$,.2f", Float.parseFloat(saldo)) + " " + Filtro.getSimbolo());

//        Toast.makeText(this, getPalabras("Articulo") + " " + saldo, Toast.LENGTH_SHORT).show();
    }
    public void onArticulosCobroNewChecked(String saldo) {
        // txtBoxTotal.setText(String.format("%1$,.2f", Float.parseFloat(saldo)) + " " + Filtro.getSimbolo());

//        Toast.makeText(this, getPalabras("Articulo") + " " + saldo, Toast.LENGTH_SHORT).show();
    }
    /**
     * Search and creates new (if needed) circle based on touch area
     *
     * @param xTouch int x of touch
     * @param yTouch int y of touch
     *
     * @return obtained {@link CircleArea}
     */
    private CircleArea obtainTouchedCircle(final float xTouch, final float yTouch, final int colorAlpha, final String mesaTouch) {
        String cModelo = "";
        switch (colorAlpha){
            case 255:
                cModelo = "FACTURAS";
                break;
            case 150:
                cModelo = "PEDIDOS";
                break;
        }
        CircleArea touchedCircle = getTouchedCircle(xTouch, yTouch, cModelo, mesaTouch);
        if (null == touchedCircle) {
            touchedCircle = new CircleArea(xTouch, yTouch, 50/*mRadiusGenerator.nextInt(RADIUS_LIMIT) + RADIUS_LIMIT*/, cModelo, mesaTouch);
            if(!cModelo.equals("")) {
                mCircles.add(touchedCircle);
            }
/*
                if (mCircles.size() == CIRCLES_LIMIT) {
                }
                if (flag == false) {
                    mCircles.add(touchedCircle);
                    flag = true;
                }
*/
        }
        return touchedCircle;
    }
    /**
     * Clears all CircleArea - pointer id relations
     */
    private void clearCirclePointer() {
        mCirclePointer.clear();
    }

    /**
     * Determines touched circle
     *
     * @param xTouch int x touch coordinate
     * @param yTouch int y touch coordinate
     *
     * @return {@link CircleArea} touched circle or null if no circle has been touched
     */
    private CircleArea getTouchedCircle(final float xTouch, final float yTouch, final String mModelo, String mMesa) {
        CircleArea touched = null;
        boolean bExiste = false;
        for (CircleArea circle : mCircles) {
            //               Log.i("Circle Modelo", circle.getCirculo() + " - " + mModelo);
            //               Log.i("Circle Mesa", circle.getMesacirculo() + " - " + mMesa);
            if ((circle.getCirculo().equals(mModelo)) && (circle.getMesacirculo().equals(mMesa))) {
                bExiste = true;
                break;
            }
        }
        if(!bExiste) {
            Log.i("Circle Touched bExiste",String.valueOf(touched)+" Nro Circles "+String.valueOf(mCircles.size()));
            return touched;
        }

        for (CircleArea circle : mCircles) {
            if (((circle.centerX - xTouch) * (circle.centerX - xTouch) + (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius * circle.radius && ((circle.getCirculo().equals(mModelo))) && (circle.getMesacirculo().equals(mMesa)))) {
                touched = circle;
                break;
            }
        }
        Log.i("Circle Touched",String.valueOf(touched)+" Nro Circles "+String.valueOf(mCircles.size()));
        return touched;
    }

    private View findViewAtPosition(View parent, int x, int y) {
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)parent;
            for (int i=0; i<viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                View viewAtPosition = findViewAtPosition(child, x, y);
                if (viewAtPosition != null) {
                    return viewAtPosition;
                }
            }
            return null;
        } else {
            Rect rect = new Rect();
            parent.getGlobalVisibleRect(rect);
            if (rect.contains(x, y)) {
                return parent;
            } else {
                return null;
            }
        }
    }
    private void populate_zonas() {
        linearLayout = (LinearLayout)findViewById(R.id.zonas);
        if (nombrezona==null) {
            nombrezona = (TextView) linearLayout.findViewById(R.id.nombre_zona);
            nombrezona.setText(getZonaNombre(lzonas.get(0).getZonasZona()));
            ((AppCompatActivity)this).getSupportActionBar().setTitle(getPalabras("Mesas")+" "+getZonaNombre(lzonas.get(0).getZonasZona()));
        }
        viewzonaList = new ArrayList<View>();
        for (int i = 0; i < lzonas.size(); i++) {
            modelzona = lzonas.get(i);

            btnzona = new Button(this);

            Drawable d = LoadImageFromWebOperations(modelzona.getZonasUrlimagen());

/*            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            Log.i("Button Image",Integer.toString(bitmap.getWidth())+" "+Integer.toString(bitmap.getHeight()));
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, Filtro.getOpmesas(), Filtro.getOpmesas(), true);
            Drawables d1 = new BitmapDrawable(getResources(),bMapScaled); //Converting bitmap into drawable
*/
            btnzona.setCompoundDrawablesWithIntrinsicBounds(  null,  null, null, d );

            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setStroke(2, Filtro.getColorItemZero());
            drawable.setColor(Color.TRANSPARENT);
            btnzona.setBackground(drawable);

//            btnzona.setBackgroundColor(Color.TRANSPARENT);
            btnzona.setText(modelzona.getZonasZona().trim());

            btnzona.setVisibility(View.VISIBLE);
            btnzona.setTag(modelzona.getZonasZona());

            btnzona.setAlpha(1.0f); // COLOR BRILLANTE MESA ABIERTA


            btnzona.setOnClickListener(new MyClickListenerZona());

            // Adds the view to the layout
            linearLayout.addView(btnzona);
            viewzonaList.add(btnzona);

        }

    }
    private final class MyClickListenerZona implements View.OnClickListener {
        @Override
        public void onClick(View v) {
/*            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(Color.RED);
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            btnzona.setBackground(shapedrawable);
*/
            // GUARDAMOS LOS MOVIMIENTOS DE MESAS
            for (int i = 0; i < mesaplanningList.size(); i++) {
                model = mesaplanningList.get(i);
                if (model.getMesaModificado()==1) {
                    Filtro.setMesa(model.getMesaMesa());
                    xCoordenate = model.getMesaXCoordenate();
                    yCoordenate = model.getMesaYCoordenate();
                    mSerialExecutor.execute(null);
                }
            }
            // QUITAMOS LAS MESAS
            for (int i = 0; i < mesaplanningList.size(); i++) {
                image  = (Button)viewmesaList.get(i);
                layout.removeView(image);

            }
            // QUITAMOS LAS ZONAS
            for (int i = 0; i < lzonas.size(); i++) {
                btnzona  = (Button)viewzonaList.get(i);
                linearLayout.removeView(btnzona);

            }
            nombrezona.setText(getZonaNombre(v.getTag().toString().trim()));
            MesasActivity.this.getSupportActionBar().setTitle(getPalabras("Mesas")+" "+getZonaNombre(v.getTag().toString().trim()));

            // Rellenar string toolbar_mesas
            mesaplanningList = new ArrayList<Mesa>();
            URL_MESASPLANNING = Filtro.getUrl() + "/get_mesasplanning.php";
            new GetMesas().execute(URL_MESASPLANNING,v.getTag().toString());

        }

    }
    Runnable pressRunnable = new Runnable() {
        @Override
        public void run() {
            btnzona.setPressed(true);
            btnzona.postOnAnimationDelayed(unpressRunnable, 250);
        }
    };

    Runnable unpressRunnable = new Runnable() {
        @Override
        public void run() {
            btnzona.setPressed(false);
            btnzona.postOnAnimationDelayed(pressRunnable, 250);
        }
    };
    /**
     * Adding spinner data mesas
     */
    private void populatePlanningMesas() {
        layout = (RelativeLayout)findViewById(R.id.topleft);
        viewmesaList = new ArrayList<View>();
        for (int i = 0; i < mesaplanningList.size(); i++) {
            model = mesaplanningList.get(i);

            image = new Button(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


////           image.setLayoutParams(new ViewGroup.LayoutParams(264, 246));
     //            image.setBackgroundResource(R.drawable.house_kitchen_table);
//            Drawables icon1=this.getResources(). getDrawable( R.drawable.house_kitchen_table);
            //show icon to the left of text
///            image.setCompoundDrawablesWithIntrinsicBounds( null, null, null, icon1 );
//            if (model.getMesaXCoordenate()!=0.00000){image.setX(model.getMesaXCoordenate());}
//            if (model.getMesaYCoordenate()!=0.00000){image.setY(model.getMesaYCoordenate());}


//// VERSION CARGAR CON LOADIMAGEFROMWEBOPERATIONS
            Drawable d = LoadImageFromWebOperations(model.getMesaUrlimagen());

            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            Log.i("Button Image",Integer.toString(bitmap.getWidth())+" "+Integer.toString(bitmap.getHeight()));

/////            Bitmap new_icon = resizeBitmapImageFn(bitmap, 500); //resizing the bitmap
/////            Drawables d1 = new BitmapDrawable(getResources(),new_icon); //Converting bitmap into drawable

////            ImageView iv = (ImageView) findViewById(R.id.imageView);
////            Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, Filtro.getOpmesas(), Filtro.getOpmesas(), true);
////            iv.setImageBitmap(bMapScaled);
            Drawable d1 = new BitmapDrawable(getResources(),bMapScaled); //Converting bitmap into drawable

            Drawable[] layers1 = {image.getBackground()};
            Drawable[] layers2 = {image.getBackground()};
            Drawable[] layers3 = {image.getBackground()};

    //        dMesa = new DecoratedTextViewDrawable(image, layers1, 0, "MESAS",model.getMesaMesa());
            dpedidos = new DecoratedTextViewDrawable(image, layers2, model.getMesaPedidos(), "PEDIDOS", model.getMesaMesa(),Filtro.getOppedidodirectomesa() );
            dfacturas = new DecoratedTextViewDrawable(image, layers3, model.getMesaFacturas(), "FACTURAS", model.getMesaMesa(),Filtro.getOpfacturadirectomesa());


    //**        image.setCompoundDrawablesWithIntrinsicBounds( (model.getMesaPedidos()>0 ? dpedidos : null), (model.getMesaFacturas()>0 ? dfacturas : null), null, d1 );

            image.setCompoundDrawablesWithIntrinsicBounds( (model.getMesaApertura()>0 ? dpedidos : null), (model.getMesaApertura()>0 ? dfacturas : null), null, d1 );
  ///          image.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ok, 0, 0, 0);
            // find the image map in the view
//            image.setBackground(Drawables.getSelectableDrawableFor(Color.TRANSPARENT));

            image.setBackgroundColor(Color.TRANSPARENT);
            image.setText(model.getMesaMesa().trim());

            image.setVisibility(View.VISIBLE);
            image.setTag(model.getMesaMesa());
            image.setX(model.getMesaXCoordenate());
            image.setY(model.getMesaYCoordenate());
            if (model.getMesaApertura() == 0) {
                image.setAlpha(0.3f); // COLOR APAGADO MESA CERRADA
            } else {
                image.setAlpha(1.0f); // COLOR BRILLANTE MESA ABIERTA
            }

/*            mImageMap = (ImageMap)findViewById(R.id.map);
            mImageMap.setImageDrawable(image.getCompoundDrawables()[0]);

            // add a click handler to react when areas are tapped
            mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler()
            {
                @Override
                public void onImageMapClicked(int id, ImageMap imageMap)
                {
                    // when the area is tapped, show the name in a
                    // text bubble
                    mImageMap.showBubble(id);
                }

                @Override
                public void onBubbleClicked(int id)
                {
                    // react to info bubble for area being tapped
                }
            });
*/
             Log.i("instance MESA",image.getClass().getName().toString()+" - "+image.getTag()+ " y "+Float.toString(image.getY())+" x "+Float.toString(image.getX()));
                String uri = "@drawable/house_kitchen_table";
                int icon = getResources().getIdentifier(uri, "drawable", getPackageName());


                image.setOnTouchListener(new MyTouchListenerImage());
                // Adds the view to the layout
                layout.addView(image);
                viewmesaList.add(image);
////            new DownloadImageTask1(image).execute(model.getMesaUrlimagen());
                Log.i("URLIMAGEN TAG: ", image.getTag() + " iconId " + Integer.toString(icon) + " x: " + Float.toString(model.getMesaXCoordenate()) + " y: " + Float.toString(model.getMesaYCoordenate()) + " IMAGEN: " + model.getMesaUrlimagen() + " MESA: " + model.getMesaMesa() + " APERTURA: " + Integer.toString(model.getMesaApertura()));
//            image.setImageDrawable(getResources().getDrawable(R.drawable.house_kitchen_table));
        }
        // Rellenar zonas
        populate_zonas();

 /*       for (int i = 0; i < mesaplanningList.size(); i++) {
            model = mesaplanningList.get(i);
            imagepre = (ImageView)layout.findViewWithTag(image.getTag());
            image.setImageDrawable(writeTextOnDrawable((Integer)imagepre.getTag(),model.getMesaMesa()));
            layout.removeView(imagepre);

            layout.addView(image);

        }*/
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
////        Log.i("Button",Integer.toString(image.getWidth())+" "+Integer.toString(image.getHeight()));
        // Call here getWidth() and getHeight()
    }
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
            for (int i = 0; i < mesaplanningList.size(); i++) {
                model = mesaplanningList.get(i);
                if (model.getMesaModificado()==1) {
                    Filtro.setMesa(model.getMesaMesa());
                    xCoordenate = model.getMesaXCoordenate();
                    yCoordenate = model.getMesaYCoordenate();
                    mSerialExecutor.execute(null);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public static class Drawables {

        @NonNull
        public static Drawable getSelectableDrawableFor(int color) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_pressed},
                        new ColorDrawable(lightenOrDarken(color, 0.20D))
                );
                stateListDrawable.addState(
                        new int[]{android.R.attr.state_focused},
                        new ColorDrawable(lightenOrDarken(color, 0.40D))
                );
                stateListDrawable.addState(
                        new int[]{},
                        new ColorDrawable(color)
                );
                return stateListDrawable;
            } else {
                ColorStateList pressedColor = ColorStateList.valueOf(lightenOrDarken(color, 0.2D));
                ColorDrawable defaultColor = new ColorDrawable(color);
                Drawable rippleColor = getRippleColor(color);
                return new RippleDrawable(
                        pressedColor,
                        defaultColor,
                        rippleColor
                );
            }
        }

        @NonNull
        private static Drawable getRippleColor(int color) {
            float[] outerRadii = new float[8];
            Arrays.fill(outerRadii, 3);
            RoundRectShape r = new RoundRectShape(outerRadii, null, null);
            ShapeDrawable shapeDrawable = new ShapeDrawable(r);
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }

        private static int lightenOrDarken(int color, double fraction) {
            if (canLighten(color, fraction)) {
                return lighten(color, fraction);
            } else {
                return darken(color, fraction);
            }
        }

        private static int lighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = lightenColor(red, fraction);
            green = lightenColor(green, fraction);
            blue = lightenColor(blue, fraction);
            int alpha = Color.alpha(color);
            return Color.argb(alpha, red, green, blue);
        }

        private static int darken(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            red = darkenColor(red, fraction);
            green = darkenColor(green, fraction);
            blue = darkenColor(blue, fraction);
            int alpha = Color.alpha(color);

            return Color.argb(alpha, red, green, blue);
        }

        private static boolean canLighten(int color, double fraction) {
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return canLightenComponent(red, fraction)
                    && canLightenComponent(green, fraction)
                    && canLightenComponent(blue, fraction);
        }

        private static boolean canLightenComponent(int colorComponent, double fraction) {
            int red = Color.red(colorComponent);
            int green = Color.green(colorComponent);
            int blue = Color.blue(colorComponent);
            return red + (red * fraction) < 255
                    && green + (green * fraction) < 255
                    && blue + (blue * fraction) < 255;
        }

        private static int darkenColor(int color, double fraction) {
            return (int) Math.max(color - (color * fraction), 0);
        }

        private static int lightenColor(int color, double fraction) {
            return (int) Math.min(color + (color * fraction), 255);
        }

    }
    private final class MyTouchListenerZona implements View.OnTouchListener {
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_UP:
                    Toast.makeText(getApplicationContext(), "Cambio de zona", Toast.LENGTH_SHORT).show();
                   break;
                ////////////////////////////////////////////
                default:
                    return false;
            }
            return true;
        }
    }
      private final class MyTouchListenerImage implements View.OnTouchListener {
        public boolean onTouch(final View view, MotionEvent motionEvent) {
            // Detect touch circle  ////////////////////////////////
            boolean desdeCircle = false;
            boolean handled = false;
            CircleArea touchedCircle;
            float xTouch;
            float yTouch;
            int pointerId;
            int actionIndex = motionEvent.getActionIndex();
            ///////////////////////////////////////////////////////
/*            int rawX, rawY;
            int pixelX,pixelY;
            final int actionindex = motionEvent.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            final int location[] = { 0, 0 };
            view.getLocationOnScreen(location);
            rawX = (int) motionEvent.getX(actionindex) + location[0];
            rawY = (int) motionEvent.getY(actionindex) + location[1];

            pixelX = (int) motionEvent.getX(actionindex);
            pixelY = (int) motionEvent.getY(actionindex);

//            int pixel = bitmap.getPixel(x,y);

//            Drawables drawable = view.getBackground();
            Bitmap bitmap =  getBitmapFromView(view);
//            int transparency = ((bitmap.getPixel(rawX,rawY) & 0xff000000) >> 24);
            int alpha = Color.alpha(bitmap.getPixel(pixelX,pixelY));
            int color = bitmap.getPixel(pixelX, pixelY);
 //           Log.i("TOUCH", "View under finger: " + findViewAtPosition(view, (int)motionEvent.getRawX(), (int)motionEvent.getRawY()));
            Log.i("Location ",view.getClass().getName().toString()+" - " + alpha + " - "+ color + " - "+view.getTag()+ " - " + Integer.toString(rawX) + " - " + Integer.toString(rawY) );
*/
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    //// valores para detect circle  ///////////////////////////////
                    if (!getMesas(view.getTag().toString(), 0)) { /* CONTROLA SI LA MESA ESTA ABIERTA*/

                        clearCirclePointer();
///                        invalidate();
                        handled = true;

                    }
                    /////////////////////////////////////////////////////////////////
                    mDownX = motionEvent.getX();
                    mDownY = motionEvent.getY();
                    xCoOrdinate = view.getX() - motionEvent.getRawX();
                    yCoOrdinate = view.getY() - motionEvent.getRawY();
                    isOnClick = true;
/****
                    actionMove=false;
                    xCoOrdinate = view.getX() - motionEvent.getRawX();
                    yCoOrdinate = view.getY() - motionEvent.getRawY();
                    Log.i("instance view DOWN",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(yCoOrdinate)+" x "+Float.toString(xCoOrdinate));
****/                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isOnClick && (Math.abs(mDownX - motionEvent.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - motionEvent.getY()) > SCROLL_THRESHOLD)) {
                        /// PARA CONTROL CIRCLE
                        if (!getMesas(view.getTag().toString(), 0)) { /* CONTROLA SI LA MESA ESTA ABIERTA*/

                            final int pointerCount = motionEvent.getPointerCount();

                            for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {
                                // Some pointer has moved, search it by pointer id
                                pointerId = motionEvent.getPointerId(actionIndex);

                                xTouch = motionEvent.getX(actionIndex);
                                yTouch = motionEvent.getY(actionIndex);

                                touchedCircle = mCirclePointer.get(pointerId);

                                if (null != touchedCircle) {
                                    touchedCircle.centerX = xTouch;
                                    touchedCircle.centerY = yTouch;
                                }
                            }
///                        invalidate();
                            handled = true;
                        }
                        ///////////////////////////////////////////////////////////////////////

                        Log.i("TOUCH", "movement detected");
                        isOnClick = false;
                        x =  motionEvent.getX();
                        y =  motionEvent.getY();
                        dx = motionEvent.getRawX();
                        dy = motionEvent.getRawY();
                        Log.d("DEBUG", "getX=" + x + "getY=" + y + "\n" + "getRawX=" + dx
                                + "getRawY=" + dy + "\n");
                        view.animate().x(motionEvent.getRawX() + xCoOrdinate).y(motionEvent.getRawY() + yCoOrdinate).setDuration(0).start();
                        Log.i("instance view MOVE",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(motionEvent.getRawY()+yCoOrdinate)+" x "+Float.toString(motionEvent.getRawX()+xCoOrdinate));
                        setMesaModificado(view.getTag().toString(),1);

                        xCoordenate = motionEvent.getRawX() + xCoOrdinate;
                        yCoordenate = motionEvent.getRawY() + yCoOrdinate;

                        setMesaCoordenadas(view.getTag().toString(),xCoordenate,yCoordenate);

                    }
/****                    actionMove=true;
                    x =  motionEvent.getX();
                    y =  motionEvent.getY();
                    dx = motionEvent.getRawX();
                    dy = motionEvent.getRawY();
                    Log.d("DEBUG", "getX=" + x + "getY=" + y + "\n" + "getRawX=" + dx
                            + "getRawY=" + dy + "\n");
                    view.animate().x(motionEvent.getRawX() + xCoOrdinate).y(motionEvent.getRawY() + yCoOrdinate).setDuration(0).start();
                    Log.i("instance view MOVE",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(motionEvent.getRawY()+yCoOrdinate)+" x "+Float.toString(motionEvent.getRawX()+xCoOrdinate));
                    setMesaModificado(view.getTag().toString(),1);
****/                    break;
                case MotionEvent.ACTION_UP:
                    if (isOnClick) {
                        //// PARA CONTROL CIRCLE
                        if (!getMesas(view.getTag().toString(), 0)) { /* CONTROLA SI LA MESA ESTA ABIERTA*/
                            // it's the first pointer, so clear all existing pointers data
                            clearCirclePointer();

                            xTouch = motionEvent.getX(0);
                            yTouch = motionEvent.getY(0);

                            Bitmap bitmap =  getBitmapFromView(view);
                            int alpha = Color.alpha(bitmap.getPixel((int)xTouch,(int)yTouch));

                            Log.i("Circle Color Touched",String.valueOf(alpha));

                             // check if we've touched inside some circle
                            touchedCircle = obtainTouchedCircle(xTouch, yTouch,alpha,view.getTag().toString());
                            touchedCircle.centerX = xTouch;
                            touchedCircle.centerY = yTouch;
                            mCirclePointer.put(motionEvent.getPointerId(0), touchedCircle);

///                    invalidate();
                            handled = true;
                            desdeCircle = false;
                            switch (alpha){
                                case 150:
                                    if(Filtro.getOppedidodirectomesa()) {
                                        desdeCircle = true;
                                        if (!ActividadPrincipal.getCruge("action_pdd_create")) {
                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            new CreaDocumentoPedido().execute(getMesaComensales(Filtro.getMesa()));
                                        }
                                    }
                                    break;
                                case 255:
                                    if(Filtro.getOpfacturadirectomesa()) {
                                        desdeCircle = true;
                                        if (!ActividadPrincipal.getCruge("action_ftp_create")) {
                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            new CreaDocumentoFactura().execute();
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }
                        ///////////////////////////////////

                        Log.i("TOUCH", "onClick ");
                        //TODO onClick code
                        xCoordenate = motionEvent.getRawX() + xCoOrdinate;
                        yCoordenate = motionEvent.getRawY() + yCoOrdinate;

                        setMesaCoordenadas(view.getTag().toString(),xCoordenate,yCoordenate);
                        Filtro.setMesa(view.getTag().toString());
                        Toast.makeText(getApplicationContext(),ActividadPrincipal.getPalabras("Mesa")+": "+Filtro.getMesa(),Toast.LENGTH_LONG).show();
//                        if (!actionMove) {
                        if(!desdeCircle) {
                            String[] test = getResources().getStringArray(R.array.menu_mesas);
                            String[] opciones;
                            if (getMesas(Filtro.getMesa(), 0)) {
                                opciones = new String[1];
                                for (int i = 0; i < opciones.length; i++) {
                                    opciones[i] = ActividadPrincipal.getPalabras(test[i]);
                                }
                            } else {
                                opciones = new String[test.length - 1];
                                for (int i = 0; i < opciones.length; i++) {
                                    opciones[i] = ActividadPrincipal.getPalabras(test[i + 1]);
                                }
                            }


                            //                    new AlertDialog.Builder(MesasActivity.this).setTitle("Operaciones con Mesa").setMessage("Hola").setNeutralButton(getResources().getString(R.string.accept),null).show();
                            AlertDialog.Builder alert = new AlertDialog.Builder(MesasActivity.this);
//                        image = (Button)layout.findViewWithTag(Filtro.getMesa());
                            Drawable d = LoadImageFromWebOperations(getMesaImagen(Filtro.getMesa()));
                            alert.setIcon(d);
                            alert.setTitle(ActividadPrincipal.getPalabras("Operaciones") + " " + ActividadPrincipal.getPalabras("Mesa") + " " + Filtro.getMesa() + " " + (getMesas(Filtro.getMesa(), 0) ? ActividadPrincipal.getPalabras("CERRADO") : ActividadPrincipal.getPalabras("ABIERTO")))
                                    .setItems(opciones, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // The 'which' argument contains the index position
                                            // of the selected item
                                            if (!(ActividadPrincipal.itemdcj.getText().equals("0"))) {
                                                if (getMesas(Filtro.getMesa(), 0)) {
                                                    switch (which) {
                                                        case 0: //Abrir Mesa
                                                            if (getMesas(Filtro.getMesa(), 0)) {
                                                                if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    if (!getMesaT_mesa(Filtro.getMesa(), lparam.get(0).getDEFAULT_TIPO_MESA_BUFFET())) {
                                                                        // DIALOGO PEDIR COMENSALES
                                                                        final AlertDialog.Builder alertcomensales = new AlertDialog.Builder(MesasActivity.this);
                                                                        final EditText input = new EditText(MesasActivity.this);
                                                                        input.setText(String.format("%02d", 0));

                                                                        // Ponerse al final del edittext
                                                                        int pos = input.getText().length();
                                                                        input.setSelection(pos);

                                                                        input.setTextColor(Color.RED);
                                                                        input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                                                        KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                                                                        input.setKeyListener(keyListener);
                                                                        alertcomensales.setTitle(getPalabras("Insertar") + " " + getPalabras("Comensales"));
                                                                        alertcomensales.setView(input);
                                                                        alertcomensales.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                                                String value = input.getText().toString();
                                                                                if (value.matches("")) {
                                                                                    Toast.makeText(MesasActivity.this, getPalabras("Valor") + " " + getPalabras("Vacio") + " " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                                                                    //            this.btnGuardar.setEnabled(false);
                                                                                } else {

                                                                                    value = value.replace(".", "");
                                                                                    value = value.replace(",", "");

                                                                                    input.setText(String.format("%2d", Integer.valueOf(value)));
                                                                                    setMesaComensales(Filtro.getMesa(), Integer.valueOf(value));
                                                                                    new UpdateMesaApertura().execute("1", value);

                                                                                }
                                                                            }
                                                                        });

                                                                        alertcomensales.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                                                dialog.cancel();
                                                                            }
                                                                        });
                                                                        alertcomensales.show();
                                                                    }else{
                                                                        int x=0;
                                                                        String ArticuloCodigoGrupo="";
                                                                        String ArticuloNombreGrupo="";
                                                                        for(x=0;x<lcategoria.size();x++) {
                                                                            if (lcategoria.get(x).getCategoriaTipo_are().equals(lparam.get(0).getDEFAULT_TIPO_ARTICULO_BUFFET().trim())) {
                                                                                ArticuloCodigoGrupo = lcategoria.get(x).getCategoriaTipo_are();
                                                                                ArticuloNombreGrupo = lcategoria.get(x).getCategoriaNombre_tipoare();
                                                                                Log.i("BUFFET: ", Integer.toString(x) + " " + lcategoria.get(x).getCategoriaTipo_are());
                                                                                break;
                                                                            }
                                                                        }
                                                                        if (!ArticuloCodigoGrupo.equals("")) {
                                                                            Log.i("ARE BUFFET", "OK");
                                                                            populate_dialog_buffet(x);
                                                                        } else {
                                                                            Log.e(TAG, "Failed to fetch data!");
                                                                            Toast.makeText(getApplicationContext(), getPalabras("No existe")+" "+getPalabras("Tipo")+" "+getPalabras("Articulo")+" BUFFET", Toast.LENGTH_SHORT).show();
                                                                        }
//                                                                        Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Es una Mesa Tipo BUFFET"), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("ABIERTA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                    }
                                                } else {
                                                    switch (which) {
                                                        case 0: //Cerrar Mesa
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {

                                                                    url_count = Filtro.getUrl() + "/CountPddOpen.php";
                                                                    new CountOpenPdd().execute(url_count, "pdd");
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 1:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_pdd_create")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    ////                                                             Log.i("CreaPDD","Llama Crear Pedido");                                                                 Log.i("PEDIDO","LLAMA CREADOCUMENTOPEDIOD");
                                                                    new CreaDocumentoPedido().execute(getMesaComensales(Filtro.getMesa()));
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 2:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_pdd_admin")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    // SALIR ACTIVITY
                                                                    Intent returnIntent = new Intent();
                                                                    returnIntent.putExtra("Mesa", Filtro.getMesa());
                                                                    returnIntent.putExtra("Action", "OPEN");
                                                                    returnIntent.putExtra("Tabla", "pdd");
                                                                    setResult(RESULT_OK, returnIntent);
                                                                    finish();
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 3:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_ftp_create")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    ldocumentopedido = new ArrayList<DocumentoPedido>();
                                                                    new GetDocumentoPedidos().execute(url_pedido_factura);
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 4:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_ftp_create")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    new CreaDocumentoFactura().execute();
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 5:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_ftp_admin")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    // SALIR ACTIVITY
                                                                    Intent returnIntent = new Intent();
                                                                    returnIntent.putExtra("Mesa", Filtro.getMesa());
                                                                    returnIntent.putExtra("Action", "OPEN");
                                                                    returnIntent.putExtra("Tabla", "ftp");
                                                                    setResult(RESULT_OK, returnIntent);
                                                                    finish();
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 6:
                                                            if (getMesas(Filtro.getMesa(), 1)) {
                                                                if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                    Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    // DIALOGO PEDIR COMENSALES
                                                                    final AlertDialog.Builder alertcomensales = new AlertDialog.Builder(MesasActivity.this);
                                                                    final EditText input = new EditText(MesasActivity.this);
                                                                    input.setText(String.format("%02d", Integer.parseInt(getMesaComensales(Filtro.getMesa()))));

                                                                    // Ponerse al final del edittext
                                                                    int pos = input.getText().length();
                                                                    input.setSelection(pos);

                                                                    input.setTextColor(Color.RED);
                                                                    input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                                                    KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                                                                    input.setKeyListener(keyListener);
                                                                    alertcomensales.setTitle(getPalabras("Insertar") + " " + getPalabras("Comensales"));
                                                                    alertcomensales.setView(input);
                                                                    alertcomensales.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            String value = input.getText().toString();
                                                                            if (value.matches("")) {
                                                                                Toast.makeText(MesasActivity.this, getPalabras("Valor") + " " + getPalabras("Vacio") + " " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                                                                //            this.btnGuardar.setEnabled(false);
                                                                            } else {

                                                                                value = value.replace(".", "");
                                                                                value = value.replace(",", "");

                                                                                input.setText(String.format("%2d", Integer.valueOf(value)));
                                                                                setMesaComensales(Filtro.getMesa(), Integer.valueOf(value));
                                                                                new UpdateMesaApertura().execute("1", value);

                                                                            }
                                                                        }
                                                                    });

                                                                    alertcomensales.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                            dialog.cancel();
                                                                        }
                                                                    });
                                                                    alertcomensales.show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa") + " " + ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                    }
                                                }
                                            } else {
                                                Snackbar.make(view, "No Hay Diario Caja Abierto", Snackbar.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

    /*                            alert.setTitle("Operaciones con Mesa");
                                alert.setPositiveButton("Crear Pedido", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (!ActividadPrincipal.getCruge("action_pdd_create")){
                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                        }else {
                                            new CreaDocumentoPedido().execute();
                                            Snackbar.make(view, "Creando Documento Pedido", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                alert.setNeutralButton("Crear Factura", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (!ActividadPrincipal.getCruge("action_ftp_create")){
                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                        }else {
                                            new CreaDocumentoFactura().execute();
                                            Snackbar.make(view, "Creando Documento Factura", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
    */
                            alert.setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });
                            //        alert.show();
                            // Create the AlertDialog
                            AlertDialog dialog = alert.create();
                            dialog.show();
                        }
//                        }
                        Log.i("instance view UP",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(yCoordenate)+" x "+Float.toString(xCoordenate));
                        //              mSerialExecutor.execute(null);

                    }
/****
                    actionMove=false;
                    xCoordenate = motionEvent.getRawX() + xCoOrdinate;
                    yCoordenate = motionEvent.getRawY() + yCoOrdinate;

                    setMesaCoordenadas(view.getTag().toString(),xCoordenate,yCoordenate);
                    Filtro.setMesa(view.getTag().toString());
                    Toast.makeText(getApplicationContext(),ActividadPrincipal.getPalabras("Mesa")+": "+Filtro.getMesa(),Toast.LENGTH_LONG).show();
                    if (!actionMove) {
                        String[] test = getResources().getStringArray(R.array.menu_mesas);
                        String[] opciones;
                        if (getMesas(Filtro.getMesa(), 0)) {
                            opciones = new String[1];
                            for (int i = 0; i < opciones.length; i++) {
                                opciones[i] = ActividadPrincipal.getPalabras(test[i]);
                            }
                        }else{
                            opciones = new String[test.length-1];
                            for (int i = 0; i < opciones.length; i++) {
                                opciones[i] = ActividadPrincipal.getPalabras(test[i+1]);
                            }
                        }


                        //                    new AlertDialog.Builder(MesasActivity.this).setTitle("Operaciones con Mesa").setMessage("Hola").setNeutralButton(getResources().getString(R.string.accept),null).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(MesasActivity.this);
//                        image = (Button)layout.findViewWithTag(Filtro.getMesa());
                        Drawables d = LoadImageFromWebOperations(getMesaImagen(Filtro.getMesa()));
                        alert.setIcon(d);
                        alert.setTitle(ActividadPrincipal.getPalabras("Operaciones")+" "+ActividadPrincipal.getPalabras("Mesa")+" " + Filtro.getMesa() + " " + (getMesas(Filtro.getMesa(), 0) ? ActividadPrincipal.getPalabras("CERRADO") : ActividadPrincipal.getPalabras("ABIERTO")))
                                .setItems(opciones, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // The 'which' argument contains the index position
                                        // of the selected item
                                        if(!(ActividadPrincipal.itemdcj.getText().equals("0"))) {

                                            if (getMesas(Filtro.getMesa(), 0)) {
                                                switch (which) {
                                                    case 0: //Abrir Mesa
                                                        if (getMesas(Filtro.getMesa(), 0)) {
                                                            if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // DIALOGO PEDIR COMENSALES
                                                                final AlertDialog.Builder alertcomensales = new AlertDialog.Builder(MesasActivity.this);
                                                                final EditText input = new EditText(MesasActivity.this);
                                                                input.setText(String.format("%02d", 0));

                                                                // Ponerse al final del edittext
                                                                int pos = input.getText().length();
                                                                input.setSelection(pos);

                                                                input.setTextColor(Color.RED);
                                                                input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                                                KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                                                                input.setKeyListener(keyListener);
                                                                alertcomensales.setTitle(getPalabras("Insertar")+" "+getPalabras("Comensales"));
                                                                alertcomensales.setView(input);
                                                                alertcomensales.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                                        String value = input.getText().toString();
                                                                        if (value.matches("")) {
                                                                            Toast.makeText(MesasActivity.this, getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                                                            //            this.btnGuardar.setEnabled(false);
                                                                        } else {

                                                                            value = value.replace(".", "");
                                                                            value = value.replace(",", "");

                                                                            input.setText(String.format("%2d", Integer.valueOf(value)));
                                                                            setMesaComensales(Filtro.getMesa(),Integer.valueOf(value));
                                                                            new UpdateMesaApertura().execute("1",value);

                                                                        }
                                                                    }
                                                                });

                                                                alertcomensales.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                                        dialog.cancel();
                                                                    }
                                                                });
                                                                alertcomensales.show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("ABIERTA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                }
                                            }else {
                                                switch (which) {
                                                    case 0: //Cerrar Mesa
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {

                                                                url_count = Filtro.getUrl() + "/CountPddOpen.php";
                                                                new CountOpenPdd().execute(url_count, "pdd");
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 1:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_pdd_create")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
     ////                                                             Log.i("CreaPDD","Llama Crear Pedido");                                                                 Log.i("PEDIDO","LLAMA CREADOCUMENTOPEDIOD");
                                                                  new CreaDocumentoPedido().execute(getMesaComensales(Filtro.getMesa()));
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 2:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_pdd_admin")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // SALIR ACTIVITY
                                                                Intent returnIntent = new Intent();
                                                                returnIntent.putExtra("Mesa", Filtro.getMesa());
                                                                returnIntent.putExtra("Action", "OPEN");
                                                                returnIntent.putExtra("Tabla", "pdd");
                                                                setResult(RESULT_OK, returnIntent);
                                                                finish();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 3:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_ftp_create")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                ldocumentopedido = new ArrayList<DocumentoPedido>();
                                                                new GetDocumentoPedidos().execute(url_pedido_factura);
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 4:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_ftp_create")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                new CreaDocumentoFactura().execute();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 5:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_ftp_admin")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // SALIR ACTIVITY
                                                                Intent returnIntent = new Intent();
                                                                returnIntent.putExtra("Mesa", Filtro.getMesa());
                                                                returnIntent.putExtra("Action", "OPEN");
                                                                returnIntent.putExtra("Tabla", "ftp");
                                                                setResult(RESULT_OK, returnIntent);
                                                                finish();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                    case 6:
                                                        if (getMesas(Filtro.getMesa(), 1)) {
                                                            if (!ActividadPrincipal.getCruge("action_mesas_update")) {
                                                                Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("No puede realizar esta accion"), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // DIALOGO PEDIR COMENSALES
                                                                final AlertDialog.Builder alertcomensales = new AlertDialog.Builder(MesasActivity.this);
                                                                final EditText input = new EditText(MesasActivity.this);
                                                                input.setText(String.format("%02d", Integer.parseInt(getMesaComensales(Filtro.getMesa()))));

                                                                // Ponerse al final del edittext
                                                                int pos = input.getText().length();
                                                                input.setSelection(pos);

                                                                input.setTextColor(Color.RED);
                                                                input.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                                                KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                                                                input.setKeyListener(keyListener);
                                                                alertcomensales.setTitle(getPalabras("Insertar")+" "+getPalabras("Comensales"));
                                                                alertcomensales.setView(input);
                                                                alertcomensales.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                                        String value = input.getText().toString();
                                                                        if (value.matches("")) {
                                                                            Toast.makeText(MesasActivity.this, getPalabras("Valor")+" "+getPalabras("Vacio")+" " + getPalabras("Comensales"), Toast.LENGTH_SHORT).show();
                                                                            //            this.btnGuardar.setEnabled(false);
                                                                        } else {

                                                                            value = value.replace(".", "");
                                                                            value = value.replace(",", "");

                                                                            input.setText(String.format("%2d", Integer.valueOf(value)));
                                                                            setMesaComensales(Filtro.getMesa(),Integer.valueOf(value));
                                                                            new UpdateMesaApertura().execute("1",value);

                                                                        }
                                                                    }
                                                                });

                                                                alertcomensales.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                                        dialog.cancel();
                                                                    }
                                                                });
                                                                alertcomensales.show();
                                                            }
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Toast.LENGTH_SHORT).show();
                                                        }
                                                        break;
                                                }
                                            }
                                        } else {
                                            Snackbar.make(view, "No Hay Diario Caja Abierto", Snackbar.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                        alert.setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                        //        alert.show();
                        // Create the AlertDialog
                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                    Log.i("instance view UP",view.getClass().getName().toString()+" - "+view.getTag()+ " y "+Float.toString(yCoordenate)+" x "+Float.toString(xCoordenate));
      //              mSerialExecutor.execute(null);
****/                    break;
                //// CONTROL CIRCLE
                case MotionEvent.ACTION_POINTER_UP:
                    if (!getMesas(view.getTag().toString(), 0)) { /* CONTROLA SI LA MESA ESTA ABIERTA*/

                        // not general pointer was up
                        pointerId = motionEvent.getPointerId(actionIndex);

                        mCirclePointer.remove(pointerId);
///                    invalidate();
                        handled = true;
                    }
                    break;

                case MotionEvent.ACTION_CANCEL:
                    if (!getMesas(view.getTag().toString(), 0)) { /* CONTROLA SI LA MESA ESTA ABIERTA*/

                        handled = true;
                    }
                    break;
                ////////////////////////////////////////////
                default:
                    return false;
            }
            return true;
/*            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
*/        }
    }
    private void populate_dialog_buffet_old(final int indiceSeccion) {
        final LinearLayout layout = new LinearLayout(this);
        layout.setId(R.id.layoutbuffet);
        layout.setOrientation(LinearLayout.VERTICAL);

        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);


        final String[] articulos = new String[comidas.get(indiceSeccion).size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        List<Articulos> articulosList = new ArrayList<Articulos>();
        for (int i = 0; i < comidas.get(indiceSeccion).size(); i++) {


            articulos[i] =  comidas.get(indiceSeccion).get(i).getNombre();
            articulosList.add(new Articulos(articulos[i], "ARTICULO" ,comidas.get(indiceSeccion).get(i).getUrlimagen()));


        }

        ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);
        mSelectedItems = new ArrayList();  // Where we track the selected items

        boolean[] checkedItems = new boolean[articulos.length];
        for (int i = 0; i < articulos.length; i++) {
            if (i==0) {
                checkedItems[i] = true;
                mSelectedItems.add(0);

            } else {
                checkedItems[i] = false;
            }
        }

        final TextView titleBoxFra = new TextView(this);
        titleBoxFra.setHint(getPalabras("Mensaje")+" "+getPalabras("Ticket"));
        titleBoxFra.setTextColor(Color.RED);
        layout.addView(titleBoxFra);

        Drawable d = LoadImageFromWebOperations(getMesaImagen(Filtro.getMesa()));

        alert.setIcon(d);
        alert.setView(layout);
        alert.setTitle(ActividadPrincipal.getPalabras("Articulos")+" "+lparam.get(0).getDEFAULT_TIPO_ARTICULO_BUFFET().trim() );                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected

/*                .setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // a choice has been made!

                        String selectedVal = comidas.get(indiceSeccion).get(which).getArticulo();
                        Log.d(TAG, "chosen " + selectedVal );
                        new UpdateActivoArticuloBuffet().execute(selectedVal,"1");
                        dialog.dismiss();
                    }
                })
*/
                alert.setMultiChoiceItems(articulos, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
     //                       if (mSelectedItems.size()==0) {
                                // If the user checked the item, add it to the selected items
                                mSelectedItems.add(which);
    //                        }else{
                                Toast.makeText(MesasActivity.this, getPalabras("Solo puede marcar un Articulo"), Toast.LENGTH_SHORT).show();
    //                        }
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                        Log.i("which",String.valueOf(which));
                        if (mSelectedItems.size()>0) {
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);

                        }else{
                            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);

                        }

                    }
                });

                // Set the action buttons
                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton(ActividadPrincipal.getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final android.app.AlertDialog dialogo = alert.create();
        dialogo.getWindow().setType(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialogo.show();

    }
    public void populate_dialog_buffet(final int indiceSeccion){
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);

        String[] articulos = new String[comidas.get(indiceSeccion).size()];
        String space01 = new String(new char[01]).replace('\0', ' ');

        final List<Articulos> articulosList = new ArrayList<Articulos>();

        for (int i = 0; i < comidas.get(indiceSeccion).size(); i++) {
            articulos[i] =  comidas.get(indiceSeccion).get(i).getNombre();
            articulosList.add(new Articulos(articulos[i], "ARTICULO" ,comidas.get(indiceSeccion).get(i).getUrlimagen()));
        }
        ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter(this, articulosList);


        mSelectedItems = new ArrayList();  // Where we track the selected items

        boolean[] checkedItems = new boolean[articulos.length];
        for (int i = 0; i < articulos.length; i++) {
            if (i==0) {
                checkedItems[i] = true;
                mSelectedItems.add(0);

            } else {
                checkedItems[i] = false;
            }
        }
        /// RELLENAR MENSAJE
        final TextView titleBoxMensaje = new TextView(this);
        titleBoxMensaje.setHint(getPalabras("Mensaje"));
        titleBoxMensaje.setTextColor(Color.RED);
        layout.addView(titleBoxMensaje);
        
        final EditText txtBoxMensaje = new EditText(this);
        txtBoxMensaje.setText(getMesaMensaje(Filtro.getMesa()));
        txtBoxMensaje.setTextSize(16);
        txtBoxMensaje.setTextColor(Color.RED);
        txtBoxMensaje.setSingleLine(false);
        txtBoxMensaje.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        layout.addView(txtBoxMensaje);


        alert.setTitle(getPalabras("Articulos")+" "+lparam.get(0).getDEFAULT_TIPO_ARTICULO_BUFFET().trim() );                // Specify the list array, the items to be selected by default (null for none),
        Drawable d = LoadImageFromWebOperations(getMesaImagen(Filtro.getMesa()));
        alert.setIcon(d);
        alert.setView(layout);

        alert.setMultiChoiceItems(articulos, checkedItems,
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
                        Log.i("which",String.valueOf(which));
                        if (mSelectedItems.size()>0 && mSelectedItems.size()<2) {
                            ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(true);

                        }else{
                            if (mSelectedItems.size()>1) {
                                Toast.makeText(MesasActivity.this, getPalabras("Solo puede marcar un Articulo"), Toast.LENGTH_SHORT).show();
                            }
                            ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setEnabled(false);

                        }

                    }
                });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (mSelectedItems.size()>0) {
                    selectedVal = comidas.get(indiceSeccion).get(mSelectedItems.get(0)).getArticulo();
                    String selectedName = comidas.get(indiceSeccion).get(mSelectedItems.get(0)).getNombre();
                    Log.d(TAG, "chosen " + selectedVal + " " + selectedName );
                    new UpdateMensajeMesaBuffet().execute(Filtro.getMesa(),txtBoxMensaje.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), getPalabras("Debe seleccionar un articulo"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        ////       alert.show();
        final android.app.AlertDialog dialogo = alert.create();
        dialogo.show();
// Initially disable the button
        if (mSelectedItems.size()>0) {
            ((android.app.AlertDialog) dialogo).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setEnabled(true);

        }else{
            ((android.app.AlertDialog) dialogo).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setEnabled(false);

        }
// Now set the textchange listener for edittext
        txtBoxMensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if edittext is empty
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                } else {
                    // Something into edit text. Enable the button.
                }

            }
        });

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
         AlertDialog.Builder builder = new AlertDialog.Builder(MesasActivity.this);
//         image = (Button)layout.findViewWithTag(Filtro.getMesa());
         Drawable d = LoadImageFromWebOperations(getMesaImagen(Filtro.getMesa()));

         builder.setIcon(d);
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
//        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
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
    private String getMesaImagen (String search){
//        Log.i("Mesa Valor: ",search);
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                return d.getMesaUrlimagen();
            }
        }
        return "";
    }
    private String getZonaNombre (String search){
//        Log.i("Mesa Valor: ",search);
        for(Zonas d : lzonas){
            if(d.getZonasZona() != null && d.getZonasZona().equals(search)){
                return d.getZonasNombre_Zona();
            }
        }
        return "***";
    }

    private String getMesaMensaje (String search){
//        Log.i("Mesa Valor: ",search);
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                return d.getMesaMensaje();
            }
        }
        return "";
    }
    private boolean getMesaT_mesa (String search, String value ){
        boolean success = false;
        Log.i("Mesa Buffet: ",search+" "+value);
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                if (d.getMesaT_mesa().equals(value)){
                    success = true;
                }else{
                    success = false;
                }
                break;
            }
        }
        return success;

    }

    private String getMesaComensales (String search){
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                return String.valueOf(d.getMesaComensales());
            }
        }
        return "0";
    }

    private void setMesas (String search, int value){
//        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
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
    private void setMesaModificado (String search, int value){
        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                d.setMesaModificado(value);
                break;
            }
        }
    }
    private void setMesaComensales (String search, int value){
//        Log.i("Mesa Valor: ",search+" "+Integer.toString(value));
        for(Mesa d : mesaplanningList){
            if(d.getMesaMesa() != null && d.getMesaMesa().contains(search)){
                d.setMesaComensales(value);
                break;
            }
        }
    }
    class UpdateActivoArticuloBuffet extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogAre = new ProgressDialog(MesasActivity.this);
            pDialogAre.setMessage(getPalabras("Modificar")+" "+getPalabras("Activo")+" Buffet..");
            pDialogAre.setIndeterminate(false);
            pDialogAre.setCancelable(true);
            pDialogAre.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // updating UI from Background Thread
            int success=0;
            try {
                String cSql = "";
                String xWhere = "";

                if(!(Filtro.getGrupo().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.GRUPO='" + Filtro.getGrupo() + "'";
                    } else {
                        xWhere += " AND are.GRUPO='" + Filtro.getGrupo() + "'";
                    }
                }
                if(!(Filtro.getEmpresa().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.EMPRESA='" + Filtro.getEmpresa() + "'";
                    } else {
                        xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
                    }
                }
                if(!(Filtro.getLocal().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.LOCAL='" + Filtro.getLocal() + "'";
                    } else {
                        xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
                    }
                }
                if(!(Filtro.getSeccion().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.SECCION='" + Filtro.getSeccion() + "'";
                    } else {
                        xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
                    }
                }
                xWhere += " AND are.ARTICULO='" + args[0] + "'";

                cSql += xWhere;
                if(cSql.equals("")) {
                    cSql="Todos";
                }
                Log.i("Sql Lista",cSql);

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("filtro", cSql);
                values.put("activo",args[1]);
                values.put("updated", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                jsonParserNew = new JSONParserNew();
                JSONObject json = jsonParserNew.makeHttpRequest(
                        url_updateare_activobuffet, "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

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
            pDialogAre.dismiss();
            if (success == 1) {
                Toast.makeText(MesasActivity.this, getPalabras("Modificar")+" "+getPalabras("Activo")+" BUFFET", Toast.LENGTH_SHORT).show();
                new UpdateMesaApertura().execute("1", "00");

                larticulobuffet = new ArrayList<Articulo>();
                URL_ARTICULOS = Filtro.getUrl() + "/RellenaListaARE.php";
                new GetAreAreBuffet().execute(URL_ARTICULOS);

            } else {
                Toast.makeText(MesasActivity.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Activo")+" BUFFET", Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }
    class UpdateNoActivoArticuloBuffet extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogAre = new ProgressDialog(MesasActivity.this);
            pDialogAre.setMessage(getPalabras("Modificar")+" "+getPalabras("Activo")+" Buffet..");
            pDialogAre.setIndeterminate(false);
            pDialogAre.setCancelable(true);
            pDialogAre.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // updating UI from Background Thread
            int success=0;
            try {
                String cSql = "";
                String xWhere = "";

                if(!(Filtro.getGrupo().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.GRUPO='" + Filtro.getGrupo() + "'";
                    } else {
                        xWhere += " AND are.GRUPO='" + Filtro.getGrupo() + "'";
                    }
                }
                if(!(Filtro.getEmpresa().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.EMPRESA='" + Filtro.getEmpresa() + "'";
                    } else {
                        xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
                    }
                }
                if(!(Filtro.getLocal().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.LOCAL='" + Filtro.getLocal() + "'";
                    } else {
                        xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
                    }
                }
                if(!(Filtro.getSeccion().equals(""))) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE are.SECCION='" + Filtro.getSeccion() + "'";
                    } else {
                        xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
                    }
                }

                xWhere += " AND are.TIPO_ARE='" + lparam.get(0).getDEFAULT_TIPO_ARTICULO_BUFFET().trim() + "'";

                cSql += xWhere;
                if(cSql.equals("")) {
                    cSql="Todos";
                }
                Log.i("Sql Lista",cSql);

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("filtro", cSql);
                values.put("activo",args[0]);
                values.put("updated", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                jsonParserNew = new JSONParserNew();
                JSONObject json = jsonParserNew.makeHttpRequest(
                        url_updateare_activobuffet, "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

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
            pDialogAre.dismiss();
            if (success == 1) {
                Toast.makeText(MesasActivity.this, getPalabras("Modificar")+" "+getPalabras("Activo")+" BUFFET", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MesasActivity.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Activo")+" BUFFET", Toast.LENGTH_SHORT).show();
                // failed to create product
            }
        }

    }
    class UpdateMensajeMesaBuffet extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MesasActivity.this);
            pDialog.setMessage(getPalabras("Modificar")+" "+getPalabras("Mensaje")+" Buffet..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
            // updating UI from Background Thread
            int success=0;
            try {
                String filtro = " WHERE mesas.GRUPO='" + Filtro.getGrupo() + "'";
                filtro += " AND mesas.EMPRESA='" + Filtro.getEmpresa() + "'";
                filtro += " AND mesas.LOCAL='" + Filtro.getLocal() + "'";
                filtro += " AND mesas.SECCION='" + Filtro.getSeccion() + "'";
                filtro += " AND mesas.CAJA='" + Filtro.getCaja() + "'";
                filtro += " AND mesas.RANGO='" + Filtro.getRango() + "'";
                filtro += " AND mesas.MESA='" + args[0] + "'";

                Calendar currentDate = Calendar.getInstance(); //Get the current date
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                String dateNow = formatter.format(currentDate.getTime());

                // Building Parameters
                ContentValues values = new ContentValues();
                values.put("filtro", filtro);
                values.put("mensaje",args[1]);
                values.put("updated", dateNow);
                values.put("usuario", Filtro.getUsuario());
                values.put("ip",getLocalIpAddress());

                // getting JSON Object
                // Note that create product url accepts POST method
                jsonParserNew = new JSONParserNew();
                JSONObject json = jsonParserNew.makeHttpRequest(
                        url_updatemesa_mensaje, "POST", values);

                // check log cat fro response
                //            Log.d("Create Response", json.toString());

                // check for success tag

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
            pDialog.dismiss();
            if (success == 1) {
                Toast.makeText(MesasActivity.this, getPalabras("Modificar")+" "+getPalabras("Mensaje")+" BUFFET", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MesasActivity.this, "ERROR NO "+getPalabras("Modificar")+" "+getPalabras("Mensaje")+" "+getPalabras("Mesa"), Toast.LENGTH_SHORT).show();
                // failed to create product
            }
            new UpdateActivoArticuloBuffet().execute(selectedVal,"1");

        }

    }
    public class GetAreAreBuffet extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
            super.onPreExecute();
            pDialogAre = new ProgressDialog(MesasActivity.this);
            pDialogAre.setMessage(ActividadPrincipal.getPalabras("Cargando")+" "+ActividadPrincipal.getPalabras("Articulos")+" BUFFET...");
            pDialogAre.setIndeterminate(false);
            pDialogAre.setCancelable(true);
            pDialogAre.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
//            Integer result = 0;
            String cSql = "";
            String xWhere = "";
            if(!(Filtro.getGrupo().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.GRUPO='" + Filtro.getGrupo() + "'";
                } else {
                    xWhere += " AND are.GRUPO='" + Filtro.getGrupo() + "'";
                }
            }
            if(!(Filtro.getEmpresa().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.EMPRESA='" + Filtro.getEmpresa() + "'";
                } else {
                    xWhere += " AND are.EMPRESA='" + Filtro.getEmpresa() + "'";
                }
            }
            if(!(Filtro.getLocal().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.LOCAL='" + Filtro.getLocal() + "'";
                } else {
                    xWhere += " AND are.LOCAL='" + Filtro.getLocal() + "'";
                }
            }
            if(!(Filtro.getSeccion().equals(""))) {
                if (xWhere.equals("")) {
                    xWhere += " WHERE are.SECCION='" + Filtro.getSeccion() + "'";
                } else {
                    xWhere += " AND are.SECCION='" + Filtro.getSeccion() + "'";
                }
            }
            if (xWhere.equals("")) {
                xWhere += " WHERE are.ACTIVOBUFFET=1";
            } else {
                xWhere += " AND are.ACTIVOBUFFET=1";
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
                    Log.i("Longitud Antes: ",Integer.toString(larticulobuffet.size()));
                    for (Iterator<Articulo> it = larticulobuffet.iterator(); it.hasNext();){
                        Articulo articulo = it.next();
                        it.remove();
                    }

                    Log.i("Longitud Despues: ",Integer.toString(larticulobuffet.size()));

                    parseResultArticulosBuffet(response.toString());
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
            pDialogAre.dismiss();
            if (result == 1) {
                Log.i("AREAREBUFFET", "OK");
            } else {
                Log.e("AREAREBUFFET", "Failed to fetch data!");
            }
        }
    }
    private void parseResultArticulosBuffet(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            Log.i("Longitud Datos: ",Integer.toString(posts.length()));
            for (int ii = 0; ii < posts.length(); ii++) {
                JSONObject post = posts.optJSONObject(ii);
                Articulo articuloItem = new Articulo(post.optInt("ID"),
                        post.optString("ARTICULO"),
                        getPalabras(post.optString("NOMBRE").trim()),
                        post.optString("TIPOPLATO"),
                        getPalabras(post.optString("NOMBRE_PLATO").trim()),
                        Float.parseFloat(post.optString("PRECIO")),
                        post.optString("CANTIDAD"),
                        Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim(),
                        post.optString("TIPO_ARE"),
                        post.optString("TIPO_IVA"),
                        post.optInt("TIVA_ID"),
                        post.optInt("DEPENDIENTETIPOPLATOMAESTRO"),
                        post.optInt("EXCLUYENTEBUFFET"),
                        post.optInt("ACTIVOBUFFET"),
                        post.optInt("SW_TIPO_ARE"),
                        post.optInt("SW_SUMA_PRECIO_INDIVIDUAL")

                );
                Log.i("ImagenUrl",articuloItem.getArticuloUrlimagen());

                larticulobuffet.add(articuloItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to get all food mesas
     */
    public class GetMesas extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MesasActivity.this);
            pDialog.setMessage(ActividadPrincipal.getPalabras("Lectura")+" "+ActividadPrincipal.getPalabras("Mesas")+"..");
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

            xWhere += " AND mesas.MESA<>'"+lparam.get(0).getDEFAULT_ESTADO_TODOS_MESA().trim() +"'"; // MESA 00 NO DEBE TENERLA EN CUENTA
            xWhere += " AND mesas.ACTIVO="+lparam.get(0).getDEFAULT_VALOR_ON_ACTIVO();
            xWhere += " AND mesas.ZONA='"+params[1]+"'";

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
            pDialog.dismiss();
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

                Mesa mesaItem = new Mesa();
                mesaItem.setMesaNombre_Mesas(post.optString("NOMBRE"));
                mesaItem.setMesaMesa(post.optString("MESA"));
                mesaItem.setMesaApertura(post.optInt("APERTURA"));
                mesaItem.setMesaUrlimagen(Filtro.getUrl() + "/image/" + post.optString("IMAGEN").trim() );
                mesaItem.setMesaXCoordenate(BigDecimal.valueOf(post.getDouble("XCOORDENATE")).floatValue());
                mesaItem.setMesaYCoordenate(BigDecimal.valueOf(post.getDouble("YCOORDENATE")).floatValue());
                mesaItem.setMesaPedidos(post.getInt("PEDIDOS"));
                mesaItem.setMesaFacturas(post.getInt("FACTURAS"));
                mesaItem.setMesaModificado(0);
                mesaItem.setMesaComensales(post.getInt("COMENSALES"));
                mesaItem.setMesaT_mesa(post.optString("T_MESA"));
                mesaItem.setMesaMensaje(post.optString("MENSAJE"));
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
            pDialog = new ProgressDialog(MesasActivity.this);
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
            final String valuecomensales = args[1];
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
                        values.put("comensales",valuecomensales);
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
                            image = (Button)layout.findViewWithTag(Filtro.getMesa());
//                            image.findViewWithTag(Filtro.getMesa());
                            if (valueapertura.equals("1")) {
                                // ABRIR MESA
                                for (int i = 0; i < mesaplanningList.size(); i++) {
                                     if(mesaplanningList.get(i).getMesaMesa().equals(Filtro.getMesa())) {
                                         model = mesaplanningList.get(i);
                                         break;
                                     }
                                }
                                setMesas(Filtro.getMesa(),1);
                                Drawable d = LoadImageFromWebOperations(model.getMesaUrlimagen());

                                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                                Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, Filtro.getOpmesas(), Filtro.getOpmesas(), true);
                                Drawable d1 = new BitmapDrawable(getResources(),bMapScaled); //Converting bitmap into drawable

                                Drawable[] layers2 = {image.getBackground()};
                                Drawable[] layers3 = {image.getBackground()};

                                dpedidos = new DecoratedTextViewDrawable(image, layers2, model.getMesaPedidos(), "PEDIDOS", model.getMesaMesa(),Filtro.getOppedidodirectomesa());
                                dfacturas = new DecoratedTextViewDrawable(image, layers3, model.getMesaFacturas(), "FACTURAS", model.getMesaMesa(),Filtro.getOpfacturadirectomesa());

                                image.setCompoundDrawablesWithIntrinsicBounds( (model.getMesaApertura()>0 ? dpedidos : null), (model.getMesaApertura()>0 ? dfacturas : null), null, d1 );

                                image.setAlpha(1.0f); // abro mesa

                                Snackbar.make(layout, ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("ABIERTA"), Snackbar.LENGTH_SHORT).show();
                                itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())+1));
                                if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                    itemmesas.setTextColor(Filtro.getColorItemZero());
                                } else {
                                    itemmesas.setTextColor(Filtro.getColorItem());
                                }

                            }else{
                                // CERRAR MESA
                                for (int i = 0; i < mesaplanningList.size(); i++) {
                                    if(mesaplanningList.get(i).getMesaMesa().equals(Filtro.getMesa())) {
                                        model = mesaplanningList.get(i);
                                        break;
                                    }
                                }
                                /// Comprobar si es MESA DE BUFFET
                                if (getMesaT_mesa(Filtro.getMesa(), lparam.get(0).getDEFAULT_TIPO_MESA_BUFFET())) {
                                    new UpdateNoActivoArticuloBuffet().execute("0"); // Pone todos los articulos tipo buffet como no activos
                                }
                                ///////////////////////////////////////////////////////////////////////////////////
                                setMesas(Filtro.getMesa(),0);
                                Drawable d = LoadImageFromWebOperations(model.getMesaUrlimagen());

                                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                                Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, Filtro.getOpmesas(), Filtro.getOpmesas(), true);
                                Drawable d1 = new BitmapDrawable(getResources(),bMapScaled); //Converting bitmap into drawable

                                Drawable[] layers2 = {image.getBackground()};
                                Drawable[] layers3 = {image.getBackground()};

                                dpedidos = new DecoratedTextViewDrawable(image, layers2, model.getMesaPedidos(), "PEDIDOS", model.getMesaMesa(),Filtro.getOppedidodirectomesa());
                                dfacturas = new DecoratedTextViewDrawable(image, layers3, model.getMesaFacturas(), "FACTURAS", model.getMesaMesa(),Filtro.getOpfacturadirectomesa());

                                image.setCompoundDrawablesWithIntrinsicBounds( (model.getMesaApertura()>0 ? dpedidos : null), (model.getMesaApertura()>0 ? dfacturas : null), null, d1 );

                                image.setAlpha(0.3f); // cierro mesa
                                Snackbar.make(layout, ActividadPrincipal.getPalabras("Mesa")+" "+ActividadPrincipal.getPalabras("CERRADA"), Snackbar.LENGTH_SHORT).show();

                                itemmesas.setText(Integer.toString(Integer.parseInt(itemmesas.getText().toString())-1));
                                if (Integer.parseInt(itemmesas.getText().toString()) == 0) {
                                    itemmesas.setTextColor(Filtro.getColorItemZero());
                                } else {
                                    itemmesas.setTextColor(Filtro.getColorItem());
                                }
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
            pDialog = new ProgressDialog(MesasActivity.this);
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

                totalfactura = "0.00";
                obsfactura = "";

                String space01 = new String(new char[01]).replace('\0', ' ');
                String myText= String.format("%1$,.2f", Float.parseFloat(totalfactura));
                myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                String newText="";
                for (int ii = 0; ii < (10-myText.length()); ii++) {
                    newText+=space01;
                }
                newText +=myText;
                totalfactura=Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo();

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
 ////               Snackbar.make(layout, ActividadPrincipal.getPalabras("Factura")+" "+ActividadPrincipal.getPalabras("Generada")+" "+Filtro.getSerie()+" "+Integer.toString(Filtro.getFactura()), Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Creado Factura ", Toast.LENGTH_SHORT).show();
                // SALIR ACTIVITY
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Mesa",Filtro.getMesa());
                returnIntent.putExtra("Action","ADD");
                returnIntent.putExtra("Tabla","ftp");
                returnIntent.putExtra("total",totalfactura);
                returnIntent.putExtra("obs",obsfactura);
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
            pDialog = new ProgressDialog(MesasActivity.this);
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

////               Log.i("CreaPDD","Sale Crear Pedido");
////                Snackbar.make(layout, "Documento Pedido Generado "+Integer.toString(Filtro.getPedido()), Snackbar.LENGTH_SHORT).show();
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
            pDialog = new ProgressDialog(MesasActivity.this);
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
            pDialog = new ProgressDialog(MesasActivity.this);
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
                if(Filtro.getUrl().contains("sqlsrv")) {
                    if (xWhere.equals("")) {
                        xWhere += " WHERE ftp.FECHA=CONVERT(DATETIME, '" +Filtro.getFechaapertura() + "', 120)";
                    } else {
                        xWhere += " AND ftp.FECHA=CONVERT(DATETIME, '" + Filtro.getFechaapertura() + "', 120)";
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
                    new UpdateMesaApertura().execute("0","0");
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
            pDialog = new ProgressDialog(MesasActivity.this);
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
            pDialog = new ProgressDialog(MesasActivity.this);
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
                        values.put("estado",lparam.get(0).getDEFAULT_ESTADO_OPEN_FACTURA());
                        values.put("serie",Filtro.getSerie());
                        values.put("factura",Long.toString(maxDate));
                        values.put("t_fra",lparam.get(0).getDEFAULT_TIPO_COBRO_OPEN_FACTURA());
                        values.put("empleado",Filtro.getEmpleado());
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

                            totalfactura = json.getString("total");
                            obsfactura = json.getString("obs");

                            String space01 = new String(new char[01]).replace('\0', ' ');
                            String myText= String.format("%1$,.2f", Float.parseFloat(totalfactura));
                            myText = myText.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                            myText = myText.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                            String newText="";
                            for (int ii = 0; ii < (10-myText.length()); ii++) {
                                newText+=space01;
                            }
                            newText +=myText;
                            totalfactura=Html.fromHtml(newText.replace(" ", "&nbsp;&nbsp;")).toString()+" "+ Filtro.getSimbolo();

                            Snackbar.make(layout, ActividadPrincipal.getPalabras("Factura")+" "+ActividadPrincipal.getPalabras("Generada")+" "+Filtro.getSerie()+" "+Integer.toString(Filtro.getFactura()), Snackbar.LENGTH_SHORT).show();
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
                returnIntent.putExtra("Total", totalfactura);
                returnIntent.putExtra("Obs", obsfactura);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }

    }
    class CalculaCabecera extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MesasActivity.this);
            pDialog.setMessage("Calcula Cabecera..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        @Override
        protected Integer doInBackground(String... args) {
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
                                xWhere += "lft.GRUPO='" + Filtro.getGrupo() + "'";
                            } else {
                                xWhere += " AND lft.GRUPO='" + Filtro.getGrupo() + "'";
                            }
                        }
                        if(!(Filtro.getEmpresa().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                            } else {
                                xWhere += " AND lft.EMPRESA='" + Filtro.getEmpresa() + "'";
                            }
                        }
                        if(!(Filtro.getLocal().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.LOCAL='" + Filtro.getLocal() + "'";
                            } else {
                                xWhere += " AND lft.LOCAL='" + Filtro.getLocal() + "'";
                            }
                        }
                        if(!(Filtro.getSeccion().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.SECCION='" + Filtro.getSeccion() + "'";
                            } else {
                                xWhere += " AND lft.SECCION='" + Filtro.getSeccion() + "'";
                            }
                        }
                        if(!(Filtro.getCaja().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.CAJA='" + Filtro.getCaja() + "'";
                            } else {
                                xWhere += " AND lft.CAJA='" + Filtro.getCaja() + "'";
                            }
                        }
                        if(!(Filtro.getSerie().equals(""))) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.SERIE='" + Filtro.getSerie() + "'";
                            } else {
                                xWhere += " AND lft.SERIE='" + Filtro.getSerie() + "'";
                            }
                        }

                        if(!(Filtro.getFactura()==0)) {
                            if (xWhere.equals("")) {
                                xWhere += "lft.FACTURA=" + Filtro.getFactura();
                            } else {
                                xWhere += " AND lft.FACTURA=" + Filtro.getFactura();
                            }
                        }


                        filtro+=xWhere;

                        Calendar currentDate = Calendar.getInstance(); //Get the current date
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
                        String dateNow = formatter.format(currentDate.getTime());

                        // Building Parameters
                        ContentValues values = new ContentValues();
                        values.put("filtro",filtro);
                        values.put("tabla","ftp");
                        values.put("lintabla","lft");
                        values.put("ivaincluido",Filtro.getIvaIncluido());
                        values.put("updated", dateNow);
                        values.put("creado", dateNow);
                        values.put("usuario", Filtro.getUsuario());
                        values.put("ip",getLocalIpAddress());

                        // getting JSON Object
                        // Note that create product url accepts POST method
                        jsonParserNew = new JSONParserNew();
                        JSONObject json = jsonParserNew.makeHttpRequest(url_update_cabecera,
                                "POST", values);

                        // check log cat fro response
                        //            Log.d("Create Response", json.toString());

                        // check for success tag

                        success = json.getInt(TAG_SUCCESS);

                        if (success == 1) {
                            Toast.makeText(MesasActivity.this, "OK UPDATE CABECERA ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MesasActivity.this, "ERROR NO UPDATE CABECERA ", Toast.LENGTH_SHORT).show();
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

            //Calcular Items
            mSerialExecutorActivity = new ActividadPrincipal.MySerialExecutor(MesasActivity.this);

            CountTable="pdd";
            url_count = Filtro.getUrl()+"/CountPddOpen.php";
            mSerialExecutorActivity.execute(null);

            CountTable="ftp";
            url_count = Filtro.getUrl()+"/CountFtpOpen.php";
            mSerialExecutorActivity.execute(null);

        }

    }


}
