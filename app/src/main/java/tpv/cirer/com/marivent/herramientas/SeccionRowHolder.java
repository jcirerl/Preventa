package tpv.cirer.com.marivent.herramientas;

/**
 * Created by JUAN on 24/06/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.modelo.Seccion;

//import com.cirer.aguas.principal.R;

public class SeccionRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView icon;
    public TextView NombreSeccion;
    public TextView DatosSeccion;
    public ImageView ivaincluido;
    public TextView IdSeccion;
    public IMyViewHolderClicks mListener;

    public SeccionRowHolder(View view, IMyViewHolderClicks listener) {
        super(view);
        mListener = listener;
        this.IdSeccion = (TextView) view.findViewById(R.id.pid);
        this.icon = (ImageView) view.findViewById(R.id.icon);
        this.NombreSeccion = (TextView) view.findViewById(R.id.row_toptext);
        this.DatosSeccion = (TextView) view.findViewById(R.id.row_bottomtext);
        this.ivaincluido = (ImageView) view.findViewById(R.id.ivaincluido);
        this.icon.setOnClickListener(this);
        view.setOnClickListener(this);

    }
    public void bind(Seccion Seccion) {
        NombreSeccion.setText(Seccion.getSeccionNombre_Sec());
        DatosSeccion.setText(Seccion.getSeccionSeccion());
        IdSeccion.setText(Integer.toString(Seccion.getSeccionId()));
    }
    @Override
    public void onClick(View v) {
              mListener.onPotato(
                      v,
                      String.valueOf(this.IdSeccion.getText()),
                      String.valueOf(this.NombreSeccion.getText()),
                      String.valueOf(this.DatosSeccion.getText()),
                      String.valueOf(this.icon.getTag().toString()),
                      String.valueOf(this.ivaincluido.getTag().toString())
              );
    }

    public static interface IMyViewHolderClicks {
        public void onPotato(View caller, String idSeccion, String nombreSeccion, String datosSeccion, String Icon, String Ivaincluido);
        public void onTomato(ImageView callerImage);
    }
}