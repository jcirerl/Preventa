package tpv.cirer.com.marivent.herramientas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Articulos;
import tpv.cirer.com.marivent.modelo.ArticulosNew;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.articulosListNew;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.comidas;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.getPalabras;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.larticulo;
import static tpv.cirer.com.marivent.ui.ActividadPrincipal.lcategoria;

/**
 * Created by JUAN on 06/09/2017.
 */

public class ArticulosListArrayAdapterNew extends RecyclerView.Adapter<ArticulosRowHolder> {

    private List<ArticulosNew> mArticulosNew;
    private Context mContext;
    private OnHeadlineSelectedListenerArticulos mCallback;


    public ArticulosListArrayAdapterNew(Context context, ArrayList<ArticulosNew> ArticulosNew) {
        this.mArticulosNew = ArticulosNew;
        this.mContext = context;

        try {
            this.mCallback = ((OnHeadlineSelectedListenerArticulos) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public ArticulosRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View vArticulosNew = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_articuloscode_row_new, null);
        //  ArticulosRowHolder mh = new ArticulosRowHolder(v);
        ArticulosRowHolder mhArticulosNew = new ArticulosRowHolder(vArticulosNew, new ArticulosRowHolder.IMyArticulosViewHolderClicks() {
            public void onPotato(final View caller,
                                 final ImageView imageArticulo,
                                 final String action,
                                 final String codigoArticulo,
                                 final String nombreArticulo,
                                 final String positionArticulo) {
                Log.i("onPotato",action + " " + codigoArticulo + " " + nombreArticulo);
                switch (action){
                    case "IMAGEN":
                        try {
                            mCallback.onArticulosNewSelected(nombreArticulo);
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;
                    case "CHECKBOX":
                        try {
                            mCallback.onArticulosNewChecked(codigoArticulo,Integer.parseInt(nombreArticulo));
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;
                    case "SPINNER":
                        try {
                            mCallback.onArticulosNewTipoplato(codigoArticulo,Integer.parseInt(nombreArticulo));
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        break;

                    case "BUTTON":
                        Log.i("Articulo Are : ", codigoArticulo+" "+nombreArticulo+" "+positionArticulo);
                        String ArticuloCodigoGrupo = "";
                        String ArticuloNombreGrupo ="";
                        final String TAG = "Articulo";
                        int x=0;

                        for(x=0;x<lcategoria.size();x++) {
                            if (lcategoria.get(x).getCategoriaTipo_are().equals(codigoArticulo.trim())) {
                                ArticuloCodigoGrupo = lcategoria.get(x).getCategoriaTipo_are();
                                ArticuloNombreGrupo = lcategoria.get(x).getCategoriaNombre_tipoare();
                                Log.i("TIPOARE: ", Integer.toString(x) + " " + lcategoria.get(x).getCategoriaTipo_are());
                                break;
                            }
                        }
                        if (!ArticuloCodigoGrupo.equals("")) {
                            Log.i("TIPOARE ", "OK");
                            final int indiceCategoria = x;
                            String[] articulos = new String[comidas.get(indiceCategoria).size()];
                            final String space01 = new String(new char[01]).replace('\0', ' ');

                            List<Articulos> articulosList = new ArrayList<Articulos>();

                            for (int i = 0; i < comidas.get(indiceCategoria).size(); i++) {

                                articulos[i] =  comidas.get(indiceCategoria).get(i).getNombre();
///            Drawables d = LoadImageFromWebOperations(larticulo.get(i).getArticuloUrlimagen());

                                articulosList.add(new Articulos(articulos[i], "ARTICULO" ,comidas.get(indiceCategoria).get(i).getUrlimagen()));

                            }
                            ArrayAdapter<Articulos> adapter = new ArticulosListArrayAdapter((Activity) caller.getContext(), articulosList);

                            final ArrayList<Integer> mSelectedItemsTipoare = new ArrayList();  // Where we track the selected items

                            boolean[] checkedItemsTipoare = new boolean[articulos.length];
                            for (int i = 0; i < articulos.length; i++) {
                                if (i==0) {
                                    checkedItemsTipoare[i] = true;
                                    mSelectedItemsTipoare.add(0);

                                } else {
                                    checkedItemsTipoare[i] = false;
                                }
                            }


                            AlertDialog.Builder builder = new AlertDialog.Builder(caller.getContext());

                            builder.setIcon(imageArticulo.getDrawable());
                            builder.setTitle(getPalabras("Tipo")+" "+ getPalabras("Articulo")+" "+ArticuloNombreGrupo );
                            // Specify the list array, the items to be selected by default (null for none),
                            // and the listener through which to receive callbacks when items are selected

                            builder.setMultiChoiceItems(articulos, checkedItemsTipoare,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which,
                                                            boolean isChecked) {
                                            if (isChecked) {
                                                // If the user checked the item, add it to the selected items
                                                mSelectedItemsTipoare.add(which);
                                            } else if (mSelectedItemsTipoare.contains(which)) {
                                                // Else, if the item is already in the array, remove it
                                                mSelectedItemsTipoare.remove(Integer.valueOf(which));
                                            }
                                            Log.i("which",String.valueOf(which));
                                            if (mSelectedItemsTipoare.size()>0 && mSelectedItemsTipoare.size()<2) {
                                                ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                        .setEnabled(true);

                                            }else{
                                                if (mSelectedItemsTipoare.size()>1) {
                                                    Toast.makeText(caller.getContext(), getPalabras("Solo puede marcar un Articulo"), Toast.LENGTH_SHORT).show();
                                                }
                                                ((android.app.AlertDialog) dialog).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                        .setEnabled(false);

                                            }

                                        }
                                    });

                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (mSelectedItemsTipoare.size()>0) {
                                        for (ArticulosNew articulo : articulosListNew) { //iterate element by element in a list
                                            if (articulo.getCodigo().equals(codigoArticulo)) {
                                                articulo.setCodigo(comidas.get(indiceCategoria).get(mSelectedItemsTipoare.get(0)).getArticulo());
                                                articulo.setName(comidas.get(indiceCategoria).get(mSelectedItemsTipoare.get(0)).getNombre());
                                                articulo.setPreu(comidas.get(indiceCategoria).get(mSelectedItemsTipoare.get(0)).getPrecio());
                                                articulo.setUrlimagen(comidas.get(indiceCategoria).get(mSelectedItemsTipoare.get(0)).getUrlimagen());
                                                articulo.setChecked(true);
                                                larticulo.get(Integer.parseInt(positionArticulo)).setArticuloArticulo(articulo.getCodigo());
                                                larticulo.get(Integer.parseInt(positionArticulo)).setArticuloNombre(articulo.getName());
                                                //////////////////////////////////////////////////////////
                                                String myTextPreu = String.format("%1$,.2f", articulo.getPreu());
                                                myTextPreu = myTextPreu.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                                myTextPreu = myTextPreu.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                                //////////////////////////////////////////////////////////

                                                String myTextNombreArticulo = String.format("%1$-32s", articulo.getName());
                                                myTextNombreArticulo = myTextNombreArticulo.replaceAll("^\\s+", ""); // Quitamos espacios izquierda
                                                myTextNombreArticulo = myTextNombreArticulo.replaceAll("\\s+$", ""); // Quitamos espacios derecha
                                                articulo.setName(StringUtils.rightPad(myTextNombreArticulo,32,space01)+" ("+
                                                        myTextPreu.toString()+" "+Filtro.getSimbolo()+")");

                                                Log.i("Articulo NOT OK: ", articulo.getName() + " " + articulo.getCodigo());
                                                break;
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }else{
                                        Toast.makeText(caller.getContext(), getPalabras("Debe seleccionar un articulo"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            builder.setNegativeButton(getPalabras("Cancelar"), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });
                            ////       alert.show();
                            final android.app.AlertDialog dialogo = builder.create();
                            dialogo.show();
// Initially disable the button
                            if (mSelectedItemsTipoare.size()>0) {
                                ((android.app.AlertDialog) dialogo).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(true);

                            }else{
                                ((android.app.AlertDialog) dialogo).getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);

                            }
                        } else {
                            Log.e(TAG, "Failed to fetch data!");
                            Toast.makeText(caller.getContext(), getPalabras("No existe")+" "+getPalabras("Tipo")+" "+getPalabras("Articulo")+" BUFFET", Toast.LENGTH_SHORT).show();
                        }
/*
                        try {
                            mCallback.onArticulosNewTipoare(codigoArticulo,nombreArticulo);
                        } catch (ClassCastException exception) {
                            // do something
                        }
*/                        break;
                }

            };

        });
        return mhArticulosNew;
    }

    @Override
    public void onBindViewHolder(ArticulosRowHolder ArticulosRowHolder, int i) {
        String myText;
        Log.i("recview comida fuera", Integer.toString(mArticulosNew.size()));
        try {
            ArticulosNew ArticulosNew = mArticulosNew.get(i);
            ArticulosRowHolder.bind(ArticulosNew);
            Log.i("recview seleccionado", ArticulosNew.getName());
/*            Picasso.with(mContext)
                    .load(ArticulosNew.getUrlimagen())
                    .resize(60, 60)
                    .centerCrop()
                    .into(ArticulosRowHolder.imagen);
*/            Glide.with(mContext)
                    .load(ArticulosNew.getUrlimagen())
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(60,60)
                    .into(ArticulosRowHolder.imagen);


        } catch (Exception e) {
            Log.i("recview Articulos", Integer.toString(mArticulosNew.size()));
            // do something
        }

    }

    @Override
    public int getItemCount() {
        return (null != mArticulosNew ? mArticulosNew.size() : 0);
    }

    public Object getItem(int pos) {
        return mArticulosNew.get(pos);
    }

    public long getItemId(int pos) {
        return mArticulosNew.indexOf(getItem(pos));
    }

    ///////////////////////////////////////////
// La actividad contenedora debe implementar esta interfaz
     public interface OnHeadlineSelectedListenerArticulos {
        void onArticulosNewSelected(String nombre);
        void onArticulosNewChecked(String codigo, int check);
        void onArticulosNewTipoplato(String codigo, int pos);
        void onArticulosNewTipoare(String codigo, String name);

    }

    public void add(ArticulosNew ArticulosNew, int position) {
        mArticulosNew.add(position, ArticulosNew);
        notifyItemInserted(position);
    }

    public void remove(ArticulosNew ArticulosNew) {
        int position = mArticulosNew.indexOf(ArticulosNew);
        mArticulosNew.remove(position);
        notifyItemRemoved(position);
    }

}
