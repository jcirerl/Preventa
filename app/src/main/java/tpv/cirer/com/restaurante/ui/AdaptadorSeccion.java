package tpv.cirer.com.restaurante.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tpv.cirer.com.restaurante.R;
import tpv.cirer.com.restaurante.herramientas.Filtro;
import tpv.cirer.com.restaurante.herramientas.SeccionRowHolder;
import tpv.cirer.com.restaurante.modelo.Seccion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import com.cirer.aguas.herramientas.SeccionRowHolder;

//import com.cirer.aguas.herramientas.Seccion;

public class AdaptadorSeccion extends RecyclerView.Adapter<SeccionRowHolder> {

    private List<Seccion> mSeccion;
    private Context mContext;
    private OnHeadlineSelectedListener mCallback;


    public AdaptadorSeccion(Context context, List<Seccion> Seccion) {
        this.mSeccion = Seccion;
        this.mContext = context;
        try {
            this.mCallback = ((OnHeadlineSelectedListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public SeccionRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View vSeccion = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_seccion_old, null);
        //  SeccionRowHolder mh = new SeccionRowHolder(v);
        SeccionRowHolder mhSeccion = new SeccionRowHolder(vSeccion, new SeccionRowHolder.IMyViewHolderClicks() {
            public void onPotato(View caller, final String idSeccion, String nombreSeccion, String datosSeccion, String Icon, String Ivaincluido) {
                final String xSeccion = nombreSeccion.toString();

                final String seccion = datosSeccion.toString();

                final int apertura = Integer.parseInt(Icon);

                final int ivaincluido = Integer.parseInt(Ivaincluido);

                String sOpcion= "";
                if (Filtro.getTag_fragment().equals("OPEN")) {sOpcion = "Aperturar";}
                if (Filtro.getTag_fragment().equals("CLOSE")) {sOpcion = "Cerrar";}

                Log.d("Poh-tah-tos", xSeccion + " " + seccion + " " + Icon + " " + Ivaincluido);
                AlertDialog.Builder dialog = new AlertDialog.Builder(caller.getContext()/*,R.style.MyAlertDialogStyle*/);
                dialog.setTitle("Seccion: "+nombreSeccion);
                dialog.setMessage("Codigo Seccion: "+datosSeccion + "\nID SECCION: "+ idSeccion + "\nIva Incluido: "+ (0 != ivaincluido ? "SI" : "NO")+ "\nAbierta: "+ (0 != apertura ? "SI" : "NO") );
                dialog.setIcon(R.drawable.mark_as_read);
                dialog.setPositiveButton("Consultar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mCallback.onSeccionSelected(Integer.parseInt(idSeccion));
                        } catch (ClassCastException exception) {
                            // do something
                        }
/*                        Intent intent = new Intent(mContext, AllLecturasActivity.class);
                        intent.putExtra("Seccion", Seccion);
                        mContext.getApplicationContext().startActivityForResult(intent,100);
*/                        dialog.cancel();
                    }
                });
                dialog.setNegativeButton(sOpcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mCallback.onSeccionSelected(Integer.parseInt(idSeccion));
                        } catch (ClassCastException exception) {
                            // do something
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();

            };
            public void onTomato(ImageView callerImage) { Log.d("To-m8-tohs",""); }
        });
        return mhSeccion;
    }

    @Override
    public void onBindViewHolder(SeccionRowHolder SeccionRowHolder, int i) {

        Log.i("recview Seccions fuera",Integer.toString(mSeccion.size()));
        try {
            /*para filtro*/
            final Seccion model = mSeccion.get(i);
            SeccionRowHolder.bind(model);
            ////////////////////////////////////////////////////////
            Seccion Seccion = mSeccion.get(i);
            if (Seccion.getSeccionApertura()==1) {
                Picasso.with(mContext).load(R.drawable.house_green)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(SeccionRowHolder.icon);
                SeccionRowHolder.icon.setTag("1");

            }else{
                Picasso.with(mContext).load(R.drawable.house_red)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(SeccionRowHolder.icon);
                SeccionRowHolder.icon.setTag("0");

            }
            SeccionRowHolder.IdSeccion.setText(Html.fromHtml(Integer.toString(Seccion.getSeccionId())));
            SeccionRowHolder.NombreSeccion.setText(Html.fromHtml(Seccion.getSeccionNombre_Sec()));
            SeccionRowHolder.DatosSeccion.setText(Html.fromHtml(Seccion.getSeccionSeccion()));
            SeccionRowHolder.NombreSeccion.setTextColor(Color.BLACK);
            SeccionRowHolder.DatosSeccion.setTextColor(Color.BLACK);
            SeccionRowHolder.NombreSeccion.setTextSize(16);
            SeccionRowHolder.DatosSeccion.setTextSize(16);

            if (Seccion.getSeccionIvaIncluido()==1) {
                Picasso.with(mContext).load(R.drawable.no)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(SeccionRowHolder.ivaincluido);
                SeccionRowHolder.ivaincluido.setTag("0");
            }else{
                Picasso.with(mContext).load(R.drawable.ok)
                        .error(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(SeccionRowHolder.ivaincluido);
                SeccionRowHolder.ivaincluido.setTag("1");

            }
        } catch (Exception e) {
            Log.i("recview Seccions dentro",Integer.toString(mSeccion.size()));
            // do something
        }

    }

    @Override
    public int getItemCount() {
        return (null != mSeccion ? mSeccion.size() : 0);
    }
    /*para filtro*/
    public void setFilter(List<Seccion> Seccions) {
        mSeccion = new ArrayList<>();
        mSeccion.addAll(Seccions);
        notifyDataSetChanged();
    }
    ///////////////////////////////////////////
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onSeccionSelected(int id);

    }
    public void add(Seccion Seccion, int position) {
        mSeccion.add(position, Seccion);
        notifyItemInserted(position);
    }

    public void remove(Seccion Seccion) {
        int position = mSeccion.indexOf(Seccion);
        mSeccion.remove(position);
        notifyItemRemoved(position);
    }
    public void addSeccions(List<Seccion> list) {
        mSeccion.addAll(0, list);
        notifyDataSetChanged();
    }

}